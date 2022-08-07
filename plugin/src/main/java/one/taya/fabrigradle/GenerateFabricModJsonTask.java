package one.taya.fabrigradle;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import com.fasterxml.jackson.databind.ObjectMapper;

import one.taya.fabrigradle.FabricModJson.Entrypoint;
import one.taya.fabrigradle.FabricModJson.EntrypointContainer;
import one.taya.fabrigradle.FabricModJson.FabricModJson;
import one.taya.fabrigradle.FabricModJson.Icon;
import one.taya.fabrigradle.FabricModJson.License;
import one.taya.fabrigradle.FabricModJson.Mixin;
import one.taya.fabrigradle.FabricModJson.NestedJarEntry;
import one.taya.fabrigradle.FabricModJson.VersionRange;

public abstract class GenerateFabricModJsonTask extends DefaultTask {

    @OutputFile
    public abstract RegularFileProperty getOutputFile();

    @Nested
    abstract Property<FabrigradleExtension> getConfig();

    Map<String, VersionRange> parseDependencies(Dependencies dependecies) {
        return
            dependecies.dependencies.size() > 0
            ? dependecies.dependencies.stream().collect(Collectors.toMap(Dependency::getId, d -> new VersionRange(d.version)))
            : null;
    }

    @TaskAction
    public void generateFabricModJson() throws IOException {
        FabrigradleExtension ext = getConfig().get();
        
        EntrypointContainer entrypoints = new EntrypointContainer();
        if(ext.getEntrypoints() != null) {
            entrypoints.main = ext.getEntrypoints().getMain().get().stream().map(Entrypoint::new).toList();
            entrypoints.client = ext.getEntrypoints().getClient().get().stream().map(Entrypoint::new).toList();
            entrypoints.server = ext.getEntrypoints().getServer().get().stream().map(Entrypoint::new).toList();
        }

        Map<String, String> languageAdapters = 
            ext.getLanguageAdapters() != null
            ? languageAdapters = ext.getLanguageAdapters().adapters
                .stream()
                .collect(
                    Collectors.toMap(
                        LanguageAdapter::getId,
                        LanguageAdapter::getImplementation
                    ))
            : null;

        Icon icon = null;
        if(ext.getIcon().isPresent()) {
            icon = new Icon(ext.getIcon().get());
        } else if(ext.getIcons().icons.size() > 0) {
            icon = new Icon(ext.getIcons().icons.stream().collect(Collectors.toMap(i -> i.size, i -> i.file)));
        }

        FabricModJson fabricModJson = new FabricModJson()
            .setSchemaVersion(1)
            .setId                 (ext.getId().getOrNull())
            .setVersion            (ext.getVersion().getOrNull())
            .setName               (ext.getName().getOrNull())
            .setDescription        (ext.getDescription().getOrNull())
            .setEnvironment        (ext.getEnvironment().getOrNull())
            .setEntrypoints        (entrypoints)
            .setJars               (ext.getJars().get().size() > 0 ? ext.getJars().get().stream().map(NestedJarEntry::new).toList() : null)
            .setLanguageAdapters   (languageAdapters)
            .setAccessWidener      (ext.getAccessWidener().getOrNull())
            .setDepends            (parseDependencies(ext.getDepends()))
            .setRecommends         (parseDependencies(ext.getRecommends()))
            .setSuggests           (parseDependencies(ext.getSuggests()))
            .setConflicts          (parseDependencies(ext.getConflicts()))
            .setBreaks             (parseDependencies(ext.getBreaks()))
            .setAuthors            (ext.getAuthors().getPeople())
            .setContributors       (ext.getContributors().getPeople())
            .setContact            (ext.getContact().getOrNull())
            .setLicense            (ext.getLicense().get().size() > 0  ? new License(ext.getLicense().get()) : null)
            .setIcon               (icon)
            .setMixins(ext.getMixins().getPackageName().isPresent() ? List.of(new Mixin("mixins.json")) : null);

        new ObjectMapper().writeValue(getOutputFile().get().getAsFile(), fabricModJson);
    }

}
