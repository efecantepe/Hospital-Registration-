package GeneralInfo;

import javax.persistence.*;

import Person.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Medication Class
 * Efe Can Tepe
 */
@Entity
@Table(name = "Medication")
public class Medication {


    @ManyToMany
    @JoinTable(name = "MedicationMedicationJoin",
                joinColumns = @JoinColumn(name= "Medication_id"),
                inverseJoinColumns= @JoinColumn(name= "Medication_id1") )
    List<Medication> mClashes;


    @ManyToOne
    @JoinColumn(name = "Department_id")
    private Department relatedField;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name= "info")
    private String info;

    @ManyToMany
    @JoinTable(name = "MedicationDiseaseJoin",
               joinColumns = @JoinColumn(name= "Medication_id"),
               inverseJoinColumns = @JoinColumn(name="Disease_id"))
    private List<Disease> dClashes= new ArrayList<>();

    //necessary parameters for DATABASE
    @ManyToMany(mappedBy = "medications")
    private List<Prescription> prescriptions;

    //CONSTRUCTORS
    Medication(){}

    public Medication(String name, String info){
        this.name= name;
        this.info= info;
    }

        //METHODS
    public void addClasshingDisease(Disease d){
        dClashes.add(d);
    }  //adding disease to medication

    public void addClashingMedication(Medication m){
        mClashes.add(m);
    } //adding disease to medication

    public void setName(String name) {
        this.name = name;
    } //medication name

    public void setInfo(String info) {
        this.info = info;
    } // information abt medication

    public void setRelatedField(Department relatedField) {
        this.relatedField = relatedField;
    }

    //GETTERS
    public int getId() {
        return id;
    }
    
    public List<Disease> getdClashes() {
        return dClashes;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public List<Medication> getmClashes() {
        return mClashes;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public Department getRelatedField() {
        return relatedField;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id= " + id +
                "\nname='" + name + '\'' +
                "\ninfo='" + info + '\'' +
                '}';
    }

    
}
