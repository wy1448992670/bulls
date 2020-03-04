 package com.goochou.p2b.model.vo;

import java.io.Serializable;

/**
 * @author ydp
 * @date 2019/06/28
 */
public class DetailsVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4848722696556384086L;
    private String link;
    private String pictureUrl;
    private String title;
    private Integer show;
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getPictureUrl() {
        return pictureUrl;
    }
    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getShow() {
        return show;
    }
    public void setShow(Integer show) {
        this.show = show;
    }
    
}
