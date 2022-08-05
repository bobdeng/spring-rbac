import ListTenant from '../../src/views/tenant/ListTenant.vue'
import {server, TenantListItem} from "../../src/model/HttpServer";

describe('Tenants.cy.ts', () => {
    it('show no data when no tenants', () => {
        server.listTenants = () => Promise.resolve([])
        cy.mount(ListTenant)
        cy.get("#noData").should("exist")
    })

    it('show  data when has tenants', () => {
        const tenant = new TenantListItem(1, "租户1")
        server.listTenants = () => Promise.resolve([tenant])
        cy.mount(ListTenant)
        cy.get("#noData").should("not.exist")
    })
})