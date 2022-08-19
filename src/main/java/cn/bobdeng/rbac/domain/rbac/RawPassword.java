package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.FieldChecker;
import cn.bobdeng.rbac.archtype.FieldIllegalException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Getter
public class RawPassword {
    private String rawPassword;
    private static final List<Predicate<String>> WEAK_CHECKERS = Arrays.asList(
            (password) -> password.length() < 8,
            (password) -> !password.matches(".*[a-z]+.*"),
            (password) -> !password.matches(".*[0-9]+.*"),
            (password) -> !password.matches(".*[A-Z]+.*")
    );
    private static final List<Predicate<String>> STRONG_CHECKERS = Arrays.asList(
            (password) -> password.length() < 10,
            (password) -> !password.matches(".*[a-z]+.*"),
            (password) -> !password.matches(".*[0-9]+.*"),
            (password) -> !password.matches(".*\\W+.*"),
            (password) -> !password.matches(".*[A-Z]+.*")
    );


    public RawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    public void ensureWeakStrength(String passwordPolicy) {
        if ("strong".equals(passwordPolicy)) {
            check(WEAK_CHECKERS);
            check(STRONG_CHECKERS);
        }
        if ("weak".equals(passwordPolicy)) {
            check(WEAK_CHECKERS);
        }
    }

    private void check(List<Predicate<String>> strongChecker) {
        if (strongChecker.stream().anyMatch(checker -> checker.test(rawPassword))) {
            throw new FieldIllegalException(List.of(new FieldChecker.FieldError("password", "密码强度不足")));
        }
    }
}
