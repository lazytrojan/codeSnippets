package codeSnippets;

import java.util.HashMap;
import java.util.Map;

interface config {

}

class s3Config implements config {

}

class MCConfig implements config {

}

interface DataStreamDeploymentStrategy {
    void doAnalyse(config foo);
}

class S3DataStreamDeployment implements DataStreamDeploymentStrategy {
    @Override
    public void doAnalyse(final config foo) {
        System.out.println(foo.getClass());
    }
}

class MCDataStreamDeployment implements DataStreamDeploymentStrategy {
    @Override
    public void doAnalyse(final config foo) {
        System.out.println(foo.getClass());
    }
}

class DSContext {
    private final DataStreamDeploymentStrategy strategy;

    public DSContext(final DataStreamDeploymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void doAnalyse(final config foo) {
        strategy.doAnalyse(foo);
    }
}

// Use registry pattern along with Strategy pattern

public class Strategy {
    static Map<String, DataStreamDeploymentStrategy> registry = new HashMap<>();

    static {
        registry.put("S3", new S3DataStreamDeployment());
        registry.put("SFMC", new MCDataStreamDeployment());
    }

    public static void main(final String[] args) {
        config foo = new s3Config();
        DSContext context = new DSContext(registry.get("S3"));
        context.doAnalyse(foo);
        context = new DSContext(registry.get("SFMC"));
        foo = new MCConfig();
        context.doAnalyse(foo);
    }
}