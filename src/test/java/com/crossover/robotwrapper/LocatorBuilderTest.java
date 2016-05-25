package com.crossover.robotwrapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class LocatorBuilderTest {

    LocatorBuilder builder;

    @Before
    public void setUp() {
        builder = new LocatorBuilder();
    }


    @Test
    public void testNullWebElement() {
        assertNull(builder.buildLocator(null));
    }

    @Test
    public void testElementWithID() {

        // Create mock
        WebElement element = Mockito.mock(WebElement.class);
        when(element.getAttribute("id")).thenReturn("btnSearch");

        // Test and Verify
        assertEquals("By.id(\"btnSearch\")", builder.buildLocator(element));
        assertNotNull(builder.getLastLocator());
    }

    @Test
    public void testElementWithName() {

        // Create mock
        WebElement element = Mockito.mock(WebElement.class);
        when(element.getAttribute("id")).thenReturn("");
        when(element.getAttribute("name")).thenReturn("myName");

        // Test and Verify
        assertEquals("By.name(\"myName\")", builder.buildLocator(element));
        assertNotNull(builder.getLastLocator());
    }

    @Test
    public void testElementWithText() {

        // Create mock
        WebElement element = Mockito.mock(WebElement.class);
        when(element.getAttribute("id")).thenReturn("");
        when(element.getAttribute("name")).thenReturn("");
        when(element.getText()).thenReturn("Search");
        when(element.getTagName()).thenReturn("button");

        // Test and Verify
        assertEquals("By.xpath(\"//button[text()='Search']\")", builder.buildLocator(element));
        assertNotNull(builder.getLastLocator());
    }

    @Test
    public void testElementWithTagName() {

        // Create mock
        WebElement element = Mockito.mock(WebElement.class);
        when(element.getAttribute("id")).thenReturn("");
        when(element.getAttribute("name")).thenReturn("");
        when(element.getText()).thenReturn("");
        when(element.getTagName()).thenReturn("button");

        // Test and Verify
        assertEquals("By.tagName(\"button\")", builder.buildLocator(element));
        assertNotNull(builder.getLastLocator());
    }



}
