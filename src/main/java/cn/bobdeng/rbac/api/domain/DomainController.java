package cn.bobdeng.rbac.api.domain;

import cn.bobdeng.rbac.domain.DomainDescription;
import cn.bobdeng.rbac.domain.DomainRepository;
import cn.bobdeng.rbac.domain.Domains;
import cn.bobdeng.rbac.security.Admin;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
public class DomainController {
    private final DomainRepository domainRepository;

    public DomainController(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }


    @PostMapping("/api/1.0/domains")
    @Admin
    @Transactional
    public void newDomain(@RequestBody NewDomainForm form) {
        new Domains(domainRepository).newDomain(new DomainDescription(form.getName(), form.getTenant()));
    }

    @DeleteMapping("/api/1.0/domains/{id}")
    @Admin
    public void deleteDomain(@PathVariable Integer id) {
        new Domains(domainRepository).delete(id);
    }
}
