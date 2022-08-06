package cn.bobdeng.rbac.api;


import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

public class WebDriverDestroyer implements TestExecutionListener {
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        if (WebDriverHandler.WEBDRIVER != null) {
           // WebDriverHandler.WEBDRIVER.close();
        }
    }
}
