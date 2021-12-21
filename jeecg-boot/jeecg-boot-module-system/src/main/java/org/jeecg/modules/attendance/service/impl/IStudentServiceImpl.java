package org.jeecg.modules.attendance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.attendance.entity.Student;
import org.jeecg.modules.attendance.mapper.StudentMapper;
import org.jeecg.modules.attendance.service.IStudentService;
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

    @Override
    public Result<?> getStudent(Integer clazzId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
        queryWrapper.eq("student_clazz", clazzId);
        List<Student> resultList = studentMapper.selectList(queryWrapper);
        Result<List<Student>> result = new Result<List<Student>>();
        result.isSuccess();
        result.setResult(resultList);
        return result;
    }
}
