package com.database.controller;

import com.database.entity.ScoreWeek;
import com.database.entity.User;
import com.database.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for ScoreWeek table
 */
@RestController
@Api(value = "ScoreWeek table controller documentation")
public class ScoreWeekController {

    @Autowired
    private ScoreWeekService service;

    /**
     * Add row to the ScoreWeek table. User id refers to User entity
     * @param ScoreWeek ScoreWeek entity which is going to be added to the database
     * @param user User entity who has the score
     * @return
     */
    @ApiOperation(value = "New Score adding method")
    @PostMapping("/addScoreWeek")
    public ScoreWeek addScore(@RequestBody ScoreWeek ScoreWeek, User user) {
        return service.saveScore(ScoreWeek, user);
    }

    /**
     *
     * @return All the scores from ScoreWeek table.
     */
    @ApiOperation(value = "Getting all the scores method")
    @GetMapping("/ScoresWeek")
    public List<ScoreWeek> findAllScores() {
        return service.getScores();
    }

    /**
     *
     * @param id id of the wanted user
     * @return User with the given id
     */
    @ApiOperation(value = "Getting score by id method")
    @GetMapping("/ScoreWeekById/{id}")
    public ScoreWeek findScoreById(@PathVariable int id) {
        return service.getScoreById(id);
    }

    /**
     * It updates the ScoreWeek table so that it only holds the entities of the last 28 day
     * @return
     */
    @ApiOperation(value = "Updating weekly score table so that it is only contains scores of the last 7 day")
    @DeleteMapping("ScoreWeek/Update/")
    public String updateScores() {
        return service.updateScores();
    }


}
