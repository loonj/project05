package cn.zhanx.project05.service;

import cn.zhanx.project05.domain.User;
import cn.zhanx.project05.vo.common.Result;

import java.util.Map;

public interface LoginService {
    public Result<Map<String,String>> login(User user);

    public Result<String> logout();
}
