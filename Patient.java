package hospitalmanagementsystem;

import java.sql.*;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    public  void removePatient() {
        System.out.println();
        System.out.println("enter patient id for remove patient : ");
        int pid = scanner.nextInt();

        System.out.println();
        String query = "delete from patient where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,pid);
            int afr = ps.executeUpdate();
            if(afr > 0) {
                System.out.println("data of patient deleted succesfully.");
            }else {
                System.out.println("id not exists.");
            }
        }catch (SQLException e) {
            System.out.println("error at delete prepared.");
        }

    }


    public void addPatient(){
        System.out.print("enter patient name : ");
        String name = scanner.next();
        System.out.print("enter patient age : ");
        int age = scanner.nextInt();
        System.out.print("enter patient gender : ");
        String gender = scanner.next();

        try {

            String query  = "Insert into patient(name,age,gender) values(?,?,?)";
            PreparedStatement preparedStatement  = connection.prepareStatement(query);
            //preparedStatement.setInt(1,id);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Patient added succesfully");
            }else {
                System.out.println("failed to Patient addtion ");
            }

        }catch(SQLException e) {
                e.printStackTrace();
        }

    }
    public void viewPatient(){
        String query = "select * from patient";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("patients : ");
            System.out.println("+---------------+--------------------+-------------+--------------+");
            System.out.println("|  Patient id   |        Name        |      Age    |    Gender    |");
            System.out.println("|---------------|--------------------|-------------|--------------|");
            while(resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-15s|%-20s|%-13s|%-14s|\n",id,name,age,gender);
                System.out.println("|---------------|--------------------|-------------|--------------|");
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean getPatient(int id){
        String query = "select * from patient where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return true;
            }else {
                return false;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
