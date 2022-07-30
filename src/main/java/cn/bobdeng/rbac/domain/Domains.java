package cn.bobdeng.rbac.domain;

public class Domains {
    private DomainRepository domainRepository;

    public Domains(DomainRepository domainRepository) {

        this.domainRepository = domainRepository;
    }

    public Domain newDomain(DomainDescription description) {
        if (this.domainRepository.findByDomain(description.getDomain()).isPresent()) {
            throw new DuplicateDomainException();
        }
        return this.domainRepository.save(new Domain(description));
    }
}
