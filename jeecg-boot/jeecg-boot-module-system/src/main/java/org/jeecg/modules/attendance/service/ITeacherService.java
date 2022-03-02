package org.jeecg.modules.attendance.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.attendance.DTO.TeacherDTO;
import org.jeecg.modules.attendance.entity.Teacher;

/**
 * @Description: 教师管理
 * @Author: shenxinyuan
 * @Date:  2022-02-22
 * @Version: V1.0
 */
public interface ITeacherService extends IService<Teacher> {

    /**
     * @功能：教师查询
     * @param TeacherDTO
     * @return
     */
    Result<IPage<TeacherDTO>> getTeacherList(TeacherDTO TeacherDTO, Integer pageNo, Integer pageSize);

    /**
     * @功能：添加教师
     * @param jsonObject
     * @return
     */
    Result<?> addTeacher(JSONObject jsonObject);

    /**
     * @功能：删除教师
     * @param id
     * @return
     */
    Result<?> delectTeacher(String id);

    /**
     * @功能：修改教师信息
     * @param jsonObject
     * @return
     */
    Result<?> editTeacher(JSONObject jsonObject);

}
