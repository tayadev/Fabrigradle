package one.taya.fabrigradle.FabricModJson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonSerialize(using = EntrypointSerializer.class)
public class Entrypoint {
    String value;
    /** Optional key denoting the language adapter to use */
    String adapter;

    public Entrypoint(String value) {
        this.value = value;
    }

    public Entrypoint(String value, String adapter) {
        this.value = value;
        this.adapter = adapter;
    }
}

class EntrypointSerializer extends StdSerializer<Entrypoint> {
    public EntrypointSerializer() { this(Entrypoint.class);}
    protected EntrypointSerializer(Class<Entrypoint> t) { super(t); }

    @Override
    public void serialize(Entrypoint entrypoint, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        if(entrypoint.adapter == null) {
            jGen.writeObject(entrypoint.value);
        } else {
            jGen.writeStartObject();
            jGen.writeObjectField("value", entrypoint.value);
            jGen.writeObjectField("adapter", entrypoint.adapter);
            jGen.writeEndObject();
        }
    }
}