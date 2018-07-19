package us.codecraft.webmagic.burton.processor.爬取大众点评美食商家;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Tainy
 * @date 2018/5/31 19:51
 */
public class DianPingFoodShop implements Serializable{

    private Integer id;

    private String shopId;// 大众点评shopId

    private String city;

    private String category;

    private String shopName;

    private String address;

    private String stars;// 星级

    private String reviewCount;// 评论数

    private String avgPriceTitle;// 人均消费

    private String favors;// 口味

    private String envirment;// 环境

    private String service;// 服务

    private String phone;// 联系方式

    private String openTime;// 营业时间

    private String introduce;// 简介

    private String brandStory;// 品牌故事

    private List<ShopPic> shopPicList;// 店铺图片

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getAvgPriceTitle() {
        return avgPriceTitle;
    }

    public void setAvgPriceTitle(String avgPriceTitle) {
        this.avgPriceTitle = avgPriceTitle;
    }

    public String getFavors() {
        return favors;
    }

    public void setFavors(String favors) {
        this.favors = favors;
    }

    public String getEnvirment() {
        return envirment;
    }

    public void setEnvirment(String envirment) {
        this.envirment = envirment;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getBrandStory() {
        return brandStory;
    }

    public void setBrandStory(String brandStory) {
        this.brandStory = brandStory;
    }

    public List<ShopPic> getShopPicList() {
        return shopPicList;
    }

    public void setShopPicList(List<ShopPic> shopPicList) {
        this.shopPicList = shopPicList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
