package org.jeecg.modules.attendance.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.attendance.DTO.StudentDTO;
import org.jeecg.modules.attendance.entity.Student;


/**
 * @Description: 学生管理
 * @Author: shenxinyuan
 * @Date:  2021-12-13
 * @Version: V1.0
 */
public interface IStudentService extends IService<Student> {

    /**
     * @功能：学生查询
     * @param studentDTO
     * @return
     */
    Result<IPage<StudentDTO>> getStudentList(StudentDTO studentDTO, Integer pageNo, Integer pageSize);

    /**
     * @功能：添加学生
     * @param jsonObject
     * @return
     */
    Result<?> addStudent(JSONObject jsonObject);

    /**
     * @功能：删除学生
     * @param id
     * @return
     */
    Result<?> delectStudent(String id);

    /**
     * @功能：修改学生信息
     * @param jsonObject
     * @return
     */
    Result<?> editStudent(JSONObject jsonObject);

}
