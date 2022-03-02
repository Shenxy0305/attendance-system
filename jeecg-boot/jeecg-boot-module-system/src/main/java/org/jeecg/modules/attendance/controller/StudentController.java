package org.jeecg.modules.attendance.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.attendance.DTO.StudentDTO;
import org.jeecg.modules.attendance.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @Author shenxinyuan
 * @since 2021-12-13
 */
@RestController
@RequestMapping("/student")
@Api(tags="学生管理")
@Slf4j
public class StudentController {

    @Autowired
    private IStudentService studentService;

    /**
     * 查询全部学生
     * @param studentDTO
     * @return 学号 学生姓名 学生专业 班级 手机号
     */
    @ApiOperation("班级学生查询接口")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<IPage<StudentDTO>> getStudentList(StudentDTO studentDTO,
                                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        return studentService.getStudentList(studentDTO, pageNo, pageSize);
    }

    /**
     * 添加学生
     * @param jsonObject
     * @return 学号 密码 姓名 院系 性别 手机号码
     */
    @ApiOperation("班级学生查询接口")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<?> addStudent(@RequestBody JSONObject jsonObject) {
        return studentService.addStudent(jsonObject);
    }

    /**
     * 删除学生
     * @param id
     * @return 学号 密码 姓名 院系 性别 手机号码
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Result<?> deleteStudent(@RequestParam(name="id",required=true) String id) {
        return studentService.delectStudent(id);
    }

    /**
     * 修改学生信息
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public Result<?> editStudent(@RequestBody JSONObject jsonObject) {
        return studentService.editStudent(jsonObject);
    }



}
