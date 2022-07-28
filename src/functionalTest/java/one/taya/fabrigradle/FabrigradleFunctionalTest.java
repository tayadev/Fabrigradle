package one.taya.fabrigradle;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.gradle.testkit.runner.BuildResult;
import org.junit.jupiter.api.Test;

public class FabrigradleFunctionalTest {
    @Test
    public void canRunTask() throws IOException {
        FabrigradleTestBuild build = new FabrigradleTestBuild();
        BuildResult result = build.runGenerateFabricModJsonTask();

        assertTrue(result.getOutput().contains("BUILD SUCCESSFUL"));
    }

}
