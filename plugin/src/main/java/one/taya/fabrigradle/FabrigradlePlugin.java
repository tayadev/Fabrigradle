package one.taya.fabrigradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class FabrigradlePlugin implements Plugin<Project> {
    public void apply(Project project) {
        FabrigradleExtension ext = project.getExtensions().create("fabrigradle", FabrigradleExtension.class);
        
        project.getTasks().register("generateFabricModJson", GenerateFabricModJson.class, task -> {
            task.getConfig().set(ext);
        });
        
        project.getTasks().register("generateMixinJson", GenerateMixinJson.class, task -> {
            task.getConfig().set(ext);
            task.dependsOn("generateFabricModJson");
        });
        
        project.getRepositories().maven(arg0 -> {
            arg0.setUrl("https://maven.fabricmc.net/");
        });
        project.getRepositories().mavenCentral();

        project.getPlugins().apply("fabric-loom");
        
        // TODO: make versions dynamic
        project.getDependencies().add("minecraft", "com.mojang:minecraft:1.19.1");
        project.getDependencies().add("mappings", "net.fabricmc:yarn:1.19.1+build.1:v2");
        project.getDependencies().add("modImplementation", "net.fabricmc:fabric-loader:0.14.8");
        project.getDependencies().add("modImplementation", "net.fabricmc.fabric-api:fabric-api:0.58.4+1.19.1");

        project.afterEvaluate(p -> {
            ext.getId().convention(project.getName());
            ext.getVersion().convention(project.getVersion().toString());
        });

    }
}
