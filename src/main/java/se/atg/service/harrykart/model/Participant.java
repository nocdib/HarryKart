package se.atg.service.harrykart.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * Class representation of the racers by lane number, horse name, and initial speed
 */
@JacksonXmlRootElement(localName = "participant")
public class Participant implements Serializable {
    @JacksonXmlProperty
    int lane;

    @JacksonXmlProperty
    String name;

    @JacksonXmlProperty
    int baseSpeed;

    public Participant() {}

    /**
     * Harry Kart race participant
     * @param lane Lane number
     * @param name Horse name
     * @param baseSpeed Speed that the initial loop is run at
     */
    public Participant(int lane, String name, int baseSpeed) {
        this.lane = lane;
        this.name = name;
        this.baseSpeed = baseSpeed;
    }

    /**
     * Returns the lane the participant is in
     * @return  int     Lane value
     */
    public int getLane() {
        return lane;
    }

    /**
     * Returns the participant's name
     * @return  String  Name of the participant
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the base speed of the participant
     * @return  int Initial speed of the participant
     */
    public int getBaseSpeed() {
        return baseSpeed;
    }

    /**
     * Sets the initial speed of the participant
     * @param baseSpeed Speed value to be set
     */
    public void setBaseSpeed(int baseSpeed) {
        this.baseSpeed = baseSpeed;
    }

    /**
     * String representation of the Participant
     * @return String Participant values
     */
    @Override
    public String toString() {
        return "Participant{" +
                "lane=" + lane +
                ", name='" + name + '\'' +
                ", baseSpeed=" + baseSpeed +
                '}';
    }

}
