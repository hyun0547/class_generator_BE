package study.generator.project.model;

public class DBConnectionDTO {
//    private String dbType;
    private String host;
    private int port;
    private String databaseName;
    private String username;
    private String password;

//    public String getDbType() {
//        return dbType;
//    }
//    public void setDbType(String dbType) {
//        this.dbType = dbType;
//    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getDatabaseName() {
        return databaseName;
    }
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
