import java.io.Serializable;
import java.time.LocalDate;

public class Rent implements Serializable {

    protected LocalDate date;
    protected int rentID;
    protected static int nextID = 100;
    protected int duration;
    protected int customerIndex;
    protected int bikeIndex;
    protected LocalDate endDate;

    public LocalDate getEndDate(){
        return endDate;
    }
    public Rent() { }

    public Rent(LocalDate date){
        this.date = date;
    }

    public Rent(LocalDate date, int duration) {
        this.date = date;
        this.duration = duration;
    }

    public Rent(LocalDate date, int duration, int customerID) {
        this.date = date;
        this.duration = duration;
        this.customerIndex = customerID;
    }
    public Rent(LocalDate date, int duration, int customerIndex, int bikeIndex) {
        this.date = date;
        this.duration = duration;
        this.customerIndex = customerIndex;
        this.bikeIndex = bikeIndex;
        this.rentID = nextID++;
        this.endDate = date.plusDays(duration);
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

//    public LocalDate getEndDate(){
//        return endDate;
//    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getBikeIndex() {
        return bikeIndex;
    }


    public void setBikeIndex(int bikeIndex) {
        this.bikeIndex = bikeIndex;
    }

    public int getRentID() {
        return rentID;
    }

    public void setRentID(int rentID) {
        this.rentID = rentID;
    }

    public int getCustomerIndex() {
        return customerIndex;
    }

    public void setCustomerIndex(int customerIndex) {
        this.customerIndex = customerIndex;
    }

}

