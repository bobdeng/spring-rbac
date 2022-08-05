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
})