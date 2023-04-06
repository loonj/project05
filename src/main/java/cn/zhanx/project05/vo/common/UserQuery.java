package cn.zhanx.project05.vo.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class UserQuery {
    private String name;
    private String pwd;
}