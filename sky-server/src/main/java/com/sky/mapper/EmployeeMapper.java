package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     */
    @Select("SELECT * FROM employee WHERE username =#{username}")
    Employee getByUsername(String username);

    /**
     * 添加员工
     * @param employee
     */
    @Insert("INSERT INTO employee(username, name, password,phone, sex, id_number,create_user,create_time,update_time,update_user) " +
            "VALUES(#{username}, #{name}, #{password}, #{phone}, #{sex}, #{idNumber},#{createUser},#{createTime},#{updateTime},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void addEmp(Employee employee);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 更新员工信息
     * @param employee
     */
    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("SELECT * FROM employee WHERE id =#{id}")
    Employee getById(Long id);
}
