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
import one.taya.fabrigradle.FabricModJson.NestedJarEntry;
import one.taya.fabrigradle.FabricModJson.Person;
import one.taya.fabrigradle.FabricModJson.VersionRange;

public abstract class GenerateFabricModJson extends DefaultTask {

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
            dependecies != null
            ? dependecies.dependencies.stream().collect(Collectors.toMap(Dependency::getId, d -> new VersionRange(d.version)))
            : null;
    }

    List<Person> parsePeople(People people) {
        return
            people != null
            ? people.people.stream().map((one.taya.fabrigradle.Person p) -> { return new Person(p.name, ContactInformation.builder().email(p.email).irc(p.irc).homepage(p.homepage).build()); }).toList()
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
            ext.getContact() != null
            ? ContactInformation
                .builder()
                .email(ext.getContact().email)
                .irc(ext.getContact().irc)
                .homepage(ext.getContact().homepage)
                .issues(ext.getContact().issues)
                .sources(ext.getContact().sources)
                .build()
            : null;

        Icon icon = null;
        if(ext.getIcon().isPresent()) {
            icon = new Icon(ext.getIcon().get());
        } else if(ext.getIcons() != null) {
            icon = new Icon(ext.getIcons().icons.stream().collect(Collectors.toMap(i -> i.size, i -> i.file)));
        }

        FabricModJson fmj = FabricModJson.builder()
            .schemaVersion(1)
            .id                 (ext.getId().getOrNull())
            .version            (ext.getVersion().getOrNull())
            .name               (ext.getName().getOrNull())
            .description        (ext.getDescription().getOrNull())
            .environment        (ext.getEnvironment().getOrNull())
            .entrypoints        (entrypoints)
            .jars               (ext.getJars().get().stream().map(NestedJarEntry::new).toList())
            .languageAdapters   (languageAdapters)
            .accessWidener      (ext.getAccessWidener().getOrNull())
            .depends            (parseDependencies(ext.getDepends()))
            .recommends         (parseDependencies(ext.getRecommends()))
            .suggests           (parseDependencies(ext.getSuggests()))
            .conflicts          (parseDependencies(ext.getConflicts()))
            .breaks             (parseDependencies(ext.getBreaks()))
            .authors            (parsePeople(ext.getAuthors()))
            .contributors       (parsePeople(ext.getContributors()))
            .contact            (contact)
            .license            (new License(ext.getLicense().get()))
            .icon               (icon)
            .build();

        new ObjectMapper().writeValue(outFile, fmj);

        System.out.println("Generated fabric.mod.json");
        System.out.println("=> " + getOutputFile().getAbsolutePath());
    }

}
