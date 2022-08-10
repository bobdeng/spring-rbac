import SelectUser from '../../../src/views/tenant/user/SelectUser.vue'

describe('SelectUser.cy.ts', () => {
    it('显示所有用户', () => {
        cy.fixture("user/users.json").then(json => {
            cy.intercept("GET", "/users?name=", json).as("users")
        })
        const onSelectSpy = cy.spy().as("onSelectSpy")
        cy.mount(SelectUser, {props: {actionName: "加入", onSelect: onSelectSpy}}).then(() => {
            (Cypress.vue.$.exposeProxy as any).show()
            cy.wait("@users")
            cy.contains("张三")
            cy.contains("加入").click().then(() => {
                expect(onSelectSpy).to.be.calledWith(1)
            })
        })

    })
})