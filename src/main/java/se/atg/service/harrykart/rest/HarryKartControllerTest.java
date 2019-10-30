package se.atg.service.harrykart.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import se.atg.service.harrykart.exception.HarryKartException;
import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.Rank;
import se.atg.service.harrykart.services.HarryKartResultService;
import se.atg.service.harrykart.services.HarryKartSerializerService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class HarryKartControllerTest {

    HarryKartSerializerService hkSerializer;
    ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        hkSerializer = new HarryKartSerializerService();
        mapper = new ObjectMapper();
    }

    /**
     *
     * @param filename XML filename to be read from /resources
     * @return
     */
    private String readFileToString(String filename) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
        Objects.requireNonNull(in);
        String xmlString = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            xmlString = br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
            return xmlString;
        }
        return xmlString;
    }

    /**
     * Lane 1 is first, Lane 2 is second, Lane 3 is third
     * @throws HarryKartException
     */
    @Test
    public void lanesFinishInOrderTest() throws HarryKartException {
        String inputXML = readFileToString("_LanesFinishInOrderTest.xml");
        HarryKart hk = hkSerializer.deserializeFromXML(inputXML);
        // Calculate the race results
        List<Rank> actualRanking = new HarryKartResultService(hk).getResults();
        // Expected race outcome
        ArrayList<Rank> expectedRanking = new ArrayList<>();
        expectedRanking.add(new Rank(1,"TIMETOBELUCKY", 0.0));
        expectedRanking.add(new Rank(2,"CARGO DOOR", 0.0));
        expectedRanking.add(new Rank(3,"HERCULES BOKO", 0.0));
        // Compare expected and actual JSON results
        String resultJson = hkSerializer.serializeToJson(actualRanking);
        String expectedJson = hkSerializer.serializeToJson(expectedRanking);
        assertEquals(resultJson, expectedJson);
    }

    /**
     * Two participants finish at the same time
     * @throws HarryKartException
     */
    @Test
    public void twoWayTieTest() throws HarryKartException {
        String inputXML = readFileToString("_TwoWayTieTest.xml");
        HarryKart hk = hkSerializer.deserializeFromXML(inputXML);
        // Calculate the race results
        List<Rank> actualRanking = new HarryKartResultService(hk).getResults();
        // Verify the finishing position of each participant
        assertEquals(actualRanking.get(0).getPosition(), 1);
        assertEquals(actualRanking.get(0).getHorse(),"WAIKIKI SILVIO");
        assertEquals(actualRanking.get(1).getPosition(), 2);
        assertEquals(actualRanking.get(1).getHorse(), "HERCULES BOKO");
        String thirdPlace = actualRanking.get(2).getHorse();
        assertEquals(actualRanking.get(2).getPosition(), 3);
        assertTrue(thirdPlace.equals("CARGO DOOR") || thirdPlace.equals("TIMETOBELUCKY"));
        String fourthPlace = actualRanking.get(3).getHorse();
        assertEquals(actualRanking.get(3).getPosition(), 3);
        assertTrue(fourthPlace.equals("CARGO DOOR") || fourthPlace.equals("TIMETOBELUCKY"));
    }

    /**
     * All participants finish at the same time
     * @throws HarryKartException
     */
    @Test
    public void allWayTieTest() throws HarryKartException {
        String inputXML = readFileToString("_AllWayTieTest.xml");
        HarryKart hk = hkSerializer.deserializeFromXML(inputXML);
        // Calculate the race results
        List<Rank> actualRanking = new HarryKartResultService(hk).getResults();
        // Verify that the finishing position of each participant is #1
        actualRanking.forEach(rank -> assertEquals(rank.getPosition(),1));
    }

    /**
     * Less than 4 participants doesn't make a race (it throws an exception)
     * @throws HarryKartException
     */
    @Test(expected=HarryKartException.class)
    public void minimumParticipantTest() throws HarryKartException {
        String inputXML = readFileToString("_MinimumNumberOfParticipants.xml");
        HarryKart hk = hkSerializer.deserializeFromXML(inputXML);
    }

    /**
     * XML file is not in a valid format: <numberOfLoops> does not match the actual number of loops listed
     * @throws HarryKartException
     */
    @Test(expected=HarryKartException.class)
    public void invalidXmlFormatTest() throws HarryKartException {
        String inputXML = readFileToString("_InvalidHarryKartFormatTest.xml");
        HarryKart hk = hkSerializer.deserializeFromXML(inputXML);
    }

    /**
     * When the base power is less than 1 on a loop, the participant hasn't completed the lap and is out of the race
     * @throws HarryKartException
     */
    @Test
    public void zeroAndNegativePowerTest() throws HarryKartException {
        String inputXML = readFileToString("_ZeroAndNegativePowerTest.xml");
        HarryKart hk = hkSerializer.deserializeFromXML(inputXML);
        List<Rank> actualRanking = new HarryKartResultService(hk).getResults();
        // Expected race outcome
        ArrayList<Rank> expectedRanking = new ArrayList<>();
        expectedRanking.add(new Rank(1,"WAIKIKI SILVIO", 0.0));
        expectedRanking.add(new Rank(2,"HERCULES BOKO", 0.0));
        // Compare expected and actual JSON results
        String resultJson = hkSerializer.serializeToJson(actualRanking);
        String expectedJson = hkSerializer.serializeToJson(expectedRanking);
        assertEquals(resultJson, expectedJson);
    }

}