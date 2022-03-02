package org.jeecg.modules.attendance.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.system.entity.SysUser;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentDTO extends SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 学号
     */
    private String account;
    /**
     * 学生姓名
     */
    private String name;
    /**
     * 学生性别
     */
    private Integer sex;
    /**
     * 学生专业id
     */
    private String majorId;
    /**
     * 学生专业名称
     */
    private String majorName;
    /**
     * 学生班级id
     */
    private String clazzId;
    /**
     * 学生班级名称
     */
    private String clazzName;
    /**
     * 学生电话
     */
    private String phone;

}
