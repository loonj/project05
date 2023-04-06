package cn.zhanx.project05.filter;

import cn.zhanx.project05.domain.LoginUser;
import cn.zhanx.project05.utils.JwtUtil;
import cn.zhanx.project05.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 只走一次，请求前
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1 获取token hearder的token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //放行，让后面的过滤器执行
            filterChain.doFilter(request,response);
            return;
        }

        //2 解析token
        String username;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            username = claims.getSubject();
        }catch (Exception e){
            throw new RuntimeException("token不合法");
        }

        //写登出接口时，只要删除redis里的信息就可以了
        //3 获取username
        LoginUser loginUser = redisCache.getCacheObject("login:"+username);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("当前用户未登录");
        }

        //4 封装Authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, null);

        //5 存入SecurityContextHoloder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //放行，让后面的过滤器执行
        filterChain.doFilter(request,response);
    }
}
