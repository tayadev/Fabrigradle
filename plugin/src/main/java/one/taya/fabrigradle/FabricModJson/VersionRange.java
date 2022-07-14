package one.taya.fabrigradle.FabricModJson;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonSerialize(using = VersionRangeSerializer.class)
public class VersionRange {
    List<String> versionRanges;

    public VersionRange(String versionRange) {
        this.versionRanges = List.of(versionRange);
    }

    public VersionRange(String ...versionRange) {
        this.versionRanges = List.of(versionRange);
    }


    public VersionRange(List<String> versionRanges) {
        this.versionRanges = versionRanges;
    }
}

class VersionRangeSerializer extends StdSerializer<VersionRange> {
    public VersionRangeSerializer() { this(null);}
    protected VersionRangeSerializer(Class<VersionRange> t) { super(t); }

    @Override
    public void serialize(VersionRange versionRange, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        if(versionRange.versionRanges.size() == 1) jGen.writeObject(versionRange.versionRanges.get(0));
        else jGen.writeObject(versionRange.versionRanges);
    }
}