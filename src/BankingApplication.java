import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import db.JdbcConnect;

public class BankingApplication {
    public static void main(String[] args){
        final String ERROR = "오류가 발생했습니다. 문제가 반복되면 관리자에게 문의해주세요.";
        Scanner scanner = new Scanner(System.in);
        int menu = -1;
        int userId = -1;

        System.out.println("시스템 접속중..");
        System.out.println("시스템에 접속했습니다.");
        System.out.println("-----------------------------------------------");
        System.out.println("NJ는 I 사에서만 사용 가능합니다.");
        System.out.println("충전 시 급여에서 차감되며 환급은 되지 않습니다.");
        System.out.println("문의사항은 000-0000-0000으로 문의바랍니다.");
        System.out.println("-----------------------------------------------");

        while (menu != 0){
            menu = 0;
            System.out.println("1. 회원가입 2. 로그인 0. 종료");
            menu = scanner.nextInt();
            if(menu == 1){

                registerPage();

            } else if(menu == 2){

                userId = loginPage();
                if(userId != -1)
                    afterLoginPage(userId);
                menu = 0;

            } else if(menu == 0) {
                System.out.println("시스템 종료중..");
                break;
            } else {
                System.out.println("다시 입력해주세요.");
            }
        }
        System.out.println("프로그램이 종료됩니다.");
        System.out.println("-----------------------------------------------");

    }
    public static void registerPage() {
        Scanner scanner = new Scanner(System.in);
        String name, employeeId, password, encodedPassword;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("----------------------회원가입---------------------");
        System.out.println("이름을 입력해주세요.");
        name = scanner.nextLine();
        System.out.println("사번을 입력해주세요.");
        employeeId = scanner.nextLine();
        System.out.println("비밀번호를 입력해주세요.");
        password = scanner.nextLine();
        md.update(password.getBytes());
        encodedPassword = String.format("%064x", new BigInteger(1, md.digest()));

        // DB에 저장
        JdbcConnect jdbcConnect = new JdbcConnect();
        boolean status = jdbcConnect.insertMember(name, employeeId, encodedPassword);

        if(status) {
            System.out.println("등록되었습니다.");
        }
    }

    public static int loginPage() {
        Scanner scanner = new Scanner(System.in);
        int userId = -1;
        String employeeId, password, encodedPassword;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println("----------------------로그인---------------------");
        System.out.println("사번을 입력해주세요.");
        employeeId = scanner.nextLine();
        System.out.println("비밀번호를 입력해주세요.");
        password = scanner.nextLine();
        md.update(password.getBytes());
        encodedPassword = String.format("%064x", new BigInteger(1, md.digest()));

        // DB와 확인
        JdbcConnect jdbcConnect = new JdbcConnect();
        userId = jdbcConnect.selectMemberByEmployeeId(employeeId, encodedPassword);
        // 사번, 비밀번호가 일치하고 승인 상태면 로그인 / 아니면 종료

        if(userId == -1){
            System.out.println("로그인에 실패했습니다. 사번과 비밀번호를 확인해주세요.");
        }
        return userId;
    }

    public static void afterLoginPage(int userId){
        Scanner scanner = new Scanner(System.in);
        int menu = -1;
        while(menu != 0){
            System.out.println("1. NJ 조회 2. NJ 충전 3. NJ 선물 0. 종료");
            menu = scanner.nextInt();

            if(menu == 1){
                JdbcConnect jdbcConnect = new JdbcConnect();
                System.out.println(jdbcConnect.selectBalanceByUserId(userId));

            } else if(menu == 2){
                System.out.println("충전할 NJ를 입력해주세요.");
                int charging;
                charging = scanner.nextInt();
                JdbcConnect jdbcConnect = new JdbcConnect();
                jdbcConnect.updateBalanceByUserId(userId, charging);
                System.out.println("충전이 완료되었습니다.");

            } else if(menu == 3){

            } else if(menu == 4){

            } else {

            }
        }
    }




}


