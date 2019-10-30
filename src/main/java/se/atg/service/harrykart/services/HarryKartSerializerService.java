package se.atg.service.harrykart.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.xml.sax.SAXException;
import se.atg.service.harrykart.exception.HarryKartException;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.Rank;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * Class to handle the conversion of Harry Kart races from XML to POJO to JSON
 */
public class HarryKartSerializerService {

    private XmlMapper xmlMapper;
    private static final String SCHEMA_FILE = "input.xsd";

    public HarryKartSerializerService() {
        this.xmlMapper = new XmlMapper();
        xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public boolean isXmlValid(String xmlString) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(new File(getResource(SCHEMA_FILE)));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xmlString)));
            return true;
        } catch (SAXException | IOException e) {
            return false;
        }
    }

    public boolean isNumberOfLoopsValid(HarryKart hk){
        return hk.getNumberOfLoops() == hk.getPowerUps().size()+1;
    }

    public int numberOfParticipants(HarryKart hk){
        return hk.getStartList().size();
    }

    private String getResource(String filename) throws FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(filename);
        Objects.requireNonNull(resource);
        return resource.getFile();
    }

    /**
     * Map Harry Cart Race XML to a HarryKart object
     * @param xmlString XML representing a Harry Kart race
     * @return HarryKart    Java representation of a Harry Kart race
     * @throws HarryKartException
     */
    public HarryKart deserializeFromXML(String xmlString) throws HarryKartException {
        // Before de-serializing, check the XML validity
        if(!this.isXmlValid(xmlString)){
            throw new HarryKartException("The Harry Kart input XML is not in a valid format.");
        }

        HarryKart harryKartObj = null;
        try {
            harryKartObj = xmlMapper.readValue(xmlString, HarryKart.class);
            /*
                After de-serializing the XML:
                 1) Verify that the number of loops matches the specified value
                 2) Verify that there are at least two race participants
             */
            if(!this.isNumberOfLoopsValid(harryKartObj)){
                throw new HarryKartException("<numberOfLoops> value in the input XML does not match the number of loops provided");
            }
            if(this.numberOfParticipants(harryKartObj) < 2){
                throw new HarryKartException("Harry Kart race cannot have less than 2 participants");
            }

        } catch (IOException e) {
            System.out.println("IOException while trying to de-serialize input XML");
            System.out.println(e);
        }
        return harryKartObj;
    }

    /**
     * Converts a collection of ranked participants to JSON format
     * @param ranking Collection of race participants ordered by finish time
     * @return  String  JSON representation of the first-, second- and third-place ranking
     */
    public String serializeToJson(List<Rank> ranking){
        ObjectMapper mapper = new ObjectMapper();
        String jsonRanking = "[]";
        try {
            jsonRanking = mapper.writeValueAsString(ranking);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"json-processing-error\": " + e.getMessage() + " }";
        }
        return "{\"ranking\": " + jsonRanking + " }";
    }

}