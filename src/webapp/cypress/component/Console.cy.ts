import Console from '../../src/views/Console.vue'

describe('Console.cy.ts', () => {
    it('playground', () => {
        cy.intercept("GET", "/tenant", {id: 1, description: {name: "租户1"}}).as("tenant")
        cy.mount(Console)
    })

})