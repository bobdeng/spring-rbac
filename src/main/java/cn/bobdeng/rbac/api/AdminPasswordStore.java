package cn.bobdeng.rbac.api;

import java.util.Optional;

public interface AdminPasswordStore {
    Optional<String> password();
}
