package one.taya.fabrigradle.FabricModJson;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Environment {
    @JsonProperty("*") ALL,
    @JsonProperty("client") CLIENT,
    @JsonProperty("server") SERVER
}