package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 插入数据
     * @param category
     */
    @Insert("INSERT INTO category(type,name,sort,status,create_time,update_time,create_user,update_user) VALUES (#{type},#{name}, #{sort}, #{status}, #{createTime},#{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value= OperationType.INSERT)
    void insert(Category category);

    /**
     * 分页查询
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 删除分类
     * @param id
     */
    @Delete("DELETE FROM category WHERE id =#{id}")
    void DeleteById(Long id);

    /**
     * 修改分类
     * @param category
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateById(Category category);

    /**
     *
     * @param type
     * @return
     */
    List<Category> list(Integer type);
}
