package cn.bobdeng.rbac.server.dao;

import cn.bobdeng.rbac.domain.Tenant;
import cn.bobdeng.rbac.domain.cbac.Context;
import cn.bobdeng.rbac.domain.cbac.ContextAuthority;
import cn.bobdeng.rbac.domain.cbac.ContextDescription;
import cn.bobdeng.rbac.domain.cbac.ContextObject;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Table(name = "t_cbac_context")
@ToString
public class CbacContextDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer tenantId;
    private String objectType;
    private String objectId;
    private String authority;

    public CbacContextDO(Context entity, Tenant tenant) {
        this.tenantId = tenant.identity();
        this.objectId = entity.description().getObject().getIdentity();
        this.objectType = entity.description().getObject().getType();
        this.authority = new Gson().toJson(entity.description().getAuthority());
    }

    public Context toEntity() {
        ContextObject object = new ContextObject(objectType, objectId);
        ContextAuthority auth = new Gson().fromJson(authority, ContextAuthority.class);
        ContextDescription description = new ContextDescription(object, auth);
        return new Context(id,description);
    }
}
