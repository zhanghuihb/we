package us.codecraft.webmagic.burton.processor.爬取大众点评美食商家;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬取大众点评美食商家
 *
 * @author Tainy
 * @date 2018/5/31 20:04
 */
public class DianPingFoodShopProcessor implements PageProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(DianPingFoodShopProcessor.class);

    private static Spider spider = Spider.create(new DianPingFoodShopProcessor());

    private static volatile int refush = 999;

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    /** 一、抓取网站的相关配置，包括编码、抓取间隔和重试次数等 */
    private Site site = Site.me()
            .setCharset("utf-8")
            .setTimeOut(30000)
            .setSleepTime(2000)
            .setRetryTimes(3)
            .setCycleRetryTimes(3)
//            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3236.0 Safari/537.36")
//            .addCookie("Cookie", "_lxsdk_cuid=163b597d5b6a0-081ff62ff5ed33-77143d4c-1fa400-163b597d5b7c8; _lxsdk=163b597d5b6a0-081ff62ff5ed33-77143d4c-1fa400-163b597d5b7c8; _hc.v=652da838-9c5a-3a68-8ebd-a5ce942bf580.1527760017; s_ViewType=10; cy=1; cye=shanghai; aburl=1; _adwc=169583271; _adwp=169583271.6343206449.1527852099.1527852099.1527852099.1; Hm_lvt_dbeeb675516927da776beeb1d9802bd4=1527852100; Hm_lpvt_dbeeb675516927da776beeb1d9802bd4=1527852100; wed_user_path=6698|0; _lx_utm=utm_source%3DBaidu%26utm_medium%3Dorganic; wedchatguest=g90742704252105288; __mta=219043110.1527852107014.1527852107014.1527852107022.2; _lxsdk_s=163bb95a938-87f-6b8-777%7C%7C132")
//            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; Trident/7.0; rv:11.0) like Gecko")
            .addCookie("Cookie", "_hc.v=79c2bbd5-1cec-4a71-eb00-44dd87c75665.1527837885; aburl=1; _lxsdk_cuid=163ba3c05ecc8-0422efa668a1ca8-530c3156-1fa400-163ba3c05ecc8; s_ViewType=10; _lxsdk=163ba3c05ecc8-0422efa668a1ca8-530c3156-1fa400-163ba3c05ecc8; cye=shanghai; _lxsdk_s=163ce95bf36-0e0-b5e-69%7C%7C34; cy=1")
            .addHeader("Accept","text/html, application/xhtml+xml, image/jxr, */*");

    /** 二、process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑 */
    @Override
    public void process(Page page) {
        LOGGER.info("status code {}",page.getStatusCode());
        // 收集店铺信息
        collectShop(page);
        // 店铺图片
        if(page.getUrl().toString().contains("http://www.dianping.com/shop") && page.getUrl().toString().contains("photos")){
            page.addTargetRequests(page.getHtml().links().regex("http://www.dianping.com/shop/\\d+/photos").all());
            page.addTargetRequests(page.getHtml().links().regex("http://www.dianping.com/shop/\\d+/photos/\\S+").all());
            List<String> typeList = page.getHtml().links().regex("/photos/\\d+/\\w+").all();
            List<String> imgList = new ArrayList<String>();
            if(CollectionUtils.isNotEmpty(typeList)){
                for(String type : typeList){
                    Request request = new Request("http://www.dianping.com" + type);
                    Map<String, Object> shopIdMap = new HashMap<String, Object>();
                    String[] urlArr = page.getUrl().toString().split("/");
                    String shopId = urlArr[4];
                    shopIdMap.put("http://www.dianping.com" + type, shopId);
                    request.setExtras(shopIdMap);

                    page.addTargetRequest(request);
                    imgList.add("http://www.dianping.com" + type);
                }
            }
            page.addTargetRequests(imgList);
        }
        // 收集店铺图片信息
        downLoadShopPic(page);
        List<String> urlList1 = page.getHtml().links().regex("//www.dianping.com/\\w+").all();
        List<String> list1 = new ArrayList<String>();
        if(!CollectionUtils.isEmpty(urlList1)){
            for(String str : urlList1){
                str = Constants.URL_PRE + str;
                list1.add(str);
            }
        }
        //LOGGER.info("list {}", JSON.toJSONString(list1));
        page.addTargetRequests(list1);
        page.addTargetRequests(page.getHtml().links().regex("http://www.dianping.com/\\w+/food").all());
        page.addTargetRequests(page.getHtml().links().regex("http://www.dianping.com/\\w+/ch10/\\w+").all());
        page.addTargetRequests(page.getHtml().links().regex("http://www.dianping.com/\\w+/ch10/\\S+").all());
        List<String> shopList = page.getHtml().links().regex("http://www.dianping.com/shop/\\w+").all();
        if(CollectionUtils.isNotEmpty(shopList)){
            List<String> shopPicList = new ArrayList<String>();
            for(String shopUrl : shopList){
                shopPicList.add(shopUrl + Constants.PIC_LAST);
            }
            page.addTargetRequests(shopPicList);
        }
        page.addTargetRequests(shopList);

    }

    /**
     * 收集店铺信息
     *
     * @param page
     * @return
     */
    private void collectShop(Page page){
        String shopName = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/h1/text()").toString();
        if(StringUtils.isNotEmpty(shopName)){
            LOGGER.info("shopName {}",shopName);
            DianPingFoodShop shop = new DianPingFoodShop();
            shop.setShopName(shopName);
            // 星级
            String stars = page.getHtml().xpath("//div[@id='basic-info']/div[1]/span[1]").toString();
            if(StringUtils.isNotEmpty(stars)){
                String[] temp = stars.split("\"");
                if(temp.length >= 2){
                    stars = temp[1];
                }
            }
            // 大众点评shopId
            String url = page.getUrl().toString();
            String shopId = "";
            if(StringUtils.isNotEmpty(url)){
                shopId = url.substring(url.lastIndexOf("/") + 1);
                LOGGER.info("shopId {}", shopId);
                shop.setShopId(shopId);
            }

            LOGGER.info("stars {}",stars);
            shop.setStars(stars);
            // 评论数
            String reviewCount = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/div[@class='brief-info']/span[2]/tidyText()").toString();
            LOGGER.info("reviewCount {}",reviewCount);
            shop.setReviewCount(reviewCount);
            // 人均消费
            String avgPriceTitle = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/div[@class='brief-info']/span[3]/tidyText()").toString();
            LOGGER.info("avgPriceTitle {}",avgPriceTitle);
            shop.setAvgPriceTitle(avgPriceTitle);
            // 口味
            String favors = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/div[@class='brief-info']/span[4]/span[1]/tidyText()").toString();
            LOGGER.info("favors {}",favors);
            shop.setFavors(favors);
            // 环境
            String envirment = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/div[@class='brief-info']/span[4]/span[2]/tidyText()").toString();
            LOGGER.info("envirment {}",envirment);
            shop.setEnvirment(envirment);
            // 服务
            String service = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/div[@class='brief-info']/span[4]/span[3]/tidyText()").toString();
            LOGGER.info("service {}",service);
            shop.setService(service);
            // 地址
            String address = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/div[@class='expand-info address']/span[2]/tidyText()").toString();
            LOGGER.info("address {}",address);
            shop.setAddress(address);
            // 电话
            String phone1 = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/p[@class='expand-info tel']/span[2]/tidyText()").toString();
            String phone2 = page.getHtml().xpath("//div[@class='main']/div[@id='basic-info']/p[@class='expand-info tel']/span[3]/tidyText()").toString();
            LOGGER.info("phone1 {} phone2 {}",phone1,phone2);
            shop.setPhone(phone1 + (StringUtils.isNotEmpty(phone2) ? ("," + phone2) : ""));
            // 营业时间
            String openTime = page.getHtml().xpath("//div[@id='basic-info']/div[4]/p[1]/span[2]/tidyText()").toString();
            LOGGER.info("openTime {}",openTime);
            shop.setOpenTime(openTime);
            // 店铺介绍
            String introduce = page.getHtml().xpath("//div[@id='basic-info']/div[4]/p[4]/text()").toString();
            LOGGER.info("introduce {}",introduce);
            shop.setIntroduce(introduce);
            // 城市
            String city = page.getHtml().xpath("//div[@class='breadcrumb']/a[1]/text()").toString();
            LOGGER.info("city {}",city);
            shop.setCity(city);
            // 分类
            String category = page.getHtml().xpath("//div[@class='breadcrumb']/a[2]/text()").toString();
            LOGGER.info("category {}",category);
            shop.setCategory(category);

            page.putField("shop", shop);

        }
    }

    /**
     * 收集店铺图片信息
     *
     * @param page
     * @return
     */
    private void downLoadShopPic(Page page){
        if(page.getUrl().toString().contains("http://www.dianping.com/photos") && (page.getUrl().toString().contains("/type") || page.getUrl().toString().contains("/tag"))){
            ShopPic shopPic = new ShopPic();
            Map<String, Object> map = page.getRequest().getExtras();
            if(map != null){
                String shopId = (String) map.get(page.getUrl().toString());
                LOGGER.info("shopId {}", shopId);
                shopPic.setShopId(shopId);
            }
            // 图片分类
            String category = page.getHtml().xpath("//body[@id='top']/div[4]/div/ul/li[3]/a/text()").toString();
            LOGGER.info("category {}",category);
            shopPic.setCategory(category);
            // 图片url
            String imgUrl = page.getHtml().xpath("//img[@id='J_main-img']/@src").toString();
            LOGGER.info("imgUrl {}",imgUrl);
            shopPic.setPiUrl(imgUrl);
            // 上传者
            String uploader = page.getHtml().xpath("//body[@id='top']/div[4]/div/ul/li[5]/strong/tidyText()").toString();
            if(StringUtils.isEmpty(uploader)){
                uploader = page.getHtml().xpath("//body[@id='top']/div[4]/div/ul/li[4]/strong/tidyText()").toString();
            }
            LOGGER.info("uploader {}",uploader);
            shopPic.setUploader(uploader);
            // 图片名称
            String picName = page.getHtml().xpath("//img[@id='J_main-img']/@alt").toString();
            if(StringUtils.isNotEmpty(page.getHtml().xpath("//body[@id='top']/div[4]/div/ul/li[5]/strong/tidyText()").toString())){
                String name = page.getHtml().xpath("//body[@id='top']/div[4]/div/ul/li[4]/a/text()").toString();
                picName = picName + " - " + name;
            }
            LOGGER.info("picName {}",picName);
            shopPic.setPicName(picName);
            // 上传时间
            String uploadTime = page.getHtml().xpath("//body[@id='top']/div[5]/div[1]/div[2]/div[2]/ul/li[1]/span/tidyText()").toString();
            LOGGER.info("uploadTime {}",uploadTime);
            shopPic.setUploadTime(uploadTime);
            // 上传设备
            String uploadEquipment = page.getHtml().xpath("//body[@id='top']/div[5]/div[1]/div[2]/div[2]/ul/li[3]/span[2]/strong/tidyText()").toString();
            LOGGER.info("uploadEquipment {}",uploadEquipment);
            shopPic.setUploadEquipment(uploadEquipment);

            page.putField("shopPic", shopPic);
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("*******************************************爬取大众点评美食商户开始*******************************************");
        // 从"http://www.dianping.com/citylist"开始抓
        spider.addUrl("http://www.dianping.com/citylist")
                // 从之前抓取到的URL继续抓取
                .setScheduler(new RedisScheduler("localhost"))
                // 保存到数据库
                .addPipeline(new DianPingFoodShopPipeline())
                // 设置代理
                //.setDownloader(httpClientDownloader)
                //开启2个线程抓取
                .thread(2)
                //启动爬虫
                .run();
        System.out.println("*******************************************爬取大众点评美食商户结束*******************************************");
    }
}
