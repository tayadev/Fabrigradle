package one.taya.fabrigradle.FabricModJson;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonSerialize(using = IconSerializer.class)
public class Icon {
    String icon;
    Map<Integer, String> icons;

    public Icon(String icon) {
        this.icon = icon;
    }

    public Icon(Map<Integer, String> icons) {
        this.icons = icons;
    }
}

class IconSerializer extends StdSerializer<Icon> {
    public IconSerializer() { this(Icon.class);}
    protected IconSerializer(Class<Icon> t) { super(t); }

    @Override
    public void serialize(Icon icon, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        jGen.writeObject(icon.icons == null ? icon.icon : icon.icons);
    }
}