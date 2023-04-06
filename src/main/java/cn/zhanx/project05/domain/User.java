package cn.zhanx.project05.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "t_rd_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名，来源于employee表,根据业务部门的规则产生唯一的员工号码
     * 800066
     */
    @TableField(value = "username")
    private String username;

    /**
     * 传参用123456
     * 用户密码 这里是123456
     */
    @TableField(value = "password")
    //@JsonIgnore
    private String password;

    /**
     * 用户状态,1有效 0 无效或锁工号
     */
    @TableField(value = "status")
    private Byte status;

    /**
     * 外键于本表的username字段，如果有上级的话，这个字段值就是上级的username
     */
    @TableField(value = "higher")
    private String higher;

    /**
     * 用户描述
     */
    @TableField(value = "comment")
    private String comment;

    @TableField(value = "update_user")
    private String updateUser;

    @TableField(value = "create_time")
    private Long createTime;

    @TableField(value = "update_time")
    private Long updateTime;

}
