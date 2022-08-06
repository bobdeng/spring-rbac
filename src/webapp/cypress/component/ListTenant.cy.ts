import ListTenant from '../../src/views/tenant/ListTenant.vue'
import {createMemoryHistory, createRouter, Router, useRouter} from 'vue-router'

describe('Tenants.cy.ts', () => {
    it('show no data when no tenants', () => {
        //server.listTenants = () => Promise.resolve([])
        cy.intercept({
            method: "GET", url: "/tenants"
        }, []).as("listTenantsNameEmpty")
        cy.mount(ListTenant)
        cy.get(".ant-empty").should("exist")
    })

    it('show  data when has tenants', () => {
        cy.intercept({
            method: "GET", url: "/tenants?name="
        }, [{id: 101, description: {name: "租户1"}}]).as("listTenantsNameEmpty")
        cy.mount(ListTenant)
        cy.get("#tableTenants").find("tbody").find("tr").should("have.length", 1)
    })
    it('should search', function () {
        cy.intercept({
            method: "GET", url: "/tenants?name="
        }, []).as("listTenantsNameEmpty")
        cy.intercept({
            method: "GET", url: "/tenants?name=%E7%A7%9F%E6%88%B7"
        }, [{id: 101, description: {name: "租户1"}}]).as("listTenants")
        cy.mount(ListTenant)
        cy.get(".ant-empty").should("exist")
        cy.get("#search").type("租户")
        cy.get("#search").type("{enter}")
        cy.get("#search").trigger("search")
        cy.get(".ant-empty").should("not.exist")
        cy.get("#tableTenants").find("tbody").find("tr").should("have.length", 1)
        cy.contains("租户1").should("exist")
    });

    it('should goto list tenants domain when click domain link', function () {
        cy.intercept({
            method: "GET", url: "/tenants?name="
        }, [{id: 101, description: {name: "租户1"}}]).as("listTenantsNameEmpty")
        let router = createRouter({
            routes: [],
            history: createMemoryHistory(),
        })
        cy.stub(router, 'push')
        cy.mount(ListTenant, {router: router});
        cy.get("#tableTenants").find("tbody").find("tr").should("have.length", 1)
        cy.get("#tableTenants").find("tbody").find("tr")
            .contains("域名").trigger("click")
            .then(() => {
                expect(router.push).to.be.calledWith({path: "/tenants/101/domains"})
            })
    });

    it('should goto roles when click 角色 link',  function () {
        cy.intercept({
            method: "GET", url: "/tenants?name="
        }, [{id: 101, description: {name: "租户1"}}]).as("listTenantsNameEmpty")
        let router = createRouter({
            routes: [],
            history: createMemoryHistory(),
        })
        cy.stub(router, 'push')
        cy.mount(ListTenant, {router: router});
        cy.get("#tableTenants").find("tbody").find("tr").should("have.length", 1)
        cy.get("#tableTenants").find("tbody").find("tr")
            .contains("角色").trigger("click")
            .then(() => {
                expect(router.push).to.be.calledWith({path: "/tenants/101/roles"})
            })
    });
})