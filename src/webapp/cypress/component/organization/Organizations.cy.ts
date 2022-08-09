import Organizations from '../../../src/views/tenant/organazation/Organizations.vue'

describe('Organizations.cy.ts', () => {
    it('显示组织结构树', () => {
        cy.fixture("organization/organizations", 'utf8').then((json) => {
            cy.intercept("GET", "/organizations", json)
        })
        cy.mount(Organizations)
        cy.contains("分公司").click().then(() => {
            cy.get("[title='分公司']").should('exist')
        })
    })

    it('should 显示添加对话框，when点击新增', function () {
        cy.fixture("organization/organizations", 'utf8').then((json) => {
            cy.intercept("GET", "/organizations", json)
        })
        cy.mount(Organizations)
        cy.contains("添加下级单位").click().then(() => {
            cy.contains("新增组织").should('exist')
        })
    });
})