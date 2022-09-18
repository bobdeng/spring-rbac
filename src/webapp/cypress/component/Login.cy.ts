import Login from '../../src/views/Login.vue'

let onAdminLoginSpy
let onUserLoginSpy
describe('Login.cy.ts', () => {
    beforeEach(() => {
        onAdminLoginSpy = cy.spy().as('onAdminLoginSpy')
        onUserLoginSpy = cy.spy().as('onUserLoginSpy')
        cy.intercept("GET", "/api/1.0/tenant", {id: 1, description: {name: "租户1"}}).as("tenant")
        cy.intercept("GET", "/api/1.0/wx_config", {statusCode: 200, body: ""})
        cy.mount(Login, {props: {onAdminLogin: onAdminLoginSpy, onUserLogin: onUserLoginSpy}})
    })
    it('playground', () => {
        cy.get("#buttonLogin").should("exist")
        cy.get("#inputPassword").should("exist")
        cy.wait("@tenant")
    })
    it('should admin login when login success', () => {
        cy.intercept("POST", "/api/1.0/admin_sessions", {
            statusCode: 200,
            data: undefined
        }).as("adminLogin")
        cy.get("#inputLoginName").type("sysadmin")
        cy.get("#buttonLogin").click();
        cy.get('@onAdminLoginSpy').should('have.been.called')
        cy.wait("@adminLogin")
    });
    it('should not emit when login fail', function () {
        cy.intercept("POST", "/api/1.0/admin_sessions", {
            statusCode: 400,
            body: "登录失败"
        })
        cy.get("#inputLoginName").type("sysadmin")
        cy.get("#buttonLogin").click();
        cy.get('@onAdminLoginSpy').should('not.been.called')
        cy.get("#error").should("have.text", "登录失败")

    });

    it('should call user login,when login name is not sysadmin', function () {
        cy.intercept("POST", "/api/1.0/user_sessions", {
            statusCode: 200,
            body: "登录"
        }).as("userLogin")
        cy.get("#inputLoginName").type("user")
        cy.get("#buttonLogin").click();
        cy.get('@onUserLoginSpy').should('been.called')
        cy.wait("@userLogin")
    });


})