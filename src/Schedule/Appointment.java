package Schedule;
import java.time.LocalDateTime;

import Person.*;
public class Appointment implements Comparable{
    // properties
    String name;
    Doctor doctor;
    Patient patient;
    LocalDateTime startingTime;
    LocalDateTime endingTime;
    Hospital place;
    Department department;
    int timeInterval;

    // constructor
    // default
    public Appointment(){
    }
    // complete
    public Appointment(String name, Doctor doctor, Patient patient, Hospital place, Department department, int timeInterval,
                       int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        this.name = name;
        this.doctor = doctor;
        this.patient = patient;
        this.place = place;
        this.department = department;
        this.timeInterval = timeInterval;

        startingTime = LocalDateTime.of(year,month,dayOfMonth,hourOfDay,minute);
        endingTime = startingTime.plusMinutes(timeInterval);
    }

    public Appointment(String name, Doctor doctor, Patient patient, Hospital place, Department department, int timeInterval,
                       LocalDateTime date){
        this.name = name;
        this.doctor = doctor;
        this.patient = patient;
        this.place = place;
        this.department = department;
        this.timeInterval = timeInterval;

        startingTime = date;
        endingTime = startingTime.plusMinutes(timeInterval);
    }

    @Override
    public int compareTo(Object o) {
        if ( !(o instanceof Appointment))
            return 0;
        return this.getStartingTime().compareTo(((Appointment) o).getStartingTime());
    }

    // getters
    public String getName() {
        return name;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Department getDepartment() {
        return department;
    }

    public Hospital getPlace() {
        return place;
    }

    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public LocalDateTime getEndingTime() {
        return endingTime;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    // setters


    public void setName(String name) {
        this.name = name;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setPlace(Hospital place) {
        this.place = place;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public void setStartingTime(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        startingTime = LocalDateTime.of(year,month,dayOfMonth,hourOfDay,minute);
    }

    public boolean setEndingTime(LocalDateTime endingTime) {
        if(endingTime.isAfter(startingTime)) {
            this.endingTime = endingTime;
            return true;
        }
        else
            return false;
    }

    public boolean setEndingTime(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        if(endingTime.isAfter(startingTime)) {
            endingTime = LocalDateTime.of(year,month,dayOfMonth,hourOfDay,minute);
            return true;
        }
        else
            return false;

    }

    /**
     * Sets the ending time according to the time interval of the appointment and the starting time
     * @param timeInterval in minutes
     */
    public void setEndingTime(int timeInterval) {
        this.timeInterval = timeInterval;
        if(startingTime != null){
            endingTime = startingTime.plusMinutes(timeInterval);
        }
    }
}
