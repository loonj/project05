package cn.zhanx.project05.controller;

import cn.zhanx.project05.domain.Banner;
import cn.zhanx.project05.feign.AolixFeign;
import cn.zhanx.project05.service.BannerService;
import cn.zhanx.project05.vo.common.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("demo")
@Api(value = "Demo相关的controller", tags = "Demo相关的接口")
public class DemoController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private AolixFeign aolixFeign;

    @ApiOperation("hello world")
    @GetMapping("hello")
    public String hello(){
        return "hello world .zhanx.cn";
    }

    @ApiOperation("查询所有Banner")
    @GetMapping("listBanner")
    public Result<List<Banner>> listBanner(){
        return Result.success(bannerService.list());
    }

    @ApiOperation(value = "加优惠券")
    @PostMapping(value = "addCoupon")
    public Result<AolixResp> addCoupon(@RequestBody AolixReq req) {
        AolixResp aolixResp = aolixFeign.addCoupon(req);
        return Result.success(aolixResp);
    }

    @ApiOperation(value = "feignLogin登录测试")
    @PostMapping(value = "feignLogin")
    public Result<LoginResp> feignLogin(@RequestBody UserQuery req) {
        return Result.success(aolixFeign.login(req));
    }

    @ApiOperation(value = "feign查询订单测试")
    @GetMapping(value = "feignListOrder")
    public Result<Object> feignListOrder(@RequestHeader String token2) {
        return Result.success(aolixFeign.listOrder(token2));
    }

}
