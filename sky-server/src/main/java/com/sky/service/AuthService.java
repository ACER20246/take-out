package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.Employee;
import com.sky.vo.EmployeeLoginVO;
import com.sky.vo.UserLoginVO;

public interface AuthService {
    EmployeeLoginVO employeeLogin(EmployeeLoginDTO employeeLoginDTO);

    UserLoginVO userLogin(UserLoginDTO userLoginDTO);
}
