package one.taya.fabrigradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class FabrigradlePlugin implements Plugin<Project> {
    public void apply(Project project) {
        
        // This doesn't work :(
        // project.getGradle().beforeSettings(settings -> {
        //     RepositoryHandler repos = settings.getPluginManagement().getRepositories();
        //     repos.maven(repo -> {
        //         repo.setUrl("https://maven.fabricmc.net");
        //     });
        //     repos.mavenCentral();
        // });
        
        FabrigradleExtension ext = project.getExtensions().create("fabrigradle", FabrigradleExtension.class);
        
        project.getTasks().register("generateFabricModJson", GenerateFabricModJsonTask.class, task -> {
            task.getConfig().set(ext);
            if(ext.getMixins().getPackageName().isPresent()) task.dependsOn("generateMixinJson");
        });
        
        project.getTasks().register("generateMixinJson", GenerateMixinJsonTask.class, task -> {
            task.getConfig().set(ext);
        });
        
        project.getTasks().register("acceptEula", AcceptEulaTask.class);

        project.getPlugins().apply("fabric-loom");
        
        // TODO: make versions dynamic
        project.getDependencies().add("minecraft", "com.mojang:minecraft:1.19.1");
        project.getDependencies().add("mappings", "net.fabricmc:yarn:1.19.1+build.1:v2");
        project.getDependencies().add("modImplementation", "net.fabricmc:fabric-loader:0.14.8");
        project.getDependencies().add("modImplementation", "net.fabricmc.fabric-api:fabric-api:0.58.4+1.19.1");
        
        project.getTasks().findByName("compileJava").dependsOn("generateFabricModJson");

        project.afterEvaluate(p -> {
            ext.getId().convention(project.getName());
            ext.getVersion().convention(project.getVersion().toString());

            if(ext.getAcceptEula().get()) project.getTasks().findByName("configureLaunch").dependsOn("acceptEula");
        });

    }
}
