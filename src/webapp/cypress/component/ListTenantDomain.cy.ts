import ListTenantDomain from '../../src/views/tenant/domain/ListTenantDomain.vue'
import {createMemoryHistory, createRouter, RouteRecordName, RouteRecordNormalized} from "vue-router";

function showOneDomain() {
    cy.intercept("GET", "/tenants/101/domains", [{id: 102, description: {domain: "www.test.com"}}]).as("listDomain")
    let router = createRouter({
        routes: [],
        history: createMemoryHistory(),
    })
    router.currentRoute.value.params.id = "101"
    cy.mount(ListTenantDomain, {router: router})
}

describe('ListTenantDomain.cy.ts', () => {
    it('list domains', () => {
        showOneDomain();
        cy.wait("@listDomain")
        cy.get(".ant-empty").should("not.exist")
    })
    it('should show delete confirm dialog when click delete', function () {
        cy.intercept("DELETE", "/domains/102", {
            statusCode: 200, body: undefined
        }).as('deleteDomain')
        showOneDomain();
        cy.wait("@listDomain")
        cy.contains('删除').click().then(() => {
            cy.contains("你确定要删除吗？").should("exist")
            cy.contains("OK").click().then(() => {
                cy.wait("@deleteDomain")
                cy.wait("@listDomain")
            })
        })
    });
})