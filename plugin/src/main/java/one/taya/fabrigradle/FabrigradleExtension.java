package one.taya.fabrigradle;

import java.util.List;

import javax.inject.Inject;

import org.gradle.api.Action;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.tasks.Nested;

import one.taya.fabrigradle.FabricModJson.Entrypoint;
import one.taya.fabrigradle.FabricModJson.EntrypointContainer;
import one.taya.fabrigradle.FabricModJson.Environment;

public class FabrigradleExtension {
    public String id;
    public void id(String id) { this.id = id; }

    public String version;
    public void version(String version) { this.version = version; }

    public Environment environment;
    public void environment(Environment environment) { this.environment = environment; }

    public String name;
    public void name(String name) { this.name = name; }

    public String description;
    public void description(String description) { this.description = description; }
    
    // TODO: figure out how to make this work???
    // public EntrypointsExtension entrypoints = objects.newInstance(EntrypointsExtension.class);
    // public void entrypoints(Action<EntrypointsExtension> action) {
    //     action.execute(entrypoints);
    // }

    @Nested
    public EntrypointsExtension entrypoints = new EntrypointsExtension();
    public void entrypoints(Action<? super EntrypointsExtension> action) {
        action.execute(entrypoints);
    }
}

class EntrypointsExtension {
    public List<String> main;
    public void main(String... main) { this.main = List.of(main); }

    public List<String> client;
    public void client(String... client) { this.client = List.of(client); }

    public List<String> server;
    public void server(String... server) { this.server = List.of(server); }

    public EntrypointContainer parse() {
        // TODO: this isn't complete
        if(main == null) return null; // this is dumb
        return EntrypointContainer.builder()
            .main(main.stream().map(e -> {return new Entrypoint(e);}).toList())
            .client(main.stream().map(e -> {return new Entrypoint(e);}).toList())
            .server(main.stream().map(e -> {return new Entrypoint(e);}).toList())
            .build();
    }
}