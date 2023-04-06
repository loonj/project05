package cn.zhanx.project05.vo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AolixReq implements Serializable {
    private static final long serialVersionUID = 3573841830343061309L;

    @ApiModelProperty(value = "openidinfo")
    private String openidinfo;
}
