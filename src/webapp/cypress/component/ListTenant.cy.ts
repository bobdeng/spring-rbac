import ListTenant from '../../src/views/tenant/ListTenant.vue'
import {server, TenantListItem} from "../../src/model/HttpServer";

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
            method: "GET", url: "/api/rbac/tenants?name="
        }, [{id: 101, name: "租户1"}]).as("listTenantsNameEmpty")
        cy.mount(ListTenant)
        cy.get("#tableTenants").find("tbody").find("tr").should("have.length", 1)
    })
    it('should search', function () {
        cy.intercept({
            method: "GET", url: "/api/rbac/tenants?name="
        }, []).as("listTenantsNameEmpty")
        cy.intercept({
            method: "GET", url: "/api/rbac/tenants?name=%E7%A7%9F%E6%88%B7"
        }, [{id: 101, name: "租户1"}]).as("listTenants")
        cy.mount(ListTenant)
        cy.get(".ant-empty").should("exist")
        cy.get("#search").type("租户")
        cy.get("#search").type("{enter}")
        cy.get("#search").trigger("search")
        cy.get(".ant-empty").should("not.exist")
        cy.get("#tableTenants").find("tbody").find("tr").should("have.length", 1)
    });

    it('should emit listDomain event when click domain link', function () {
        cy.intercept({
            method: "GET", url: "/api/rbac/tenants?name="
        }, [{id: 101, name: "租户1"}]).as("listTenantsNameEmpty")
        cy.mount(ListTenant, {
            router: {
                push: () => {
                }
            }
        })
        cy.get("#tableTenants").find("tbody").find("tr")
            .find("button").trigger("click")
    });
})