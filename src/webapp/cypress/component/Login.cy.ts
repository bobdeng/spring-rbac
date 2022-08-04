import Login from '../../src/views/Login.vue'

describe('Login.cy.ts', () => {
    beforeEach(()=>{
        cy.mount(Login)
    })
    it('playground', () => {
        cy.get("#buttonLogin").should("exist")
        cy.get("#inputPassword").should("exist")
    })
    it('should login when login success', () => {
        
    });
})