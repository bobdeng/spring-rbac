package cn.bobdeng.rbac.api.oauth;

import lombok.Getter;

@Getter
public class WxUserInfo {
    private String nickname;

    public WxUserInfo(String nickname) {
        this.nickname = nickname;
    }
}
