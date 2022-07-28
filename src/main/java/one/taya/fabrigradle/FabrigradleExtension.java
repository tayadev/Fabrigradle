package one.taya.fabrigradle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import one.taya.fabrigradle.FabricModJson.Environment;

@Accessors(chain = true)
public class FabrigradleExtension {
    @Input @Optional @Getter @Setter String id;
    @Input @Optional @Getter @Setter String version;

    @Input @Optional @Getter @Setter Environment environment;

    @Nested @Optional @Getter Entrypoints entrypoints;
    public void entrypoints(Action<? super Entrypoints> action) {
        if (entrypoints == null) entrypoints = new Entrypoints();
        action.execute(entrypoints);
    }

    @Input @Optional @Getter List<String> jars = new ArrayList<String>();
    void jars(String... jar) { this.jars.addAll(List.of(jar)); }

    @Nested @Optional @Getter LanguageAdapters languageAdapters;
    public void languageAdapters(Action<? super LanguageAdapters> action) {
        if (languageAdapters == null) languageAdapters = new LanguageAdapters();
        action.execute(languageAdapters);
    }

    // TODO: figure out mixin handling
    @Nested @Optional @Getter @Setter Mixins mixins;
    public void mixins(Action<? super Mixins> action) {
        if (mixins == null) mixins = new Mixins();
        action.execute(mixins);
    }

    @Input @Optional @Getter @Setter String accessWidener;

    @Nested @Optional @Getter Dependencies depends;
    public void depends(Action<? super Dependencies> action) {
        if (depends == null) depends = new Dependencies();
        action.execute(depends);
    }

    @Nested @Optional @Getter Dependencies recommends;
    public void recommends(Action<? super Dependencies> action) {
        if (recommends == null) recommends = new Dependencies();
        action.execute(recommends);
    }

    @Nested @Optional @Getter Dependencies suggests;
    public void suggests(Action<? super Dependencies> action) {
        if (suggests == null) suggests = new Dependencies();
        action.execute(suggests);
    }

    @Nested @Optional @Getter Dependencies conflicts;
    public void conflicts(Action<? super Dependencies> action) {
        if (conflicts == null) conflicts = new Dependencies();
        action.execute(conflicts);
    }

    @Nested @Optional @Getter Dependencies breaks;
    public void breaks(Action<? super Dependencies> action) {
        if (breaks == null) breaks = new Dependencies();
        action.execute(breaks);
    }

    @Input @Optional @Getter @Setter String name;
    @Input @Optional @Getter @Setter String description;

    @Nested @Optional @Getter People authors;
    public void authors(Action<? super People> action) {
        if (authors == null) authors = new People();
        action.execute(authors);
    }

    @Nested @Optional @Getter People contributors;
    public void contributors(Action<? super People> action) {
        if (contributors == null) contributors = new People();
        action.execute(contributors);
    }

    @Nested @Optional @Getter Contact contact;
    public void contact(Action<? super Contact> action) {
        if (contact == null) contact = new Contact();
        action.execute(contact);
    }

    @Input @Optional @Getter List<String> license = new ArrayList<String>();
    void license(String... license) { this.license.addAll(List.of(license)); }

    @Nested @Optional @Getter Icons icons;
    public void icons(Action<? super Icons> action) {
        if (icons == null) icons = new Icons();
        action.execute(icons);
    }

    @Input @Optional @Getter @Setter String icon;
}

class Entrypoints {
    @Input @Optional @Getter
    List<String> main = new ArrayList<String>();
    void main(String... main) { this.main.addAll(List.of(main)); };
    
    @Input @Optional @Getter
    List<String> client = new ArrayList<String>();
    void client(String... client) { this.client.addAll(List.of(client)); };

    @Input @Optional @Getter
    List<String> server = new ArrayList<String>();
    void server(String... server) { this.server.addAll(List.of(server)); };
}

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

class People {
    @Nested @Optional @Getter List<Person> people = new ArrayList<Person>();

    Person name(String name) {
        Person person = new Person(name);
        people.add(person);
        return person;
    }
}

class Person {

    // TODO: figure out how to allow custom properties?

    @Input @Optional @Getter String name;
    @Input @Optional @Getter String email;
    @Input @Optional @Getter String irc;
    @Input @Optional @Getter String homepage;

    Person(String name) { this.name = name; }
    Person email(String email) { this.email = email; return this; }
    Person irc(String irc) { this.irc = irc; return this; }
    Person homepage(String homepage) { this.homepage = homepage; return this; }
}

class Contact {
    @Input @Optional @Getter String email;
    @Input @Optional @Getter String irc;
    @Input @Optional @Getter String homepage;
    @Input @Optional @Getter String issues;
    @Input @Optional @Getter String sources;

    void email(String email) { this.email = email; }
    void irc(String irc) { this.irc = irc; }
    void homepage(String homepage) { this.homepage = homepage; }
    void issues(String issues) { this.issues = issues; }
    void sources(String sources) { this.sources = sources; }
}

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

class Mixins {
    @Input @Getter String packageName;
    @Input @Optional @Getter List<String> mixins;
    @Input @Optional @Getter List<String> client;
    @Input @Optional @Getter List<String> server;
    @Input @Optional @Getter String refmap;
    @Input @Optional @Getter Integer priority;
    @Input @Optional @Getter String plugin;
    @Input @Optional @Getter Boolean required;
    @Input @Optional @Getter String minVersion;
    @Input @Optional @Getter Boolean setSourceFile;
    @Input @Optional @Getter Boolean verbose;
    @Input @Optional @Getter String compatibilityLevel;
    @Input @Optional @Getter Map<String, Integer> injectors;
}