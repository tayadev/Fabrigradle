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
        if(this.projectDir.exists()) deleteProjectDir(this.projectDir.toPath());
        this.projectDir.mkdirs();
        writeString(new File(this.projectDir, "settings.gradle"), "");
        writeString(new File(this.projectDir, "gradle.properties"), "org.gradle.jvmargs=-Xmx1G");

        addConfig("""
            plugins {
                id 'one.taya.fabrigradle'
            }

            fabrigradle {
                versions {
                    minecraft '1.19.2'
                    mappings '1.19.2+build.1'
                    loader '0.14.9'
                    fabricApi '0.58.6+1.19.2'
                }
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

    private static void deleteProjectDir(Path path) throws IOException {
        Files.walk(path)
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .filter(f -> f.getName() != ".gradle")
            .forEach(File::delete);
    }
}
