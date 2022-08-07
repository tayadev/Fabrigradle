package one.taya.fabrigradle.FabricModJson;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@JsonSerialize(using = PersonSerializer.class)
public class Person {
    String name;
    Map<String, String> contact;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, Map<String, String> contact) {
        this.name = name;
        this.contact = contact;
    }
}

class PersonSerializer extends StdSerializer<Person> {
    public PersonSerializer() { this(Person.class);}
    protected PersonSerializer(Class<Person> t) { super(t); }

    @Override
    public void serialize(Person person, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        if(person.contact == null) {
            jGen.writeObject(person.name);
        } else {
            jGen.writeStartObject();
            jGen.writeObjectField("name", person.name);
            jGen.writeObjectField("contact", person.contact);
            jGen.writeEndObject();
        }
    }
}