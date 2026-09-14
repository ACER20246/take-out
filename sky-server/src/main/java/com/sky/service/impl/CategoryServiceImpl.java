package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void save(CategoryDTO categoryDTO,Long id) {
        // Implementation for saving a category
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
        category.setStatus(StatusConstant.DISABLE);
        category.setCreateUser(id); // Assuming the current user ID is 1 for this example
        category.setUpdateUser(id);
        categoryMapper.insert(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据Id删除分类
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        Integer count = dishMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        count=setmealMapper.countByCategoryId(id);
        if(count>0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.DeleteById(id);
    }

    @Override
    public void update(CategoryDTO categoryDTO,Long id) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        //category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(id); // Assuming the current user ID is 1 for this example
        categoryMapper.updateById(category);
    }

    @Override
    public void changeStatus(Integer status, Long id, Long currentEmpId) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        category.setUpdateUser(currentEmpId);
        //category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateById(category);
    }

    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
