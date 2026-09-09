package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "员工管理", description = "员工相关的操作")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @Operation(summary = "员工登录", description = "员工登录接口")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @Operation(summary = "员工退出", description = "员工退出接口")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 添加员工
     * @param employeeDTO
     * @return
     */
    @Operation(summary = "添加员工", description = "添加员工接口")
    @PostMapping
    public Result addEmployee(@RequestBody EmployeeDTO employeeDTO,@AuthenticationPrincipal Long currentEmpId){
        log.info("新增员工{}",employeeDTO);
        // Add employee logic here
        employeeService.addEmployee(employeeDTO,currentEmpId);
        return Result.success();
    }

    /**
     * 分页查询员工
     * @param employeePageQueryDTO
     * @return
     */
    @Operation(summary = "分页查询员工", description = "分页查询员工接口")
    @GetMapping("/page")
    public Result<PageResult> listEmployees(@ParameterObject EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询员工{}",employeePageQueryDTO);
        // List employees logic here
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @Operation(summary = "修改员工状态", description = "修改员工状态接口")
    @PostMapping("/status/{status}")
    public Result changeStatus(@RequestParam Long id, @PathVariable("status") Integer status){
        employeeService.changeStatus(status,id);
        return Result.success();
    }
}
