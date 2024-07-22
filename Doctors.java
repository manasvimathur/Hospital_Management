package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
    private static Connection connection;


    public Doctors(Connection connection){
        this.connection = connection;
    }


    public void viewdoctors(){
        String query = "Select * from doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+----------------------+---------+-------------+");
            System.out.println("| Doctors ID |  Doctor Name         |   Specialization      |");
            System.out.println("+------------+----------------------+-----------------------+");
            while(resultset.next()){
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String specialization = resultset.getString("specialization");
                System.out.printf("|%-13s|%-23s|%-24s|\n",id,name,specialization );
                System.out.println("+------------+----------------------+-----------------------+");

            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static boolean getdoctorbyid(int id) {
        String query = "SELECT * FROM doctors WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setInt(1,id);
            ResultSet resultset = preparedStatement.executeQuery();
            if(resultset.next()){
                return true;
            }else{
                return false;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
