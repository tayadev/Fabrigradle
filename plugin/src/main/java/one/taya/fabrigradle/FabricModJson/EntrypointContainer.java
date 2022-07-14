package one.taya.fabrigradle.FabricModJson;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/** The entrypoints used by this mod */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntrypointContainer {
    /** The entrypoint for all environments (classes must implement ModInitializer) */
    public List<Entrypoint> main;

    /** The entrypoint for the client environment (classes must implement ClientModInitializer) */
    public List<Entrypoint> client;
    
    /** The entrypoint for the server environment (classes must implement DedicatedServerModInitializer) */
    public List<Entrypoint> server;
}