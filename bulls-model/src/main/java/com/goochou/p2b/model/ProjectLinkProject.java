package com.goochou.p2b.model;

public class ProjectLinkProject {
    private Integer id;
    private Integer parentId;

    private Double parentAmount;

    private Integer subcalssId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Double getParentAmount() {
        return parentAmount;
    }

    public void setParentAmount(Double parentAmount) {
        this.parentAmount = parentAmount;
    }

    public Integer getSubcalssId() {
        return subcalssId;
    }

    public void setSubcalssId(Integer subcalssId) {
        this.subcalssId = subcalssId;
    }
}