package com.crossover.robotwrapper.testactions;

import com.crossover.robotwrapper.TestAction;
import org.openqa.selenium.WebDriver;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class ActionClickMiddle extends TestAction {

    public ActionClickMiddle(WebDriver driver) {
        super("ClickMiddle", driver);
    }

    @Override
    public String generateCode() {

        return "";
    }

    @Override
    public void execute() {

    }
}
