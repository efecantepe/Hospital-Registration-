import java.util.Collections;

public class Patient { // should implement Sendable and extend Person
    // properties
    private int ID;
    private GeneralInfo info;
    private ArrayList<Doctor> doctors;
    private ArrayList<Appointment> appointment;
    private Address address;
    private boolean inICU;
    private ArrayList<Disease> activeDiseases;

    // constructors
    // simple - just initializes
    public Patient(String name, String email, int ID){
        super(name, email); // FIXIT Person class should have createRandomPassword and showPassword methods
        this.ID = ID;
        info = new GeneralInfo();
        doctors = new ArrayList<Doctor>();
        appointment = new ArrayList<Appointment>();
        address = new Address();
        inICU = false;
        activeDiseases = new ArrayList<Disease>();
    }

    // complete constructor - if something does not exist, put null (?)
    public Patient(String name, String email, int ID, boolean inICU, String city, String country, String address, Disease[] diseases,
                   Patient mother, Patient father, Patient partner, Patient[] siblings, Patient[] children){
        super(name, email);
        this.ID = ID;
        this.inICU = inICU;

        // if any one address field is not null, then create an instance of Address
        if (city != null || country != null || address != null){
            address = new Address(city, country, address); // FIXIT check Address constructor
        }

        doctors = new ArrayList<Doctor>();
        addDoctor(Booting.getUser); // user must be the patient's doctor

        appointment = new ArrayList<Appointment>(); // no way to add appointments in the patient creation screen

        activeDiseases = new ArrayList<Disease>();
        Collections.addAll(activeDiseases, diseases);

        info = new GeneralInfo(mother, father, partner, siblings, children); // FIXIT check GeneralInfo constructor
        // ^^ should initialize empty body and consultations
        // ^^ should check if any Patient is null first
    }

    // methods

    public boolean addConsultation(Consultation consultation){
        return info.addConsultation(consultation); //FIXIT add this method to generalInfo (boolean?)
    }

    public Consultation getLastConsultation(){
        return info.getLastConsultation(); //FIXIT add this method to generalInfo
    }

    public boolean isPatientOf(Doctor doctor){
        return doctors.contains(doctor);
    }

    public boolean addDoctor(Doctor doctor){
        doctors.add(doctor);
        return true; // should there be a quota to doctors?
    }

    // FIXIT seeNotifications should be in the Person class (?)

    public void addActiveDisease(Disease disease){ // was boolean in the UML
        activeDiseases.add(disease);
    }

    public boolean removeActiveDisease(Disease disease){ // was boolean in the UML
        return activeDiseases.remove(disease);
    }

    public void drawBody(){
        info.drawBody();
    }

    // *** getters ***

    public Address getAddress() {
        return address;
    }

    public ArrayList<Appointment> getAppointment() {
        return appointment;
    }

    public ArrayList<Disease> getActiveDiseases() {
        return activeDiseases;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public GeneralInfo getInfo() {
        return info;
    }

    public int getID() {
        return ID;
    }

    // *** setters ***

    public void setAddress(Address address) {
        this.address = address;
    }

    // setAppointment method should not exist as it may potentially clash with the doctor's schedule

    public void setActiveDiseases(ArrayList<Disease> activeDiseases) {
        this.activeDiseases = activeDiseases;
    }

    public void setDoctors(ArrayList<Doctor> doctors) { // when will this be used?
        this.doctors = doctors;
    }

    public void setID(int ID) { // should it be allowed?
        this.ID = ID;
    }

    public void setInfo(GeneralInfo info) {
        this.info = info;
    }

    public void setInICU(boolean inICU) {
        this.inICU = inICU;
    }
}
