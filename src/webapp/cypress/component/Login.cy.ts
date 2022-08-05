import Login from '../../src/views/Login.vue'
import {server} from "../../src/model/HttpServer";

let onLoginSpy
describe('Login.cy.ts', () => {
    beforeEach(() => {
        onLoginSpy = cy.spy().as('onLoginSpy')
        cy.mount(Login, {props: {onLogin: onLoginSpy}})
    })
    it('playground', () => {
        cy.get("#buttonLogin").should("exist")
        cy.get("#inputPassword").should("exist")
    })
    it('should login when login success', () => {
        cy.intercept("POST", "/admin_sessions", {
            statusCode: 200,
            data: undefined
        })
        cy.get("#buttonLogin").click();
        cy.get('@onLoginSpy').should('have.been.called')
    });
    it('should not emit when login fail', function () {
        cy.intercept("POST", "/admin_sessions", {
            statusCode: 400,
            body: "登录失败"
        })
        cy.get("#buttonLogin").click();
        cy.get('@onLoginSpy').should('not.been.called')
        cy.get("#error").should("have.text", "登录失败")
    });
})