package com.java.spring.ipldashboard.data;

import com.java.spring.ipldashboard.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JobCompletionNotificationListner extends JobExecutionListenerSupport {

    private final EntityManager entityManager;

    @Autowired public JobCompletionNotificationListner(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            Map<String, Team> teamData = new HashMap<>();

            entityManager.createQuery("select distinct m.team1, count(*) from Match m group by m.team1", Object[].class)
                    .getResultList().stream()
                    .map(t -> new Team((String)t[0], (long)t[1]))
                    .forEach(team -> teamData.put(((Team) team).getTeamName(), team));

            entityManager.createQuery("select distinct m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultList().stream()
                    .forEach(e -> {
                        Team team = teamData.get((String)e[0]);
                        team.setTotalMatches(team.getTotalMatches() + (long)e[1]);
                    });

            entityManager.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
                    .getResultList().stream()
                    .forEach(e -> {
                       Team team = teamData.get((String) e[0]);
                       if (team != null) team.setTotalWins((long)e[1]);
                    });

            teamData.values()
                    .forEach(e -> entityManager.persist(e));
            /*teamData.values()
                    .forEach(System.out::println);*/
        }
    }

}