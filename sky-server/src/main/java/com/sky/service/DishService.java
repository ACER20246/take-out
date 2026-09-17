package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void saveWithFlavor(DishDTO dishDTO, Long currentId);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void delete(List<Long> ids);
    /**
     * 查询菜品和对应的口味
     * @param id
     * @return
     */
    DishVO getById(Long id);

    void updateWithFlavor(DishDTO dishDTO, Long currentId);
}
