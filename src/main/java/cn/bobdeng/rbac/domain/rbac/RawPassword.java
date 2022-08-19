package cn.bobdeng.rbac.domain.rbac;

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
        if ("none".equals(passwordPolicy)) {
            return;
        }
        if ("strong".equals(passwordPolicy)) {
            check(STRONG_CHECKERS);
        }
        check(WEAK_CHECKERS);
    }

    private void check(List<Predicate<String>> strongChecker) {
        if (strongChecker.stream().anyMatch(checker -> checker.test(rawPassword))) {
            throw new WeakPasswordException();
        }
    }
}
