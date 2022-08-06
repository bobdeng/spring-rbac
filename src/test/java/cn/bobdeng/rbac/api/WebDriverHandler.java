package cn.bobdeng.rbac.api;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;

@Service
public class WebDriverHandler {
    public static WebDriver WEBDRIVER;
    private int port = 8080;
    @Autowired(required = false)
    private ServletWebServerApplicationContext webServerAppCtxt;

    @PostConstruct
    private void init() {
        if (webServerAppCtxt != null) {
            WEBDRIVER = createWebDriver();
            this.port = webServerAppCtxt.getWebServer().getPort();
        }
    }


    public WebDriver createWebDriver() {
        try {
            System.setProperty("webdriver.edge.driver", getChromeDriverBinaryPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ChromeDriver();
    }

    private String getChromeDriverBinaryPath() throws IOException {
        try (Stream<Path> walkStream = Files.walk(Paths.get(System.getProperty("user.home"), ".gradle", "webdriver", "chromedriver"))) {
            return walkStream
                    .filter(this::isChromeDriverBinary)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("can't find chrome driver binary"))
                    .toAbsolutePath().toString();
        }
    }

    private boolean isChromeDriverBinary(Path p) {
        File file = p.toFile();
        return file.isFile() && (file.getPath().endsWith("chromedriver") || file.getPath().endsWith("chromedriver.exe"));
    }

    public void open(String url) {
        WEBDRIVER.get(getBaseUrl() + url);
    }

    @NotNull
    protected String getBaseUrl() {
        return "http://" + getDomain();
    }

    @NotNull
    private String getDomain() {
        return "localhost:" + port;
    }

    public void removeAllCookies() {
        WEBDRIVER.manage().deleteAllCookies();
    }

    public Cookie getCookie(String cookieName) {
        return WEBDRIVER.manage().getCookieNamed(cookieName);
    }

    public void addCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue, "/");
        WEBDRIVER.manage().addCookie(cookie);
    }
}
