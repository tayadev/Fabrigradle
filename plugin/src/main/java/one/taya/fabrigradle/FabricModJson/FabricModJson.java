package one.taya.fabrigradle.FabricModJson;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Setter;
import lombok.experimental.Accessors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Setter
public class FabricModJson {
    
    /** The version of the fabric.mod.json schema (Required) */
    @JsonProperty("schemaVersion")
    Integer schemaVersion;

    /** The mod identifier (Required) */
    @JsonProperty("id")
    String id;

    /** The mod version (Required) */
    @JsonProperty("version")
    String version;

    /** The environment where this mod will be loaded */
    @JsonProperty("environment")
    Environment environment;

    /** The entrypoints used by this mod */
    @JsonProperty("entrypoints")
    EntrypointContainer entrypoints;

    /** Contains an array of nestedJar objects */
    @JsonProperty("jars")
    List<NestedJarEntry> jars;

    /** A string→string dictionary, connecting namespaces to LanguageAdapter implementations */
    @JsonProperty("languageAdapters")
    Map<String, String> languageAdapters;

    /** Contains a list of mixin configuration files for the Mixin library as filenames relative to the mod root */
    @JsonProperty("mixins")
    List<Mixin> mixins;

    /** Path to an access widener definition file */
    @JsonProperty("accessWidener")
    String accessWidener;

    /** id→versionRange map for dependencies. Failure to meet these causes a hard failure */
    @JsonProperty("depends")
    Map<String, VersionRange> depends;

    /** id→versionRange map for dependencies. Failure to meet these causes a soft failure (warning) */
    @JsonProperty("recommends")
    Map<String, VersionRange> recommends;

    /** id→versionRange map for dependencies. Are not matched and are mainly used as metadata */
    @JsonProperty("suggests")
    Map<String, VersionRange> suggests;

    /** id→versionRange map for dependencies. A successful match causes a soft failure (warning) */
    @JsonProperty("conflicts")
    Map<String, VersionRange> conflicts;

    /** id→versionRange map for dependencies. A successful match causes a hard failure */
    @JsonProperty("breaks")
    Map<String, VersionRange> breaks;

    /** Name of the mod */
    @JsonProperty("name")
    String name;

    /** Description of the mod */
    @JsonProperty("description")
    String description;

    /** Direct authorship information */
    @JsonProperty("authors")
    List<Person> authors;

    /** Conributor information */
    @JsonProperty("contributors")
    List<Person> contributors;

    /** Contact information for the project */
    @JsonProperty("contact")
    ContactInformation contact;

    /** The license the mod uses */
    @JsonProperty("license")
    License license;

    /** The mod's icon */
    @JsonProperty("icon")
    Icon icon;

    /** A map of namespace:id→value for custom data fields */
    @JsonProperty("custom")
    Map<String, Object> additionalProperties;

}
