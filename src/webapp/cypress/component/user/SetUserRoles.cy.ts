import SetUserRoles from '../../../src/views/tenant/user/SetUserRoles.vue'

describe('SetUserRoles.cy.ts', () => {
  beforeEach(() => {
    cy.intercept("GET", "/api/1.0/roles", [{id: 3, description: {name: "Boss"}}, {
      id: 4,
      description: {name: "Manager"}
    }])
    cy.intercept("GET", "/api/1.0/users/100/roles", [{id: 3, description: {name: "Boss"}}])
    cy.intercept("PUT", "/api/1.0/users/100/roles", {statusCode: 200}).as("setRoles")
  })
  it('playground', () => {
    cy.mount(SetUserRoles).then(() => {
      (Cypress.vue.$.exposeProxy as any).show(100)
    })
  })

  it('should 显示所有角色', function () {
    cy.mount(SetUserRoles).then(() => {
      (Cypress.vue.$.exposeProxy as any).show(100)
      cy.contains("Boss").should("exist")
      cy.contains("Manager").should("exist")
    })
  });

  it('should 缺省选中用户已经有的角色', function () {
    cy.mount(SetUserRoles).then(() => {
      (Cypress.vue.$.exposeProxy as any).show(100)
      cy.get('input[type="checkbox"]:checked').should("have.length", 1)
    })
  });

  it('should 保存角色', function () {
    cy.mount(SetUserRoles).then(() => {
      (Cypress.vue.$.exposeProxy as any).show(100)
      cy.contains("保 存").click()
      cy.wait("@setRoles")
      cy.contains("保存成功")
    })
  });
})