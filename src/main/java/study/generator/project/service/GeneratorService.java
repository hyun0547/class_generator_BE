package study.generator.project.service;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import study.generator.project.mapper.GeneratorMapper;
import study.generator.project.model.DBConnectionDTO;
import study.generator.project.model.GeneratorDTO;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Map;

@Service
public class GeneratorService {
    public void createTable(GeneratorDTO model, DBConnectionDTO dbConnection) throws Exception {
        SqlSessionFactory sqlSessionFactory = createSqlSessionFactory(dbConnection);

        String fields = mapFieldsToSQL(model);

        SqlSession session = sqlSessionFactory.openSession();
        GeneratorMapper mapper = session.getMapper(GeneratorMapper.class);
        mapper.createTable(model.getModelName(), fields);
    }

    private SqlSessionFactory createSqlSessionFactory(DBConnectionDTO dbConnection) throws Exception {
        DataSource dataSource = createDataSource(dbConnection);

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactoryBuilder builderInitializer = new SqlSessionFactoryBuilder();
        Configuration configuration = builderInitializer.build(inputStream).getConfiguration();

        configuration.setEnvironment(new org.apache.ibatis.mapping.Environment(
                "development",
                new ManagedTransactionFactory(),
                dataSource
        ));

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return builder.build(configuration);
    }

    private DataSource createDataSource(DBConnectionDTO dbConnection) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        String jdbcUrl = String.format("jdbc:%s://%s:%d/%s",
//                dbConnection.getDbType()
                "mysql",
                dbConnection.getHost(), dbConnection.getPort(), dbConnection.getDatabaseName());
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(dbConnection.getUsername());
        dataSource.setPassword(dbConnection.getPassword());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setDriverClassName(getDriverClassName(dbConnection.getDbType()));

        return dataSource;
    }

//    private String getDriverClassName(String dbType) {
//        switch (dbType.toLowerCase()) {
//            case "mysql":
//                return "com.mysql.cj.jdbc.Driver";
//            case "postgresql":
//                return "org.postgresql.Driver";
//            case "oracle":
//                return "oracle.jdbc.OracleDriver";
//            default:
//                throw new IllegalArgumentException();
//        }
//    }

    private String mapFieldsToSQL(GeneratorDTO model) {
        StringBuilder fieldBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : model.getFields().entrySet()) {
            String columnName = entry.getKey();
            String columnType = mapToSQLType(entry.getValue());
            fieldBuilder.append(columnName).append(" ").append(columnType).append(", ");
        }
        fieldBuilder.setLength(fieldBuilder.length() - 2);
        return fieldBuilder.toString();
    }

    private String mapToSQLType(String fieldType) {
        switch (fieldType.toLowerCase()) {
            case "string":
                return "VARCHAR(255)";
            case "integer":
                return "INT";
            case "boolean":
                return "BOOLEAN";
            case "date":
                return "DATE";
            case "float":
                return "FLOAT";
            default:
                throw new IllegalArgumentException();
        }
    }
}
