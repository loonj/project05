package cn.zhanx.project05.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_rd_banner")
public class Banner extends Model<Banner> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键,系统自动生成，可以不用管它", example = "0", hidden = true)
    private Long id;

    @JSONField(name = "image")
    private String image;
    private String url;
    private Integer status;
    private String comment;
    private String updateUser;
    private Long createTime;
    private Long updateTime;
}
