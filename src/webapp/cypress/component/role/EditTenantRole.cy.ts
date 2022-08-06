import EditTenantRole from '../../../src/views/tenant/role/EditTenantRole.vue'

describe('EditTenantRole.cy.ts', () => {
    beforeEach(() => {
        cy.intercept("GET", "/functions", [{
            key: "role",
            name: "角色管理",
            children: [
                {
                    key: "role.create",
                    name: "新增角色"
                }
            ]
        }])
        cy.intercept("GET", "/tenants/100/roles/101", {
            statusCode: 200,
            body: JSON.stringify({
                description: {
                    name: "角色1",
                    allows: ['role.create']
                }
            })
        })
    })
    it('show load saved role ', () => {
        cy.mount(EditTenantRole, {props: {tenant: 100}}).then(() => {
            (Cypress.vue.$.exposeProxy as any).show("101")
            cy.get(".ant-modal").should("exist")
            cy.get("#inputName").should('have.value', "角色1")
            cy.get(".ant-tree-checkbox-checked").should("have.length", 2)
        });
    })
    it('should save', function () {
        cy.intercept("PATCH", "/tenants/100/roles/101", {statusCode: 200})

        cy.mount(EditTenantRole, {props: {tenant: 100}}).then(() => {
            (Cypress.vue.$.exposeProxy as any).show("101")
            cy.contains("确 定").click().then(() => {
                cy.contains("保存成功").should("exist")
            })

        })
    });
})