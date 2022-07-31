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

        // Using example from fabric example mod to test this out
        MixinJson mj = new MixinJson()
            .setRequired(true)
            .setMinVersion("0.8")
            .setPackageName("net.fabricmc.example.mixin")
            .setCompatibilityLevel("JAVA_8")
            .setMixins(List.of())
            .setClient(List.of("ExampleMixin"))
            .setInjectors(Map.of("defaultRequire", 1));

        String serialized = new ObjectMapper().writeValueAsString(mj);
        System.out.println(serialized);
        String expected = Files.readString(Paths.get(getClass().getClassLoader().getResource("exampleMod-mixins.json").toURI()));

        JSONAssert.assertEquals(expected, serialized, JSONCompareMode.STRICT);
    }

}
