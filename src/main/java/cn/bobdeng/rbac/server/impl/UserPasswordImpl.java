package cn.bobdeng.rbac.server.impl;

import cn.bobdeng.rbac.domain.Password;
import cn.bobdeng.rbac.domain.User;
import cn.bobdeng.rbac.server.dao.PasswordDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserPasswordImpl implements User.UserPassword {
    private PasswordDAO passwordDAO;

    public UserPasswordImpl(PasswordDAO passwordDAO) {
        this.passwordDAO = passwordDAO;
    }

    @Override
    public Optional<Password> findByIdentity(Integer integer) {
        return passwordDAO.findById(integer);
    }

    @Override
    public Password save(Password entity) {
        return passwordDAO.save(entity);
    }

    @Override
    public String encodePassword(String rawPassword) {
        return new BCryptPasswordEncoder().encode(rawPassword);
    }

    @Override
    public boolean match(String rawPassword, String password) {
        return new BCryptPasswordEncoder().matches(rawPassword, password);
    }
}
