package Person;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * Person class
 * @author Eylul Badem
 * @version 1.0, 21.04.2021
*/
@MappedSuperclass
public class Person {
    
    // Properties
    @Id
    @Column(name = "id")
    int id;
    
    private String name;
    private String email;
    private String password;
    private List<Notification> notifications;
    
    // Constructor

    public Person(){}

    public Person ( String name, String email)
    {
        this.name = name;
        this.email = email;

        notifications = new ArrayList<Notification>();
    }
    public Person ( String name, String email, String password )
    {
        this.name = name;
        this.email = email;
        this.password = password;
        notifications = new ArrayList<Notification>();
    }
    
    // Methods
    
    public String getName()
    {
        return name;
    }
   
    public String getEmail()
    {
        return email;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public ArrayList<Notification> getNot()
    {
        return (ArrayList<Notification>) notifications;
    }
    
    /**
     * This method sends a notification to a chosen person of any type
     * @param p person to send the notification, @param n the notification to send
     * @return true if the sending process was successful
     */
    public boolean sendNotification( Person p, Notification n )
    {
        boolean check = false;
        p.getNot().add(n);
        
        if ( p.getNot().contains(n))
            check = true;
        
        return check;
    }
    
}

