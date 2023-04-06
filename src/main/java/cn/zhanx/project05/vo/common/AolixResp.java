package cn.zhanx.project05.vo.common;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AolixResp<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 3573841830343061309L;

    public static final int SUCC_CODE = 0;

    @ApiModelProperty(value = "状态码 0:成功,其它为失败)")
    private int code;

    @ApiModelProperty(value = "错误码")
    private String errCode;

    @ApiModelProperty(value = "错误描述")
    private String errMsg;

    @ApiModelProperty(value = "返回时间")
    private Long sysTime;

    @ApiModelProperty(value = "业务数据")
    private T data;


}

