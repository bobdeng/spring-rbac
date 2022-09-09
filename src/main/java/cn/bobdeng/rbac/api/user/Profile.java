package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.domain.rbac.User;
import lombok.Getter;

@Getter
public class Profile {
    private String name;
    private Integer id;

    public Profile(User user) {
        this.name = user.description().getName();
        this.id = user.identity();
    }

    public static Profile admin() {
        return new Profile("系统管理员");
    }

    public Profile(String name) {
        this.name = name;
    }
}
