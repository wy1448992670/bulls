 package com.goochou.p2b.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @author ydp
 * @date 2019/06/11
 */
public class ProvinceVO implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 8986773913257725336L;
    private Integer id;
    private String name;
    private List<CityVO> citys;
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
    public List<CityVO> getCitys() {
        return citys;
    }
    public void setCitys(List<CityVO> citys) {
        this.citys = citys;
    }
    public ProvinceVO(Integer id, String name, List<CityVO> citys) {
        super();
        this.id = id;
        this.name = name;
        this.citys = citys;
    }
}
