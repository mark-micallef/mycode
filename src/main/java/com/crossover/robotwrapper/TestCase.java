package com.crossover.robotwrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by markmicallef on 24/05/2016.
 */
public class TestCase {

    protected List<TestAction> actions;

    public TestCase() {
        actions = new ArrayList<TestAction>();
    }

    public List<TestAction> getActions() {
        return actions;
    }

    public TestAction[] getActionsArray() {
        return actions.toArray(new TestAction[actions.size()]);
    }

    public void addAction(TestAction action) {
        actions.add(action);
    }

    public void clear() {
        actions.clear();
    }


    public String generateCode() {
        StringBuffer sb = new StringBuffer();


        //Generate opening boilerplate
        sb.append("import org.junit.*;\n");
        sb.append("import static org.junit.Assert.*;\n");
        sb.append("import java.util.concurrent.TimeUnit;\n");
        sb.append("import org.openqa.selenium.*;\n");
        sb.append("import org.openqa.selenium.interactions.Actions;\n");
        sb.append("import org.openqa.selenium.firefox.FirefoxDriver;\n\n");
        sb.append("public class SampleTestSuite {\n\n");
        sb.append(" WebDriver driver;\n");
        sb.append(" WebElement element;\n");
        sb.append("  Actions builder;\n\n");
        sb.append(" @Before\n");
        sb.append(" public void setUp() {\n");
        sb.append("     driver = new FirefoxDriver();\n");
        sb.append("     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);\n");
        sb.append("     builder = new Actions(driver);\n");
        sb.append(" }\n\n");
        sb.append(" @After\n");
        sb.append(" public void tearDown() {\n");
        sb.append("     driver.close();\n");
        sb.append(" }\n\n");
        sb.append(" @Test\n");


        //Generate test code
        sb.append(" public void myTestCase() {\n");

        for (TestAction action : actions) {
            sb.append(action.generateCode());
            sb.append(action.getVerificationCode());
        }

        sb.append(" }\n\n");

        //Generate closing boilerplate code
        sb.append("}\n");

        return sb.toString();
    }

}
