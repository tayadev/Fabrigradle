package one.taya.fabrigradle;

import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import one.taya.fabrigradle.MixinJson.MixinJson;

public abstract class GenerateMixinJsonTask extends DefaultTask {

    @OutputFile
    public abstract RegularFileProperty getOutputFile();

    @Nested
    abstract Property<FabrigradleExtension> getConfig();

    @TaskAction
    public void generateMixinJson() throws JsonGenerationException, JsonMappingException, IOException {

        FabrigradleExtension ext = getConfig().get();
        Mixins m = ext.getMixins();

        MixinJson mixinJson = new MixinJson()
            .setRequired(m.getRequired().getOrNull())
            .setPackageName(m.getPackageName().getOrNull())
            .setMixins(m.getMixins().getOrNull())
            .setClient(m.getClient().getOrNull())
            .setServer(m.getServer().getOrNull())
            .setCompatibilityLevel(m.getCompatibilityLevel().getOrNull())
            .setPlugin(m.getPlugin().getOrNull())
            .setRefmap(m.getRefmap().getOrNull())
            .setSetSourceFile(m.getSetSourceFile().getOrNull())
            .setVerbose(m.getVerbose().getOrNull())
            .setPriority(m.getPriority().getOrNull())
            .setMinVersion(m.getMinVersion().getOrNull());

        new ObjectMapper().writeValue(getOutputFile().get().getAsFile(), mixinJson);
    }

}
