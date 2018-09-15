package spittr.jdbc;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import spittr.db.WorldRepository;
import spittr.db.jdbc.JdbcWorldRepository;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

@Configuration
public class JdbcMysqlConfig {
    @Bean
    @Profile("jdbc")
    public DataSource jdbcDataSource() throws IOException {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL("jdbc:mysql://localhost:3306/world");
        mysqlDataSource.setDatabaseName("world");
        mysqlDataSource.setPort(3306);
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("1234");
        return mysqlDataSource;
    }

    @Bean
    @Profile("dev")
    public DataSource dataSource() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties prop = new Properties();
        prop.load(is);
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(prop.getProperty("url"));
        dataSource.setDriverClassName(prop.getProperty("driverClassName"));
        dataSource.setUsername(prop.getProperty("username"));
        dataSource.setPassword(prop.getProperty("password"));
        dataSource.setInitialSize(Integer.parseInt(prop.getProperty("initialSize").trim()));
        dataSource.setMaxActive(Integer.parseInt(prop.getProperty("initialSize").trim()));
        return dataSource;
    }

    @Bean
    @Profile("prod")
    public DataSource dbcpDataSource() {
        DataSource dataSource = null;
        // 加载prop配置文件
        try {
            Properties prop = PropertiesLoaderUtils.loadAllProperties("application.properties");
            dataSource = BasicDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public WorldRepository worldRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcWorldRepository(jdbcTemplate);
    }
}
