import com.crossover.robotwrapper.Utils;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SampleTestSuite {

    WebDriver driver;
    WebElement element;
    Actions builder;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        builder = new Actions(driver);
    }

    @After
    public void tearDown() {
        driver.close();
    }

    @Test
    public void myTestCase() {
        driver.get("http://www.google.com");
        driver.findElement(By.id("lst-ib")).sendKeys("Malta");
        driver.findElement(By.name("btnK")).click();
        Utils.sleep(1000);
        assertTrue(driver.getTitle().contains("Malta")); }

}
