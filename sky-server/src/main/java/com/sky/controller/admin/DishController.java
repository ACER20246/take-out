package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/page")
    @Operation(summary = "分页查询菜品", description = "分页查询菜品接口")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO ) {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @DeleteMapping
    @Operation(summary = "删除菜品", description = "删除菜品接口")
    public  Result delete(@RequestParam List<Long> ids){
        log.info("删除菜品：{}", ids);
        dishService.delete(ids);
        return Result.success();
    }
    @Operation(summary = "根据ID查询菜品", description = "根据ID查询菜品接口")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("查询菜品：{}", id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @param currentId
     * @return
     */
    @PutMapping
    @Operation(summary = "修改菜品", description = "修改菜品接口")
    public Result update(@RequestBody DishDTO dishDTO,@AuthenticationPrincipal Long currentId) {
        log.info("修改菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO,currentId);
        return Result.success();
    }
}
