package cn.bobdeng.rbac.api;

import cn.bobdeng.rbac.domain.AdminPassword;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AdminPasswordNotifierImpl implements AdminPassword.Notifier, AdminPassword.Store {
    private String password;
    private String encodedPassword;

    @Override
    public void notify(String password) {
        this.password = password;
        System.out.println(password);
    }

    public void clear() {
        this.password = null;
        this.encodedPassword = null;
    }

    public String getPassword() {
        return password;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    @Override
    public void save(String password) {
        this.encodedPassword = password;
    }

    @Override
    public Optional<String> get() {
        return Optional.ofNullable(encodedPassword);
    }
}
