import UserConsole from "../../src/views/UserConsole.vue";

describe('UserConsole.cy.ts', () => {
    it('playground', () => {
        cy.intercept("GET", "/tenant", {id: 1, description: {name: "租户1"}}).as("getTenant")
        cy.mount(UserConsole)
        cy.wait("@getTenant")
        cy.contains("租户1")
    })
})