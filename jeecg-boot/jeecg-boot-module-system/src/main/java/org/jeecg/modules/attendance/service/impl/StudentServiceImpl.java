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
import org.jeecg.modules.attendance.entity.Student;
import org.jeecg.modules.attendance.mapper.StudentMapper;
import org.jeecg.modules.attendance.service.IStudentService;
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

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学生管理实现类
 * </p>
 *
 * @Author shenxinyuan
 * @since 2021-12-13
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private BaseCommonService baseCommonService;

    @Override
    public Result<IPage<StudentDTO>> getStudentList(StudentDTO studentDTO, Integer pageNo, Integer pageSize) {
        Result<IPage<StudentDTO>> result = new Result<IPage<StudentDTO>>();
        IPage<StudentDTO> page = new Page<StudentDTO>(pageNo, pageSize);
        List<StudentDTO> list = studentMapper.getStudentList(studentDTO);
        page.setRecords(list).setTotal(list.size());
        result.setSuccess(true);
        result.setResult(page);
        return result;
    }

    @Override
    public Result<?> addStudent(JSONObject jsonObject) {
        Result<StudentDTO> result = new Result<StudentDTO>();
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<SysRole>();
        sysRoleQueryWrapper.eq("role_name", "学生");
        String selectedRoles = sysRoleMapper.selectOne(sysRoleQueryWrapper).getId();
        String depart = jsonObject.getString("depart");
        Student student = new Student();
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
            student.setUserId(sysUser.getId());
            student.setClazzId(jsonObject.getString("clazzId"));
            student.setCreateBy(sysUser.getCreateBy());
            student.setCreateTime(sysUser.getCreateTime());
            studentMapper.insert(student);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    @Override
    public Result<?> delectStudent(String id) {
        baseCommonService.addLog("删除用户，id： " +id ,CommonConstant.LOG_TYPE_2, 3);
        sysUserService.deleteUser(id);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
        queryWrapper.eq("user_id", id);
        studentMapper.delete(queryWrapper);
        return Result.ok("删除成功");
    }

    @Override
    public Result<?> editStudent(JSONObject jsonObject) {
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
