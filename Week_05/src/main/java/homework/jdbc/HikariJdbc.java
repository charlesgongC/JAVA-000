package homework.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class HikariJdbc {
        private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbc";
        private static final String USER = "root";
        private static final String PASSWORD = "123";
        private static final String DRIVER_CLASS = "org.h2.Driver";
        private static final String POOL_SIZE = "10";

        public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("jdbcUrl", JDBC_URL);
        properties.setProperty("driverClassName", DRIVER_CLASS);
        properties.setProperty("dataSource.user", USER);
        properties.setProperty("dataSource.password", PASSWORD);
        properties.setProperty("dataSource.maximumPoolSize", POOL_SIZE);
        HikariConfig hikariConfig = new HikariConfig(properties);
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        Connection conn = hikariDataSource.getConnection();
        String sql = "DROP TABLE IF EXISTS STUDENT;" + "\n" +
                "CREATE TABLE STUDENT(id INT PRIMARY KEY,name VARCHAR(100))";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.execute();

        sql = "INSERT INTO STUDENT VALUES(?, ?)";
        preparedStatement = conn.prepareStatement(sql);
        String[] names = {"张三", "李四", "王五"};
        // 增  批处理方式
        for (int i = 0; i < names.length; i++) {
            preparedStatement.setInt(1, i + 1);
            preparedStatement.setString(2, names[i]);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
        // 删
        sql = "DELETE FROM STUDENT WHERE name=?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, "张三");
        preparedStatement.execute();
        // 改  增加事务
        try {
            conn.setAutoCommit(false);
            sql = "UPDATE STUDENT SET name=? WHERE name=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "李四2");
            preparedStatement.setString(2, "李四");
            preparedStatement.execute();
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
        }
        // 查
        sql = "SELECT * FROM STUDENT WHERE id=?";
        preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, 3);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id") + "," + resultSet.getString("name"));
        }
        // 查数据集
        sql = "SELECT * FROM STUDENT";
        preparedStatement = conn.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name"));
        }
        //释放资源
        preparedStatement.close();
        //关闭连接
        hikariDataSource.close();
    }
}
