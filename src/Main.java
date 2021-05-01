import Person.*;
import Schedule.*;

import javax.swing.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Doctor doctor1 = new Doctor("Deniz Yılmaz", "fkfkkf", "dkdkdkd");
        Doctor doctor2 = new Doctor("Gamze Yılmaz", "fkfkkf", "dkdkdkd");
        Doctor doctor3 = new Doctor("Okan Yılmaz", "fkfkkf", "dkdkdkd");
        Doctor doctor4 = new Doctor("Tekman Yılmaz", "fkfkkf", "dkdkdkd");
        Doctor doctor5 = new Doctor("Selçuk Yılmaz", "fkfkkf", "dkdkdkd");

        Patient patient1 = new Patient("Selin Gündüz", "jddkdkdk");
        Patient patient2 = new Patient("Ali Gündüz", "jddkdkdk");
        Patient patient3 = new Patient("Berk Gündüz", "jddkdkdk");
        Patient patient4 = new Patient("Buse Gündüz", "jddkdkdk");

        Hospital hospital = new Hospital("FirstHospital", 22);
        Department department1 = new Department("Cardiology");
        Department department2 = new Department("Oncology");
        Department department3 = new Department("Dermatology");

        hospital.addDepartment(department1);
        hospital.addDepartment(department2);
        hospital.addDepartment(department3);

        hospital.addDoctor(doctor1, department1);
        hospital.addDoctor(doctor2, department1);
        hospital.addDoctor(doctor3, department2);
        hospital.addDoctor(doctor4, department2);
        hospital.addDoctor(doctor5, department3);

        Appointment appointment = new Appointment("App", doctor1, patient1, doctor1.getHospital(), doctor1.getDepartment(), 50, 2021,5,1,9,30);
        doctor1.addAppointment(appointment);

        doctor1.assignPatient(patient1);
        doctor1.assignPatient(patient2);

        Task task1 = new Task("Do this", doctor1,doctor1,"kk",true);
        Task task2 = new Task("Do that", doctor1,doctor1,"jj", false);

        doctor1.addTask(task1);
        doctor1.addTask(task2);

        JFrame frame = new MainDoctorJFrame(doctor1);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // centers the window
    }
}