import Organization from '../../../src/views/tenant/organazation/Organization.vue'
import {operations} from "../Operations";

describe('Organization.cy.ts', () => {
    it('当新增成功触发add事件', () => {
        let onAddSpy = cy.spy().as("onAddSpy")
        cy.intercept("POST", "/organizations", {statusCode: 200}).as("newOrganization")
        cy.mount(Organization, {props: {onAdd: onAddSpy}})
        cy.get("#buttonAdd").click()
        cy.contains("确 定").click().then(() => {
            operations.closeNotification()
            expect(onAddSpy).to.be.called
        })

    })
})