package org.jeecg.modules.attendance.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.attendance.DTO.StudentDTO;
import org.jeecg.modules.attendance.DTO.TeacherDTO;
import org.jeecg.modules.attendance.entity.Student;
import org.jeecg.modules.attendance.entity.Teacher;
import org.jeecg.modules.attendance.entity.TeacherClazz;
import org.jeecg.modules.attendance.entity.TeacherCourse;
import org.jeecg.modules.attendance.mapper.TeacherClazzMapper;
import org.jeecg.modules.attendance.mapper.TeacherCourseMapper;
import org.jeecg.modules.attendance.mapper.TeacherMapper;
import org.jeecg.modules.attendance.service.ITeacherService;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.system.entity.SysRole;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.mapper.SysRoleMapper;
import org.jeecg.modules.system.mapper.SysUserMapper;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 教师管理实现类
 * </p>
 *
 * @Author shenxinyuan
 * @since 2022-02-22
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private TeacherClazzMapper teacherClazzMapper;

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private BaseCommonService baseCommonService;

    @Override
    public Result<IPage<TeacherDTO>> getTeacherList(TeacherDTO TeacherDTO, Integer pageNo, Integer pageSize) {
        Result<IPage<TeacherDTO>> result = new Result<IPage<TeacherDTO>>();
        IPage<TeacherDTO> page = new Page<TeacherDTO>(pageNo, pageSize);
        List<TeacherDTO> list = teacherMapper.getTeacherList(TeacherDTO);
        page.setRecords(list).setTotal(list.size());
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @Override
    public Result<?> addTeacher(JSONObject jsonObject) {
        Result<StudentDTO> result = new Result<StudentDTO>();
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<SysRole>();
        sysRoleQueryWrapper.eq("role_name", "教师");
        String selectedRoles = sysRoleMapper.selectOne(sysRoleQueryWrapper).getId();
        String depart = jsonObject.getString("depart");
        Teacher teacher = new Teacher();
        try {
            SysUser user = JSON.parseObject(jsonObject.toJSONString(), SysUser.class);
            user.setWorkNo(user.getUsername());
            user.setCreateTime(new Date());//设置创建时间
            String salt = oConvertUtils.randomGen(8);
            user.setSalt(salt);
            String passwordEncode = PasswordUtil.encrypt(user.getUsername(), user.getPassword(), salt);
            user.setPassword(passwordEncode);
            user.setStatus(1);
            user.setDelFlag(CommonConstant.DEL_FLAG_0);
            // 保存用户走一个service 保证事务
            sysUserService.saveUser(user, selectedRoles, depart);
            QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<SysUser>();
            sysUserQueryWrapper.eq("work_no", user.getWorkNo());
            SysUser sysUser = sysUserMapper.selectOne(sysUserQueryWrapper);
            teacher.setUserId(sysUser.getId());
            teacher.setCreateBy(sysUser.getCreateBy());
            teacher.setCreateTime(sysUser.getCreateTime());
            teacherMapper.insert(teacher);
            QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<Teacher>();
            teacherQueryWrapper.eq("user_id", sysUser.getId());
            Teacher newTeacher = teacherMapper.selectOne(teacherQueryWrapper);
            String[] clazzIds = String.valueOf(jsonObject.get("clazzIds")).split(",");
            String[] courseIds = String.valueOf(jsonObject.get("courseIds")).split(",");
            for (String clazzId: clazzIds) {
                TeacherClazz teacherClazz = new TeacherClazz();
                teacherClazz.setTeacherId(newTeacher.getId());
                teacherClazz.setClazzId(clazzId);
                teacherClazzMapper.insert(teacherClazz);
            }
            for (String courseId: courseIds) {
                TeacherCourse teacherCourse = new TeacherCourse();
                teacherCourse.setTeacherId(newTeacher.getId());
                teacherCourse.setCourseId(courseId);
                teacherCourseMapper.insert(teacherCourse);
            }
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @Override
    public Result<?> delectTeacher(String id) {
        baseCommonService.addLog("删除用户，id： " +id ,CommonConstant.LOG_TYPE_2, 3);
        sysUserService.deleteUser(id);
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<Teacher>();
        teacherQueryWrapper.eq("user_id", id);
        String teacherId = teacherMapper.selectOne(teacherQueryWrapper).getId();
        teacherMapper.delete(teacherQueryWrapper);
        QueryWrapper<TeacherClazz> teacherClazzQueryWrapper = new QueryWrapper<TeacherClazz>();
        QueryWrapper<TeacherCourse> teacherCourseQueryWrapper = new QueryWrapper<TeacherCourse>();
        teacherClazzQueryWrapper.eq("teacher_id", teacherId);
        teacherCourseQueryWrapper.eq("teacher_id", teacherId);
        teacherClazzMapper.delete(teacherClazzQueryWrapper);
        teacherCourseMapper.delete(teacherCourseQueryWrapper);
        return Result.ok("删除成功");
    }

    @Override
    public Result<?> editTeacher(JSONObject jsonObject) {
        Result<StudentDTO> result = new Result<StudentDTO>();
        try {
            SysUser sysUser = sysUserService.getById(jsonObject.getString("userId"));
            baseCommonService.addLog("编辑用户，id： " +jsonObject.getString("userId") ,CommonConstant.LOG_TYPE_2, 2);
            if(sysUser==null) {
                result.error500("未找到对应实体");
            } else {
                SysUser user = new SysUser();
                user.setId(jsonObject.getString("userId"));
                user.setRealname(jsonObject.getString("realname"));
                user.setPhone(jsonObject.getString("phone"));
                user.setUpdateTime(new Date());
                sysUserMapper.updateById(user);
                result.success("修改成功!");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }
}
