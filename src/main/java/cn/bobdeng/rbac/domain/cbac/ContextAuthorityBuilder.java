package cn.bobdeng.rbac.domain.cbac;

public class ContextAuthorityBuilder {
    private Integer userId;
    private String[] roles;
    private String[] organizations;
    public  ContextAuthorityBuilder withUser(int userId) {
        this.userId = userId;
        return this;
    }

    public ContextAuthorityBuilder withRoles(String... roleNames) {
        this.roles = roleNames;
        return this;
    }

    public ContextAuthorityBuilder withOrganizations(String... organizationNames) {
        this.organizations = organizationNames;
        return this;
    }
    public ContextAuthority build(){
        return new ContextAuthority(userId,roles,organizations);
    }
}
