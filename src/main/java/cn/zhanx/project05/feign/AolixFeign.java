package cn.zhanx.project05.feign;

import cn.zhanx.project05.vo.common.AolixResp;
import cn.zhanx.project05.vo.common.AolixReq;
import cn.zhanx.project05.vo.common.LoginResp;
import cn.zhanx.project05.vo.common.UserQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "${feignapi.jutrade.domain}", name = "jutrade")
public interface AolixFeign {

    /**
     * 插入优惠券 测试
     * @param req
     * @return result
     */
    @PostMapping(value = "/ticket/shh/add-coupon")
    AolixResp addCoupon(@RequestBody AolixReq req);

    //https://m.jutrade.cn/ticket/public/login"
    @PostMapping(value ="/ticket/public/login",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    LoginResp login(@RequestBody UserQuery userQuery);

    @GetMapping(value = "/ticket/api/order/list-order",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    Object listOrder(@RequestHeader String token);

}
