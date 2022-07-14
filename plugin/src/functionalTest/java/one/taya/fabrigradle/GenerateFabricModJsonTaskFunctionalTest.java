package one.taya.fabrigradle;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class GenerateFabricModJsonTaskFunctionalTest {

    @Test
    public void generatesFabricModJson() throws IOException {
        FabrigradleTestBuild build = new FabrigradleTestBuild();
        build.runGenerateFabricModJsonTask();

        File fabricModJson = new File(build.projectDir.getPath().concat("/build/resources/main/fabric.mod.json"));
        assertTrue(fabricModJson.exists());
    }

    @Test
    public void id() {
        modJsonTest(" fabrigradle { id 'example' }", "{ \"schemaVersion\": 1, \"id\": \"example\" }");
    }

    @Test
    public void idUseProjectIdWhenMissing() {
        modJsonTest("", "{ \"schemaVersion\": 1, \"id\": \"functionalTest\" }");
    }

    @Test
    public void name() {
        modJsonTest("fabrigradle { name 'Example' }", "{ \"schemaVersion\": 1, \"name\": \"Example\" }");
    }

    @Test
    public void entrypoints() {
        modJsonTest("fabrigradle { entrypoints { main 'a', 'b' } }", "{ \"schemaVersion\": 1, \"entrypoints\": { \"main\": [\"a\", \"b\"] } }");
    }

    @Test
    public void jars() {
        modJsonTest("fabrigradle { jars 'a', 'b' }", "{ \"schemaVersion\": 1, \"jars\": [{ \"file\": \"a\"}, {\"file\": \"b\"}] }");
    }

    @Test
    public void languageAdapters() {
        modJsonTest("fabrigradle { languageAdapters { id 'a' implementation 'b' } }", "{ \"schemaVersion\": 1, \"languageAdapters\": { \"a\": \"b\"} }");
    }

    @Test
    public void accessWidener() {
        modJsonTest("fabrigradle { accessWidener 'a' }", "{ \"schemaVersion\": 1, \"accessWidener\": \"a\" }");
    }

    @Test
    public void depends() {
        modJsonTest("""
            fabrigradle {
                depends {
                    id 'a' version 'b'
                    id 'c' version 'd'
                }
            }
        """, "{ \"schemaVersion\": 1, \"depends\": {\"a\": \"b\", \"c\": \"d\"} }");
    }

    @Test
    public void recommends() {
        modJsonTest("""
            fabrigradle {
                recommends {
                    id 'a' version 'b'
                    id 'c' version 'd'
                }
            }
        """, "{ \"schemaVersion\": 1, \"recommends\": {\"a\": \"b\", \"c\": \"d\"} }");
    }

    @Test
    public void suggests() {
        modJsonTest("""
            fabrigradle {
                suggests {
                    id 'a' version 'b'
                    id 'c' version 'd'
                }
            }
        """, "{ \"schemaVersion\": 1, \"suggests\": {\"a\": \"b\", \"c\": \"d\"} }");
    }

    @Test
    public void conflicts() {
        modJsonTest("""
            fabrigradle {
                conflicts {
                    id 'a' version 'b'
                    id 'c' version 'd'
                }
            }
        """, "{ \"schemaVersion\": 1, \"conflicts\": {\"a\": \"b\", \"c\": \"d\"} }");
    }

    @Test
    public void breaks() {
        modJsonTest("""
            fabrigradle {
                breaks {
                    id 'a' version 'b'
                    id 'c' version 'd'
                }
            }
        """, "{ \"schemaVersion\": 1, \"breaks\": {\"a\": \"b\", \"c\": \"d\"} }");
    }

    private String getGeneratedFabricModJson(FabrigradleTestBuild build) throws IOException {
        return Files.readString(build.projectDir.toPath().resolve("build/resources/main/fabric.mod.json"));
    }

    private void modJsonTest(String config, String expected) {

        System.out.println("Running with config: " + config);

        try {
            FabrigradleTestBuild build = new FabrigradleTestBuild();
            build.addConfig(config);
            build.runGenerateFabricModJsonTask();
            String fabricModJson = getGeneratedFabricModJson(build);

            JSONAssert.assertEquals(expected, fabricModJson, false);
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
