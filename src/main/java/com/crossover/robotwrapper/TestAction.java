package com.crossover.robotwrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;

/**
 * Created by markmicallef on 24/05/2016.
 */
public abstract class TestAction {

    protected String name;
    protected String elementLocatorCode;
    protected By elementLocator;
    protected WebDriver driver;
    protected Actions builder;
    protected String verificationCode;

    //Variables for code generation
    protected String webdriverVariableName = "driver";

    public TestAction(String name, WebDriver driver) {
        this.name = name;
        this.driver = driver;
        this.elementLocatorCode = null;

        builder = new Actions(driver);
    }

    public String toString() {

        if (elementLocatorCode == null) {
            return name;
        } else {
            return name + " on " + elementLocatorCode.toString();
        }
    }

    public void setElementLocatorCode(String elementLocatorCode) {
        this.elementLocatorCode = elementLocatorCode;
    }

    public void setElementLocator(By by) {
        this.elementLocator = by;
    }

    public void collectUserInput() {}

    public void collectVerificationCode() {
        verificationCode = JOptionPane.showInputDialog(null, "Enter your verification code:");
    }

    public String getVerificationCode() {
        if (verificationCode == null) {
            return "";
        } else {
            return verificationCode;
        }
    }

    public abstract String generateCode();

    public abstract void execute();
}
