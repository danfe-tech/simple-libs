# simple-libs
A collection of daily useful utility classes for different purpose.

## Current Version
```xml
     <version>0.2.4</version>
```

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
      <version>Current Version</version>
    </dependency>
```
Step 3 ( optional ) : Add jdbc module dependency
```xml
    <dependency>
        <groupId>tech.danfe</groupId>
        <artifactId>simple-libs-jdbc</artifactId>
        <version>Current Version</version>
    </dependency>
```
Step 4 ( optional ) : Add cdi supported jdbc module
```xml
    <dependency>
        <groupId>tech.danfe</groupId>
        <artifactId>simple-libs-jdbc-cdi</artifactId>
       <version>Current Version</version>
    </dependency>
```


## API

## Examples 
```java
// simple-libs-jdbc 
 SimpleDataSource dataSource = new SimpleDataSource(JDBC_DRIVER, DB_URL, USER, PASS);
 JdbcTemplate jdbcHelper = new JdbcTemplate(dataSource);
 Song song = new Song("12478", "test Name", 10, "Named param");
 String sql = "Insert into songs (song_key,filename,title,price,created,note) values (:songKey,:fileName,:title,:price,:created,:note)";
 List<QueryParameter> parameters = new ArrayList<>();
 parameters.add(new QueryParameter("songKey", song.getSongKey()));
 parameters.add(new QueryParameter("fileName", song.getFileName()));
 parameters.add(new QueryParameter("title", song.getTitle()));
 parameters.add(new QueryParameter("price", song.getPrice()));
 parameters.add(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
 parameters.add(new QueryParameter("note", "test"));
 this.jdbcTemplate.executeUpdate(sql, parameters);
        
 //Query for List
 List<Song> songs = jdbcTemplate.queryForList("select song_key,filename from songs", new SongMapper());

 // Mapper
 public class SongMapper implements RowMapper<Song> {
    @Override
    public Song mapRow(ResultSet resultSet) {
        return new Song(ResultSetUtils.getString(resultSet, "song_key", null), ResultSetUtils.getString(resultSet, "filename", null), ResultSetUtils.getDouble(resultSet, "price", 0), ResultSetUtils.getString(resultSet, "title", null));
    }
}
```

## Using Transaction
```java
    jdbcTemplate.beginTransaction()    // To begin transaction
    jdbcTemplate.commitTransaction()   // To commit transaction
    jdbcTemplate.rollbackTransaction() // To rollback transaction
```
## Using JDBC Batch
```java
     Song song = new Song("12478", "test Name", 10, "Named param");
     String sql = "Insert into songs (song_key,filename,title,price,created,note) values           (:songKey,:fileName,:title,:price,:created,:note)";
     List<BatchParameter> parameters = new ArrayList<>();
     // first batch parameter
     BatchParameter parameter = new BatchParameter();
     parameter.addParameter(new QueryParameter("songKey", "4502"));
     parameter.addParameter(new QueryParameter("fileName", song.getFileName()));
     parameter.addParameter(new QueryParameter("title", song.getTitle()));
     parameter.addParameter(new QueryParameter("price", song.getPrice()));
     parameter.addParameter(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
     parameter.addParameter(new QueryParameter("note", "test"));
     parameters.add(parameter);

      // second batch parameter
     BatchParameter parameterTwo = new BatchParameter();
     parameterTwo.addParameter(new QueryParameter("songKey", song.getSongKey()));
     parameterTwo.addParameter(new QueryParameter("fileName", song.getFileName()));
     parameterTwo.addParameter(new QueryParameter("title", song.getTitle()));
     parameterTwo.addParameter(new QueryParameter("price", song.getPrice()));
     parameterTwo.addParameter(new QueryParameter("created", song.getCreated(), QueryParameter.ParameterType.Date));
     parameterTwo.addParameter(new QueryParameter("note", "test"));
     parameters.add(parameterTwo);

     int[] result = this.jdbcTemplate.executeBatch(sql, parameters);
```

## Using JDBC CDI Module
 1. Implement DataSourceConfig 
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
 2. Inject as below 
```java
    @Inject
    @SimpleJdbcTemplate
    private JdbcTemplateWrapper wrapper;
    
    public void query(){
        this.wrapper.getTemplate().queryForXXX();
    }
```





