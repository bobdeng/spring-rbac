export const operations = {
    closeNotification(){
        return cy.get("[data-icon='close']").click()
    },
    clickConfirm(){
        return cy.contains("确 定").click()
    }
}