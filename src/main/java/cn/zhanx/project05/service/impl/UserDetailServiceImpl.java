package cn.zhanx.project05.service.impl;

import cn.zhanx.project05.domain.LoginUser;
import cn.zhanx.project05.domain.User;
import cn.zhanx.project05.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1根据用户名从数据库获取用户
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名错误");
        }
        return new LoginUser(user);
    }
}
