package cn.bobdeng.rbac.api;


import cn.bobdeng.rbac.DataSourceConfig;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

public class WebDriverDestroyer implements TestExecutionListener {
    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        DataSourceConfig.redissonClient.shutdown();
    }
}
