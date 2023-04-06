package cn.zhanx.project05.vo.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("API统一返回结果对象")
public class Result<T> {

    private static final int SUCCESS = 0;
    private static final int ERROR = -1;
    @ApiModelProperty("API需要返回的结果对象")
    private T data;
    @ApiModelProperty("0:成功,-1:失败")
    private int code;
    @ApiModelProperty("失败时的错误码")
    private String errCode;
    @ApiModelProperty("失败时的错误信息，当code=1时，该值为:success")
    private String errMsg;
    @ApiModelProperty("时间戳 单位毫秒")
    private long sysTime;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, SUCCESS, "", "success");
    }

    public static <T> Result<T> error(String errMsg) {
        return error(null, errMsg);
    }

    public static <T> Result<T> error(String errCode, String errMsg) {
        return new Result<>(null, ERROR, errCode, errMsg);
    }


    public static <T> Result<T> error(String errCode, String errMsg, T data) {
        return new Result<>(data, ERROR, errCode, errMsg);
    }

    private Result(T data, int code, String errCode, String errMsg) {
        this.data = data;
        this.code = code;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.sysTime = System.currentTimeMillis();
    }

}