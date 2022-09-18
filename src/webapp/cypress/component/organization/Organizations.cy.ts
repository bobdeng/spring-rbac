import Organizations from '../../../src/views/tenant/organazation/Organizations.vue'

describe('Organizations.cy.ts', () => {
    it('显示组织结构树', () => {
        cy.fixture("organization/organizations", 'utf8').then((json) => {
            cy.intercept("GET", "/api/1.0/organizations", json)
        })
        cy.fixture("organization/employees.json").then(json => {
            cy.intercept("GET", "/api/1.0/organizations/2/employees", json).as("employees")
        })
        cy.mount(Organizations)
        cy.contains("分公司").click().then(() => {
            cy.get("[title='分公司']").should('exist')
        })
    })

    it('should 显示添加对话框，when点击新增', function () {
        cy.fixture("organization/organizations", 'utf8').then((json) => {
            cy.intercept("GET", "/api/1.0/organizations", json)
        })
        cy.mount(Organizations)
        cy.contains("添加下级单位").click().then(() => {
            cy.contains("新增组织").should('exist')
        })
    });

    it('should 刷新树，当添加成功', function () {
        cy.fixture("organization/organizations", 'utf8').then((json) => {
            cy.intercept("GET", "/api/1.0/organizations", json).as("list")
        })
        cy.intercept("POST", "/api/1.0/organizations", {statusCode: 200}).as("newOrganization")
        cy.mount(Organizations)
        cy.wait("@list")
        cy.contains("添加下级单位").click().then(() => {
            cy.contains("确 定").click().then(()=>{
                cy.wait("@list")
            })
        })
    });
})