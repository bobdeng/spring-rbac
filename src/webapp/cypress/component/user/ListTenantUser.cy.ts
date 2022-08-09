import ListTenantUser from '../../../src/views/tenant/user/ListTenantUser.vue'

describe('ListTenantUser.cy.ts', () => {
    beforeEach(() => {
        cy.intercept("GET", "/roles", [{id: 3, description: {name: "角色1"}}])
    })
    it('列出所有租户下的用户', () => {
        cy.intercept("GET", "/users?name=", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.wait("@listUser")
        cy.contains("张三").should("exist")
    })
    it('根据名字查询', function () {
        cy.intercept("GET", "/users?name=12", [{id: 2, description: {name: "张三"}}]).as("listUserByName")
        cy.intercept("GET", "/users?name=", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.get("#search").type("12\n")
        cy.wait("@listUserByName")
    })
    it('should show add user dialog when click 新增button', function () {
        cy.intercept("GET", "/users?name=", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.contains("新 增").click().then(() => {
            cy.contains("新增用户").should("exist")
        })
    });
    it('should reset user password', function () {
        cy.intercept("PATCH", "/users/2/password", {statusCode: 200, body: {password: "13456"}}).as("reset")
        cy.intercept("GET", "/users?name=", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.contains("重置密码").click().then(() => {
            cy.contains("确 定").click().then(() => {
                cy.wait("@reset")
                cy.contains("新密码为：13456")
            })
        })
    });
})