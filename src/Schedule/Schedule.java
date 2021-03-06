package Schedule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import Database.Database;
import Person.*;

/**
 * Schedule class
 * @author Kardelen Ceren
 */
@Entity
@Table(name = "Schedule")
public class Schedule {
    // properties

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;

    @OneToMany(mappedBy = "schedule",cascade = CascadeType.ALL)
    List<DailySchedule> days;

    @Column(name = "startingH")
    int startingHour;
    @Column(name = "startingM")
    int startingMinute;
    @Column(name = "endingH")
    int endingHour;
    @Column(name = "endingM")
    int endingMinute;

    //DATABASE için gerekli
    @OneToOne(mappedBy = "schedule")
    Doctor doctor;

    @Transient 
    Database db;

    // constructor
    // simple
    public Schedule(){
        days = new ArrayList<>();
        startingHour = 8;
        startingMinute = 0;
        endingHour = 17;
        endingMinute = 0;
    }

    // complete
    public Schedule(int startingHour, int startingMinute, int endingHour, int endingMinute) {
        days = new ArrayList<>();
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;
        this.endingHour = endingHour;
        this.endingMinute = endingMinute;
    }

    // methods

    /**
     * adds apoointment to schecule of person
     * @param app Appointment object
     * @return boolean if it is added successfullye
     *         false otherwise
     */
    public boolean addAppointment(Appointment app){
        
        DailySchedule day = findDay(app.getStartingTime());
        if (day != null){
            return day.addAppointment(app);
        }
        // if the day is not in the schedule, create that day and add the appointment
        else{
            LocalDateTime date = app.getStartingTime();
            System.out.println(date.getYear());
            DailySchedule newDay = new DailySchedule(date.getYear(),date.getMonthValue(), date.getDayOfMonth(),
                    startingHour, startingMinute, endingHour, endingMinute);
            System.out.println("SSSSSSSSSSSS");
            days.add(newDay);
            newDay.setSchedule(this);
            return newDay.addAppointment(app);
        }
    }

    /**
     *
     * @param app Appointmnet object
     * @return boolean if it is cancelled successfully
     *         false otherwise
     */
    public boolean cancelAppointment(Appointment app){
        DailySchedule day = findDay(app.getStartingTime());
        if (day == null){
            return false;
        }
        else{
            return day.cancelAppointment(app);
        }
    }

    /**
     *
     * @param app Appointment object
     * @param newYear as int
     * @param newMonth as int
     * @param newDayOfMonth as int
     * @param newHour as int
     * @param newMinute as int
     * @return boolean if it is postponed successfully
     *         false otherwise
     */
    public boolean postponeAppointment(Appointment app, int newYear, int newMonth, int newDayOfMonth, int newHour, int newMinute){
        DailySchedule day = findDay(app.getStartingTime());
        if (day == null){
            return false;
        }
        else{
            // create a new appointment with a new date
            Appointment newApp = new Appointment(app.getName(), app.getDoctor(), app.getPatient(), app.getPlace(), app.getDepartment(), app.getTimeInterval(),
                    newYear, newMonth, newDayOfMonth, newHour, newMinute);

            // if the appointment was successfully added, then cancel the odd appointment
            if(addAppointment(newApp)){
                day.cancelAppointment(app);
                return true;
            }
            else return false;
        }
    }

    /**
     * postpone the appointment
     * @param app Appointment object
     * @param newDate LocalDateTime
     * @return boolean if it is postponed successfully
     *         false otherwise
     */
    public boolean postponeAppointment(Appointment app, LocalDateTime newDate){
        DailySchedule day = findDay(app.getStartingTime());
        if (day == null){
            return false;
        }
        else{
            // create a new appointment with a new date
            Appointment newApp = new Appointment(app.getName(), app.getDoctor(), app.getPatient(), app.getPlace(), app.getDepartment(), app.getTimeInterval(),
                   newDate);

            // if the appointment was successfully added, then cancel the odd appointment
            if(addAppointment(newApp)){
                day.cancelAppointment(app);
                return true;
            }
            else return false;
        }
    }

    /**
     * gives the available time intervals of the person
     * @param date LocalDateTime  object
     * @return available intervals as LocalDateTime list
     */
    public ArrayList<LocalDateTime> getAvailableIntervals(LocalDateTime date){
        DailySchedule day = findDay(date);
        if (day == null){
            day = new DailySchedule(date.getYear(),date.getMonthValue(), date.getDayOfMonth(),
                    startingHour, startingMinute, endingHour, endingMinute);
            days.add(day);
            day.setSchedule(this);
        }
        return day.getAvailableIntervals();
    }

    /**
     * gives the appointments at given date
     * @param date LocalDateTime Object
     * @return appointments as Appointment list
     */
    public List<Appointment> getDateAppointments(LocalDateTime date){
        DailySchedule day = findDay(date);
        if (day != null){
            return day.getAppointments();
        }
        // if the day is not in the schedule, create that day
        else{
            DailySchedule newDay = new DailySchedule(date.getYear(),date.getMonthValue(), date.getDayOfMonth(),
                    startingHour, startingMinute, endingHour, endingMinute);
            
            days.add(newDay);
            return newDay.getAppointments();
        }
    }

    /**
     *  it gives the daily schedule object at given date
     * @param appDate as LocalDateTime Object
     * @return list of DailySchedule
     */
    public DailySchedule findDay(LocalDateTime appDate){
        LocalDateTime date;
        //((List<DailySchedule>)days).trimToSize();
        for (int i = days.size() - 1; i >= 0; i--) {
            DailySchedule day= days.get(i);
            day.convertBack();


            date = day.getDate();
            
            if (date.getDayOfMonth() == appDate.getDayOfMonth() && date.getMonthValue() == appDate.getMonthValue()
                    && date.getYear() == appDate.getYear()){
                return days.get(i);
            }
        }
        return null;
    }

    // getters

    public List<DailySchedule> getDays() {
        return days;
    }

    public int getStartingHour() {
        return startingHour;
    }

    public int getStartingMinute() {
        return startingMinute;
    }

    public int getEndingHour() {
        return endingHour;
    }

    public int getEndingMinute() {
        return endingMinute;
    }

    // setters
    public void setStartingHour(int startingHour) {
        this.startingHour = startingHour;
    }

    public void setStartingMinute(int startingMinute) {
        this.startingMinute = startingMinute;
    }

    public void setEndingHour(int endingHour) {
        this.endingHour = endingHour;
    }

    public void setEndingMinute(int endingMinute) {
        this.endingMinute = endingMinute;
    }

    public void setDb(Database db) {
        this.db = db;
    }
}
