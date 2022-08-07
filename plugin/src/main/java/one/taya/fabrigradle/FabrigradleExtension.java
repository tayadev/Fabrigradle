package one.taya.fabrigradle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import one.taya.fabrigradle.FabricModJson.Environment;
import one.taya.fabrigradle.FabricModJson.Person;

public abstract class FabrigradleExtension {

    @Nested @Optional abstract public Versions getVersions();
    public void versions(Action<? super Versions> action) { action.execute(getVersions()); }

    @Input @Optional abstract public Property<String> getId();
    void id(String id) { getId().set(id); }

    @Input @Optional abstract public Property<String> getVersion();
    void version(String version) { getVersion().set(version); }

    @Input @Optional abstract public Property<Environment> getEnvironment();
    void environment(Environment environment) { getEnvironment().set(environment); }

    @Nested abstract public Entrypoints getEntrypoints();
    public void entrypoints(Action<? super Entrypoints> action) { action.execute(getEntrypoints()); }

    @Input @Optional abstract public ListProperty<String> getJars();
    public void jars(String... jars) { getJars().addAll(jars); }

    @Nested abstract public LanguageAdapters getLanguageAdapters();
    public void languageAdapters(Action<? super LanguageAdapters> action) { action.execute(getLanguageAdapters()); }

    @Nested abstract public Mixins getMixins();
    public void mixins(Action<? super Mixins> action) { action.execute(getMixins()); }

    @Input @Optional abstract public Property<String> getAccessWidener();
    public void accessWidener(String accessWidener) { getAccessWidener().set(accessWidener); }

    @Nested abstract public Dependencies getDepends();
    public void depends(Action<? super Dependencies> action) { action.execute(getDepends()); }

    @Nested abstract public Dependencies getRecommends();
    public void recommends(Action<? super Dependencies> action) { action.execute(getRecommends()); }

    @Nested abstract public Dependencies getSuggests();
    public void suggests(Action<? super Dependencies> action) { action.execute(getSuggests()); }

    @Nested abstract public Dependencies getConflicts();
    public void conflicts(Action<? super Dependencies> action) { action.execute(getConflicts()); }

    @Nested abstract public Dependencies getBreaks();
    public void breaks(Action<? super Dependencies> action) { action.execute(getBreaks()); }

    @Input @Optional abstract public Property<String> getName();
    void name(String name) { getName().set(name); }

    @Input @Optional abstract public Property<String> getDescription();
    void description(String description) { getDescription().set(description); }

    @Nested abstract public People getAuthors();
    public void authors(Action<? super People> action) { action.execute(getAuthors()); }

    @Nested abstract public People getContributors();
    public void contributors(Action<? super People> action) { action.execute(getContributors()); }

    @Input @Optional abstract public MapProperty<String, String> getContact();
    public void contact(Map<String, String> map) { getContact().set(map); }

    @Input @Optional abstract public ListProperty<String> getLicense();
    public void license(String... license) { getLicense().addAll(license); }

    @Nested abstract public Icons getIcons();
    public void icons(Action<? super Icons> action) { action.execute(getIcons()); }

    @Input @Optional abstract public Property<String> getIcon();
    public void icon(String icon) { getIcon().set(icon); }

    @Input @Optional abstract public Property<Boolean> getAcceptEula();
    public void eula(boolean eula) { getAcceptEula().set(eula); }
}

@NoArgsConstructor
abstract class Versions {
    @Input @Optional abstract public Property<String> getMinecraft();
    public void minecraft(String version) { getMinecraft().set(version); }

    @Input @Optional abstract public Property<String> getMappings();
    public void mappings(String version) { getMappings().set(version); }

    @Input @Optional abstract public Property<String> getLoader();
    public void loader(String version) { getLoader().set(version); }

    @Input @Optional abstract public Property<String> getFabricApi();
    public void fabricApi(String version) { getFabricApi().set(version); }
}

@NoArgsConstructor
abstract class Entrypoints {
    @Input @Optional abstract public ListProperty<String> getMain();
    void main(String... main) { getMain().addAll(main); }

    @Input @Optional abstract public ListProperty<String> getClient();
    void client(String... client) { getClient().addAll(client); }

    @Input @Optional abstract public ListProperty<String> getServer();
    void server(String... server) { getServer().addAll(server); }
}

@NoArgsConstructor
class LanguageAdapters {
    @Nested @Optional @Getter List<LanguageAdapter> adapters = new ArrayList<LanguageAdapter>();

    LanguageAdapter id(String id) {
        LanguageAdapter languageAdapter = new LanguageAdapter(id);
        adapters.add(languageAdapter);
        return languageAdapter;
    }
}

class LanguageAdapter {
    @Input @Getter String id;
    @Input @Getter String implementation;

    LanguageAdapter(String id) { this.id = id; }
    void implementation(String implementation) { this.implementation = implementation; }
}

@NoArgsConstructor
class Dependencies {
    @Nested @Optional @Getter List<Dependency> dependencies = new ArrayList<Dependency>();

    Dependency id(String id) {
        Dependency dependency = new Dependency(id);
        dependencies.add(dependency);
        return dependency;
    }
}

class Dependency {
    @Input @Getter String id;
    @Input @Getter String version;

    Dependency(String id) { this.id = id; }
    void version(String version) { this.version = version; }
}

@NoArgsConstructor
class People {
    @Nested @Optional @Getter List<Person> people = new ArrayList<Person>();

    void person(Map<String, String> map) {
        String name = map.get("name");
        map.remove("name");
        people.add(
            new Person(
                name,
                map
            )
        );
    }
}

@NoArgsConstructor
class Icons {
    @Nested @Optional @Getter List<Icon> icons = new ArrayList<Icon>();

    Icon size(int size) {
        Icon icon = new Icon(size);
        icons.add(icon);
        return icon;
    }
}

class Icon {
    @Input @Getter int size;
    @Input @Getter String file;

    Icon(int size) { this.size = size; }
    void file(String file) { this.file = file; }
}

@NoArgsConstructor
abstract class Mixins {
    @Input @Optional abstract Property<String> getPackageName();
    void packageName(String packageName) { getPackageName().set(packageName); }

    @Input @Optional abstract ListProperty<String> getMixins();
    void mixins(String... mixins) { getMixins().addAll(mixins); }

    @Input @Optional abstract ListProperty<String> getClient();
    void client(String... mixins) { getClient().addAll(mixins); }

    @Input @Optional abstract ListProperty<String> getServer();
    void server(String... mixins) { getServer().addAll(mixins); }

    @Input @Optional abstract Property<String> getRefmap();
    void refmap(String refmap) { getRefmap().set(refmap); }

    @Input @Optional abstract Property<Integer> getPriority();
    void priority(int priority) { getPriority().set(priority); }

    @Input @Optional abstract Property<String> getPlugin();
    void plugin(String plugin) { getPlugin().set(plugin); }

    @Input @Optional abstract Property<Boolean> getRequired();
    void required(boolean required) { getRequired().set(required); }

    @Input @Optional abstract Property<String> getMinVersion();
    void minVersion(String minVersion) { getMinVersion().set(minVersion); }

    @Input @Optional abstract Property<Boolean> getSetSourceFile();
    void setSourceFile(boolean setSourceFile) { getSetSourceFile().set(setSourceFile); }

    @Input @Optional abstract Property<Boolean> getVerbose();
    void verbose(boolean verbose) { getVerbose().set(verbose); }

    @Input @Optional abstract Property<String> getCompatibilityLevel();
    void compatibilityLevel(String compatibilityLevel) { getCompatibilityLevel().set(compatibilityLevel); }
}