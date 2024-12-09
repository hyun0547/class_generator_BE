package study.generator.project.model;

import java.util.Map;

public class GeneratorDTO {
    private String modelName;
    private Map<String, String> fields;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}
