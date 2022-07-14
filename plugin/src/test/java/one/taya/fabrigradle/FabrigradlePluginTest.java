/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package one.taya.fabrigradle;

import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A simple unit test for the 'Fabrigradle.greeting' plugin.
 */
class FabrigradlePluginTest {
    @Test void pluginRegistersATask() {
        // Create a test project and apply the plugin
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("one.taya.fabrigradle");

        // Verify the result
        assertNotNull(project.getTasks().findByName("generateFabricModJson"));
    }
}
