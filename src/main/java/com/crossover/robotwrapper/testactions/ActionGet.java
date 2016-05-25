package com.crossover.robotwrapper.testactions;

import com.crossover.robotwrapper.TestAction;
import org.openqa.selenium.WebDriver;

import javax.swing.*;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class ActionGet extends TestAction {

    String url = null;

    public ActionGet(WebDriver driver) {
        super("Get", driver);
    }

    @Override
    public String toString() {
        if (url == null) {
            return name;
        } else {
            return name + " " + url;
        }
    }

    @Override
    public String generateCode() {
        return "        driver.get(\"" + url + "\");\n";
    }

    @Override
    public void execute() {
        driver.get(url);
    }

    @Override
    public void collectUserInput() {
        url = JOptionPane.showInputDialog(null, "What URL do you want to get?");
        super.collectUserInput();
    }
}
