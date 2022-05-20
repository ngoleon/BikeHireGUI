public class eBike extends Bike {

    private int maximumDistance;
    private boolean motorStrength;

    public eBike() {}
    public eBike(double pricePerDay, boolean availability, String type, int maximumDistance, boolean motorStrength) {
        super(pricePerDay, availability, type);
        this.maximumDistance = maximumDistance;
        this.motorStrength = motorStrength;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }

    public void setMaximumDistance(int maximumDistance) {
        this.maximumDistance = maximumDistance;
    }

    public boolean getMotorStrength() {
        return motorStrength;
    }

    public String getMotorStrengthString() {
        if(motorStrength) {
            return "Full Power";
        } else {
            return "Power Assist";
        }
    }
    public void setMotorStrength(boolean motorStrength) {
        this.motorStrength = motorStrength;
    }

}
