package DB;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class JdbcConnect {
    private String server;
    private String userName;
    private String password;

    public JdbcConnect() {
        Map<String, Object> propMap = null;
        try {
            propMap = new Yaml().load(new FileReader("src/db.yml"));
        } catch (FileNotFoundException e) {
            System.out.println("오류가 발생했습니다. 문제가 반복되면 관리자에게 문의해주세요.");
        }
        server = (String) propMap.get("server");
        userName = (String) propMap.get("user_name");
        password = (String) propMap.get("password");
//        setServer((String) propMap.get("server"));
//        setUserName((String) propMap.get("user_name"));
//        setPassword((String) propMap.get("password"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("오류가 발생했습니다. 문제가 반복되면 관리자에게 문의해주세요.");
        }
    }

    public Connection getConnection() { // 커넥션 해주는 역할, DB객체
        Connection connection = null;
        // 접속
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + server + "/banking" + "?useSSL=false", userName, password);
        } catch(SQLException e) {
            System.out.println("오류가 발생했습니다. 문제가 반복되면 관리자에게 문의해주세요.");
        }

        return connection;
    }

    public boolean insertMember(String name, String employeeId, String password) {
        String sql = " INSERT INTO Member(NAME, EMPLOYEE_ID, PASSWORD) "
                + " VALUES(?, ?, ?) ";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        int count = 0;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, employeeId);
            preparedStatement.setString(3, password);
            count = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("오류가 발생했습니다. 문제가 반복되면 관리자에게 문의해주세요.");
        } finally {
            // DB close 필수!
            // 접속이 된 것
            try {
                if(connection != null) {
                    connection.close();
                }
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                System.out.println("오류가 발생했습니다. 문제가 반복되면 관리자에게 문의해주세요.");
            }
        }
        return count > 0 ? true : false;
    }
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
