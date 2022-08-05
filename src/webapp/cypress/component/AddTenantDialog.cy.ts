import AddTenantDialog from '../../src/views/tenant/AddTenantDialog.vue'

describe('AddTenantDialog.cy.ts', () => {
    it('hide modal when init', () => {
        cy.mount(AddTenantDialog)
        cy.get(".ant-modal").should("not.exist")
    })
    it('show when call show', () => {
        cy.mount(AddTenantDialog)
        cy.get("#buttonShow").click().then(() => {
            cy.get(".ant-modal").should("exist")
        })
    })

    it('clear form when show', () => {
        cy.mount(AddTenantDialog)
        cy.get("#buttonShow").click().then(() => {
            cy.get("#inputName").type("租户1")
            cy.contains('取 消').click()
            cy.get("#buttonShow").click().then(() => {
                cy.get("#inputName").should("have.value", "")
            })
        })
    })
    it('should save tenant', function () {
        cy.intercept('POST', "/api/tenants", {
            statusCode: 200, body: undefined
        }).as("newTenant")
        cy.mount(AddTenantDialog)
        cy.get("#buttonShow").click().then(() => {
            cy.get("#inputName").type("租户1")
            cy.contains('确 定').click()
            cy.wait("@newTenant")
            cy.get(".ant-modal").should("not.exist")
        })
    });

    it('should error when save fail', function () {
        cy.intercept('POST', "/api/tenants", {
            statusCode: 400, body: "租户名重复"
        }).as("newTenant")
        cy.mount(AddTenantDialog)
        cy.get("#buttonShow").click().then(() => {
            cy.get("#inputName").type("租户1")
            cy.contains('确 定').click()
            cy.get(".ant-modal").should("exist")
            cy.contains("租户名重复").should("exist")
        })
    });
})