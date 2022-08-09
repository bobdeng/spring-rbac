import Organizations from '../../../src/views/tenant/organazation/Organizations.vue'

describe('Organizations.cy.ts', () => {
    it('显示组织结构树', () => {
        cy.fixture("organization/organizations", 'utf8').then((json) => {
            cy.intercept("GET", "/organizations", json)
        })
        cy.mount(Organizations)
        cy.contains("分公司").click().then(() => {
            cy.get("#organizationName").should('have.text', "分公司")
        })
    })
})