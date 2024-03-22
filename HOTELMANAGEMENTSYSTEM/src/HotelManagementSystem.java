//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.sql.*;
import java.sql.Timestamp;
import java.util.Scanner;
import java.lang.String;


public class HotelManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/reservation_details";
    private static final String user_name = "root";
    private static final String password = "Viku2407";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(url, user_name, password);
            while (true) {
                System.out.println();
                System.out.println("WELCOME TO HOTEL MANAGEMENT SERVICE");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. RESERVE A ROOM");
                System.out.println("2. VIEW RESERVATION ");
                System.out.println("3. GET ROOM NO. ");
                System.out.println("4. UPDATE RESERVATION ");
                System.out.println("5. DELETE RESERVATION");
                System.out.println("0. EXIT");
                System.out.println("CHOOSE YOUR OPTION");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        reserveRoom(connection, scanner);
                        break;
                    case 2:
                        viewReservation(connection);
                        break;
                    case 3:
                        getRoomNo(connection, scanner);
                        break;
                    case 4:
                        updateReservation(connection, scanner);
                        break;
                    case 5:
                        deleteReservation(connection, scanner);
                        break;
                    case 0:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid Choice option");
                }
            }
        } catch (SQLException s) {
            System.out.println(s.getMessage());
        } catch (InterruptedException u) {
            throw new RuntimeException(u);
        }
    }

    private static void reserveRoom(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter Customer Name");
            String name = scanner.next();
            scanner.nextLine();
            System.out.println("Enter Customer ID ");
            String cus_Id = scanner.next();
            scanner.nextLine();
            System.out.println("Enter Customer Room_No.");
            int room_no = scanner.nextInt();
            System.out.println("Enter Customer contact_no.");
            String con_no = scanner.next();

            String sql = "insert into reservation_details ( CUSTOMER_NAME, CUSTOMER_ID, ALLOCATED_ROOM, CUSTOMER_CONT )" + "values ('" +
                    name + "'," + cus_Id + "," + room_no + "," + con_no + ")";

            try (Statement statement = connection.createStatement()) {
                int affected_row = statement.executeUpdate(sql);

                if (affected_row > 0) {
                    System.out.println("Reservation Registration Successfully");
                } else {
                    System.out.println("Reservation Registration Failed");
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    private static void viewReservation(Connection connection) throws SQLException {
        String sql = "SELECT  RESERVATION_NO, CUSTOMER_NAME, CUSTOMER_ID, ALLOCATED_ROOM, CUSTOMER_CONT, RESERVATION FROM  reservation_details;";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Current Reservation Details :");
            System.out.println("+-------------------+-----------------+--------------------+-------------------+---------------+-------------+");
            System.out.println("| RESERVATION_NO,   |   CUSTOMER_NAME, | CUSTOMER_ID,      | ALLOCATED_ROOM,    | CUSTOMER_CONT,| RESERVATION |");
            System.out.println("+-------------------+-----------------+--------------------+-------------------+---------------+-------------+");

            while (resultSet.next()) {
                int reservation_NO = resultSet.getInt("RESERVATION_NO");
                String Cus_Name = resultSet.getString("CUSTOMER_NAME");
                int Cus_Id = resultSet.getInt("CUSTOMER_ID");
                int room_No = resultSet.getInt("ALLOCATED_ROOM");
                String Cus_cont = resultSet.getString("CUSTOMER_CONT");
                //String date_Time = resultSet.getTimestamp("RESERVATION").toString();

                System.out.printf("|  %-16d | %-18s | %-18d | %-26s | %-18s |  \n", reservation_NO, Cus_Name, Cus_Id, room_No, Cus_cont);

            }
            System.out.println("+-------------------+-----------------+--------------------+-------------------+---------------+-------------+");
        }

    }

    private static void getRoomNo(Connection connection, Scanner scanner) {
        try {
            System.out.println("Enter Reservation ID ");
            int reservation_id = scanner.nextInt();
            System.out.println("Enter guest Name");
            String guest_name = scanner.next();

            String sql = " SELECT ALLOCATED_ROOM FROM RESERVATION_DETAILS" + " WHERE  RESERVATION_NO = " + reservation_id + " AND CUSTOMER_NAME ='"
                    + guest_name + "'";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    int room_no = resultSet.getInt("ALLOCATED_ROOM");
                    System.out.println("Reservation details of reservation_id is " + reservation_id
                            + "\n Guest_name is  " + guest_name +  " \n room_no is " + room_no);
                } else {
                    System.out.println("Now reservation for this details");
                }
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    private static void updateReservation(Connection connection, Scanner scanner){
        System.out.println("Enter resistration Id to upadte : ");
        int res_Id = scanner.nextInt();
        scanner.nextLine();

        if (!resevationExist(connection,res_Id)){
            System.out.println("registration id is not found");
            return;
        }
        System.out.println("Enter New Guest Name");
        String newguest_Name = scanner.nextLine();
        System.out.println("Enter New Customer ID ");
        int newcus_Id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter New Customer Room_No.");
        int newroom_no = scanner.nextInt();
        System.out.println("Enter  New Customer contact_no.");
        String newcon_no = scanner.next();

        String sql = "UPDATE reservation_details SET CUSTOMER_NAME = '" + newguest_Name + "',CUSTOMER_ID = " + newcus_Id +"'ALLOCATED_ROOM = " + newroom_no +
   "',CUSTOMER_CONT =" + newcon_no +"WHERE RESERVATION_NO =" + res_Id;

        try(Statement statement=connection.createStatement()){
            int affected = statement.executeUpdate(sql);

            if(affected >0 ){
                System.out.println("Updation successfully");
            }else {
                System.out.println("updation failed");
            }
        }catch (SQLException s){
            s.printStackTrace();
        }


    }

    private static void deleteReservation(Connection connection, Scanner scanner) {
        System.out.println("Enter reservation ID");
        int reservationID = scanner.nextInt();
        scanner.nextLine();

        if (!resevationExist(connection, reservationID)) {
            System.out.println("Reservation not exist on this ID");
            return;
        }

        String sql = "DELETE FROM reservation_details WHERE RESERVATION_NO = " + reservationID ;

        try (Statement statement = connection.createStatement()) {
            int affectedRow = statement.executeUpdate(sql);

            if (affectedRow>0) {
                System.out.println("Customer details Deleted Successfully");
            } else{
                System.out.println("customer details Deleted failed ");
            }
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    private static boolean resevationExist(Connection connection, int reservationID) {
        try {
            String sql = "SELECT RESERVATION_NO FROM reservation_details WHERE RESERVATION_NO = " + reservationID;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
                return resultSet.next();
            }
        } catch (SQLException s) {
            s.printStackTrace();
            return false;
        }
    }
private static void exit()throws InterruptedException{
    System.out.print("Exiting System");
    int i = 7;
    while (i!=0){
        System.out.print(".");
        Thread.sleep(400);
        i--;
    }
    System.out.println();
    System.out.println("THANK YOU FOR USING HOTEL RESERVATION SYSTEM");
}

}