package com.java.spring.ipldashboard.controller;

import com.java.spring.ipldashboard.model.Match;
import com.java.spring.ipldashboard.model.Team;
import com.java.spring.ipldashboard.repository.MatchRepository;
import com.java.spring.ipldashboard.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class TeamController {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping(value = "/team/{teamName}")
    public Team getTeam (@PathVariable String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        team.setMatches(matchRepository.findLatestMatchesByTeamName(teamName, 4));
        return team;
    }

    @GetMapping("/team/{teamName}/matches")
    public List<Match> getMatchesForTeam (@PathVariable String teamName, @RequestParam int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);
        return matchRepository.getMatchesByTeamBetweenDates(teamName,
                startDate,
                endDate);
    }

    @GetMapping("/teams")
    public Iterable<Team> getAllTeams () {
        return teamRepository.findAll();
    }
}
