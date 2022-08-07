import ListTenantUser from '../../../src/views/tenant/user/ListTenantUser.vue'

describe('ListTenantUser.cy.ts', () => {
    beforeEach(() => {
        cy.intercept("GET", "/users", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.intercept("GET","/roles",[{id:3,description:{name:"角色1"}}])
        cy.mount(ListTenantUser)
    })
    it('列出所有租户下的用户', () => {
        cy.wait("@listUser")
        cy.contains("张三").should("exist")
    })
    it('should show add user dialog when click 新增button', function () {
        cy.contains("新 增").click().then(() => {
            cy.contains("新增用户").should("exist")
        })
    });
})