import Home from '../../src/views/Home.vue'
import {createMemoryHistory, createRouter} from "vue-router";

let router: any;

function mount() {
    router = createRouter({
        routes: [],
        history: createMemoryHistory(),
    })
    cy.stub(router, 'replace')
    cy.intercept("GET", "/wx_config", {statusCode: 200, body: ""})
    cy.intercept("GET", "/tenant", {id: 1, description: {name: "租户1"}}).as("tenant")
    cy.mount(Home, {router: router})
}

describe('Home.cy.ts', () => {
    it('goto admin console when admin login', () => {
        cy.intercept("POST", "/admin_sessions", {statusCode: 200}).as("adminLogin")
        mount();
        cy.get("#inputLoginName")
            .type("sysadmin")
        cy.get("#inputPassword").type("123456")
        cy.get("#buttonLogin").click().then(() => {
            cy.wait("@adminLogin")
            expect(router.replace).to.be.calledWith({name: "console"})
        })
    })
    it('goto user console when user login', () => {
        cy.intercept("POST", "/user_sessions", {statusCode: 200}).as("userLogin")
        mount();
        cy.get("#inputLoginName").type("admin")
        cy.get("#inputPassword").type("123456")
        cy.get("#buttonLogin").click().then(() => {
            cy.wait("@userLogin")
            expect(router.replace).to.be.calledWith({name: "userConsole"})
        })
    })
})