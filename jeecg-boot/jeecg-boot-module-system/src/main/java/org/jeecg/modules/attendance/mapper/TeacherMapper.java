package org.jeecg.modules.attendance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.attendance.DTO.TeacherDTO;
import org.jeecg.modules.attendance.entity.Teacher;

import java.util.List;

/**
 * @Description: 教师管理
 * @Author: shenxinyuan
 * @Date:  2022-02-22
 * @Version: V1.0
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    List<TeacherDTO> getTeacherList(TeacherDTO teacherDTO);

}
