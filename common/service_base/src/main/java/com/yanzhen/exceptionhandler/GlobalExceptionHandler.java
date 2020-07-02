package com.yanzhen.exceptionhandler;


import com.yanzhen.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R exceptionHandler( Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常。。。");
    }
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R exceptionHandler(GuliException e){
        log.error(e.getMsg());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
