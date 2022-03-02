package org.jeecg.modules.attendance.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.system.entity.SysUser;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeacherDTO extends SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 工号
     */
    private String account;
    /**
     * 教师姓名
     */
    private String name;
    /**
     * 教师性别
     */
    private Integer sex;
    /**
     * 教师专业id
     */
    private String majorId;
    /**
     * 教师专业名称
     */
    private String majorName;
    /**
     * 教师班级id
     */
    private String clazzId;
    /**
     * 教师班级名称
     */
    private String clazzName;
    /**
     * 教师课程id
     */
    private String courseId;
    /**
     * 教师课程名称
     */
    private String courseName;
    /**
     * 教师电话
     */
    private String phone;

}
