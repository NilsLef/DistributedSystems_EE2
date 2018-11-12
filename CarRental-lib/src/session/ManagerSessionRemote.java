package session;

import java.util.Set;
import javax.ejb.Remote;
import rental.Car;
import rental.CarRentalCompany;
import rental.CarType;

@Remote
public interface ManagerSessionRemote {
    
    public Set<CarType> getCarTypes(String company);
    
    //new
    public Set<String> getBestClients() throws Exception;
    public CarType getMostPopularCarTypeIn(String carRentalCompanyName, int year) throws Exception;
    public int getNumberOfReservationsBy(String clientName) throws Exception;
            
    public int getNumberOfReservationsForCarType(String company, String type);
    
            
    public Set<Integer> getCarIds(String company,String type);
    
    public int getNumberOfReservations(String company, String type, int carId);
    

      
    public void addCarRentalCompany(CarRentalCompany crc);
    
    public void addCarToCompany(CarRentalCompany crc, Car car);
}