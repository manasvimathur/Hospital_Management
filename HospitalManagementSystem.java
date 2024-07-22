package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

import static java.lang.Class.forName;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "!Manu2529";

    public static void main(String[] args) {
        try {
                Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection, scanner);
            Doctors doctor = new Doctors(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your Choice: ");
                int choice = scanner.nextInt();

                switch(choice){
                    case 1:
                          patient.addpatient();
                          System.out.println();
                          break;
                    case 2:
                        patient.viewpatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewdoctors();
                        System.out.println();
                        break;
                    case 4:
                            bookappointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        System.out.print("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM")
                        return;

                    default:
                        System.out.println("Enter Valid Choice");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void bookappointment(Patient patient, Doctors doctor, Connection connection, Scanner scanner){
        System.out.println("Enter Patient ID:  ");
        int patientid = scanner.nextInt();
        System.out.println("Enter Doctor ID:  ");
        int  doctorid = scanner.nextInt();
        System.out.println("Enter Appointment Date in(dd-mm-yy-):  ");
            String appointmentdate = scanner.next();
            if(patient.getpatientbyid(patientid) && Doctors.getdoctorbyid(doctorid)){
                if(checkdoctoravailabilty(doctorid, appointmentdate, connection)){
                        String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                        try{
                            PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                            preparedStatement.setInt(1, patientid);
                            preparedStatement.setInt(2, doctorid);
                            preparedStatement.setString(3, appointmentdate);
                            int rowsAffected =  preparedStatement.executeUpdate();
                            if(rowsAffected>0){
                                System.out.println("Appointment Booked!!");
                            }else{
                                System.out.println("FAILED to Book Appointment!");
                            }
                        }catch(SQLException e){
                            e.printStackTrace();
                        }
                }else{
                    System.out.println("Doctor not available on this date");
                }
            }else{
                System.out.print("Either Doctor or Patient doesnt exist!!");
            }
    }
    public static boolean checkdoctoravailabilty(int doctorid, String appointmentdate, Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorid);
            preparedStatement.setString(2, appointmentdate);
            ResultSet resultset = preparedStatement.executeQuery();
            if(resultset.next()){
                int count = resultset.getInt(1);
                if(count == 0){
                    return true;
                } else{
                    return false;
                }
            } else{
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}
