package one.taya.fabrigradle.FabricModJson;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Setter;
import lombok.experimental.Accessors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Setter
public class ContactInformation {

    /** Contact e-mail pertaining to the mod */
    @JsonProperty("email")
    String email;

    /** IRC channel pertaining to the mod. Must be of a valid URL format */
    @JsonProperty("irc")
    String irc;

    /** Project or user homepage. Must be a valid HTTP/HTTPS address */
    @JsonProperty("homepage")
    String homepage;

    /** Project issue tracker. Must be a valid HTTP/HTTPS address */
    @JsonProperty("issues")
    String issues;

    /** Project source code repository. Must be a valid URL */
    @JsonProperty("sources")
    String sources;

    @JsonIgnore
    private Map<String, String> additionalProperties;

    @JsonAnyGetter
    public Map<String, String> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, String value) {
        this.additionalProperties.put(name, value);
    }
}
