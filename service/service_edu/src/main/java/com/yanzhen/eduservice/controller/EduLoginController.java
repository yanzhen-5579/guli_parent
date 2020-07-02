package com.yanzhen.eduservice.controller;

import com.yanzhen.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @PostMapping("/login")
    public R login() {
        return R.ok().data("token", "yzz");
    }

    @GetMapping("/info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "yz").data("avatar", "http://localhost:8001/images/1.jpg");
    }
}
