package cn.bobdeng.rbac.domain;

import cn.bobdeng.rbac.archtype.FieldIllegalException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DomainsTest {
    @Test
    public void save_domain() {
        DomainDescription localhost = new DomainDescription("localhost", 1);
        DomainRepository domainRepository = mock(DomainRepository.class);
        Domains domains = new Domains(domainRepository);
        domains.newDomain(localhost);
        verify(domainRepository).save(new Domain(localhost));
    }

    @Test
    public void throw_when_domain_duplicate() {
        DomainDescription localhost = new DomainDescription("localhost", 1);
        DomainRepository domainRepository = mock(DomainRepository.class);
        when(domainRepository.findByDomain("localhost")).thenReturn(Optional.of(new Domain(localhost)));
        Domains domains = new Domains(domainRepository);
        assertThrows(DuplicateDomainException.class, () -> domains.newDomain(localhost));
    }

    @Test
    public void throw_when_domain_length_more_than_50() {
        assertThrows(FieldIllegalException.class, () ->
                new Domains(null)
                        .newDomain(new DomainDescription("www.a123456789a123456789a123456789a123456789a123456", 1)));
    }
    @Test
    public void throw_when_domain_length_is_empty() {
        assertThrows(FieldIllegalException.class, () ->
                new Domains(null)
                        .newDomain(new DomainDescription("", 1)));
    }
}