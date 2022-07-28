package one.taya.fabrigradle.MixinJson;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MixinJson {
    
    /** defines the parent package for this group of mixins (this is important because the package and all subpackages will be excluded from the LaunchClassLoader at run time) */
    @JsonProperty("package")
    String packageName;

    /** defines the list of mixin "classes" within the parent package to apply for this configuration, each mixin "class" is specified relative to the parent package, sub-packages are allowed. Entries in this list are applied to both client and dedicated server side. */
    @JsonProperty("mixins")
    List<String> mixins;

    /** defines the list of mixins to apply only on the client side */
    @JsonProperty("client")
    List<String> client;

    /** defines the list of mixins to apply only on the dedicated server side */
    @JsonProperty("server")
    List<String> server;

    /** defines the name of the reference map filename for this set */
    @JsonProperty("refmap")
    String refmap;

    /** defines the priority of this mixin set relative to other configurations */
    @JsonProperty("priority")
    Integer priority;

    /** the name of an optional companion plugin class for the mixin configuration which can tweak the mixin configuration programmatically at run time */
    @JsonProperty("plugin")
    String plugin;

    /** defines whether the mixin set is required or not. If a single mixin failing to apply should be considered a failure state for the entire game, then the required flag should be set to true */
    @JsonProperty("required")
    Boolean required;

    /** should be set if this mixin set relies on some mixin functionality which was added in a particular version. It can be omitted for version-agnostic mixin sets */
    @JsonProperty("minVersion")
    String minVersion;

    /** causes the mixin processor to overwrite the source file property in target classes with the source file of the mixin class. This can be useful when debugging mixins */
    @JsonProperty("setSourceFile")
    Boolean setSourceFile;

    /** promotes all DEBUG-level log messages to INFO level for this mixin set */
    @JsonProperty("verbose")
    Boolean verbose ;

    // fabric only???
    @JsonProperty("compatibilityLevel")
    String compatibilityLevel;

    // fabric only???
    @JsonProperty("injectors")
    Map<String, Integer> injectors;

}
