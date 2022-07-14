package one.taya.fabrigradle.FabricModJson;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonSerialize(using = LicenseSerializer.class)
public class License {
    List<String> licenses;

    public License(String license) {
        this.licenses = List.of(license);
    }

    public License(String ...license) {
        this.licenses = List.of(license);
    }

    public License(List<String> licenses) {
        this.licenses = licenses;
    }
}

class LicenseSerializer extends StdSerializer<License> {
    public LicenseSerializer() { this(null);}
    protected LicenseSerializer(Class<License> t) { super(t); }

    @Override
    public void serialize(License license, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        if(license.licenses.size() == 1) jGen.writeObject(license.licenses.get(0));
        else jGen.writeObject(license.licenses);
    }
}