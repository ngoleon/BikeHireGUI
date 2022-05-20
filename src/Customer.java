import java.io.Serializable;

public class Customer implements Serializable {
    protected int ID;
    protected static int nextID = 100;
    protected String name;
    protected String phoneNumber;
    protected boolean currentlyRenting = false;

    public Customer(){}
    public Customer(String name, String phoneNumber) {
        this.ID = nextID++;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.currentlyRenting = false;
    }

    public Customer(String name, String phoneNumber, boolean renting) {
        this.ID = nextID++;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.currentlyRenting = renting;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isCurrentlyRenting() {
        return currentlyRenting;
    }

    public String isCurrentlyRentingStr() {
        if (currentlyRenting) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public void setCurrentlyRenting(boolean currentlyRenting) {
        this.currentlyRenting = currentlyRenting;
    }


}
