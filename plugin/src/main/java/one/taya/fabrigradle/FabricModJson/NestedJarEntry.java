package one.taya.fabrigradle.FabricModJson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NestedJarEntry {
    @JsonProperty("file")
    String file;

    public NestedJarEntry(String file) {
        this.file = file;
    }
}
