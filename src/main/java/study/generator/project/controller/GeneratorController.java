package study.generator.project.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.generator.project.model.GeneratorDTO;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

@RestController
public class GeneratorController {

    @PostMapping("/")
    public String Generator(@ModelAttribute GeneratorDTO model) {
        StringBuilder classBuilder = new StringBuilder();
        classBuilder.append(String.format("public class %s {\n\n", model.getModelName()));

        for (Map.Entry<String, String> entry : model.getFields().entrySet()) {
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

        try (FileWriter writer = new FileWriter("/Users/hyun/Workspace/class_generator/generated_file/"+model.getModelName()+".java")) {
            writer.write(classBuilder.toString());
        } catch (IOException e) {
            System.out.println("error: " + e.getMessage());
        }

        return "";
    }
}