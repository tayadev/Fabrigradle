package one.taya.fabrigradle;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import com.fasterxml.jackson.databind.ObjectMapper;

import one.taya.fabrigradle.FabricModJson.ContactInformation;
import one.taya.fabrigradle.FabricModJson.Entrypoint;
import one.taya.fabrigradle.FabricModJson.EntrypointContainer;
import one.taya.fabrigradle.FabricModJson.FabricModJson;
import one.taya.fabrigradle.FabricModJson.Icon;
import one.taya.fabrigradle.FabricModJson.License;
import one.taya.fabrigradle.FabricModJson.Mixin;
import one.taya.fabrigradle.FabricModJson.NestedJarEntry;
import one.taya.fabrigradle.FabricModJson.Person;
import one.taya.fabrigradle.FabricModJson.VersionRange;

public abstract class GenerateFabricModJsonTask extends DefaultTask {

    @OutputFile
    public File getOutputFile() {
        File folder = getProject().file("build/resources/main");
        folder.mkdirs();
        return new File(folder, "fabric.mod.json");
    }

    @Nested
    abstract Property<FabrigradleExtension> getConfig();

    Map<String, VersionRange> parseDependencies(Dependencies dependecies) {
        return
            dependecies.dependencies.size() > 0
            ? dependecies.dependencies.stream().collect(Collectors.toMap(Dependency::getId, d -> new VersionRange(d.version)))
            : null;
    }

    List<Person> parsePeople(People people) {
        return
            people.people.size() > 0
            ? people.people.stream().map((one.taya.fabrigradle.Person p) -> { return new Person(p.name, new ContactInformation().setEmail(p.email).setIrc(p.irc).setHomepage(p.homepage)); }).toList()
            : null;
    }

    @TaskAction
    public void generateFabricModJson() throws IOException {
        
        File outFile = getOutputFile();
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

        ContactInformation contact =
            new ContactInformation()
                .setEmail(ext.getContact().getEmail().getOrNull())
                .setIrc(ext.getContact().getIrc().getOrNull())
                .setHomepage(ext.getContact().getHomepage().getOrNull())
                .setIssues(ext.getContact().getIssues().getOrNull())
                .setSources(ext.getContact().getSources().getOrNull());
        // TODO: figure out how to make contact = null when empty

        Icon icon = null;
        if(ext.getIcon().isPresent()) {
            icon = new Icon(ext.getIcon().get());
        } else if(ext.getIcons().icons.size() > 0) {
            icon = new Icon(ext.getIcons().icons.stream().collect(Collectors.toMap(i -> i.size, i -> i.file)));
        }

        FabricModJson fmj = new FabricModJson()
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
            .setAuthors            (parsePeople(ext.getAuthors()))
            .setContributors       (parsePeople(ext.getContributors()))
            .setContact            (contact)
            .setLicense            (ext.getLicense().get().size() > 0  ? new License(ext.getLicense().get()) : null)
            .setIcon               (icon)
            .setMixins(ext.getMixins().getPackageName().isPresent() ? List.of(new Mixin("mixins.json")) : null);

        new ObjectMapper().writeValue(outFile, fmj);
    }

}
