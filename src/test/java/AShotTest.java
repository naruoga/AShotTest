import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AShotTest {
    WebDriver driver;

    @Test
    public void ChromeTest() throws IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--start-maximized");
        driver = new ChromeDriver(opt);
        genericTest("chrome");
    }

    @Test
    public void GeckoTest() throws IOException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        genericTest("gecko");
    }

    @Test
    public void IETest() throws IOException {
        WebDriverManager.iedriver().setup();
        driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        genericTest("InternetExplorer");
    }

    @Test
    public void MSEdgeTest() throws IOException {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        genericTest("MSEdge");
    }

    // TODO: Someone may write other browsers' tests


    private void genericTest(String browsername) throws IOException {
        driver.get("https://github.com/yandex-qatools/ashot");

        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(driver);

        File outputdir = new File("build/screenshot");
        if (!outputdir.exists()) {
            outputdir.mkdirs();
        }
        File outputfile = new File(outputdir.getAbsolutePath() + "/" + browsername + ".png");
        ImageIO.write(screenshot.getImage(), "png", outputfile);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
