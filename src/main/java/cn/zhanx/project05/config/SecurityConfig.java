package cn.zhanx.project05.config;

import cn.zhanx.project05.filter.JwtAuthenticationTokenFilter;
import cn.zhanx.project05.vo.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().antMatchers("/images/**","/js/**","/webjars/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable() //关闭csrf
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors() //开启跨域
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").anonymous() //只能未登录的人才能访问
                .antMatchers("/demo/hello","/**/*.js",
                        "/**/*.css",
                        "/**/*.woff","/v2/api-docs/**","/webjars/**",
                        "/**/*.ttf","/layui/**","/images/**", "/swagger-resources/**",
                        "/swagger-ui.html", "/doc.html","/eureka/","/demo/feignLogin","/demo/feignListOrder").permitAll() //不管登录没登录都能访问
                //除了上面的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //把这个jwtAuthenticationTokenFilter过滤器放在UsernamePasswordAuthenticationFilter之前
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager=authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }



}
