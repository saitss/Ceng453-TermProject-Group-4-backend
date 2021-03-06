package com.database.service;

import com.database.entity.ScoreWeek;
import com.database.entity.User;
import com.database.repository.ScoreWeekRepository;
import com.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for ScoreWeek Table
 */
@Service
public class ScoreWeekService {

    @Autowired
    private ScoreWeekRepository scoreWeekRepository;

    @Autowired
    private UserRepository userRepository;


    public ScoreWeek saveScore(ScoreWeek scoreWeek) {
        return scoreWeekRepository.save(scoreWeek);
    }

    /**
     * validate user and score
     * @param score score of the user
     * @param user   user
     */
    private void validateUserAndScore(ScoreWeek score , User user){

        Assert.notNull(user , "User is null.");
        Assert.notNull(score , "Score is null.");

    }

    /**
     *
     * @return score weeks
     */
    public List<ScoreWeek> getScores() {
        List<ScoreWeek> scores = scoreWeekRepository.findAll(Sort.by(Sort.Direction.DESC , "score"));

        return scores;
    }

    /**
     *
     * @param id id of the player
     * @return data base result
     */
    public ScoreWeek getScoreById(int id) {
        return scoreWeekRepository.findById(id).orElse(null);
    }

    /**
     *
     * @param scoreWeek score of the user in week
     * @param user   current user whose score will recorded to the table
     */
    public void assignUserToScore(ScoreWeek scoreWeek, User user) {

        scoreWeek.setUser(user);

    }

    /**
     * update scores
     * @return string in order to easy debug
     */

    public String updateScores() {
        List<ScoreWeek> scoreWeeks = scoreWeekRepository.findAll();
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

        for(ScoreWeek scoreWeek : scoreWeeks){
            if ((today.getTime() - scoreWeek.getSqlDate().getTime())/100 > (6048000)  ){
                scoreWeekRepository.deleteById(scoreWeek.getId());
            }
        }
        return "Week Table Updated";
    }

}

