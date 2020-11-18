package homework.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SimpleJdbc {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    private static final String DRIVER_CLASS = "org.h2.Driver";

    public static void main(String[] args) throws Exception {
        Class.forName(DRIVER_CLASS);
        Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE IF EXISTS STUDENT;" + "\n" +
                "CREATE TABLE STUDENT(id INT PRIMARY KEY,name VARCHAR(100))");
        // 增
        statement.executeUpdate("INSERT INTO STUDENT VALUES(1, '迪丽热巴')");
        statement.executeUpdate("INSERT INTO STUDENT VALUES(2, '古力娜扎')");
        statement.executeUpdate("INSERT INTO STUDENT VALUES(3, '马尔扎哈')");
        // 删
        statement.executeUpdate("DELETE FROM STUDENT WHERE name='张三'");
        // 改
        statement.executeUpdate("UPDATE STUDENT SET name='叽里呱啦' WHERE name='马尔扎哈'");
        // 查数据集
        ResultSet rs = statement.executeQuery("SELECT * FROM STUDENT");
        while (rs.next()) {
            System.out.println(rs.getString("id") + "," + rs.getString("name"));
        }
        //释放资源
        statement.close();
        //关闭连接
        conn.close();
    }
}
