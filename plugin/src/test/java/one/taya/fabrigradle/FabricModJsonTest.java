package one.taya.fabrigradle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import one.taya.fabrigradle.FabricModJson.Entrypoint;
import one.taya.fabrigradle.FabricModJson.EntrypointContainer;
import one.taya.fabrigradle.FabricModJson.Environment;
import one.taya.fabrigradle.FabricModJson.FabricModJson;
import one.taya.fabrigradle.FabricModJson.Icon;
import one.taya.fabrigradle.FabricModJson.License;
import one.taya.fabrigradle.FabricModJson.Mixin;
import one.taya.fabrigradle.FabricModJson.NestedJarEntry;
import one.taya.fabrigradle.FabricModJson.Person;
import one.taya.fabrigradle.FabricModJson.VersionRange;

public class FabricModJsonTest {
    
    @Test
    public void exampleModFabricModJson() throws JSONException, IOException, URISyntaxException {
        FabricModJson fabricModJson = new FabricModJson()
            .setSchemaVersion(1)
            .setId("modid")
            .setVersion("1.0.0")
            .setName("Example Mod")
            .setDescription("This is an example description! Tell everyone what your mod is about!")
            .setAuthors(List.of(new Person("Me!")))
            .setContact(Map.of("homepage", "https://fabricmc.net/", "sources", "https://github.com/FabricMC/fabric-example-mod"))
            .setLicense(new License("CC0-1.0"))
            .setIcon(new Icon("assets/modid/icon.png"))
            .setEnvironment(Environment.ALL)
            .setEntrypoints(EntrypointContainer.builder().main(List.of(new Entrypoint("net.fabricmc.example.ExampleMod"))).build())
            .setMixins(List.of(new Mixin("modid.mixins.json")))
            .setDepends(Map.of(
                "fabricloader", new VersionRange(">=0.14.6"),
                "fabric", new VersionRange("*"),
                "minecraft", new VersionRange("~1.19"),
                "java", new VersionRange(">=17")
            ))
            .setSuggests(Map.of(
                "another-mod", new VersionRange("*")
            ));

        String serialized = new ObjectMapper().writeValueAsString(fabricModJson);
        String expected = Files.readString(Paths.get(getClass().getClassLoader().getResource("exampleMod-fabric.mod.json").toURI()));
        JSONAssert.assertEquals(expected, serialized, JSONCompareMode.STRICT);
    }
    
    @Test
    public void maxModFabricModJson() throws JSONException, IOException, URISyntaxException {
        FabricModJson fabricModJson = new FabricModJson()
            .setSchemaVersion(1)
            .setId("example")
            .setVersion("0.0.1")
            .setEnvironment(Environment.SERVER)
            .setEntrypoints(EntrypointContainer.builder()
                .main(List.of(
                    new Entrypoint("net.fabricmc.example.ExampleMod"),
                    new Entrypoint("net.fabricmc.example.ExampleMod::handle"),
                    new Entrypoint("package.ClassName", "kotlin")
                ))
                .build())
            .setJars(List.of(new NestedJarEntry("nested/vendor/dependency.jar")))
            .setLanguageAdapters(Map.of("kotlin", "net.fabricmc.language.kotlin.KotlinAdapter"))
            .setMixins(List.of(new Mixin("modid.mixins.json"), new Mixin("modid.client-mixins.json", Environment.CLIENT)))
            .setAccessWidener("modid.accesswidener")
            .setDepends(Map.of("mod", new VersionRange("1.0.0")))
            .setRecommends(Map.of("mod", new VersionRange("1.0.0")))
            .setSuggests(Map.of("mod", new VersionRange("1.0.0")))
            .setBreaks(Map.of("mod", new VersionRange("1.0.0")))
            .setConflicts(Map.of("mod", new VersionRange("1.0.0")))
            .setName("Example Mod")
            .setDescription("Mod Description")
            .setContact(Map.of(
                "email", "mail@example.org",
                "irc", "irc://example.org",
                "homepage", "https://example.org",
                "issues", "https://example.org",
                "sources", "git://example.org"
            ))
            .setAuthors(List.of(new Person("You", Map.of("email", "mail@example.org"))))
            .setContributors(List.of(new Person("You", Map.of("email", "mail@example.org"))))
            .setLicense(new License("CC0-1.0", "GPL-3.0"))
            .setIcon(new Icon(Map.of(64, "assets/modid/icon-64.png", 128, "assets/modid/icon-128.png")));

        String serialized = new ObjectMapper().writeValueAsString(fabricModJson);
        String expected = Files.readString(Paths.get(getClass().getClassLoader().getResource("max-fabric.mod.json").toURI()));
        JSONAssert.assertEquals(expected, serialized, JSONCompareMode.STRICT);
    }
}
