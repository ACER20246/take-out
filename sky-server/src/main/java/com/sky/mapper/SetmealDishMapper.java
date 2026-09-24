package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
//处理套餐与菜品
public interface SetmealDishMapper {
    /**
     * 根据菜品ID获取套餐ID列表
     *
     * @param ids 菜品ID列表
     * @return 套餐ID列表
     */
    List<Long> getSetmealIdsByDishIds(List<Long> ids);

    /**
     * 批量插入套餐菜品关系
     *
     * @param setmealDishes 套餐菜品关系列表
     */
    void insertBatch(List<SetmealDish> setmealDishes);
    /**
     * 根据套餐ID删除套餐菜品关系
     *
     * @param id 套餐ID
     */
    @Delete("DELETE FROM setmeal_dish WHERE setmeal_id =#{id}")
    void deleteBySetmealId(Long id);

    /**
     * 根据套餐id查询套餐和菜品的关联关系
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);
}
