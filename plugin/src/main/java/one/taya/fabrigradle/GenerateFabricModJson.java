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

    @TaskAction
    public void generateFabricModJson() throws IOException {
        
        File outFile = getOutputFile();
        FabrigradleExtension ext = getConfig().get();
        
        EntrypointContainer entrypoints = new EntrypointContainer();
        if(ext.getEntrypoints() != null) {
            entrypoints.main = ext.getEntrypoints().main.stream().map(Entrypoint::new).toList();
            entrypoints.client = ext.getEntrypoints().client.stream().map(Entrypoint::new).toList();
            entrypoints.server = ext.getEntrypoints().server.stream().map(Entrypoint::new).toList();
        }

        Map<String, String> languageAdapters = null;
        if(ext.getLanguageAdapters() != null) languageAdapters = ext.getLanguageAdapters().adapters.stream().collect(Collectors.toMap(LanguageAdapter::getId, LanguageAdapter::getImplementation));

        Map<String, VersionRange> depends = ext.getDepends() != null ? ext.getDepends().dependencies.stream().collect(Collectors.toMap(Dependency::getId, d -> new VersionRange(d.version))) : null;
        Map<String, VersionRange> recommends = ext.getRecommends() != null ? ext.getRecommends().dependencies.stream().collect(Collectors.toMap(Dependency::getId, d -> new VersionRange(d.version))) : null;
        Map<String, VersionRange> suggests = ext.getSuggests() != null ? ext.getSuggests().dependencies.stream().collect(Collectors.toMap(Dependency::getId, d -> new VersionRange(d.version))) : null;
        Map<String, VersionRange> conflicts = ext.getConflicts() != null ? ext.getConflicts().dependencies.stream().collect(Collectors.toMap(Dependency::getId, d -> new VersionRange(d.version))) : null;
        Map<String, VersionRange> breaks = ext.getBreaks() != null ? ext.getBreaks().dependencies.stream().collect(Collectors.toMap(Dependency::getId, d -> new VersionRange(d.version))) : null;

        List<Person> authors = ext.getAuthors() != null ? ext.getAuthors().people.stream().map((one.taya.fabrigradle.Person p) -> { return new Person(p.name, ContactInformation.builder().email(p.email).irc(p.irc).homepage(p.homepage).build()); }).toList() : null;
        List<Person> contributors = ext.getContributors() != null ? ext.getContributors().people.stream().map((one.taya.fabrigradle.Person p) -> { return new Person(p.name, ContactInformation.builder().email(p.email).irc(p.irc).homepage(p.homepage).build()); }).toList() : null;

        ContactInformation contact = ext.getContact() != null ? ContactInformation.builder().email(ext.getContact().email).irc(ext.getContact().irc).homepage(ext.getContact().homepage).issues(ext.getContact().issues).sources(ext.getContact().sources).build() : null;

        Icon icon = null;
        if(ext.getIcon() != null) {
            icon = new Icon(ext.getIcon());
        } else if(ext.getIcons() != null) {
            icon = new Icon(ext.getIcons().icons.stream().collect(Collectors.toMap(i -> i.size, i -> i.file)));
        }

        FabricModJson fmj = FabricModJson.builder()
            .schemaVersion(1)
            .id             (ext.getId())
            .version        (ext.getVersion())
            .name           (ext.getName())
            .description    (ext.getDescription())
            .environment    (ext.getEnvironment())
            .entrypoints    (entrypoints)
            .jars           (ext.getJars().stream().map(NestedJarEntry::new).toList())
            .languageAdapters(languageAdapters)
            .accessWidener  (ext.accessWidener)
            .depends        (depends)
            .recommends     (recommends)
            .suggests       (suggests)
            .conflicts      (conflicts)
            .breaks         (breaks)
            .authors        (authors)
            .contributors   (contributors)
            .contact        (contact)
            .license        (new License(ext.getLicense()))
            .icon           (icon)
            .build();

        new ObjectMapper().writeValue(outFile, fmj);

        System.out.println("Generated fabric.mod.json");
        System.out.println("=> " + getOutputFile().getAbsolutePath());
    }

}
