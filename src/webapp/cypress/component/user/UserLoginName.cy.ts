import UserLoginName from '../../../src/views/tenant/user/UserLoginName.vue'
import {operations} from "../Operations";

const userId = 2;
describe('UserLoginName.cy.ts', () => {
    it('显示用户绑定的角色', () => {
        cy.intercept("GET", `/api/1.0/users/${userId}/login_name`, {
            id: 1,
            description: {name: "bobdeng"}
        }).as("getLoginName")
        cy.mount(UserLoginName).then(() => {
            (Cypress.vue.$.exposeProxy as any).show(userId)
            cy.contains("用户登录名");
            cy.wait("@getLoginName")
            cy.contains("bobdeng")
            cy.get("#buttonRemove")
        })
    })

    it('解绑账户', () => {
        cy.intercept("GET", `/api/1.0/users/${userId}/login_name`, {
            id: 1,
            description: {name: "bobdeng"}
        }).as("getLoginName")
        cy.intercept("DELETE", `/api/1.0/login_names/1`, {statusCode: 200}).as("delete")
        cy.mount(UserLoginName).then(() => {
            (Cypress.vue.$.exposeProxy as any).show(userId)
            cy.wait("@getLoginName")
            cy.get("#buttonRemove").click().then(() => {
                cy.wait("@delete")
                cy.contains("解绑成功")
                operations.closeNotification()
            })
        })
    })

    it('绑定账户', () => {
        cy.intercept("GET", `/api/1.0/users/${userId}/login_name`, {statusCode: 200, body: undefined}).as("getLoginName")
        cy.intercept("POST", `/api/1.0/login_names`, {statusCode: 200}).as("new")
        cy.mount(UserLoginName).then(() => {
            (Cypress.vue.$.exposeProxy as any).show(userId)
            cy.wait("@getLoginName")
            cy.get("#inputLoginName").type("bobdeng")
            cy.get("#buttonAdd").click().then(() => {
                cy.wait("@new")
                cy.contains("绑定成功")
                cy.contains("用户登录名").should("not.exist")
            })
        })
    })

    it('绑定账户失败', () => {
        cy.intercept("GET", `/api/1.0/users/${userId}/login_name`, {statusCode: 200, body: undefined}).as("getLoginName")
        cy.intercept("POST", `/api/1.0/login_names`, {statusCode: 400, body: "账户已存在"}).as("new")
        cy.mount(UserLoginName).then(() => {
            (Cypress.vue.$.exposeProxy as any).show(userId)
            cy.wait("@getLoginName")
            cy.get("#inputLoginName").type("bobdeng")
            cy.get("#buttonAdd").click().then(() => {
                cy.wait("@new")
                cy.contains("账户已存在")
            })
        })
    })

})