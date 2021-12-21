package org.jeecg.modules.attendance.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.attendance.entity.Student;

import java.util.List;

/**
 * @Description: 学生管理
 * @Author: shenxinyuan
 * @Date:  2021-12-13
 * @Version: V1.0
 */
public interface IStudentService extends IService<Student> {

    /**
     * @功能：获取班级学生信息
     * @param clazzId
     * @return
     */
    Result<?> getStudent(Integer clazzId);

}
