 package com.goochou.p2b.model.vo;

import java.io.Serializable;

/**
 * @author ydp
 * @date 2019/06/11
 */
public class CityVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3543227289854856006L;
    private Integer id;
    private String name;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public CityVO(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
