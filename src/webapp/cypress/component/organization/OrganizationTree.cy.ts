import OrganizationTree from '../../../src/views/tenant/organazation/OrganizationTree.vue'

describe('OrganizationTree.cy.ts', () => {
    it('显示组织结构树', () => {
        cy.intercept("GET", "/organizations", [{id: 1, description: {name: "总公司", parent: null}}, {
            id: 2,
            description: {name: "分公司", parent: 1}
        }])
        cy.mount(OrganizationTree)
        cy.contains("总公司")
        cy.contains("分公司")
    })

    it('点击组织触发事件', () => {
        cy.intercept("GET", "/organizations", [{id: 1, description: {name: "总公司", parent: null}}, {
            id: 2,
            description: {name: "分公司", parent: 1}
        }])
        let onSelectSpy = cy.spy().as('onSelectSpy')
        cy.mount(OrganizationTree, {props: {onSelect: onSelectSpy}})
        cy.contains("分公司").click().then(() => {
            expect(onSelectSpy).to.be.calledWith({
                id: 2,
                description: {name: "分公司", parent: 1}
            })
        })
    })
})