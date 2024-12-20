package study.generator.project.mapper;

import org.apache.ibatis.annotations.Param;

public interface GeneratorMapper {
    void createTable(@Param("tableName") String tableName, @Param("fields") String fields);
}
