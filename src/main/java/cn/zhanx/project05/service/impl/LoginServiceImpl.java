package cn.zhanx.project05.service.impl;

import cn.zhanx.project05.domain.LoginUser;
import cn.zhanx.project05.domain.User;
import cn.zhanx.project05.service.LoginService;
import cn.zhanx.project05.utils.JwtUtil;
import cn.zhanx.project05.utils.RedisCache;
import cn.zhanx.project05.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result<Map<String,String>> login(User user) {
        //使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        //校验失效了
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名密码错误");
        }

        //自己生成jwt给前端
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        System.out.println(loginUser);
        String jwt= JwtUtil.createJWT(loginUser.getUser().getUsername(),null);
        Map<String,String> map=new HashMap<>();
        map.put("token",jwt);
        redisCache.setCacheObject("login:"+loginUser.getUser().getUsername(),loginUser);

        //系统用户相关信息放入redis
        return Result.success(map);
    }

    @Override
    public Result<String> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser =(LoginUser)authentication.getPrincipal();
        String username = loginUser.getUser().getUsername();
        redisCache.deleteObject("login:"+username);
        return Result.success(username+"用户退出成功");
    }
}
