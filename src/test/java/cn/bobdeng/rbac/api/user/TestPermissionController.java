package cn.bobdeng.rbac.api.user;

import cn.bobdeng.rbac.security.Permission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
public class TestPermissionController {
    @GetMapping("/test_permission_granted")
    @Permission(allows = "user.create")
    @Transactional
    public String testPermissionGranted() {
        return "permission_granted";
    }

    @GetMapping("/test_permission_denied")
    @Permission(allows = "permission.not.exist")
    @Transactional
    public String permissionDenied() {
        return "hello";
    }
}
