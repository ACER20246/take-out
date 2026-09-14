package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/category")
@Tag(name = "分类管理", description = "分类相关的操作")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "新增分类", description = "新增分类接口")
    @PostMapping
    public Result add(@RequestBody CategoryDTO categoryDTO, @AuthenticationPrincipal Long id) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO, id);
        return Result.success();
    }

    @Operation(summary = "分页查询分类", description = "分页查询分类接口")
    @GetMapping("/page")
    public Result<PageResult> page(@ParameterObject CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @Operation(summary = "根据Id删除分类", description = "根据Id删除分类接口")
    public Result deleteById(Long id) {
        log.info("根据Id删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "根据Id修改分类", description = "根据Id修改分类接口")
    public Result updateById(@RequestBody CategoryDTO categoryDTO, @AuthenticationPrincipal Long id) {
        log.info("根据Id修改分类：{}", id);
        categoryService.update(categoryDTO, id);
        return Result.success();
    }
    @Operation(summary = "修改状态",description ="修改分类状态" )
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable Integer status,Long id,@AuthenticationPrincipal Long currentEmpId) {
        log.info("修改分类状态：{}", status);
        // Implement the logic to change the status of the category
        categoryService.changeStatus(status, id,currentEmpId);
        return Result.success();
    }
    @Operation(summary = "查询同类分类",description = "查询同类分类接口" )
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        log.info("查询所有分类");
        List<Category> categoryList = categoryService.list(type);
        return Result.success(categoryList);
    }
}
