package one.taya.fabrigradle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;

public class FabrigradleTestBuild {

    public File projectDir;

    public FabrigradleTestBuild() throws IOException {
        this.projectDir = new File("build/functionalTest");
        if(this.projectDir.exists()) deleteDir(this.projectDir.toPath());
        this.projectDir.mkdirs();
        writeString(new File(this.projectDir, "settings.gradle"), "");

        addConfig("""
            plugins {
                id 'one.taya.fabrigradle'
            }
        """);
    }

    public void addConfig(String config) throws IOException {
        writeString(new File(this.projectDir, "build.gradle"), config);
    }

    public BuildResult runGenerateFabricModJsonTask() {
        return GradleRunner.create()
                .forwardOutput()
                .withPluginClasspath()
                .withArguments("generateFabricModJson")
                .withProjectDir(this.projectDir)
                .build();
    }

    private static void writeString(File file, String string) throws IOException {
        try (Writer writer = new FileWriter(file, true)) {
            writer.write(string);
        }
    }

    private static void deleteDir(Path path) throws IOException {
        Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
    }
}
