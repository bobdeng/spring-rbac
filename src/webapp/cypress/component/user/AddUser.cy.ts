import AddUser from '../../../src/views/tenant/user/AddUser.vue'

function mockServer() {
    cy.intercept("GET", "/roles", [{id: 2, description: {name: "角色1"}}, {id: 3, description: {name: "角色2"}}])
        .as("listRoles")

}

describe('AddUser.cy.ts', () => {
    it('显示所有可用角色', () => {
        mockServer();
        cy.mount(AddUser).then(() => {
            (Cypress.vue.$.exposeProxy as any).show()
            cy.wait("@listRoles")
            cy.contains("角色1").should("exist")
            cy.contains("关 闭").click().then(() => {
                cy.contains("新增角色").should("not.exist")
            })
        })
    })
    it('should 提示成功并清空部分输入，当保存并继续', function () {
        mockServer();
        cy.intercept('POST', "/users", {statusCode: 200}).as("newUser")
        cy.mount(AddUser).then(() => {
            (Cypress.vue.$.exposeProxy as any).show()
            cy.wait("@listRoles")
            cy.get("#inputName").type("张三")
            cy.get("#inputLoginName").type("zhangsan")
            cy.get("#inputPassword").type("123456")
            cy.contains("角色1").click()
            cy.contains("保 存").click()
            cy.wait("@newUser")
            cy.contains("新增成功")
            cy.get("#inputName").should("have.value", "")
        })
    });
})