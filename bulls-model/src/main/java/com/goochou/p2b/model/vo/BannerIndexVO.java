 package com.goochou.p2b.model.vo;

import java.io.Serializable;

/**
 * @author ydp
 * @date 2019/06/24
 */
public class BannerIndexVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4960711210808085724L;
    private String link;
    private String pictureUrl;
    private String key;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
