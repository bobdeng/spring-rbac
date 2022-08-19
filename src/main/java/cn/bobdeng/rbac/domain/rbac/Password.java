package cn.bobdeng.rbac.domain.rbac;

import cn.bobdeng.rbac.archtype.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Password implements Entity<Integer, PasswordDescription> {
    private Integer id;
    private PasswordDescription description;

    @Override
    public PasswordDescription description() {
        return description;
    }


}
