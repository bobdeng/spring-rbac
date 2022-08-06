import ListTenantRole from '../../../src/views/tenant/role/ListTenantRole.vue'
import {createMemoryHistory, createRouter} from "vue-router";

function showOneRole() {
    cy.intercept("GET", "/tenants/101/roles", [{
        id: 102,
        description: {name: "角色1", allows: ['role.create']}
    }]).as("listRoles")
    cy.intercept("GET", "/functions", [])
    let router = createRouter({
        routes: [],
        history: createMemoryHistory(),
    })
    router.currentRoute.value.params.id = "101"
    cy.mount(ListTenantRole, {router: router})
}

describe('ListTenantRole.cy.ts', () => {
    it('list roles', () => {
        showOneRole();
        cy.get(".ant-empty").should("not.exist")
        cy.contains("角色1").should("exist")
    })
    it('should delete role', function () {
        showOneRole();
        cy.intercept("DELETE", "/tenants/101/roles/102", {statusCode: 200}).as("delete")
        cy.contains("删除").trigger("click").then(() => {
            cy.contains("OK").click().then(() => {
                cy.wait("@delete")
                cy.wait("@listRoles")
            })
        })
    });

})