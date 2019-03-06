package com.wzn.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShoppingVO implements Serializable {
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.receiver_name
     *
     * @mbggenerated
     */
    private String receiverName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.receiver_phone
     *
     * @mbggenerated
     */
    private String receiverPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.receiver_mobile
     *
     * @mbggenerated
     */
    private String receiverMobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.receiver_province
     *
     * @mbggenerated
     */
    private String receiverProvince;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.receiver_city
     *
     * @mbggenerated
     */
    private String receiverCity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.receiver_district
     *
     * @mbggenerated
     */
    private String receiverDistrict;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.receiver_address
     *
     * @mbggenerated
     */
    private String receiverAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column w_shopping.receiver_zip
     *
     * @mbggenerated
     */
    private String receiverZip;

}
