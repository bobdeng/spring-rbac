import WxLoginButton from '../../src/components/WxLoginButton.vue'

describe('WxLoginButton.cy.ts', () => {
    it('当没有配置微信登录，不显示', () => {
        cy.intercept("GET", "/wx_config", {statusCode: 200, body: null}).as("wxConfig")
        cy.mount(WxLoginButton)
        cy.wait("@wxConfig")
        cy.contains("微信登录").should("not.exist")
    })
    it('当有配置微信登录，显示', () => {
        cy.intercept("GET", "/wx_config", {
            statusCode: 200, body: {
                appId: "appid_123",
                callback: "https://www.test.com/",
                code:"123456"
            }
        }).as("wxConfig")
        cy.mount(WxLoginButton)
        cy.wait("@wxConfig")
        cy.contains("微信登录").should("exist")
        cy.contains("微信登录").click().then(() => {
            cy.url().should("eq", "https://open.weixin.qq.com/connect/qrconnect?appid=appid_123&redirect_uri=https%3A%2F%2Fwww.test.com%2F&response_type=code&scope=snsapi_login&state=123456#wechat_redirect");
        })
    })
})