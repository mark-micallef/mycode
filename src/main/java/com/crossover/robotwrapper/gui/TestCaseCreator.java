package com.crossover.robotwrapper.gui;

import com.crossover.robotwrapper.*;
import com.crossover.robotwrapper.testactions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by markmicallef on 23/05/2016.
 */
public class TestCaseCreator extends JDialog implements Runnable, ActionListener, WindowListener {

    WebDriver driver;
    WebElement currentElement = null;
    String currentLocatorCode = null;
    By currentLocator = null;
    TestCase testcase;

    JTextField fldComponentDetails;
    JButton btnValidateLocator;
    JComboBox<TestAction> cmbActions;
    JButton btnAddAction;
    JList<TestAction> lstTestCaseActions;
    JButton btnClearTestCase;
    JButton btnGenerateTestCaseAsText;
    JCheckBox chkExecuteActionAfterCreation;

    LocatorBuilder locatorBuilder;

    SpyTool spyTool;

    boolean dirty = false;
    boolean shuttingDown = false;

    public TestCaseCreator(SpyTool spyTool, WebDriver driver) {
        this.spyTool = spyTool;
        this.driver = driver;
    }

    public void init() {

        testcase = new TestCase();

        locatorBuilder = new LocatorBuilder();

        setTitle("Test Action Creator");
        setSize(640, 480);
        setLayout(new BorderLayout());

        //Layout Dialog
        JPanel panel = new JPanel();
        fldComponentDetails = new JTextField("<no component selected>", JLabel.CENTER);
        JLabel lblCompLoc = new JLabel("Locator: ");
        Font f = lblCompLoc.getFont();
        lblCompLoc.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        panel.add(lblCompLoc);
        panel.add(fldComponentDetails);
        btnValidateLocator = new JButton("Validate Locator");
        btnValidateLocator.setToolTipText("Validates the locator in the text field (assumes it to be an xpath expression)");
        btnValidateLocator.addActionListener(this);
        panel.add(btnValidateLocator);

        JPanel panel2 = new JPanel(new GridLayout(2, 1));
        panel2.add(panel);

        //Build Action List
        cmbActions = new JComboBox<TestAction>();
        cmbActions.addItem(new ActionActivate(driver));
        cmbActions.addItem(new ActionClick(driver));
        cmbActions.addItem(new ActionClickMiddle(driver));
        cmbActions.addItem(new ActionClickRight(driver));
        cmbActions.addItem(new ActionClose(driver));
        cmbActions.addItem(new ActionDblClick(driver));
        cmbActions.addItem(new ActionDblClickMiddle(driver));
        cmbActions.addItem(new ActionDblClickRight(driver));
        ActionGet actionGet = new ActionGet(driver);
        cmbActions.addItem(actionGet);
        cmbActions.addItem(new ActionSendKeys(driver));

        cmbActions.setSelectedItem(actionGet);

        chkExecuteActionAfterCreation = new JCheckBox("Execute action now");
        chkExecuteActionAfterCreation.setSelected(true);

        panel = new JPanel();
        panel.add(new JLabel("Action: "));
        panel.add(cmbActions);
        btnAddAction = new JButton("Add Action");
        btnAddAction.addActionListener(this);
        panel.add(btnAddAction);
        panel.add(chkExecuteActionAfterCreation);

        panel2.add(panel);

        add(panel2, BorderLayout.NORTH);

        lstTestCaseActions = new JList<TestAction>();
        add(new JScrollPane(lstTestCaseActions), BorderLayout.CENTER);

        //Layout bottom panel
        panel = new JPanel();
        btnClearTestCase = new JButton("Clear Test Case");
        btnGenerateTestCaseAsText = new JButton("Generate Test Case as Text");

        btnClearTestCase.addActionListener(this);
        btnGenerateTestCaseAsText.addActionListener(this);

        panel.add(btnClearTestCase);
        panel.add(btnGenerateTestCaseAsText);

        add(panel, BorderLayout.SOUTH);

        //Add Window-Level Listeners
        addWindowListener(this);

        setVisible(true);

    }

    public void updateElement(WebElement element) {
        currentElement = element;
        currentLocatorCode = locatorBuilder.buildLocator(currentElement);
        currentLocator = locatorBuilder.getLastLocator();
        dirty = true;
    }

    public void run() {
        init();

        while (!shuttingDown) {
            if (dirty) {
                if (currentLocatorCode != null) {
                    fldComponentDetails.setText(currentLocatorCode.toString());
                }
                lstTestCaseActions.setListData(testcase.getActionsArray());
                dirty = false;
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }

        }

    }

    public boolean validateLocator(String locatorToCheck) {
        currentLocator = null;

        if (locatorToCheck.startsWith("By.name(\"")) {
            locatorToCheck = locatorToCheck.substring("By.name(\"".length());
            locatorToCheck = locatorToCheck.substring(0, locatorToCheck.lastIndexOf("\")"));
            currentLocatorCode = "By.name(\"" + locatorToCheck + "\")";
            currentLocator = By.name(locatorToCheck);
        } else if (locatorToCheck.startsWith("By.tagName(\"")) {
            locatorToCheck = locatorToCheck.substring("By.tagName(\"".length());
            locatorToCheck = locatorToCheck.substring(0, locatorToCheck.lastIndexOf("\")"));
            currentLocatorCode = "By.tagName(\"" + locatorToCheck + "\")";
            currentLocator = By.tagName(locatorToCheck);
        } else if (locatorToCheck.startsWith("By.xpath(\"")) {
            locatorToCheck = locatorToCheck.substring("By.xpath(\"".length());
            locatorToCheck = locatorToCheck.substring(0, locatorToCheck.lastIndexOf("\")"));
            currentLocatorCode = "By.xpath(\"" + locatorToCheck + "\")";
            currentLocator = By.xpath(locatorToCheck);
        } else {
            //Assume an xpath expression
            currentLocatorCode = "By.xpath(\"" + locatorToCheck + "\")";
            currentLocator = By.xpath(locatorToCheck);
        }

        boolean error = true;

        if (currentLocator != null) {

            error = false;

            try {
                driver.findElement(currentLocator);
            } catch (Exception ex) {
                ex.printStackTrace();
                error = true;
            }
        }

        if (!error) {
            currentElement = driver.findElement(currentLocator);

        }

        return !error;
    }

    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == btnAddAction) {

            //Create Test Action
            TestAction action = null;
            Object selectedItem = cmbActions.getSelectedItem();

            if (selectedItem instanceof ActionActivate) {
                action = new ActionActivate(driver);
            } else if (selectedItem instanceof ActionClick) {
                action = new ActionClick(driver);
            } else if (selectedItem instanceof ActionClickMiddle) {
                action = new ActionClickMiddle(driver);
            } else if (selectedItem instanceof ActionClickRight) {
                action = new ActionClickRight(driver);
            } else if (selectedItem instanceof ActionClose) {
                action = new ActionClose(driver);
            } else if (selectedItem instanceof ActionDblClick) {
                action = new ActionDblClick(driver);
            } else if (selectedItem instanceof ActionDblClickMiddle) {
                action = new ActionDblClickMiddle(driver);
            } else if (selectedItem instanceof ActionDblClickRight) {
                action = new ActionDblClickRight(driver);
            } else if (selectedItem instanceof ActionGet) {
                action = new ActionGet(driver);
            } else if (selectedItem instanceof ActionSendKeys) {
                action = new ActionSendKeys(driver);
            } else {
                System.out.println("Unkown test action type " + selectedItem.toString());
            }

            if (action != null) {
                //Populate test action and add to current test case
                action.setElementLocatorCode(currentLocatorCode);
                action.setElementLocator(currentLocator);
                action.collectUserInput();
                testcase.addAction(action);
                dirty = true;

                //Execute action if needed
                if (chkExecuteActionAfterCreation.isSelected()) {
                    spyTool.pause();
                    action.execute();
                    Utils.sleep(1000);
                    spyTool.restart();
                }

                // Collect verification code
                action.collectVerificationCode();

            }
        } else if (e.getSource() == btnClearTestCase) {
            testcase.clear();
            dirty = true;
        } else if (e.getSource() == btnGenerateTestCaseAsText) {
            System.out.println(testcase.generateCode());
        } else if (e.getSource() == btnValidateLocator) {
            if (!validateLocator(fldComponentDetails.getText())) {
                JOptionPane.showMessageDialog(null, "Invalid locator or element not found");
            } else {
                JOptionPane.showMessageDialog(null, "The locator points to an existing element");
            }
        }

    }

    public void shutDown() {
        shuttingDown = true;
        spyTool.shutDown();
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
        shutDown();
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
        spyTool.pause();
    }

    public void windowDeactivated(WindowEvent e) {
        spyTool.restart();
    }
}
