 package com.goochou.p2b.model.vo;

import java.io.Serializable;

import com.goochou.p2b.utils.StringUtils;

/**
 * @author ydp
 * @date 2019/06/24
 */
public class IconIndexVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6779836546980968426L;
    private String key;
    private Integer id;
    private String path;
    private String pathGray;
    private String title;
    private String link;
    private String headerTitle;
    private String subtitle;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getPathGray() {
        return pathGray;
    }
    public void setPathGray(String pathGray) {
        this.pathGray = pathGray;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getHeaderTitle() {
        if (StringUtils.isBlank(key)) {
            return "";
        }
        switch (key) {
            case "index":
                return "首页";
            case "bulls":
                return "领养";
            case "shop":
                return "商城";
            case "user":
                return "我的";
            default:
                return "";
        }
    }
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
}
