package study.generator.project.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@RestController
public class HomeController {

    @PostMapping("/")
    public String hello(String modelName, Map<String, String> fields) {
        StringBuilder classBuilder = new StringBuilder();
        classBuilder.append(String.format("public class %s {\n\n", modelName));

        fields.put("name", "String");

        for (Map.Entry<String, String> entry : fields.entrySet()) {
            String fieldName = entry.getKey();
            String fieldType = entry.getValue();

            classBuilder.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n");

            // Getter
            classBuilder.append("\n    public ").append(fieldType).append(" get")
                    .append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1))
                    .append("() {\n")
                    .append("        return ").append(fieldName).append(";\n    }\n");

            // Setter
            classBuilder.append("\n    public void set")
                    .append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1))
                    .append("(").append(fieldType).append(" ").append(fieldName).append(") {\n")
                    .append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n    }\n");
        }

        classBuilder.append("}\n");

        try (FileWriter writer = new FileWriter("/Users/hyun/Workspace/class_generator/generated_file/"+modelName+".java")) {
            writer.write(classBuilder.toString());
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }

        return "";
    }
}