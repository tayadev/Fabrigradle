package one.taya.fabrigradle;

import java.io.File;
import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import com.fasterxml.jackson.databind.ObjectMapper;

import one.taya.fabrigradle.FabricModJson.FabricModJson;

public class GenerateFabricModJson extends DefaultTask {

    @OutputFile
    public File getOutputFile() {
        return getProject().file("build/resources/main/fabric.mod.json");
    }

    @TaskAction
    public void generateFabricModJson() throws IOException {
        
        File outFile = getOutputFile();
        outFile.getParentFile().mkdirs();
        FabrigradleExtension ext = getProject().getExtensions().getByType(FabrigradleExtension.class);

        FabricModJson fmj = FabricModJson.builder()
            .schemaVersion(1)
            .id(ext.id)
            .version(ext.version)
            .entrypoints(ext.entrypoints.parse())
            .name(ext.name)
            .build();

        new ObjectMapper().writeValue(outFile, fmj);

        System.out.println("Generated fabric.mod.json");
        System.out.println("=> " + getOutputFile().getAbsolutePath());
    }

}
