package se.atg.service.harrykart.services;

import se.atg.service.harrykart.model.HarryKart;
import se.atg.service.harrykart.model.Rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Class to handle Harry Kart race result calculation and display
 */
public class HarryKartResultService {
    private static final double TRACK_LENGTH = 1000.0;
    HarryKart race;

    /**
     * The race to be ranked
     * @param race  The Harry Kart race to be tabulated
     */
    public HarryKartResultService(HarryKart race) {
        this.race = race;
    }

    /**
     * Calculate the rank of the race participants
     * @return List<Rank>   List of participants ranked by their order of race completion
     */
    public List<Rank> getResults() {
        ArrayList<Rank> standings = new ArrayList<>();

        /*
        For each participant
            1) Go through each race loop
            2) Get the lane where lane.number = participant.lane
            3) baseSpeed += lane.power. baseSpeed <= 0 means the lap is not run and the horse is out of the race.
            4) time += TRACK_LENGTH / baseSpeed
        */
        race.getStartList().forEach(participant -> {
            Rank rank = new Rank(0, participant.getName(), TRACK_LENGTH/participant.getBaseSpeed());
            race.getPowerUps().stream()
                    .forEach(loop -> loop.getLanes().stream()
                            .filter(lane -> lane.getNumber() == participant.getLane()).
                                    forEach(lane -> {
                                        int loopSpeed = participant.getBaseSpeed() + lane.getPowerValue();
                                        if(loopSpeed <= 0){
                                            rank.setTime(rank.getTime() + Double.MAX_VALUE); // Time set to the highest possible value. Horse is last place.
                                        } else {
                                            participant.setBaseSpeed(participant.getBaseSpeed() + lane.getPowerValue());
                                            rank.setTime(rank.getTime() + (TRACK_LENGTH/participant.getBaseSpeed()) );
                                        }
                                    }));
            standings.add(rank); // Add rank to collection
        });

        Collections.sort(standings); // Sort with lowest times first
        standings.removeIf(rank -> rank.getTime() >= Double.MAX_VALUE); // Remove participants who do not complete the race

        // Rank each participant by time. Equal times have the same rank.
        int finalPlacement = 1;
        standings.get(0).setPosition(finalPlacement);
        for(int positionIndex = 1; positionIndex < standings.size(); positionIndex++){
            if(standings.get(positionIndex).getTime() == standings.get(positionIndex-1).getTime()){
                standings.get(positionIndex).setPosition(finalPlacement);
            }else{
                standings.get(positionIndex).setPosition(++finalPlacement);
            }
        }
        // Disregard lower than 3rd place.
        return standings.stream().filter(rank -> rank.getPosition() <= 3).collect(toList());
    }

}
