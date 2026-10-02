package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 购物车数据
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新购物车数据
     * @param existingCart
     */
    @Update("UPDATE shopping_cart SET number = #{number} WHERE id =#{id}")
    void update(ShoppingCart existingCart);

    /**
     * 插入数据到购物车
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart(user_id, dish_id, setmeal_id, name, image, dish_flavor, number, amount, create_time) " +
            "values(#{userId}, #{dishId}, #{setmealId}, #{name}, #{image}, #{dishFlavor}, #{number}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);
}
