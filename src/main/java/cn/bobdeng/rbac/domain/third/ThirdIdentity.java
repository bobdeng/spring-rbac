package cn.bobdeng.rbac.domain.third;

import cn.bobdeng.rbac.archtype.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class ThirdIdentity implements Entity<Integer, ThirdDescription> {
    private Integer id;
    private ThirdDescription description;

    public ThirdIdentity(ThirdDescription description) {
        this.description = description;
    }

    @Override
    public ThirdDescription description() {
        return description;
    }
}
