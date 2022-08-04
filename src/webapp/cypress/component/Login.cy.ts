import Login from '../../src/views/Login.vue'
import {server} from "../../src/HttpServer";

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
        server.login = () => Promise.resolve()
        cy.get("#buttonLogin").click();
        cy.get('@onLoginSpy').should('have.been.called', 1)
    });
})