import java.io.Serializable;

public class Bike implements Serializable {

    //variables
    protected double pricePerDay;
    protected boolean availability;
    protected String type;
    protected static int nextID = 100;
    protected int bikeID;



    //constructors
    public Bike() {
    }


    public Bike(double pricePerDay, boolean availability, String type) {
        this.pricePerDay = pricePerDay;
        this.availability = availability;
        this.type = type;
        this.bikeID = nextID++;
    }

    //getters and setters
    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    //---------------------------------------
    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBikeID() {
        return bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }
}