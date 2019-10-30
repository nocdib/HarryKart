package se.atg.service.harrykart.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to represent the elements of a Harry Kart race: Participants, Loops, and power-ups
 */
@JacksonXmlRootElement(localName = "harryKart")
public class HarryKart implements Serializable {
    int numberOfLoops;
    ArrayList<Participant> startList;
    ArrayList<Loop> powerUps;

    public HarryKart() {}

    /**
     * Harry Kart object
     * @param numberOfLoops Integer value of the <numberOfLoops> element
     * @param startList List of participants in the race
     * @param powerUps Values for each participant in each loop
     */
    public HarryKart(int numberOfLoops, ArrayList<Participant> startList, ArrayList<Loop> powerUps) {
        this.numberOfLoops = numberOfLoops;
        this.startList = startList;
        this.powerUps = powerUps;
    }

    /**
     * Return the <numberOfLoops> value
     * @return int  The value of the <numberOfLoops> element
     */
    public int getNumberOfLoops() {
        return numberOfLoops;
    }

    /**
     * Returns a collection of objects representing each race participant
     * @return  ArrayList<Participant>  List of race participants
     */
    public ArrayList<Participant> getStartList() {
        return startList;
    }

    /**
     * Returns the Power values for each loop
     * @return ArrayList<Loop> Collection of objects representing the power each lane has in a loop
     */
    public ArrayList<Loop> getPowerUps() {
        return powerUps;
    }

    /**
     * A String representation of the Harry Kart XML
     * @return  String  String representation of the Harry Kart race
     */
    @Override
    public String toString() {
        return "\tStart List: " + this.getStartList() + "\n" +
                "\tLoops: " + this.getNumberOfLoops() + "\n" +
                "\tPower Ups: " + this.getPowerUps();
    }


}
