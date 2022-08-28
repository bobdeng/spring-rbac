package cn.bobdeng.rbac.api.cbac;

import cn.bobdeng.rbac.security.ObjectPermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestCbacController {
    @GetMapping("/cbac/{id}")
    @ObjectPermission(type = "mission", id = "#id")
    public String shouldNotAllow(@PathVariable String id) {
        return "通过";
    }
}
