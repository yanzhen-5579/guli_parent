package com.yanzhen.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {
    @ApiModelProperty("教师名称，模糊查询")
    private String name;
    @ApiModelProperty("教师头衔")
    private Integer level;
    @ApiModelProperty("开始时间")
    private String begin;
    @ApiModelProperty("结束时间")
    private String end;
}
