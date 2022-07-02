package db;

import java.io.FileReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        PreparedStatement preparedStatement = null;
        String server = null;
        String dbUserName = null;
        String dbPassword = null;

        String name = null;
        String employeeId = null;
        String userPassword = null;

        try {
            FileReader dbConfig= new FileReader("src/config/db.properties");
            Properties properties = new Properties();

            properties.load(dbConfig);
            server = properties.getProperty("server");
            dbUserName = properties.getProperty("user_name");
            dbPassword = properties.getProperty("password");

            System.out.println(server);
            System.out.println(dbUserName);
            System.out.println(dbPassword);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Connection connection = null;
        // 접속
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + server + "/banking" + "?useSSL=false", dbUserName, dbPassword);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        // insertData

//        name = "테스트";
//        employeeId = "2022";
//        userPassword = "1234";
//
//        String insertSql = " INSERT INTO Member(NAME, EMPLOYEE_ID, PASSWORD, CREATE_DATE) "
//                + " VALUES(?, ?, ?, ?) ";
//
//
//        int count = 0;
//
//        try {
//            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
//            preparedStatement = connection.prepareStatement(insertSql);
//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, employeeId);
//            preparedStatement.setString(3, userPassword);
//            preparedStatement.setTimestamp(4, ts);
//
//            count = preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        // selectData
        employeeId = "2022";
        String selectSql = " SELECT * FROM member WHERE EMPLOYEE_ID = " + employeeId + " ";

        preparedStatement = null;
        ResultSet selectResult = null;

        try {
            preparedStatement = connection.prepareStatement(selectSql);
            selectResult = preparedStatement.executeQuery();
            if(selectResult.next()){
                System.out.println(selectResult.getInt("ID"));
                System.out.println(selectResult.getString("NAME"));
                System.out.println(selectResult.getString("PASSWORD"));
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
    }
}
