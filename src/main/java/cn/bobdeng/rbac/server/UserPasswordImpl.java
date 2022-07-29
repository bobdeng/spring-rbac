package cn.bobdeng.rbac.server;

import cn.bobdeng.rbac.domain.Password;
import cn.bobdeng.rbac.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserPasswordImpl implements User.UserPassword {
    private User user;
    private PasswordDAO passwordDAO;

    public UserPasswordImpl(User user, TenantRepositoryImpl tenantRepository, PasswordDAO passwordDAO) {
        this.user = user;
        this.passwordDAO = passwordDAO;
    }

    @Override
    public List<Password> subList(int from, int to) {
        return null;
    }

    @Override
    public Stream<Password> list() {
        return null;
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
    public int size() {
        return 0;
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
