package com.crossover.robotwrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class LocatorBuilder {

    protected By lastLocator = null;

    public String buildLocator(WebElement element) {

        if (element == null) {
            return null;
        }

        String id = element.getAttribute("id").trim();
        String name = element.getAttribute("name");
        String tagName = element.getTagName();
        String text = element.getText();

        //If ID is available, use that
        if (id.length() > 0) {
            lastLocator = By.id(id);
            return "By.id(\"" + id + "\")";
        }

        //If name is available, use that
        if (name.length() > 0) {
            lastLocator = By.name(name);
            return "By.name(\"" + name + "\")";
        }

        //If text is available, use that and tag
        if (text.length() > 0) {
            lastLocator = By.xpath("//" + tagName + "[text()='" + text.replace("'", "\'") + "']");
            return "By.xpath(\"//" + tagName + "[text()='" + text.replace("'", "\'") + "']\")";
        }

        //Otherwise, use the tag and hope for the best
        lastLocator = By.tagName(tagName);
        return "By.tagName(\"" + tagName + "\")";
    }

    public By getLastLocator() {
        return lastLocator;
    }

}
