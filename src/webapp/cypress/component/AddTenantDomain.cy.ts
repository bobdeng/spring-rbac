import AddTenantDomain from '../../src/views/tenant/domain/AddTenantDomain.vue'

const tenant = "100"
describe('AddTenantDomain.cy.ts', () => {
    it('hide dialog when init', () => {
        cy.mount(AddTenantDomain, {props: {tenant: tenant}})
        cy.get(".ant-modal").should("not.exist")
    })

    it('should dialog when click add', () => {
        cy.mount(AddTenantDomain, {props: {tenant: tenant}})
        cy.get("#buttonShow").click().then(() => {
            cy.get(".ant-modal").should("exist")
        })
    })
    it('clear form when show', () => {
        cy.mount(AddTenantDomain, {props: {tenant: tenant}})
        cy.get("#buttonShow").click().then(() => {
            cy.get("#inputName").type("www.test.com")
            cy.contains('取 消').click()
            cy.get("#buttonShow").click().then(() => {
                cy.get("#inputName").should("have.value", "")
            })
        })
    })

    it('should save domain', function () {

        let onSuccessSpy = cy.spy().as('onSuccessSpy')
        cy.intercept('POST', `/domains`, {
            statusCode: 200, body: undefined
        }).as("newDomain")
        cy.mount(AddTenantDomain, {props: {onSuccess: onSuccessSpy, tenant: tenant}})
        cy.get("#buttonShow").click().then(() => {
            cy.get("#inputName").type("www.test.com")
            cy.contains('确 定').click()
            cy.wait("@newDomain")
            cy.get(".ant-modal").should("not.exist")
            cy.get('@onSuccessSpy').should('have.been.called')
        })
    });

    it('should error when save domain fail', function () {
        cy.intercept('POST', "/domains", {
            statusCode: 400, body: "域名重复"
        }).as("newDomain")
        cy.mount(AddTenantDomain)
        cy.get("#buttonShow").click().then(() => {
            cy.get("#inputName").type("www.test.com")
            cy.contains('确 定').click()
            cy.wait("@newDomain")
            cy.get(".ant-modal").should("exist")
            cy.contains("域名重复").should("exist")
        })
    });

})