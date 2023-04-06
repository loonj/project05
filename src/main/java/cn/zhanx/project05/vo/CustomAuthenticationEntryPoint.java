package cn.zhanx.project05.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 未登录执行这里，相当于表单过滤器
 * @date: 2021/7/2 4:44 下午
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","401");
        resultData.put("msg", "未登陆");
        out.write(new ObjectMapper().writeValueAsString(resultData));
        out.flush();
        out.close();
    }
}