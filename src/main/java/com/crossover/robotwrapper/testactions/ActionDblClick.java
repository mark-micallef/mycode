package com.crossover.robotwrapper.testactions;

import com.crossover.robotwrapper.TestAction;
import org.openqa.selenium.WebDriver;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class ActionDblClick extends TestAction {

    public ActionDblClick(WebDriver driver) {
        super("DblClick", driver);
    }

    @Override
    public String generateCode() {
        return "        builder.doubleClick(driver.findElement(" + elementLocatorCode + ")).build().perform();\n";
    }

    @Override
    public void execute() {
        builder.doubleClick(driver.findElement(elementLocator)).build().perform();
    }

}
