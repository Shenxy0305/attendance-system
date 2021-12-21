package org.jeecg.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Columns;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @Author shenxinyuan
 * @since 2021-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("at_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "student_id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生姓名
     */
    @TableField(value = "student_name")
    private String name;

    /**
     * 学生年龄
     */
    @TableField(value = "student_age")
    private Integer age;

    /**
     * 学生班级id
     */
    @TableField(value = "student_clazz")
    private String clazzId;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @TableLogic
    @TableField(value = "del_flg")
    private Integer delFlag;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;
}
