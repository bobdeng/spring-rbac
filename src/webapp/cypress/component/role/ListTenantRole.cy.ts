import ListTenantRole from '../../../src/views/tenant/role/ListTenantRole.vue'
import {createMemoryHistory, createRouter} from "vue-router";

let router:any;

function showOneRole() {
    cy.intercept("GET", "/api/1.0/tenants/101/roles", [{
        id: 102,
        description: {name: "角色1", allows: ['role.create']}
    }]).as("listRoles")
    cy.intercept("GET", "/api/1.0/functions", [])
    router = createRouter({
        routes: [],
        history: createMemoryHistory(),
    })
    cy.stub(router, 'go')
    router.currentRoute.value.params.id = "101"
    cy.mount(ListTenantRole, {router: router})
}

describe('ListTenantRole.cy.ts', () => {
    it('list roles', () => {
        showOneRole();
        cy.get(".ant-empty").should("not.exist")
        cy.contains("角色1").should("exist")
    })
    it('should go back where back', function () {
        showOneRole();
        cy.get("#buttonBack").click().then(() => {
            expect(router.go).to.be.calledWith(-1)
        })
    });
    it('should delete role', function () {
        showOneRole();
        cy.intercept("DELETE", "/api/1.0/tenants/101/roles/102", {statusCode: 200}).as("delete")
        cy.contains("删除").trigger("click").then(() => {
            cy.contains("OK").click().then(() => {
                cy.contains("删除成功")
                cy.wait("@delete")
                cy.wait("@listRoles")
            })
        })
    });

})