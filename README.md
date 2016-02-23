# simple-libs
A collection of daily useful utility classes for different purpose.

## Maven dependency

Step 1 : Add repository 
```xml
    <repositories>
        <repository>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <id>central</id>
            <name>libs-release</name>
            <url>http://repo.drose.com.np/artifactory/libs-release</url>
        </repository>
    </repositories>
```
Step 2 : Add core dependency 
```xml
    <dependency>
      <groupId>tech.danfe</groupId>
      <artifactId>simple-libs-core</artifactId>
      <version>0.1.0</version>
    </dependency>
```
Step 3 ( optional ) : Add jdbc module dependency
```xml
    <dependency>
        <groupId>tech.danfe</groupId>
        <artifactId>simple-libs-jdbc</artifactId>
        <version>0.1.0</version>
    </dependency>
```
Step 4 ( optional ) : Add cdi supported jdbc module
```xml
    <dependency>
        <groupId>tech.danfe</groupId>
        <artifactId>simple-libs-jdbc-cdi</artifactId>
        <version>0.1.0</version>
</dependency>
```


## API

## Examples 
```java
// simple-libs-jdbc 
 SimpleDataSource dataSource = new SimpleDataSource(JDBC_DRIVER, DB_URL, USER, PASS);
 JdbcTemplate jdbcHelper = new JdbcTemplate(dataSource);
 Song song = new Song("12478", "test Name", 10, "Named param");
 String sql = "Insert into songs (song_key,filename,title,price) values (:songKey,:fileName,:title,:price)";
 jdbcHelper.execute(sql, ObjectUtils.toMap(song));

 //Query for List
 List<Song> songs = jdbcHelper.queryForList("select * from songs", new SongMapper());

 // Mapper
 public class SongMapper implements RowMapper<Song> {
    @Override
    public Song mapRow(ResultSet resultSet) {
        return new Song(ResultSetUtils.getString(resultSet, "song_key", null), ResultSetUtils.getString(resultSet, "filename", null), ResultSetUtils.getDouble(resultSet, "price", 0), ResultSetUtils.getString(resultSet, "title", null));
    }
}
```

## Using JDBC CDI Module
1. Just need to implement DataSourceConfig 
```java
    public class SimpleDataSource implement DataSourceConfig
    {
        @Resource(mapped="my-jndi-datasource")
        private DataSource datasource;
        @Override
        public DataSource getDataSource(){
            return datasource;
        }
    }
```





