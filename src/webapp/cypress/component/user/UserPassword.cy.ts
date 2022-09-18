import UserPassword from '../../../src/views/tenant/user/UserPassword.vue'

describe('UserPassword.cy.ts', () => {
    it('should change password', () => {
        cy.intercept("PUT", "/api/1.0/password", {statusCode: 200}).as("setPassword")
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
    it('should show error where confirmation not match', function () {
        cy.mount(UserPassword)
        cy.get("#inputCurrentPassword").type("123456")
        cy.get("#inputNewPassword").type("444555")
        cy.get("#inputConfirmation").type("444556")
        cy.contains("确 定").click().then(() => {
            cy.contains("两次密码输入不一致")
        });
    });
})