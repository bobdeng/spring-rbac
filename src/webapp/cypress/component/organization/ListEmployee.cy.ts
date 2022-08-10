import Employee from '../../../src/views/tenant/organazation/Employees.vue'
import {operations} from "../Operations";

function setup() {
    cy.fixture("organization/employees.json").then(json => {
        cy.intercept("GET", "/organizations/10/employees", json).as("employees")
    })
    cy.fixture("user/users.json").then(json => {
        cy.intercept("GET", "/users?name=", json).as("users")
    })
}

describe('ListEmployee.cy.ts', () => {
    it('显示列表', () => {
        setup();
        cy.mount(Employee, {props: {organization: {id: 10}}})
        cy.wait("@employees")
        cy.contains("张三")
        cy.contains("李四")
    })

    it('点击新增，显示用户列表', () => {
        setup();
        cy.intercept("PUT","/organizations/10/employees",{statusCode:200}).as("putUser")
        cy.mount(Employee, {props: {organization: {id: 10}}})
        cy.wait("@employees")
        cy.get("#buttonAdd").click().then(()=>{
            cy.contains("选择用户")
            cy.contains("加入").click().then(()=>{
                cy.wait("@putUser")
                cy.contains("操作成功")
                cy.wait("@employees")
                operations.closeNotification()
            })
        })
    })
    it('should 删除成员，when点击成员的删除', function () {
        setup()
        cy.intercept("DELETE","/organizations/10/employees/1",{statusCode:200}).as("deleteUser")
        cy.mount(Employee, {props: {organization: {id: 10}}})
        cy.wait("@employees")
        cy.contains("删除").click().then(()=>{
            cy.wait("@deleteUser")
            cy.wait("@employees")
        })
    });
})