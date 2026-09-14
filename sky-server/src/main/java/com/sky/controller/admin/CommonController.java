package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliyunOSSOperator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@Tag(name = "通用接口", description = "通用接口相关的操作")
public class CommonController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    @Operation(summary = "文件上传", description = "文件上传接口")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        log.info("文件上传: {}", file.getOriginalFilename());
        String originFilename=file.getOriginalFilename();
        String extension= null;
        if (originFilename != null) {
            extension = originFilename.substring(originFilename.lastIndexOf("."));
        }
        String objectName= UUID.randomUUID().toString() + extension;
        String filePath=aliyunOSSOperator.upload(file.getBytes(), objectName);
        return Result.success(filePath);
    }
}
