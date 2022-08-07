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

import one.taya.fabrigradle.MixinJson.MixinJson;

public class MixinJsonTest {
     
    @Test
    public void exampleModMixinJson() throws JSONException, IOException, URISyntaxException {
        MixinJson mixinJson = new MixinJson()
            .setRequired(true)
            .setMinVersion("0.8")
            .setPackageName("net.fabricmc.example.mixin")
            .setCompatibilityLevel("JAVA_8")
            .setMixins(List.of())
            .setClient(List.of("ExampleMixin"))
            .setInjectors(Map.of("defaultRequire", 1));

        String serialized = new ObjectMapper().writeValueAsString(mixinJson);
        String expected = Files.readString(Paths.get(getClass().getClassLoader().getResource("exampleMod-mixins.json").toURI()));
        JSONAssert.assertEquals(expected, serialized, JSONCompareMode.STRICT);
    }

    @Test
    public void maxMixinJson() throws IOException, URISyntaxException, JSONException {
        MixinJson mixinJson = new MixinJson()
            .setMixins(List.of("mixinA", "mixinB", "mixinC"))
            .setServer(List.of("serverMixinA", "serverMixinB", "serverMixinC"))
            .setClient(List.of("clientMixinA", "clientMixinB", "clientMixinC"))
            .setCompatibilityLevel("compatibilityLevel")
            .setInjectors(Map.of("injectorA", 1, "injectorB", 2))
            .setMinVersion("minVersion")
            .setPackageName("package")
            .setPlugin("plugin")
            .setPriority(1)
            .setRefmap("refmap")
            .setRequired(true)
            .setSetSourceFile(true)
            .setVerbose(true);
        
        String serialized = new ObjectMapper().writeValueAsString(mixinJson);
        String expected = Files.readString(Paths.get(getClass().getClassLoader().getResource("max-mixins.json").toURI()));
        JSONAssert.assertEquals(expected, serialized, JSONCompareMode.STRICT);
    }

}
