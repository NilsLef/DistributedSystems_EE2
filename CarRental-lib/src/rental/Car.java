package rental;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import rental.CarType;
import rental.Reservation;

@Entity
public class Car implements Serializable {
    @Id
    private int id;
    private CarType type;
    private Set<Reservation> reservations;

    /***************
     * CONSTRUCTOR *
     ***************/
    public Car() {
        
    }
    
    public Car(int uid, CarType type) {
    	this.id = uid;
        this.type = type;
        this.reservations = new HashSet<Reservation>();
    }

    /******
     * ID *
     ******/
    public int getId() {
    	return id;
    }
    
    public void setId(int ID) {
        this.id = ID;
    }
    
    /************
     * CAR TYPE *
     ************/
    @OneToOne(cascade = ALL)
    public CarType getType() {
        return type;
    }
	
	public void setType(CarType type) {
		this.type = type;
	}
    /****************
     * RESERVATIONS *
     ****************/

    public boolean isAvailable(Date start, Date end) {
        if(!start.before(end))
            throw new IllegalArgumentException("Illegal given period");

        for(Reservation reservation : reservations) {
            if(reservation.getEndDate().before(start) || reservation.getStartDate().after(end))
                continue;
            return false;
        }
        return true;
    }
    
    public void addReservation(Reservation res) {
        reservations.add(res);
    }
    
    public void removeReservation(Reservation reservation) {
        // equals-method for Reservation is required!
        reservations.remove(reservation);
    }
    
    @ManyToMany(cascade=ALL)
    public Set<Reservation> getReservations() {
        return reservations;
    }
}