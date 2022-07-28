package one.taya.fabrigradle;

import org.gradle.api.Project;
import org.gradle.api.Plugin;

public class FabrigradlePlugin implements Plugin<Project> {
    public void apply(Project project) {
        FabrigradleExtension ext = project.getExtensions().create("fabrigradle", FabrigradleExtension.class);

        // defaults
        ext.getId().convention(project.getName());
        ext.getVersion().convention(project.getVersion().toString());

        project.getTasks().register("generateFabricModJson", GenerateFabricModJson.class, task -> {
            task.getConfig().set(ext);
        });
    }
}
