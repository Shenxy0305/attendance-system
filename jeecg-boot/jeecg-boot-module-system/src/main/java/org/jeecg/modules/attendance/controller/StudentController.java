package org.jeecg.modules.attendance.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.attendance.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    IStudentService studentService;

    /**
     * 查询班级学生
     * @param clazzId
     * @return
     */
    @ApiOperation("班级学生查询接口")
    @RequestMapping(value = "/clazz", method = RequestMethod.POST)
    public Result<?> getClazzStudent(@RequestParam Integer clazzId) {
        return studentService.getStudent(clazzId);
    }

}
