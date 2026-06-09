package org.projects;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * EnvParser reads the .env file and returns a map of environment variables in the form of a key-value pair.
 * The class itself won't do it much, but it will be useful for other classes to use.
 * @author Mason Doti
 */
public class EnvParser {
    public EnvParser() {
    }
    public Map<String, String> getEnvVars() throws IOException {
        Path envPath = Paths.get(".env").toAbsolutePath();
        Map<String, String> envVars = Files.readAllLines(envPath)
                .stream()
                .filter(line -> !line.startsWith("#") && line.contains("="))
                .map(line -> line.split("=", 2))
                .collect(Collectors.toMap(arr -> arr[0].trim(), arr -> arr[1].trim()));
        return envVars;
    }
}