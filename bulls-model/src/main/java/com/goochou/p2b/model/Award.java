package com.goochou.p2b.model;

public class Award {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column award.id
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column award.name
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column award.amount
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    private Double amount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column award.id
     *
     * @return the value of award.id
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column award.id
     *
     * @param id the value for award.id
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column award.name
     *
     * @return the value of award.name
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column award.name
     *
     * @param name the value for award.name
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column award.amount
     *
     * @return the value of award.amount
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column award.amount
     *
     * @param amount the value for award.amount
     *
     * @mbggenerated Wed Jul 01 14:19:28 CST 2015
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}