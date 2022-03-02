package org.jeecg.modules.attendance.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.attendance.service.IClazzService;
import org.jeecg.modules.attendance.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @Author shenxinyuan
 * @since 2022-1-5
 */
@RestController
@RequestMapping("/clazz")
@Api(tags="班级管理")
@Slf4j
public class ClazzController {


}
