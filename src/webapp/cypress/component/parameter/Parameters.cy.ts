import Parameters from '../../../src/views/parameter/Parameters.vue'

describe('Parameters.cy.ts', () => {
    beforeEach(() => {
        cy.intercept("GET", "/parameters", [{id: 100, description: {key: "param.key1", name: "参数名称", value: "100"}}])
            .as("list")
    })
    it('列出所有参数', () => {
        cy.mount(Parameters)
        cy.wait("@list")
        cy.contains("参数名称")
    })

    it('should save所有参数', function () {
        cy.intercept("PUT", "/parameters", {statusCode: 200}).as("save")
        cy.mount(Parameters)
        cy.wait("@list")
        cy.get("#buttonSave").click().then(() => {
            cy.wait("@save").then((interception) => {
                console.log(interception.request.body)
                expect(interception.request.body).to.be.eql([
                    {key: "param.key1", value: "100"}
                ])
            })
            cy.contains("保存成功")
        })
    });
})