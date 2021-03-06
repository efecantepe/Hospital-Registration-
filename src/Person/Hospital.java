/**
 * Hospital Class
 * @author Yusuf Doğan
 * @version 20/04/2021
 *
 * This class is created for representing the information of the Hospital.
 * A doctor can read the data of the Hospital.
 * A Admin can both read and write the data of the Hospital
 * A Patient mainly does not access of the information of the Hospital though they are some exceptions
 */

package Person;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import GeneralInfo.*;
import Schedule.Appointment;


@Entity
@Table(name = "Hospital")
public class Hospital {

    //properties
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "name")
    private String hospitalName;

    @OneToMany(mappedBy = "hospital",cascade = CascadeType.ALL)
    private List<Department> departments;

    @OneToMany(mappedBy = "hospital")
    private List<Doctor> hospitalDoctors;

    @OneToMany(mappedBy = "icuHospital")
    List<Patient> icuPatients= new ArrayList<>(); // needed?

    @OneToOne
    @JoinColumn(name = "Address_id")
    private Address address;

    @Column(name = "icuCapacity")
    private int icuCapacity;
    @Transient
    private int icuOccupancy;
    
    @Column(name= "phoneNumber")
    private String phoneNumber;
    @Column(name= "email")
    private String email;

    //DATABASE için gerekli
    @OneToMany(mappedBy = "place")
    private List<Appointment> appointments;

    @OneToOne(mappedBy = "hospital")
    private Admin admin;

    //constructors
    public Hospital(){
        departments = new ArrayList<Department>();
        hospitalDoctors = new ArrayList<Doctor>();
        icuPatients = new ArrayList<Patient>();
        icuOccupancy = 0;

    }//TODO patients dan icuPatients ve NormalPatients arrayListi yaratan method
    public Hospital( String hospitalName, int icuCapacity){
        this.hospitalName = hospitalName;
        departments = new ArrayList<Department>();
        hospitalDoctors = new ArrayList<Doctor>();
        icuPatients = new ArrayList<Patient>();

        this.icuCapacity = icuCapacity;
        icuOccupancy = 0;
    }

    public Hospital(String hospitalName, int icuCapacity, String phoneNumber, String email) {
        this.hospitalName = hospitalName;
        this.icuCapacity = icuCapacity;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }



    //getters
    public Address getAddress() {
        return address;
    }
    public List<Department> getDepartments() {
        return departments;
    }
    
    public List<Doctor> getHospitalDoctors() {
        return hospitalDoctors;
    }
    
    public int getIcuCapacity() {
        return icuCapacity;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getIcuOccupancy() {
        return icuOccupancy;
    }

    public ArrayList<Patient> getIcuPatients() {
        return (ArrayList<Patient>) icuPatients;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public int getId() {
        return id;
    }

    //setters

    public void setEmail(String email) {
        this.email = email;
    }


    public void setIcuCapacity(int capacity){
        icuCapacity = capacity;

    }
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    //methods

    public List<Patient> getAllPatients(){
        List<Patient> patients = new ArrayList<Patient>();
        for(Doctor doctor : hospitalDoctors){
            patients.addAll(doctor.getPatients());
        }
        return patients;
    }

    /**
     *
     * @param p
     * @return
     */
    public boolean assignPatientToIcu( Patient p) {
        if(icuOccupancy < icuCapacity) {

            p.privateSetInIcu(true,this);
            icuPatients.add(p);
            icuOccupancy++;
            return true;
        }
        return false;
    }
    //TODO 
    public boolean unassignPatientFromIcu(Patient p){
        if(icuPatients.remove(p)){
            icuOccupancy--;
            p.privateSetInIcu(false,this);
            return true;
        }
        return false;
    }


    /**
     *
     * @param d
     */
    public void addDepartment( Department d){
        departments.add(d);
        d.setHospital(this);

    }
    /**
     *
     * @param doctor
     * @param dp
     */
    public boolean addDoctor(Doctor doctor, Department dp ){
        if( departments.contains(dp)) {
            hospitalDoctors.add(doctor);
            dp.addDocToDepartment(doctor);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public double calculateIcuOccupancyPercentage(){
        if (icuCapacity != 0)
            return ( (double) icuOccupancy / (double) icuCapacity ) * 100;
        else
            return 0;
    }
    public Department getSpecificDepartment(String department) {
        for ( int i = 0; i < getDepartments().size(); i++ ) {
            if (getDepartments().get(i).getDepartmentName().equals(department))
                return getDepartments().get(i);
        }
        return null;
    }

    
    
}
