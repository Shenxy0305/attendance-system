package org.jeecg.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 教师课程表
 * </p>
 *
 * @Author shenxinyuan
 * @since 2022-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("at_teacher_course")
public class TeacherCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 教师id
     */
    @TableField(value = "teacher_id")
    private String teacherId;

    /**
     * 课程id
     */
    @TableField(value = "course_id")
    private String courseId;
}
