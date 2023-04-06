package cn.zhanx.project05.service.impl;

import cn.zhanx.project05.domain.User;
import cn.zhanx.project05.mapper.UserMapper;
import cn.zhanx.project05.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}

