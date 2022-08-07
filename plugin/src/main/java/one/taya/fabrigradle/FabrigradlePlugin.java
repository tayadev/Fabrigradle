package one.taya.fabrigradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class FabrigradlePlugin implements Plugin<Project> {
    public void apply(Project project) {

        FabrigradleExtension ext = project.getExtensions().create("fabrigradle", FabrigradleExtension.class);
        
        project.getTasks().register("generateFabricModJson", GenerateFabricModJsonTask.class, task -> {
            task.getConfig().set(ext);
            if(ext.getMixins().getPackageName().isPresent()) task.dependsOn("generateMixinJson");
        });
        
        project.getTasks().register("generateMixinJson", GenerateMixinJsonTask.class, task -> {
            task.getConfig().set(ext);
        });
        
        project.getTasks().register("acceptEula", AcceptEulaTask.class);
        
        project.afterEvaluate(p -> {
            ext.getId().convention(project.getName());
            ext.getVersion().convention(project.getVersion().toString());
            
            project.getPluginManager().apply("fabric-loom");

            project.getDependencies().add("minecraft", "com.mojang:minecraft:" + ext.getVersions().minecraft);
            project.getDependencies().add("mappings", "net.fabricmc:yarn:" + ext.getVersions().mappings + ":v2");
            project.getDependencies().add("modImplementation", "net.fabricmc:fabric-loader:" + ext.getVersions().loader);
            project.getDependencies().add("modImplementation", "net.fabricmc.fabric-api:fabric-api:" + ext.getVersions().fabricApi);
            
            project.getTasks().findByName("compileJava").dependsOn("generateFabricModJson");
            if(ext.getAcceptEula().getOrElse(false)) project.getTasks().findByName("configureLaunch").dependsOn("acceptEula");
        });

    }
}
