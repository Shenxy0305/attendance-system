package org.jeecg.modules.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeecg.modules.attendance.DTO.StudentDTO;
import org.jeecg.modules.attendance.entity.Student;

import java.util.List;

/**
 * @Description: 学生管理
 * @Author: shenxinyuan
 * @Date:  2021-12-13
 * @Version: V1.0
 */
public interface StudentMapper extends BaseMapper<Student> {

    List<StudentDTO> getStudentList(StudentDTO studentDTO);
}
