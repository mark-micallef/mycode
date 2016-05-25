package com.crossover.robotwrapper;

import com.crossover.robotwrapper.gui.TestCaseCreator;
import org.openqa.selenium.*;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.List;

/**
 * Created by markmicallef on 23/05/2016.
 */
public class SpyTool {

    WebDriver driver;
    TestCaseCreator creator;
    LocatorBuilder locatorBuilder;

    boolean paused = false;
    boolean active = false;
    boolean shuttingDown = false;

    WebElement lastElementFound = null;
    int numTimesConsecutivelyFound = 0;

    public static void main(String[] argv) throws Exception {
        new SpyTool("http://www.google.com");
    }

    public SpyTool(String url) {
        locatorBuilder = new LocatorBuilder();
        driver = new FirefoxDriver();
        driver.get(url);
        creator = new TestCaseCreator(this, driver);
        new Thread(creator).start();
        start();
    }

    public void shutDown() {
        shuttingDown = true;
    }

    public void pause() {
        //Set pause flag so control loop will stop
        paused = true;

        //Wait for current control loop to finish its work before returning
        while (active) {
            Utils.sleep(100);
        }
    }

    public void restart() {
        paused = false;
    }

    public void start() {

        while (!shuttingDown) {
            if (!paused) {
                active = true;
                WebElement e = getElementAtMouseLocation();
                if (e != null) {
                    if (e.equals(lastElementFound)) {
                        numTimesConsecutivelyFound++;
                        if (numTimesConsecutivelyFound == 5) {
                            creator.updateElement(e);
                        }
                    } else {
                        numTimesConsecutivelyFound = 1;
                    }
                }

                lastElementFound = e;
                active = false;
            }

            Utils.sleep(100);

        }

        driver.quit();
        System.exit(0);
    }

    protected WebElement getElementAtMouseLocation() {
        Point mouseLocation = adjustPointRelativeToBrowserViewport(MouseInfo.getPointerInfo().getLocation());
        return getElementAt(mouseLocation);
    }

    public Point adjustPointRelativeToBrowserViewport(Point mouseLocation) {
        int x = mouseLocation.x;
        int y = mouseLocation.y;

        //Adjust mouse location relative to browser window
        x = x - driver.manage().window().getPosition().getX();
        y = y - driver.manage().window().getPosition().getY();

        //Adjust mouse location relative to viewport
        int viewPortWidth = ((Long) ((JavascriptExecutor) driver).executeScript("return document.documentElement.clientWidth")).intValue();
        int viewPortHeight = ((Long) ((JavascriptExecutor) driver).executeScript("return document.documentElement.clientHeight")).intValue();
        int windowWidth = driver.manage().window().getSize().getWidth();
        int windowHeight = driver.manage().window().getSize().getHeight();
        x = x - (windowWidth - viewPortWidth);
        y = y - (windowHeight - viewPortHeight);

        return new Point(x, y);
    }


    public WebElement getElementAt(Point p) {
        return getElementAt((int) p.getX(), (int) p.getY());
    }

    public WebElement getElementAt(int x, int y) {

        List<WebElement> elements = getAllElementsOfInterest();

        WebElement result = null;

        boolean found = false;
        for (int i = 0; i < elements.size() && !found; i++) {
            Rectangle r = elements.get(i).getRect();

            found = x > r.getX()
                    && y > r.getY()
                    && x < (r.getX() + r.getWidth())
                    && y < (r.getY() + r.getHeight());

            if (found) {
                result = elements.get(i);
            }

        }

        return result;

    }

    protected List<WebElement> getAllElementsOfInterest() {
        return driver.findElements(By.xpath("//button|//a|//input|//select"));
    }


}
