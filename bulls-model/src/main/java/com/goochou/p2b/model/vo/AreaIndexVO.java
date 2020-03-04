package com.goochou.p2b.model.vo;


import java.io.Serializable;
import java.util.List;

public class AreaIndexVO implements Serializable {

    private static final long serialVersionUID = -8549522955721055455L;
    
    private String id;
    
    private String code;

    private String parentCode;
    
    private String name;
    
    private List<AreaIndexVO> citys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AreaIndexVO> getCitys() {
        return citys;
    }

    public void setCitys(List<AreaIndexVO> citys) {
        this.citys = citys;
    }
}
