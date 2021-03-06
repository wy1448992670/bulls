package com.goochou.p2b.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EnterprisePictureExample {
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    protected List<Criteria> oredCriteria;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    protected int limitStart = -1;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    protected int limitEnd = -1;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public EnterprisePictureExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria)this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria)this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andUploadIdIsNull() {
            addCriterion("upload_id is null");
            return (Criteria)this;
        }

        public Criteria andUploadIdIsNotNull() {
            addCriterion("upload_id is not null");
            return (Criteria)this;
        }

        public Criteria andUploadIdEqualTo(Integer value) {
            addCriterion("upload_id =", value, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdNotEqualTo(Integer value) {
            addCriterion("upload_id <>", value, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdGreaterThan(Integer value) {
            addCriterion("upload_id >", value, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("upload_id >=", value, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdLessThan(Integer value) {
            addCriterion("upload_id <", value, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdLessThanOrEqualTo(Integer value) {
            addCriterion("upload_id <=", value, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdIn(List<Integer> values) {
            addCriterion("upload_id in", values, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdNotIn(List<Integer> values) {
            addCriterion("upload_id not in", values, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdBetween(Integer value1, Integer value2) {
            addCriterion("upload_id between", value1, value2, "uploadId");
            return (Criteria)this;
        }

        public Criteria andUploadIdNotBetween(Integer value1, Integer value2) {
            addCriterion("upload_id not between", value1, value2, "uploadId");
            return (Criteria)this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria)this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria)this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria)this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria)this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria)this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria)this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria)this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria)this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria)this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria)this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria)this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria)this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdIsNull() {
            addCriterion("enterprise_id is null");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdIsNotNull() {
            addCriterion("enterprise_id is not null");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdEqualTo(Integer value) {
            addCriterion("enterprise_id =", value, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdNotEqualTo(Integer value) {
            addCriterion("enterprise_id <>", value, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdGreaterThan(Integer value) {
            addCriterion("enterprise_id >", value, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("enterprise_id >=", value, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdLessThan(Integer value) {
            addCriterion("enterprise_id <", value, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdLessThanOrEqualTo(Integer value) {
            addCriterion("enterprise_id <=", value, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdIn(List<Integer> values) {
            addCriterion("enterprise_id in", values, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdNotIn(List<Integer> values) {
            addCriterion("enterprise_id not in", values, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdBetween(Integer value1, Integer value2) {
            addCriterion("enterprise_id between", value1, value2, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andEnterpriseIdNotBetween(Integer value1, Integer value2) {
            addCriterion("enterprise_id not between", value1, value2, "enterpriseId");
            return (Criteria)this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria)this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria)this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria)this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria)this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria)this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria)this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria)this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria)this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria)this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria)this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria)this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table enterprise_picture
     * @mbg.generated  Mon Jun 03 18:42:02 CST 2019
     */
    public static class Criterion {
        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table enterprise_picture
     *
     * @mbggenerated do_not_delete_during_merge Mon Nov 21 17:27:59 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}