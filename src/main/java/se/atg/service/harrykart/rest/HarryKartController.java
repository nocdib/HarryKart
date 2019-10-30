package se.atg.service.harrykart.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.atg.service.harrykart.exception.HarryKartException;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.Rank;
import se.atg.service.harrykart.services.*;

import java.util.List;

/**
 * Entry-point for the application. API accepts XML POST requests at http://localhost:8080/api/play
 */
@RestController
@RequestMapping("/api")
public class HarryKartController {

    /**
     * HTTP Request handler at the /play endpoint. Only accepts POST requests with XML payload and returns JSON
     * @param inputXML XML representation of a Harry Kart race
     * @return  String  JSON representation of the race results top placers
     */
    @RequestMapping(method = RequestMethod.POST, path = "/play", consumes = "application/xml", produces = "application/json")
    public String playHarryKart(@RequestBody String inputXML) {
        HarryKartSerializerService hkSerializer = new HarryKartSerializerService();
        try {
            // De-serialize the XML
            HarryKart hk = hkSerializer.deserializeFromXML(inputXML);
            // Calculate the race results
            List<Rank> ranking = new HarryKartResultService(hk).getResults();
            // return JSON results
            return hkSerializer.serializeToJson(ranking);
        } catch(HarryKartException e) {
            System.out.println("HarryKartException: " + e.getMessage());
            return "{\"message\": " + e.getMessage() + " }";
        }
    }

}
