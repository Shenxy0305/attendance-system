package org.jeecg.modules.attendance.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.attendance.DTO.StudentDTO;
import org.jeecg.modules.attendance.entity.Student;
import org.jeecg.modules.attendance.mapper.StudentMapper;
import org.jeecg.modules.attendance.service.IStudentService;
import org.jeecg.modules.system.entity.SysRole;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学生管理实现类
 * </p>
 *
 * @Author shenxinyuan
 * @since 2021-12-13
 */
@Service
public class IStudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public Result<?> getStudentList(StudentDTO studentDTO) {
        Result<List<StudentDTO>> result = new Result<List<StudentDTO>>();
        List<StudentDTO> resultList = studentMapper.getStudentList(studentDTO);
        result.setResult(resultList);
        return result;
    }

    @Override
    public Result<?> addStudent(JSONObject jsonObject) {
        Result<StudentDTO> result = new Result<StudentDTO>();
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>();
        queryWrapper.eq("role_name", "学生");
        String selectedRoles = sysRoleMapper.selectOne(queryWrapper).getId();
        String selectedDeparts = jsonObject.getString("selecteddeparts");
        return null;
    }
}
