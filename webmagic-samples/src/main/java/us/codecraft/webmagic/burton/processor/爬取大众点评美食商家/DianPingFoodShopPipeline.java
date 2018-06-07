package us.codecraft.webmagic.burton.processor.爬取大众点评美食商家;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.burton.util.JdbcUtil;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author Tainy
 * @date 2018/5/31 20:05
 */
public class DianPingFoodShopPipeline implements Pipeline {

    private static final Logger LOGGER = LoggerFactory.getLogger(DianPingFoodShopPipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        DianPingFoodShop shop = resultItems.get("shop");
        if(shop != null){
            LOGGER.info("爬取大众点评美食商户 持久化 begin...");
            String sql = "INSERT INTO dzdp_shop(shop_id, city,category,shopName,address,stars,reviewCount,avgPriceTitle,favors,envirment,service,phone,openTime,introduce)"
                    + " VALUES ('" + shop.getShopId() + "','" + shop.getCity() + "','" + shop.getCategory() + "','" + shop.getShopName()+ "','" + shop.getAddress()+ "','"
                    + shop.getStars() + "','" + shop.getReviewCount() + "','" + shop.getAvgPriceTitle()+ "','" + shop.getFavors() + "','"
                    + shop.getEnvirment() + "','" + shop.getService() + "','" + shop.getPhone() + "','" + shop.getOpenTime() + "','"
                    + shop.getIntroduce() + "')";
            try {
                JdbcUtil.insert(sql, "dzdp_shop");
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOGGER.info("爬取大众点评美食商户 持久化 finish...");
        }
        ShopPic shopPic = resultItems.get("shopPic");
        if(shopPic != null){
            LOGGER.info("爬取大众点评美食商户图片 持久化 begin...");
            String sql = "INSERT INTO dzdp_shop_pic(shop_id, category, picName, piUrl, uploader, uploadTime, uploadEquipment)"
                    + " VALUES ('" + shopPic.getShopId() + "','" + shopPic.getCategory() + "','" + shopPic.getPicName()+ "','" + shopPic.getPiUrl()+ "','"
                    + shopPic.getUploader() + "','" + shopPic.getUploadTime() + "','" + shopPic.getUploadEquipment() + "')";
            try {
                JdbcUtil.insert(sql, "dzdp_shop_pic");
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOGGER.info("爬取大众点评美食商户图片 持久化 finish...");
        }
    }
}
