package one.taya.fabrigradle;

import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FabrigradlePluginTest {
    @Test void pluginRegistersATask() {
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("one.taya.fabrigradle");

        assertNotNull(project.getTasks().findByName("generateFabricModJson"));
        assertNotNull(project.getTasks().findByName("generateMixinJson"));
        assertNotNull(project.getTasks().findByName("acceptEula"));
    }
}
