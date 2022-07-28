package one.taya.fabrigradle.FabricModJson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonSerialize(using = MixinSerializer.class)
public class Mixin {
    String config;
    Environment environment;

    public Mixin(String config) {
        this.config = config;
    }

    public Mixin(String config, Environment environment) {
        this.config = config;
        this.environment = environment;
    }
}

class MixinSerializer extends StdSerializer<Mixin> {
    public MixinSerializer() { this(Mixin.class);}
    protected MixinSerializer(Class<Mixin> t) { super(t); }

    @Override
    public void serialize(Mixin mixin, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        if(mixin.environment == null) {
            jGen.writeObject(mixin.config);
        } else {
            jGen.writeStartObject();
            jGen.writeObjectField("config", mixin.config);
            jGen.writeObjectField("environment", mixin.environment);
            jGen.writeEndObject();
        }
    }
}
