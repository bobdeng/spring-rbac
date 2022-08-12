package cn.bobdeng.rbac;

import cn.bobdeng.rbac.domain.AdminPassword;

import java.util.Optional;

public class NotifierImpl implements AdminPassword.Notifier, AdminPassword.Store {
    private String password;

    @Override
    public void notify(String password) {
        System.out.println(password);
    }

    @Override
    public void save(String password) {
        this.password = password;
    }

    @Override
    public Optional<String> get() {
        return Optional.ofNullable(password);
    }
}
