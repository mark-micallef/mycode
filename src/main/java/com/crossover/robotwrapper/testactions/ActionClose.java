package com.crossover.robotwrapper.testactions;

import com.crossover.robotwrapper.TestAction;
import org.openqa.selenium.WebDriver;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class ActionClose extends TestAction {

    public ActionClose(WebDriver driver) {
        super("Close", driver);
    }

    @Override
    public String generateCode() {
        return "        driver.close();\n";
    }

    @Override
    public void execute() {
        driver.close();
    }

}
