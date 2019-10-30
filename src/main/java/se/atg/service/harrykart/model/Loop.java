package se.atg.service.harrykart.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representation of a loop within the race
 */
@JacksonXmlRootElement(localName = "loop")
public class Loop implements Serializable {
    @JacksonXmlProperty(isAttribute = true)
    int number;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "lane")
    ArrayList<Lane> lanes;

    public Loop() {}

    /**
     * Loop has an ordinal number and a series of lanes with power-up values
     * @param number    Loop number
     * @param lanes Collection of lane objects
     */
    public Loop(int number, ArrayList<Lane> lanes) {
        this.number = number;
        this.lanes = lanes;
    }

    /**
     * Returns the ordinal number of the loop in the race
     * @return  int Loop number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Return the collection of lanes for the loop
     * @return ArrayList<Lane> The lanes within the loop
     */
    public ArrayList<Lane> getLanes() {
        return lanes;
    }

    /**
     * String representation of the loop
     * @return String   Loop in string format
     */
    @Override
    public String toString() {
        return "Loop{" +
                "number=" + getNumber() +
                ", lanes='" + getLanes() + '\'' +
                '}';
    }

}
