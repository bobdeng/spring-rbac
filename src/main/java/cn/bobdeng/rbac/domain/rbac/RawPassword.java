package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.FieldChecker;
import cn.bobdeng.rbac.archtype.FieldIllegalException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@Getter
public class RawPassword {
    private String rawPassword;
    private static final String SEED_NUMBERS = "0123456789";
    private static final String SEED_LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String SEED_CAP_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String SEED_NOT_WORDS = "!@#$%&*";
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

    public static RawPassword random() {
        String rawPassword = takeFrom(SEED_NUMBERS, 2) +
                takeFrom(SEED_CAP_LETTERS, 3) +
                takeFrom(SEED_LOWER_LETTERS, 3) +
                takeFrom(SEED_NOT_WORDS, 2);
        return new RawPassword(rawPassword);
    }

    private static String takeFrom(String seed, int length) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(seed.charAt(random.nextInt(seed.length())));
        }
        return result.toString();
    }

    public void ensureStrength(String passwordPolicy) {
        if ("strong".equals(passwordPolicy)) {
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
