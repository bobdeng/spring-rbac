package cn.bobdeng.rbac.api;

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
        String baseUrl = "http://localhost:" + port;
        WEBDRIVER.navigate().to(baseUrl + url);
    }

}
