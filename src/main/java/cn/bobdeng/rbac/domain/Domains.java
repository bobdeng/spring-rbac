package cn.bobdeng.rbac.domain;

public class Domains {
    private DomainRepository domainRepository;

    public Domains(DomainRepository domainRepository) {

        this.domainRepository = domainRepository;
    }

    public void newDomain(DomainDescription description) {
        if (this.domainRepository.findByDomain(description.getDomain()).isPresent()) {
            throw new DuplicateDomainException();
        }
        this.domainRepository.save(new Domain(description));
    }
}
