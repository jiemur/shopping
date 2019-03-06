package com.wzn.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;


@Data
public class ServerResponse <T>{
    private int status;
    private String  msg;
    private T data;


    public ServerResponse() {

    }
    public ServerResponse(int status) {
        this.status = status;

    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;

    }
    public ServerResponse(int status,  T data) {
        this.status = status;
        this.data = data;
    }

    public ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;


    }
/*
* 调用接口成功时回调
*
* */
    public static ServerResponse responseSuccess(){
        return new ServerResponse(ResponseCode.SUCCESS) ;
    }
    public static<T> ServerResponse responseSuccess(T data){
        return new ServerResponse(ResponseCode.SUCCESS,data) ;
    }
    public static<T> ServerResponse responseSuccess(T data, String msg){
        return new ServerResponse(ResponseCode.SUCCESS,msg,data) ;
    }
    public static ServerResponse responseSuccess(int status,String msg){
        return new ServerResponse(status,msg);

    }




/*
* 调用接口失败时回调
* */

    public static ServerResponse responseErroe(){
        return new ServerResponse(ResponseCode.ERROR);
    }
    public static ServerResponse responseErroe(int status){
        return new ServerResponse(status);
    }
    public static ServerResponse responseErroe(int status,String msg){
        return new ServerResponse(status,msg);
    }
    public static ServerResponse responseErroe(String msg){
        return new ServerResponse(ResponseCode.ERROR,msg);
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /*
    *
    * 判断接口是否正确返回,@JsonIgnore 忽略属性值
    * */
    @JsonIgnore
    public boolean isSuccess(){
    return this.status==ResponseCode.SUCCESS;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
