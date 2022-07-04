package db;

import java.io.FileReader;
import java.io.Reader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

public class JdbcConnect {
    private String server;
    private String userName;
    private String password;

    public JdbcConnect() {

        try {
            FileReader dbConfig= new FileReader("src/config/db.properties");
            Properties properties = new Properties();

            properties.load(dbConfig);
            server = properties.getProperty("server");
            userName = properties.getProperty("user_name");
            password = properties.getProperty("password");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
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
        String sql = " INSERT INTO Member(NAME, EMPLOYEE_ID, PASSWORD, CREATE_DATE) "
                + " VALUES(?, ?, ?, ?) ";

        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;

        int count = 0;

        try {
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, employeeId);
            preparedStatement.setString(3, password);
            preparedStatement.setTimestamp(4, timestamp);

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

    public int selectMemberByEmployeeId(String employeeId, String password){
        String sql = " SELECT * FROM member WHERE EMPLOYEE_ID = " + employeeId + " ";

        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet selectResult = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            selectResult = preparedStatement.executeQuery();
            if(selectResult.next())
                if(selectResult.getString("PASSWORD").equalsIgnoreCase(password)){
                    return selectResult.getInt("ID");
                }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        // DB close
        try {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    public int selectBalanceByUserId(int userId){
        String sql = " SELECT * FROM MEMBER WHERE id = " + userId + " ";

        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet selectResult = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            selectResult = preparedStatement.executeQuery();
            if(selectResult.next()){
                return selectResult.getInt("BALANCE");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // DB close
        try {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
        }
        return -1;
    }

    public boolean updateBalanceByUserId(int userId, int charging){
        int balance = 0;
        int count = 0;

        String selectSql = " SELECT * FROM MEMBER WHERE id = " + userId + " ";


        PreparedStatement preparedStatement = null;
        Connection connection = getConnection();
        ResultSet selectResult = null;

        try {
            preparedStatement = connection.prepareStatement(selectSql);
            selectResult = preparedStatement.executeQuery();
            if(selectResult.next()){
                balance = selectResult.getInt("Balance");
                balance += charging;
            }
            String updateSql = " UPDATE Member SET BALANCE = " + balance + " WHERE id = " + userId  + " ";
            preparedStatement = connection.prepareStatement(updateSql);
            count = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // DB close
        try {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
        }

        return count > 0 ? true : false;
    }


}
