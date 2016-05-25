package com.crossover.robotwrapper.testactions;

import com.crossover.robotwrapper.TestAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class ActionActivate extends TestAction {

    public ActionActivate(WebDriver driver) {
        super("Activate (focus)", driver);
    }

    @Override
    public String generateCode() {
        StringBuffer sb = new StringBuffer();
        sb.append("     element = driver.findElement(" + elementLocatorCode + ");\n");
        sb.append("     if (\"input\".equals(element.getTagName())) {\n");
        sb.append("         element.sendKeys(\"\");\n");
        sb.append("     } else {\n");
        sb.append("         builder.moveToElement(element).build().perform();\n");
        sb.append("     }\n");
        return sb.toString();
    }

    @Override
    public void execute() {
        builder.moveToElement(driver.findElement(elementLocator)).build().perform();

        WebElement element = driver.findElement(elementLocator);
        if ("input".equals(element.getTagName())) {
            element.sendKeys("");
        } else {
            builder.moveToElement(element).build().perform();

        }
    }
}
