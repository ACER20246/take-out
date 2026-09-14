package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dish")
@Tag(name = "菜品管理", description = "菜品相关的操作")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @Operation(summary = "新增菜品", description = "新增菜品接口")
    public Result save(@RequestBody DishDTO dishDTO,@AuthenticationPrincipal Long currentId) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO,currentId);
        return Result.success();
    }
}
