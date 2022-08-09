package cn.bobdeng.rbac.api.organization;

import lombok.Data;

@Data
public class NewOrganizationForm {
    private String name;
    private Integer parent;
}
