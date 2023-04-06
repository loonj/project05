package cn.zhanx.project05.vo.common;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
public class LoginResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String errCode;
    private String errMsg;
    private Long sysTime;
    private DataVo data;

    @Data
    @NoArgsConstructor
    public static class DataVo{
        private String msg;
        private String name;
        @JsonProperty("session_id")
        private String sessionId;
    }
}

//    {
//        "data": {
//        "msg": "登录成功",
//                "name": "800012",
//                "session_id": "ticket-54dd3838ea2c4d939c44435a58bb0b38"
//    },
//        "code": 0,
//            "errCode": "",
//            "errMsg": "success",
//            "sysTime": 1603091113702
//    }