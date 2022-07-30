package cn.bobdeng.rbac.api;

import org.springframework.stereotype.Service;

@Service
public class AdminPasswordNotifierImpl implements AdminPasswordNotifier {
    private String password;

    @Override
    public void notify(String password) {
        this.password = password;
        System.out.println(password);
    }

    public void clear() {
        this.password = null;
    }

    public String getPassword() {
        return password;
    }
}
