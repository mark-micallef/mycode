package com.crossover.robotwrapper.testactions;

import com.crossover.robotwrapper.TestAction;
import org.openqa.selenium.WebDriver;

import javax.swing.*;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class ActionSendKeys extends TestAction {

    String keysToSend;

    public ActionSendKeys(WebDriver driver) {
        super("SendKeys", driver);
    }


    @Override
    public String toString() {

        if (elementLocatorCode == null) {
            return name;
        } else {
            return name + " \"" + keysToSend + "\" " + " to " + elementLocatorCode.toString();
        }
    }

    @Override
    public void execute() {
        driver.findElement(elementLocator).sendKeys(keysToSend);
    }

    @Override
    public String generateCode() {
        return "driver.findElement(" + elementLocatorCode + ").sendKeys(\"" + keysToSend + "\");\n";
    }

    @Override
    public void collectUserInput() {
        keysToSend = JOptionPane.showInputDialog(null, "What keys do you want to send?");
        super.collectUserInput();
    }
}
