package cn.bobdeng.rbac.api;


import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class WebDriverDestroyer implements TestExecutionListener {
    @Autowired
    RedissonClient redissonClient;

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        if (WebDriverHandler.WEBDRIVER != null) {
            //WebDriverHandler.WEBDRIVER.close();
        }
    }
}
