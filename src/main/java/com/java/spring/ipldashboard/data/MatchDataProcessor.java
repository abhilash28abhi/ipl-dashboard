package com.java.spring.ipldashboard.data;

import com.java.spring.ipldashboard.model.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

/**
 * Class which does batching processing of the input CSV file data.
 * The process method is called for each line of the CSV file
 */
@Slf4j
public class MatchDataProcessor implements ItemProcessor<MatchDataInput, Match> {


    @Override
    public Match process(MatchDataInput matchDataInput) throws Exception {
        Match match = new Match();

        match.setId(Long.parseLong(matchDataInput.getId()));
        match.setCity(matchDataInput.getCity());
        match.setDate(LocalDate.parse(matchDataInput.getDate()));
        match.setPlayerOfMatch(matchDataInput.getPlayer_of_match());
        match.setVenue(matchDataInput.getVenue());

        String firstIningsTeam, secondIningsTeam;
        if ("bat".equalsIgnoreCase(matchDataInput.getToss_decision())) {
            firstIningsTeam = matchDataInput.getToss_winner();
            secondIningsTeam = matchDataInput.getToss_winner()
                    .equals(matchDataInput.getTeam1()) ? matchDataInput.getTeam2() : matchDataInput.getTeam1();
        } else {
            secondIningsTeam = matchDataInput.getToss_winner();
            firstIningsTeam = matchDataInput.getToss_winner()
                    .equals(matchDataInput.getTeam1()) ? matchDataInput.getTeam2() : matchDataInput.getTeam1();
        }
        match.setTeam1(firstIningsTeam);
        match.setTeam2(secondIningsTeam);
        match.setTossWinner(matchDataInput.getToss_winner());
        match.setMatchWinner(matchDataInput.getWinner());
        match.setTossDecision(matchDataInput.getToss_decision());
        match.setResult(matchDataInput.getResult());
        match.setResultMargin(matchDataInput.getResult_margin());
        match.setUmpire1(matchDataInput.getUmpire1());
        match.setUmpire2(matchDataInput.getUmpire2());
        return match;
    }
}
