package se.atg.service.harrykart.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

import java.io.Serializable;

/**
 * Class representation of a lane within a loop--lane number and power value
 */
public class Lane implements Serializable {
    @JacksonXmlProperty(isAttribute = true)
    int number;

    @JacksonXmlText
    int powerValue;

    public Lane() {}

    /**
     * A lane within a loop
     * @param number    Lane number
     * @param powerValue    Power-Up value
     */
    public Lane(int number, int powerValue) {
        this.number = number;
        this.powerValue = powerValue;
    }

    /**
     * Returns the lane number
     * @return int  The lane number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the power-up value
     * @return int  The power-up value
     */
    public int getPowerValue() {
        return powerValue;
    }

    /**
     * The lane in string form
     * @return  String  Representation of the lane
     */
    @Override
    public String toString() {
        return "Lane{" +
                "number=" + getNumber() +
                ", power='" + getPowerValue() + '\'' +
                '}';
    }

}
