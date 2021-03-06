package com.goochou.p2b.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MigrationInvestmentBillExample {
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    protected int limitStart = -1;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    protected int limitEnd = -1;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public MigrationInvestmentBillExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table migration_investment_bill
     * @mbg.generated
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table migration_investment_bill
     * @mbg.generated
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria)this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria)this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria)this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria)this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria)this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria)this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdIsNull() {
            addCriterion("migration_investment_id is null");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdIsNotNull() {
            addCriterion("migration_investment_id is not null");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdEqualTo(Long value) {
            addCriterion("migration_investment_id =", value, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdNotEqualTo(Long value) {
            addCriterion("migration_investment_id <>", value, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdGreaterThan(Long value) {
            addCriterion("migration_investment_id >", value, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("migration_investment_id >=", value, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdLessThan(Long value) {
            addCriterion("migration_investment_id <", value, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdLessThanOrEqualTo(Long value) {
            addCriterion("migration_investment_id <=", value, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdIn(List<Long> values) {
            addCriterion("migration_investment_id in", values, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdNotIn(List<Long> values) {
            addCriterion("migration_investment_id not in", values, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdBetween(Long value1, Long value2) {
            addCriterion("migration_investment_id between", value1, value2, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andMigrationInvestmentIdNotBetween(Long value1, Long value2) {
            addCriterion("migration_investment_id not between", value1, value2, "migrationInvestmentId");
            return (Criteria)this;
        }

        public Criteria andBidIdIsNull() {
            addCriterion("bid_id is null");
            return (Criteria)this;
        }

        public Criteria andBidIdIsNotNull() {
            addCriterion("bid_id is not null");
            return (Criteria)this;
        }

        public Criteria andBidIdEqualTo(Long value) {
            addCriterion("bid_id =", value, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdNotEqualTo(Long value) {
            addCriterion("bid_id <>", value, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdGreaterThan(Long value) {
            addCriterion("bid_id >", value, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdGreaterThanOrEqualTo(Long value) {
            addCriterion("bid_id >=", value, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdLessThan(Long value) {
            addCriterion("bid_id <", value, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdLessThanOrEqualTo(Long value) {
            addCriterion("bid_id <=", value, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdIn(List<Long> values) {
            addCriterion("bid_id in", values, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdNotIn(List<Long> values) {
            addCriterion("bid_id not in", values, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdBetween(Long value1, Long value2) {
            addCriterion("bid_id between", value1, value2, "bidId");
            return (Criteria)this;
        }

        public Criteria andBidIdNotBetween(Long value1, Long value2) {
            addCriterion("bid_id not between", value1, value2, "bidId");
            return (Criteria)this;
        }

        public Criteria andPeriodsIsNull() {
            addCriterion("periods is null");
            return (Criteria)this;
        }

        public Criteria andPeriodsIsNotNull() {
            addCriterion("periods is not null");
            return (Criteria)this;
        }

        public Criteria andPeriodsEqualTo(Byte value) {
            addCriterion("periods =", value, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsNotEqualTo(Byte value) {
            addCriterion("periods <>", value, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsGreaterThan(Byte value) {
            addCriterion("periods >", value, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsGreaterThanOrEqualTo(Byte value) {
            addCriterion("periods >=", value, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsLessThan(Byte value) {
            addCriterion("periods <", value, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsLessThanOrEqualTo(Byte value) {
            addCriterion("periods <=", value, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsIn(List<Byte> values) {
            addCriterion("periods in", values, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsNotIn(List<Byte> values) {
            addCriterion("periods not in", values, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsBetween(Byte value1, Byte value2) {
            addCriterion("periods between", value1, value2, "periods");
            return (Criteria)this;
        }

        public Criteria andPeriodsNotBetween(Byte value1, Byte value2) {
            addCriterion("periods not between", value1, value2, "periods");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeIsNull() {
            addCriterion("receive_time is null");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeIsNotNull() {
            addCriterion("receive_time is not null");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeEqualTo(Date value) {
            addCriterion("receive_time =", value, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeNotEqualTo(Date value) {
            addCriterion("receive_time <>", value, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeGreaterThan(Date value) {
            addCriterion("receive_time >", value, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("receive_time >=", value, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeLessThan(Date value) {
            addCriterion("receive_time <", value, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeLessThanOrEqualTo(Date value) {
            addCriterion("receive_time <=", value, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeIn(List<Date> values) {
            addCriterion("receive_time in", values, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeNotIn(List<Date> values) {
            addCriterion("receive_time not in", values, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeBetween(Date value1, Date value2) {
            addCriterion("receive_time between", value1, value2, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveTimeNotBetween(Date value1, Date value2) {
            addCriterion("receive_time not between", value1, value2, "receiveTime");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusIsNull() {
            addCriterion("receive_corpus is null");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusIsNotNull() {
            addCriterion("receive_corpus is not null");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusEqualTo(BigDecimal value) {
            addCriterion("receive_corpus =", value, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusNotEqualTo(BigDecimal value) {
            addCriterion("receive_corpus <>", value, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusGreaterThan(BigDecimal value) {
            addCriterion("receive_corpus >", value, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_corpus >=", value, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusLessThan(BigDecimal value) {
            addCriterion("receive_corpus <", value, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_corpus <=", value, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusIn(List<BigDecimal> values) {
            addCriterion("receive_corpus in", values, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusNotIn(List<BigDecimal> values) {
            addCriterion("receive_corpus not in", values, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_corpus between", value1, value2, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveCorpusNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_corpus not between", value1, value2, "receiveCorpus");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestIsNull() {
            addCriterion("receive_interest is null");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestIsNotNull() {
            addCriterion("receive_interest is not null");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestEqualTo(BigDecimal value) {
            addCriterion("receive_interest =", value, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestNotEqualTo(BigDecimal value) {
            addCriterion("receive_interest <>", value, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestGreaterThan(BigDecimal value) {
            addCriterion("receive_interest >", value, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_interest >=", value, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestLessThan(BigDecimal value) {
            addCriterion("receive_interest <", value, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_interest <=", value, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestIn(List<BigDecimal> values) {
            addCriterion("receive_interest in", values, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestNotIn(List<BigDecimal> values) {
            addCriterion("receive_interest not in", values, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_interest between", value1, value2, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_interest not between", value1, value2, "receiveInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestIsNull() {
            addCriterion("receive_increase_interest is null");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestIsNotNull() {
            addCriterion("receive_increase_interest is not null");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestEqualTo(BigDecimal value) {
            addCriterion("receive_increase_interest =", value, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestNotEqualTo(BigDecimal value) {
            addCriterion("receive_increase_interest <>", value, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestGreaterThan(BigDecimal value) {
            addCriterion("receive_increase_interest >", value, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_increase_interest >=", value, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestLessThan(BigDecimal value) {
            addCriterion("receive_increase_interest <", value, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_increase_interest <=", value, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestIn(List<BigDecimal> values) {
            addCriterion("receive_increase_interest in", values, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestNotIn(List<BigDecimal> values) {
            addCriterion("receive_increase_interest not in", values, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_increase_interest between", value1, value2, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andReceiveIncreaseInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_increase_interest not between", value1, value2, "receiveIncreaseInterest");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeIsNull() {
            addCriterion("is_receive_before is null");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeIsNotNull() {
            addCriterion("is_receive_before is not null");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeEqualTo(Boolean value) {
            addCriterion("is_receive_before =", value, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeNotEqualTo(Boolean value) {
            addCriterion("is_receive_before <>", value, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeGreaterThan(Boolean value) {
            addCriterion("is_receive_before >", value, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_receive_before >=", value, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeLessThan(Boolean value) {
            addCriterion("is_receive_before <", value, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeLessThanOrEqualTo(Boolean value) {
            addCriterion("is_receive_before <=", value, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeIn(List<Boolean> values) {
            addCriterion("is_receive_before in", values, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeNotIn(List<Boolean> values) {
            addCriterion("is_receive_before not in", values, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeBetween(Boolean value1, Boolean value2) {
            addCriterion("is_receive_before between", value1, value2, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBeforeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_receive_before not between", value1, value2, "isReceiveBefore");
            return (Criteria)this;
        }

        public Criteria andIsReceiveIsNull() {
            addCriterion("is_receive is null");
            return (Criteria)this;
        }

        public Criteria andIsReceiveIsNotNull() {
            addCriterion("is_receive is not null");
            return (Criteria)this;
        }

        public Criteria andIsReceiveEqualTo(Boolean value) {
            addCriterion("is_receive =", value, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveNotEqualTo(Boolean value) {
            addCriterion("is_receive <>", value, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveGreaterThan(Boolean value) {
            addCriterion("is_receive >", value, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_receive >=", value, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveLessThan(Boolean value) {
            addCriterion("is_receive <", value, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveLessThanOrEqualTo(Boolean value) {
            addCriterion("is_receive <=", value, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveIn(List<Boolean> values) {
            addCriterion("is_receive in", values, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveNotIn(List<Boolean> values) {
            addCriterion("is_receive not in", values, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveBetween(Boolean value1, Boolean value2) {
            addCriterion("is_receive between", value1, value2, "isReceive");
            return (Criteria)this;
        }

        public Criteria andIsReceiveNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_receive not between", value1, value2, "isReceive");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeIsNull() {
            addCriterion("real_receive_time is null");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeIsNotNull() {
            addCriterion("real_receive_time is not null");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeEqualTo(Date value) {
            addCriterion("real_receive_time =", value, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeNotEqualTo(Date value) {
            addCriterion("real_receive_time <>", value, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeGreaterThan(Date value) {
            addCriterion("real_receive_time >", value, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("real_receive_time >=", value, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeLessThan(Date value) {
            addCriterion("real_receive_time <", value, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeLessThanOrEqualTo(Date value) {
            addCriterion("real_receive_time <=", value, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeIn(List<Date> values) {
            addCriterion("real_receive_time in", values, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeNotIn(List<Date> values) {
            addCriterion("real_receive_time not in", values, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeBetween(Date value1, Date value2) {
            addCriterion("real_receive_time between", value1, value2, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andRealReceiveTimeNotBetween(Date value1, Date value2) {
            addCriterion("real_receive_time not between", value1, value2, "realReceiveTime");
            return (Criteria)this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria)this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria)this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria)this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria)this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria)this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria)this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria)this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria)this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria)this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria)this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria)this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria)this;
        }
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the database table migration_investment_bill
     * @mbg.generated
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
     * This class corresponds to the database table migration_investment_bill
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}