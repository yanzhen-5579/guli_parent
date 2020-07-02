package com.yanzhen.oss.controller;

import com.yanzhen.commonutils.R;
import com.yanzhen.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadFile(@RequestParam("file") MultipartFile multipartFile){
        String url = ossService.uploadFileAvatar(multipartFile);
        return R.ok().data("url",url);
    }
}
