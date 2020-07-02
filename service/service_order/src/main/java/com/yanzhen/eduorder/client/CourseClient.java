package com.yanzhen.eduorder.client;

import com.yanzhen.commonutils.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface CourseClient {
    @PostMapping("/eduservice/coursefront/getcourseorder/{id}")
    public CourseWebVoOrder getCourseOrder(@PathVariable("id") String id);
}
