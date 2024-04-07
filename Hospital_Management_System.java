package hospitalmanagementsystem;
import java.sql.*;
import java.util.Scanner;
public class Hospital_Management_System {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";                                                             
    private static final String password = "@#aditya2006";

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try {
                Connection connection = DriverManager.getConnection(url,username,password);
                Patient patient = new Patient(connection,scanner);
                Docters docters = new Docters(connection);
                while(true) {
                    System.out.println();
                    System.out.println();
                    System.out.println("-*-*-*-*-*-*-*-*-*-*  HOSPITAL MANAGEMENT SYSTEM  -*-*-*-*-*-*-*-*-*-*");
                    System.out.println();
                    System.out.println("1.Add Patient.");
                    System.out.println("2.View Patients.");
                    System.out.println("3.View Docters.");
                    System.out.println("4.Book Appoinment.");
                    System.out.println("5.remove patient.");
                    System.out.println("6.exit.");
                    System.out.print("enter your choice : ");
                    int choice = scanner.nextInt();

                    switch(choice) {
                        case 1:patient.addPatient();
                            System.out.println();
                        break;
                        case 2:patient.viewPatient();
                            System.out.println();
                        break;
                        case 3:docters.viewDocters();
                            System.out.println();
                        break;
                        case 4:bookappionment(patient,docters,connection,scanner);
                            System.out.println();
                        break;
                        case 5:
                            patient.removePatient();
                        break;
                        case 6:
                            return;
                        default:
                            System.out.println("Invalid choice");
                    }
                }

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public static void bookappionment(Patient patient , Docters docters,Connection connection,Scanner scanner){
        System.out.print("enter patient id : ");
        int patient_id = scanner.nextInt();
        System.out.print("enter docters id : ");
        int docters_id = scanner.nextInt();
        System.out.println("enter Appionment date (YYYY-MM-DD)");
        String appioment_date = scanner.next();
        if(patient.getPatient(patient_id) && docters.getDocters(docters_id)) {
            if(checkDocterAvailbility(docters_id,appioment_date,connection)) {
                    String AppionmentQuery = "insert into appoiment(patient_id,dosters_id,appoiment_date) values(?,?,?,?)";
                    try {
                        PreparedStatement preparedStatement = connection.prepareStatement(AppionmentQuery);
                        preparedStatement.setInt(1,patient_id);
                        preparedStatement.setInt(2,docters_id);
                        preparedStatement.setString(3,appioment_date);
                        int affectedrow = preparedStatement.executeUpdate();
                        if(affectedrow > 0){
                            System.out.println("Appionment booked");
                        }
                        else {
                            System.out.println("Appionment can't booked");
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }else {
                System.out.println("Docter not available on this date");
            }
            System.out.println("either docter or patient dosen't exist");
        }
    }
    public static boolean checkDocterAvailbility(int docters_id,String appioment_date,Connection connection) {
        String query = "select count(*) from appoiment where docters_id = ? and appoiment_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,docters_id);
            preparedStatement.setString(2,appioment_date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                int count = resultSet.getInt(1);
                if(count == 0){
                    return true;
                }
                else {
                    return false;
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
