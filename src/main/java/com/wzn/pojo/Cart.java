package com.wzn.pojo;

import java.util.Date;

public class Cart {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_cart.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_cart.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_cart.product_id
     *
     * @mbggenerated
     */
    private Integer productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_cart.quantity
     *
     * @mbggenerated
     */
    private Integer quantity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_cart.checked
     *
     * @mbggenerated
     */
    private Integer checked;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_cart.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_cart.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column w_cart.id
     *
     * @return the value of w_cart.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column w_cart.id
     *
     * @param id the value for w_cart.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column w_cart.user_id
     *
     * @return the value of w_cart.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column w_cart.user_id
     *
     * @param userId the value for w_cart.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column w_cart.product_id
     *
     * @return the value of w_cart.product_id
     *
     * @mbggenerated
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column w_cart.product_id
     *
     * @param productId the value for w_cart.product_id
     *
     * @mbggenerated
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column w_cart.quantity
     *
     * @return the value of w_cart.quantity
     *
     * @mbggenerated
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column w_cart.quantity
     *
     * @param quantity the value for w_cart.quantity
     *
     * @mbggenerated
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column w_cart.checked
     *
     * @return the value of w_cart.checked
     *
     * @mbggenerated
     */
    public Integer getChecked() {
        return checked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column w_cart.checked
     *
     * @param checked the value for w_cart.checked
     *
     * @mbggenerated
     */
    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column w_cart.create_time
     *
     * @return the value of w_cart.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column w_cart.create_time
     *
     * @param createTime the value for w_cart.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column w_cart.update_time
     *
     * @return the value of w_cart.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column w_cart.update_time
     *
     * @param updateTime the value for w_cart.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}