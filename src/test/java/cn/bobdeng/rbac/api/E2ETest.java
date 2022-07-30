package cn.bobdeng.rbac.api;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;

import javax.annotation.PostConstruct;

public class E2ETest {
    @Autowired
    protected ServletWebServerApplicationContext webServerAppCtxt;
    private int port;
    protected WebDriver webDriver;
    @PostConstruct
    public void setup(){
        this.port = webServerAppCtxt.getWebServer().getPort();
    }

}
