package com.java.spring.ipldashboard.repository;

import com.java.spring.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    default List<Match> findLatestMatchesByTeamName(String teamName, int count) {
        Pageable pageable = PageRequest.of(0, count);
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);
    }

    public List<Match> getByTeam1OrTeam2OrderByDateDesc (String teamName1, String teamName2, Pageable pageable);

//    public List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(String teamName1,
//            LocalDate date1,
//            LocalDate date2,
//            String teamName2,
//            LocalDate date3,
//            LocalDate date4);

    @Query("select m from Match m where (m.team1=:teamName or m.team2=:teamName) and (m.date between :startDate and :endDate) "
            + "order by date desc")
    List<Match> getMatchesByTeamBetweenDates (@Param("teamName") String teamName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
