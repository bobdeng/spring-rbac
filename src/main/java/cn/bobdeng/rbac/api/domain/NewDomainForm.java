package cn.bobdeng.rbac.api.domain;

import lombok.Data;

@Data
public class NewDomainForm {
    private String name;
    private Integer tenant;
}
