package org.jeecg.modules.attendance.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.attendance.DTO.StudentDTO;
import org.jeecg.modules.attendance.DTO.TeacherDTO;
import org.jeecg.modules.attendance.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @Author shenxinyuan
 * @since 2022-02-22
 */
@RestController
@RequestMapping("/teacher")
@Api(tags="教师管理")
@Slf4j
public class TeacherController {

    @Autowired
    private ITeacherService teacherService;

    /**
     * 查询全部教师
     * @param TeacherDTO
     * @return 工号 教师姓名 教师专业 教学班级 手机号
     */
    @ApiOperation("班级学生查询接口")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<TeacherDTO>> getTeacherList(TeacherDTO TeacherDTO,
                                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        return teacherService.getTeacherList(TeacherDTO, pageNo, pageSize);
    }

    /**
     * 添加教师
     * @param jsonObject
     * @return 工号 密码 姓名 院系 课程 性别 手机号码
     */
    @ApiOperation("班级学生查询接口")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addTeacher(@RequestBody JSONObject jsonObject) {
        return teacherService.addTeacher(jsonObject);
    }

    /**
     * 删除学生
     * @param id
     * @return 学号 密码 姓名 院系 性别 手机号码
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result<?> deleteTeacher(@RequestParam(name="id",required=true) String id) {
        return teacherService.delectTeacher(id);
    }

    /**
     * 修改教师信息
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Result<?> editTeacher(@RequestBody JSONObject jsonObject) {
        return teacherService.editTeacher(jsonObject);
    }

}
