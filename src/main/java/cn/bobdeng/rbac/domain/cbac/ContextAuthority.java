package cn.bobdeng.rbac.domain.cbac;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
public class ContextAuthority {
    private Integer userId;
    private String[] roles;
    private String[] organizations;

    public static ContextAuthorityBuilder builder() {
        return new ContextAuthorityBuilder();
    }

    public ContextAuthority(Integer userId, String[] roles, String[] organizations) {
        this.userId = userId;
        this.roles = roles;
        this.organizations = organizations;
    }

    public boolean match(ContextAuthority actual) {
        if (this.userId != null && this.userId.equals(actual.userId)) {
            return true;
        }
        if (anyPropsMatch(this.roles, actual.roles)) return true;
        return anyPropsMatch(this.organizations, actual.organizations);
    }

    private boolean anyPropsMatch(String[] expect, String[] actual) {
        if (expect == null || expect.length <= 0) {
            return false;
        }
        Set<String> roleNameSet = Stream.of(expect).collect(Collectors.toSet());
        return Stream.of(actual).anyMatch(roleNameSet::contains);
    }

    public void validate() {
        if (userId == null && isEmpty(this.roles) && isEmpty(organizations)) {
            throw new EmptyAuthorityException();
        }
    }

    private boolean isEmpty(String[] array) {
        return array == null || array.length == 0;
    }
}
