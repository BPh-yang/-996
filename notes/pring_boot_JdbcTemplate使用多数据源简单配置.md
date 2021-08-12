# pring boot JdbcTemplate使用多数据源简单配置

resources 下创建 jdbc.properties配置文件，配置如下

```
#Orcale 配置
driverClassName=oracle.jdbc.OracleDriver
url=jdbc:oracle:thin:@127.0.0.1:1521:ORCL
username=system
password=root
#其中orcale连接注意
#　url=jdbc:oracle:thin:@127.0.0.1:1521:ORCL
　 “:orcl“ ，这里用“:”表示这里指定的是数据库的SID，"/orcl"表示指定的是服务名

#MySql 配置
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://10.137.186.40:3306/platform?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false
username=root
password=root

```

JdbcTemplate工具类

```
public class JdbcTemplateController {

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    public DriverManagerDataSource getDataSource() throws IOException {
        Properties properties = new Properties();
    // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbczhgl.properties");
    // 使用properties对象加载输入流
        properties.load(in);

        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName((String)properties.get("driverClassName"));
        dataSource.setUrl((String)properties.get("url"));
        dataSource.setUsername((String)properties.get("username"));
        dataSource.setPassword((String)properties.get("password"));
        return dataSource;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
```

使用

```
JdbcTemplateController jdbcTemplateController = new JdbcTemplateController();
JdbcTemplate jdbcTemplate = new JdbcTemplate(jdbcTemplateController.getDataSource());
List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM FUNDING_PLAN_VIEW ");
```
