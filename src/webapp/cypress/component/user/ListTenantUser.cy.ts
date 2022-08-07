import ListTenantUser from '../../../src/views/tenant/user/ListTenantUser.vue'

describe('ListTenantUser.cy.ts', () => {
    it('列出所有租户下的用户', () => {
        cy.intercept("GET", "/users", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.wait("@listUser")
        cy.contains("张三").should("exist")
    })
})