import AddTenantRole from '../../../src/views/tenant/role/AddTenantRole.vue'

const tenant = 100
describe('AddTenantRole.cy.ts', () => {
    beforeEach(() => {
        cy.intercept("GET", "/functions", {
            statusCode: 200,
            body: JSON.stringify([
                {
                    key: "m1",
                    name: "模块1",
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
    it('should save role', function () {
        let onSuccessSpy = cy.spy().as('onSuccessSpy')
        cy.intercept("POST", `/tenants/${tenant}/roles`, {statusCode: 200}).as("saveRole")
        cy.mount(AddTenantRole, {props: {tenant: tenant,onSuccess:onSuccessSpy}})
        cy.wait("@functions")
        cy.contains("新 增").click().then(() => {
            cy.get(".ant-modal").should("exist")
            cy.contains("模块1").click().then(() => {
                cy.contains("确 定").click().then(() => {
                    cy.contains("新增成功")
                    cy.wait("@saveRole")
                    cy.get("@onSuccessSpy").should("be.called")
                })
            })
        })
    });

})