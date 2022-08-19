package cn.bobdeng.rbac.domain.config;

import java.util.Arrays;
import java.util.List;

public class BaseParameters {

    public static final String PASSWORD_POLICY = "rbac.password_policy";

    public static List<ParameterName> list() {
        return Arrays.asList(
                new ParameterName(PASSWORD_POLICY, "密码强度要求(strong/weak/none)", "none")
        );
    }
}
