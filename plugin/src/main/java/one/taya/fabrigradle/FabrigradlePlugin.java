package one.taya.fabrigradle;


import java.io.IOException;
import java.net.URL;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.tasks.TaskContainer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FabrigradlePlugin implements Plugin<Project> {
    public void apply(Project project) {

        FabrigradleExtension ext = project.getExtensions().create("fabrigradle", FabrigradleExtension.class);
        
        TaskContainer tasks = project.getTasks();
        DependencyHandler dependencies = project.getDependencies();

        tasks.register("generateFabricModJson", GenerateFabricModJsonTask.class, task -> {
            task.getConfig().set(ext);
            task.getOutputFile().set(project.getLayout().getBuildDirectory().file("resources/main/fabric.mod.json"));
            if(ext.getMixins().getPackageName().isPresent()) task.dependsOn("generateMixinJson");
        });
        
        tasks.register("generateMixinJson", GenerateMixinJsonTask.class, task -> {
            task.getConfig().set(ext);
            task.getOutputFile().set(project.getLayout().getBuildDirectory().file("resources/main/mixins.json"));
        });
        
        tasks.register("acceptEula", AcceptEulaTask.class, task -> {
            task.getOutputFile().set(project.getLayout().getProjectDirectory().file("run/eula.txt"));
        });
        
        ext.getId().convention(project.getName().toLowerCase());
        
        try {
            String apiUrl = "https://meta.fabricmc.net/v2";
            Versions versions = ext.getVersions();
            versions.getMinecraft().convention(getJson(apiUrl + "/versions/game").get(0).get("version").asText());
            versions.getMappings().convention(getJson(apiUrl + "/versions/yarn/" + versions.getMinecraft().get()).get(0).get("version").asText());
            versions.getLoader().convention(getJson(apiUrl + "/versions/loader/" + versions.getMinecraft().get()).get(0).get("loader").get("version").asText());
        } catch(IOException e) {}
        
        
        project.afterEvaluate(p -> {
            ext.getVersion().convention(project.getVersion().toString());
            
            // FIXME: major issue with this is that you can't add custom modImplementation dependencies,
            // because the configuration type doesn't exist at config resolution time,
            // but if i move this to before config resolution then loom complains about not having a minecraft version
            // or it doesn't allow you to pick a custom minecraft version
            project.getPluginManager().apply("fabric-loom");
            dependencies.add("minecraft", "com.mojang:minecraft:" + ext.getVersions().getMinecraft().get());
            dependencies.add("mappings", "net.fabricmc:yarn:" + ext.getVersions().getMappings().get() + ":v2");
            dependencies.add("modImplementation", "net.fabricmc:fabric-loader:" + ext.getVersions().getLoader().get());
            
            tasks.findByName("compileJava").dependsOn("generateFabricModJson");
            if(ext.getAcceptEula().getOrElse(false)) tasks.findByName("configureLaunch").dependsOn("acceptEula");
        });

    }

    JsonNode getJson(String url) throws IOException {
        return new ObjectMapper().readTree(new URL(url).openConnection().getInputStream());
    }
}
