package org.jeecg.modules.attendance.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.DictModel;
import org.jeecg.modules.attendance.entity.Clazz;
import org.jeecg.modules.attendance.mapper.ClazzMapper;
import org.jeecg.modules.attendance.service.IClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 班级管理实现类
 * </p>
 *
 * @Author shenxinyuan
 * @since 2022-1-5
 */
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements IClazzService {


}
