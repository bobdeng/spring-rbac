import UserPassword from '../../../src/views/tenant/user/UserPassword.vue'

describe('UserPassword.cy.ts', () => {
    it('should change password', () => {
        cy.intercept("PUT", "/password", {statusCode: 200}).as("setPassword")
        cy.mount(UserPassword)
        cy.get("#inputCurrentPassword").type("123456")
        cy.get("#inputNewPassword").type("444555")
        cy.get("#inputConfirmation").type("444555")
        cy.contains("确 定").click().then(() => {
            cy.wait("@setPassword")
            cy.contains("修改成功")
            cy.get("#inputCurrentPassword").should("have.value", "")
        })

    })
})