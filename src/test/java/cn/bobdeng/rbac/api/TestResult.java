package cn.bobdeng.rbac.api;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class TestResult {
    static Map<String, BiConsumer<TestResult, String>> setters = Map.of(
    );

    static {
        setters.put("HHH", TestResult::setA);
        setters.put("JJJ", TestResult::setA);
    }

    public void setA(String a) {

    }

    public void setB(String a) {

    }

    public void set(Map<String, String> values) {
        setters.entrySet().forEach(setter -> {
            setter.getValue().accept(this, values.get(setter.getKey()));
        });
    }
}
