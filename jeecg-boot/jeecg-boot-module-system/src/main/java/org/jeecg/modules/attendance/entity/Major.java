package org.jeecg.modules.attendance.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 专业表
 * </p>
 *
 * @Author shenxinyuan
 * @since 2021-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("at_major")
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "major_id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 专业名称
     */
    @TableField(value = "major_name")
    private String majorName;

    /**
     * 院系id
     */
    @TableField(value = "depart_id")
    private String departId;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @TableLogic
    @TableField(value = "del_flag")
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
