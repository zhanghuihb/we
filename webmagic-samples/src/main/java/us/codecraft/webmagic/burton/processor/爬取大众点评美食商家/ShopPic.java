package us.codecraft.webmagic.burton.processor.爬取大众点评美食商家;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Tainy
 * @date 2018/6/4 12:57
 */
public class ShopPic implements Serializable{

    private Integer id;

    private String shopId;

    private String category;// 两级分类

    private String picName;// 图片名称

    private String piUrl;// 图片url

    private String uploader;// 上传者

    private String uploadTime;// 上传时间

    private String uploadEquipment;// 上传设备

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPiUrl() {
        return piUrl;
    }

    public void setPiUrl(String piUrl) {
        this.piUrl = piUrl;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadEquipment() {
        return uploadEquipment;
    }

    public void setUploadEquipment(String uploadEquipment) {
        this.uploadEquipment = uploadEquipment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
