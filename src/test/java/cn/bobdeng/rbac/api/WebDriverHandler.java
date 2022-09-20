package cn.bobdeng.rbac.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Rule;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.Network;

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
    @Rule
    public BrowserWebDriverContainer<?> chrome = new BrowserWebDriverContainer<>()
            .withCapabilities(new ChromeOptions())
            .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, new File(""));
    @Autowired
    Environment environment;

    @PostConstruct
    private void init() {
        if (WEBDRIVER == null) {
            chrome.start();
            WEBDRIVER = chrome.getWebDriver();
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

    public String getBaseUrl() {
        return "http://" + getDomain();
    }

    public String getLocalhostUrl() {
        return "http://localhost:" + getPort();
    }

    private String getDomain() {
        return "host.docker.internal:" + getPort();
    }

    @Nullable
    private String getPort() {
        return environment.getProperty("local.server.port");
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
