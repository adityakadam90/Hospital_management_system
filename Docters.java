package hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Docters {
    private Connection connection;

    public Docters(Connection connection) {
        this.connection = connection;

    }
    public void viewDocters(){
        String query = "select * from docters;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Docters : ");
            System.out.println("+---------------+--------------------+--------------------------+");
            System.out.println("|  Docters id   |        Name        |      specilization       |");
            System.out.println("|---------------|--------------------|--------------------------|");
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specifiction = resultSet.getString("specilization");
                System.out.printf("|  %-13s|  %-18s|  %-24s|\n",id,name,specifiction);
                System.out.println("|---------------|--------------------|--------------------------|");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean getDocters(int id){
        String query = "select * from docters where id = ?";
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
