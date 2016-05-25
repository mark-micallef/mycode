package com.crossover.robotwrapper.testactions;

import com.crossover.robotwrapper.TestAction;
import org.openqa.selenium.WebDriver;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class ActionClick extends TestAction {

    public ActionClick(WebDriver driver) {
        super("Click", driver);
    }

    @Override
    public String generateCode() {
        return "        driver.findElement(" + elementLocatorCode.toString() + ").click();\n";
    }

    @Override
    public void execute() {
        driver.findElement(elementLocator).click();
    }
}
