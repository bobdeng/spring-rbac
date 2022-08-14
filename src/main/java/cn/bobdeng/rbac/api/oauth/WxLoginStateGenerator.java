package cn.bobdeng.rbac.api.oauth;

public interface WxLoginStateGenerator {
    String generate();

    boolean verify(String code);
}
