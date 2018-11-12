package session;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import rental.CarRentalCompany;
import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

@Stateful
public class CarRentalSession implements CarRentalSessionRemote {

    private String renter;
    private List<Quote> allQuotes = new LinkedList<Quote>();
    
    @PersistenceContext
    private EntityManager em;

    /*@Override
    public Set<String> getAllRentalCompanies() {
        return new HashSet<String>(RentalStore.getRentals().keySet());
    }
    
    @Override
    public List<CarType> getAvailableCarTypes(Date start, Date end) {
        List<CarType> availableCarTypes = new LinkedList<CarType>();
        for(String crc : getAllRentalCompanies()) {
            for(CarType ct : RentalStore.getRentals().get(crc).getAvailableCarTypes(start, end)) {
                if(!availableCarTypes.contains(ct))
                    availableCarTypes.add(ct);
            }
        }
        return availableCarTypes;
    }

    @Override
    public Quote createQuote(String company, ReservationConstraints constraints) throws ReservationException {
        
        try {
            Quote out = RentalStore.getRental(company).createQuote(constraints, renter);
            quotes.add(out);
            return out;
        } catch(Exception e) {
            throw new ReservationException(e);
        }
    }

    @Override
    public List<Quote> getCurrentQuotes() {
        return quotes;
    }

    @Override
    public List<Reservation> confirmQuotes() throws ReservationException {
        List<Reservation> done = new LinkedList<Reservation>();
        try {
            for (Quote quote : quotes) {
                done.add(RentalStore.getRental(quote.getRentalCompany()).confirmQuote(quote));
            }
        } catch (Exception e) {
            for(Reservation r:done)
                RentalStore.getRental(r.getRentalCompany()).cancelReservation(r);
            throw new ReservationException(e);
        }
        return done;
    }

    @Override
    public void setRenterName(String name) {
        if (renter != null) {
            throw new IllegalStateException("name already set");
        }
        renter = name;
    }
    */

    @Override
    public void setRenterName(String name) {
        this.renter = name;
    }

    @Override
    public String getCheapestCarType(Date start, Date end, String region) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CarType> getAvailableCarTypes(Date start, Date end) {
        List<CarRentalCompany> allCompanies = em.createQuery("SELECT company FROM CarRentalCompany rentalCompany").getResultList();
        Set<CarType> allTypes = new HashSet<CarType>();
        for(CarRentalCompany rentalcompany : allCompanies) {
            for(CarType cartype : rentalcompany.getAvailableCarTypes(start, end)) {
                allTypes.add(cartype);
            }
        }
        return new LinkedList<CarType>(allTypes);
    }

    @Override
    public Quote createQuote(String company, ReservationConstraints constraints) throws ReservationException {
        CarRentalCompany crc = em.find(CarRentalCompany.class, company);
        Quote quote = crc.createQuote(constraints, renter);
        allQuotes.add(quote);
        return quote; 
    }

    @Override
    public List<Reservation> confirmQuotes() throws ReservationException {
        List<Reservation> confirmed = new LinkedList<Reservation>();
        try {
            for (Quote quote : allQuotes) {
                CarRentalCompany crc = em.find(CarRentalCompany.class , quote.getRentalCompany());
                confirmed.add(crc.confirmQuote(quote));
            }
        } catch (ReservationException exc) {
            exc.printStackTrace();
        }
        return confirmed; 
    }
    
}