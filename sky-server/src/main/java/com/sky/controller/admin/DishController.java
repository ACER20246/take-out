package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Tag(name = "菜品管理", description = "菜品相关的操作")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @Operation(summary = "新增菜品", description = "新增菜品接口")
    public Result save(@RequestBody DishDTO dishDTO,@AuthenticationPrincipal Long currentId) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO,currentId);
        //清理缓存
        String key = "dish_"+dishDTO.getCategoryId();
        clearcache(key);
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
        //将所有菜品缓存数据删除
        clearcache("dish_*");
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
        //将所有菜品缓存数据删除
        clearcache("dish_*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @Operation(summary = "修改菜品状态", description = "修改菜品状态接口")
    public Result<String> updateStatus(@PathVariable Integer status,Long id) {
        log.info("修改菜品状态：{}", status);
        dishService.changeStatus(status,id);
        //将所有菜品缓存数据删除
        clearcache("dish_*");
        return Result.success("菜品状态修改成功");
    }
    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @Operation(summary ="根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 清理缓存数据
     * @param pattern
     */
    private void clearcache(String pattern){
        //将所有菜品缓存数据删除
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
