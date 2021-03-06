package com.goochou.p2b.model;

import java.io.Serializable;

public class PrairieAreaTactics implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prairie_area_tactics.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prairie_area_tactics.ear_number
     *
     * @mbg.generated
     */
    private String earNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prairie_area_tactics.prairie_id
     *
     * @mbg.generated
     */
    private Long prairieId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prairie_area_tactics.sequence
     *
     * @mbg.generated
     */
    private Integer sequence;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prairie_area_tactics.cron_expression
     *
     * @mbg.generated
     */
    private String cronExpression;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column prairie_area_tactics.prairie_area_id
     *
     * @mbg.generated
     */
    private Long prairieAreaId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table prairie_area_tactics
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prairie_area_tactics.id
     *
     * @return the value of prairie_area_tactics.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prairie_area_tactics.id
     *
     * @param id the value for prairie_area_tactics.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prairie_area_tactics.ear_number
     *
     * @return the value of prairie_area_tactics.ear_number
     *
     * @mbg.generated
     */
    public String getEarNumber() {
        return earNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prairie_area_tactics.ear_number
     *
     * @param earNumber the value for prairie_area_tactics.ear_number
     *
     * @mbg.generated
     */
    public void setEarNumber(String earNumber) {
        this.earNumber = earNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prairie_area_tactics.prairie_id
     *
     * @return the value of prairie_area_tactics.prairie_id
     *
     * @mbg.generated
     */
    public Long getPrairieId() {
        return prairieId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prairie_area_tactics.prairie_id
     *
     * @param prairieId the value for prairie_area_tactics.prairie_id
     *
     * @mbg.generated
     */
    public void setPrairieId(Long prairieId) {
        this.prairieId = prairieId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prairie_area_tactics.sequence
     *
     * @return the value of prairie_area_tactics.sequence
     *
     * @mbg.generated
     */
    public Integer getSequence() {
        return sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prairie_area_tactics.sequence
     *
     * @param sequence the value for prairie_area_tactics.sequence
     *
     * @mbg.generated
     */
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prairie_area_tactics.cron_expression
     *
     * @return the value of prairie_area_tactics.cron_expression
     *
     * @mbg.generated
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prairie_area_tactics.cron_expression
     *
     * @param cronExpression the value for prairie_area_tactics.cron_expression
     *
     * @mbg.generated
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column prairie_area_tactics.prairie_area_id
     *
     * @return the value of prairie_area_tactics.prairie_area_id
     *
     * @mbg.generated
     */
    public Long getPrairieAreaId() {
        return prairieAreaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column prairie_area_tactics.prairie_area_id
     *
     * @param prairieAreaId the value for prairie_area_tactics.prairie_area_id
     *
     * @mbg.generated
     */
    public void setPrairieAreaId(Long prairieAreaId) {
        this.prairieAreaId = prairieAreaId;
    }
}