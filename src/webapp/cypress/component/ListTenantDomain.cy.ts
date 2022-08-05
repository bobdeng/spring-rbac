import ListTenantDomain from '../../src/views/tenant/domain/ListTenantDomain.vue'
import {createMemoryHistory, createRouter, RouteRecordName, RouteRecordNormalized} from "vue-router";

describe('ListTenantDomain.cy.ts', () => {
    it('list domains', () => {
        cy.intercept("GET", "/tenants/101/domains", [{id: 102, description: {domain: "www.test.com"}}]).as("listDomain")
        let router = createRouter({
            routes: [],
            history: createMemoryHistory(),
        })
        router.currentRoute.value.params.id = "101"
        cy.mount(ListTenantDomain, {router: router})
        cy.wait("@listDomain")
        cy.get(".ant-empty").should("not.exist")
    })
})