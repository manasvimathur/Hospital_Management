package HospitalManagementSystem;

import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addpatient(){
        System.out.println("Enter Patient Name:  ");
                String Name = scanner.next();
        System.out.println("Enter Patient Age:  ");
                int Age = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
                String Gender = scanner.next();
        try{
            String query = "INSERT INTO patients(name,age,gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, Name);
            preparedStatement.setInt(2, Age);
            preparedStatement.setString(3, Gender);
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient added SUCCESSFULLY.");
            }else{
                System.out.println("FAILED! to add Patient");
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }
        public void viewpatients(){
        String query = "Select * from patients";
        try{
           PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+----------------------+---------+-------------+");
            System.out.println("| Patient ID |  Patient Name        |    Age  |   Gender    |");
            System.out.println("+------------+----------------------+---------+-------------+");
        while(resultset.next()){
            int id = resultset.getInt("id");
            String name = resultset.getString("name");
            int age = resultset.getInt("age");
            String gender = resultset.getString("gender");
            System.out.printf("|%-13s|%-23s|%-10s|%-14s|\n",id, name, age,gender);
            System.out.println("+------------+----------------------+---------+-------------+");

        }

        }catch (SQLException e){
            e.printStackTrace();
        }
        }
        public boolean getpatientbyid(int id) {
            String query = "SELECT * FROM patients WHERE id = ?";
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
