package com.crossover.robotwrapper.testactions;

import com.crossover.robotwrapper.TestAction;
import org.openqa.selenium.WebDriver;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class ActionClickRight extends TestAction {

    public ActionClickRight(WebDriver driver) {
        super("ClickRight", driver);
    }

    @Override
    public String generateCode() {
        return "        builder.contextClick(driver.findElement(" + elementLocatorCode + ")).build().perform();\n";
    }

    @Override
    public void execute() {
        builder.contextClick(driver.findElement(elementLocator)).build().perform();
    }

}
