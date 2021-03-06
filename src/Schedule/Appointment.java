package Schedule;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import Person.*;
/**
 * Appointment class that enables patients get appointment from wished doctor or hospital
 * @author Kardelen Ceren
 */

@Entity
@Table(name = "Appointment")
public class Appointment implements Comparable{
    // properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    String name;
    
    @ManyToOne
    @JoinColumn(name = "Doctor_id")
    Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "Patient_id")
    Patient patient;

    @Column(name = "start")
    String startStr;
    @Column(name = "end")
    String endStr;

    @Transient
    LocalDateTime startingTime;
    
    @Transient
    LocalDateTime endingTime;

    @Column(name = "timeInterval")
    int timeInterval;

    @ManyToOne
    @JoinColumn(name = "Hospital_id")
    Hospital place;

    @ManyToOne
    @JoinColumn(name = "Department_id")
    Department department;

    

    //necessary for DATABASE
    @ManyToOne
    @JoinColumn(name = "DailySchedule_id")
    DailySchedule dailySchedule;


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

        setStartStr();
        setEndStr();

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

        setStartStr();
        setEndStr();
    }

    @Override
    public int compareTo(Object o) {
        if ( !(o instanceof Appointment))
            return 0;
        return this.getStartingTime().compareTo(((Appointment) o).getStartingTime());
    }

    public void calculateEndingTime(){
        setStartingTime();
        endingTime = startingTime.plusMinutes(timeInterval);
        setEndStr();
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
        setStartingTime();
        return startingTime;
    }

    public LocalDateTime getEndingTime() {
        setEndingTime();
        return endingTime;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    // setters


    public void setStartingTime(){
        startingTime = Converter.toLocalDateTime(startStr);
    }
    public void setStartStr(){
        startStr = Converter.toString(startingTime);
    }
    public void setEndingTime(){
        endingTime = Converter.toLocalDateTime(endStr);
    }
    public void setEndStr() {
        endStr = Converter.toString(endingTime);
    }

    public void setDailySchedule(DailySchedule dailySchedule) {
        this.dailySchedule = dailySchedule;
    }

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

    public void setStartStr(String startStr) {
        this.startStr = startStr;
    }
    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }

    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }

    public void setStartingTime(int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        startingTime = LocalDateTime.of(year,month,dayOfMonth,hourOfDay,minute);
    }

    /**
     * this method set ending time with LocalDateTime option
     * @param endingTime
     * @return true if it is ended
     *         false otherwise
     */
    public boolean setEndingTime(LocalDateTime endingTime) {
        if(endingTime.isAfter(startingTime)) {
            this.endingTime = endingTime;
            return true;
        }
        else
            return false;
    }

    /**
     *
     * @param year as int
     * @param month as int
     * @param dayOfMonth as int
     * @param hourOfDay as int
     * @param minute as int
     * @return true if it is ended
     *         false otherwise
     */
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

