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

import one.taya.fabrigradle.FabricModJson.ContactInformation;
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

        // Using example from fabric example mod to test this out
        FabricModJson fmj = FabricModJson.builder()
            .schemaVersion(1)
            .id("modid")
            .version("1.0.0")
            .name("Example Mod")
            .description("This is an example description! Tell everyone what your mod is about!")
            .authors(List.of(new Person("Me!")))
            .contact(ContactInformation.builder().homepage("https://fabricmc.net/").sources("https://github.com/FabricMC/fabric-example-mod").build())
            .license(new License("CC0-1.0"))
            .icon(new Icon("assets/modid/icon.png"))
            .environment(Environment.ALL)
            .entrypoints(EntrypointContainer.builder().main(List.of(new Entrypoint("net.fabricmc.example.ExampleMod"))).build())
            .mixins(List.of(new Mixin("modid.mixins.json")))
            .depends(Map.of(
                "fabricloader", new VersionRange(">=0.14.6"),
                "fabric", new VersionRange("*"),
                "minecraft", new VersionRange("~1.19"),
                "java", new VersionRange(">=17")
            ))
            .suggests(Map.of(
                "another-mod", new VersionRange("*")
            ))
            .build();

        String serialized = new ObjectMapper().writeValueAsString(fmj);
        String expected = Files.readString(Paths.get(getClass().getClassLoader().getResource("exampleMod-fabric.mod.json").toURI()));

        JSONAssert.assertEquals(expected, serialized, JSONCompareMode.STRICT);
    }
    
    @Test
    public void maxModFabricModJson() throws JSONException, IOException, URISyntaxException {

        FabricModJson fmj = FabricModJson.builder()
            .schemaVersion(1)
            .id("example")
            .version("0.0.1")
            .environment(Environment.SERVER)
            .entrypoints(EntrypointContainer.builder()
                .main(List.of(
                    new Entrypoint("net.fabricmc.example.ExampleMod"),
                    new Entrypoint("net.fabricmc.example.ExampleMod::handle"),
                    new Entrypoint("package.ClassName", "kotlin")
                ))
                .build())
            .jars(List.of(new NestedJarEntry("nested/vendor/dependency.jar")))
            .languageAdapters(Map.of("kotlin", "net.fabricmc.language.kotlin.KotlinAdapter"))
            .mixins(List.of(new Mixin("modid.mixins.json"), new Mixin("modid.client-mixins.json", Environment.CLIENT)))
            .accessWidener("modid.accesswidener")
            .depends(Map.of("mod", new VersionRange("1.0.0")))
            .recommends(Map.of("mod", new VersionRange("1.0.0")))
            .suggests(Map.of("mod", new VersionRange("1.0.0")))
            .breaks(Map.of("mod", new VersionRange("1.0.0")))
            .conflicts(Map.of("mod", new VersionRange("1.0.0")))
            .name("Example Mod")
            .description("Mod Description")
            .contact(ContactInformation.builder()
                .email("mail@example.org")
                .irc("irc://example.org")
                .homepage("https://example.org")
                .issues("https://example.org")
                .sources("git://example.org")
                .build())
            .authors(List.of(new Person("You", ContactInformation.builder().email("mail@example.org").build())))
            .contributors(List.of(new Person("You", ContactInformation.builder().email("mail@example.org").build())))
            .license(new License("CC0-1.0", "GPL-3.0"))
            .icon(new Icon(Map.of(64, "assets/modid/icon-64.png", 128, "assets/modid/icon-128.png")))
            .build();

        String serialized = new ObjectMapper().writeValueAsString(fmj);
        String expected = Files.readString(Paths.get(getClass().getClassLoader().getResource("max-fabric.mod.json").toURI()));

        JSONAssert.assertEquals(expected, serialized, JSONCompareMode.STRICT);
    }
}
