import AddSubOrganization from '../../../src/views/tenant/organazation/AddSubOrganization.vue'
import {operations} from "../Operations";

describe('AddOrganization.cy.ts', () => {
    it('添加子公司保存', () => {
        let onSuccessSpy = cy.spy().as("onSuccessSpy")
        cy.intercept("POST", "/organizations", {statusCode: 200}).as("newOrganization")
        cy.mount(AddSubOrganization, {props: {onSuccess: onSuccessSpy}}).then(() => {
            Cypress.vue.$.exposeProxy.show()
            cy.get("#inputName").type("子公司名称")
            cy.contains("确 定").click().then(() => {
                cy.contains("新增成功")
                expect(onSuccessSpy).to.be.called
                cy.contains("新增组织").should('not.exist')
                cy.wait("@newOrganization")
                operations.closeNotification()
            })
        })
    })

    it('保存失败提示', () => {
        let onSuccessSpy = cy.spy().as("onSuccessSpy")
        cy.intercept("POST", "/organizations", {statusCode: 400, body: "名字重复"}).as("newOrganization")
        cy.mount(AddSubOrganization, {props: {onSuccess: onSuccessSpy}}).then(() => {
            Cypress.vue.$.exposeProxy.show()
            cy.get("#inputName").type("子公司名称")
            cy.contains("确 定").click().then(() => {
                cy.contains("名字重复")
                expect(onSuccessSpy).not.be.called
                cy.contains("新增组织").should('exist')
                cy.wait("@newOrganization")
            })
        })
    })
})