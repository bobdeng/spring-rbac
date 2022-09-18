import ListTenantUser from '../../../src/views/tenant/user/ListTenantUser.vue'
import {operations} from "../Operations";

describe('ListTenantUser.cy.ts', () => {
    beforeEach(() => {
        cy.intercept("GET", "/api/1.0/roles", [{id: 3, description: {name: "角色1"}}])
    })
    it('列出所有租户下的用户', () => {
        cy.intercept("GET", "/api/1.0/users?name=", [{id: 2, description: {name: "张三", status: "Normal"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.wait("@listUser")
        cy.contains("张三").should("exist")
        cy.contains("锁定")
        cy.get("[data-icon='smile']")
    })
    it('根据名字查询', function () {
        cy.intercept("GET", "/api/1.0/users?name=12", [{id: 2, description: {name: "张三"}}]).as("listUserByName")
        cy.intercept("GET", "/api/1.0/users?name=", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.get("#search").type("12\n")
        cy.wait("@listUserByName")
    })
    it('should show add user dialog when click 新增button', function () {
        cy.intercept("GET", "/api/1.0/users?name=", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.contains("新 增").click().then(() => {
            cy.contains("新增用户").should("exist")
        })
    });
    it('should lock user', function () {
        cy.intercept("GET", "/api/1.0/users?name=", [{id: 2, description: {name: "张三", status: "Normal"}}]).as("listUser")
        cy.intercept("POST", "/api/1.0/users/2/lock", {statusCode: 200}).as("lock")
        cy.mount(ListTenantUser)
        cy.contains("锁定").click().then(() => {
            cy.wait("@lock")
            cy.contains("用户已锁定")
            operations.closeNotification();
        })
    });
    it('should unlock user', function () {
        cy.intercept("GET", "/api/1.0/users?name=", [{id: 2, description: {name: "张三", status: "Locked"}}]).as("listUser")
        cy.intercept("DELETE", "/api/1.0/users/2/lock", {statusCode: 200}).as("unlock")
        cy.mount(ListTenantUser)
        cy.contains("解锁").click().then(() => {
            cy.wait("@unlock")
            cy.contains("用户已解锁")
            operations.closeNotification();
        })
    });

    it('should show login name ', function () {
        cy.intercept("PATCH", "/api/1.0/users/2/password", {statusCode: 200, body: {password: "13456"}}).as("reset")
        cy.intercept("GET", "/api/1.0/users?name=", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.intercept("GET", `/api/1.0/users/2/login_name`, {
            id: 1,
            description: {name: "bobdeng"}
        }).as("getLoginName")
        cy.mount(ListTenantUser)
        cy.contains("登录名").click().then(() => {
            cy.contains("bobdeng")
        })
    });

    it('should reset user password', function () {
        cy.intercept("PATCH", "/api/1.0/users/2/password", {statusCode: 200, body: {password: "13456"}}).as("reset")
        cy.intercept("GET", "/api/1.0/users?name=", [{id: 2, description: {name: "张三"}}]).as("listUser")
        cy.mount(ListTenantUser)
        cy.contains("重置密码").click().then(() => {
            cy.contains("确 定").click().then(() => {
                cy.wait("@reset")
                cy.contains("新密码为：13456")
                operations.closeNotification();
            })
        })
    });


})