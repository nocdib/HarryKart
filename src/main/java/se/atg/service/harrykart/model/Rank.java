package se.atg.service.harrykart.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representation of the ranking of a participant based on their order of race completion
 */
@JsonIgnoreProperties(value = { "time" }) // omit time field when serializing object to JSON
public class Rank implements Comparable<Rank>{
    int position;
    String horse;
    double time;

    public Rank() {}

    /**
     * The rank of the Participant
     * @param position  The order that participant has finished in
     * @param horse The participant's name
     * @param time  The simulated time of completion
     */
    public Rank(int position, String horse, double time) {
        this.position = position;
        this.horse = horse;
        this.time = time;
    }

    /**
     * Returns the position of the participant
     * @return  int Order of race completion
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set the rank of the participant
     * @param position  Order of race completion
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Returns the name of the participant
     * @return  String Participant's name
     */
    public String getHorse() {
        return horse;
    }

    /**
     * Returns the representative value quantifying the order of completion
     * @return double Proxy value for the time the race was completed in
     */
    public double getTime() {
        return time;
    }

    /**
     * Sets the representative value quantifying the order of completion
     * @param time  Proxy value for the time the race was completed in
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * Comparison logic for sorting rank by race completion time
     * @param other Rank object of another participant to be compared with
     * @return int Value signifying greater than, less than, or equal to ranking based on time
     */
    @Override
    public int compareTo(Rank other) {
        if(this.getTime() == other.getTime())
            return 0;
        else if(this.getTime() > other.getTime())
            return 1;
        else
            return -1;
    }

    /**
     * String representation of a participant's rank at the end of the race
     * @return  String Finishing position and time of the participant
     */
    @Override
    public String toString() {
        return "Rank{" +
                "position=" + position +
                ", horse='" + horse + '\'' +
                ", time=" + time +
                '}';
    }
}