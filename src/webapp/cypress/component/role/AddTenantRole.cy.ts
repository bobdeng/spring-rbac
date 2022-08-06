import AddTenantRole from '../../../src/views/tenant/role/AddTenantRole.vue'

describe('AddTenantRole.cy.ts', () => {
    beforeEach(() => {
        cy.intercept("GET", "/functions", {
            statusCode: 200,
            body: JSON.stringify([
                {
                    name: "模块1",
                    key: "m1",
                    children: [
                        {name: "模块1.功能1", key: "m1.fun1"}
                    ]
                }
            ])
        }).as("functions")
    })
    it('hide dialog', () => {
        cy.mount(AddTenantRole)
        cy.get(".ant-modal").should("not.exist")
    })
    it('should show dialog when add', function () {
        cy.mount(AddTenantRole)
        cy.wait("@functions")
        cy.contains("新 增").click().then(() => {
            cy.get(".ant-modal").should("exist")
            cy.contains("模块1")
            cy.contains("模块1.功能1")
        })
    });

})