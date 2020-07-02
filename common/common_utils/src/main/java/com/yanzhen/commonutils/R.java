package com.yanzhen.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {

    @ApiModelProperty("是否成功")
    private boolean success;
    @ApiModelProperty("返回码")
    private Integer code;
    @ApiModelProperty("返回信息")
    private String message;
    @ApiModelProperty("返回数据")
    private Map<String,Object> data = new HashMap<>();

    private R(){};

    public static R ok(){
        R r = new R();
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        r.setSuccess(true);
        return  r;
    }

    public static R error(){
        R r = new R();
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        r.setSuccess(false);
        return  r;
    }
    public  R code(Integer code){
        this.setCode(code);
        return this;
    }
    public  R success(boolean success){
        this.setSuccess(success);
        return this;
    }
    public R message(String message){
        this.setMessage(message);
        return this;
    }
    public R data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
}
