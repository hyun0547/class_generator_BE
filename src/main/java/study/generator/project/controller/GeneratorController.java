package study.generator.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.generator.project.model.GeneratorDTO;
import study.generator.project.service.GeneratorService;

import java.util.Map;

@RestController
public class GeneratorController {

    @Autowired
    private GeneratorService generatorService;

    @PostMapping("/")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity<byte[]> Generator(@ModelAttribute GeneratorDTO model) {
        StringBuilder classBuilder = new StringBuilder();
        classBuilder.append(String.format("public class %s {\n\n", model.getModelName()));

        for (Map.Entry<String, String> entry : model.getFields().entrySet()) {
            String fieldType = entry.getValue();
            String fieldName = entry.getKey();

            classBuilder.append("    private ").append(fieldType).append(" ").append(fieldName).append(";\n");
        }

        for (Map.Entry<String, String> entry : model.getFields().entrySet()) {
            String fieldType = entry.getValue();
            String fieldName = entry.getKey();

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

//        try (FileWriter writer = new FileWriter("/Users/hyun/Workspace/class_generator/generated_file/"+model.getModelName()+".java")) {
//            writer.write(classBuilder.toString());
//        } catch (IOException e) {
//            System.out.println("error: " + e.getMessage());
//        }

        byte[] classBytes = classBuilder.toString().getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + model.getModelName() + ".java");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

        return new ResponseEntity<>(classBytes, headers, HttpStatus.OK);
    }
}