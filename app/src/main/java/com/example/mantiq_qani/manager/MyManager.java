package com.example.mantiq_qani.manager;


import com.example.mantiq_qani.model.MyModel;

import java.util.ArrayList;

public class MyManager {
    private ArrayList<MyModel> questions;
    private int level = 0;
    private int totalScore=0;
    private final int deltaScore = 2;
    private final int maxScore = 10;
    private int currentScore=maxScore;

    public MyManager(ArrayList<MyModel> questions) {
        this.questions = questions;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    private MyModel getCurrentQuestion() {
        return questions.get(level);
    }

    public ArrayList<Integer> getImages() {
        return getCurrentQuestion().getImages();
    }

    public String getVariants() {
        return getCurrentQuestion().getVariant();
    }

    public int getAnswerLength(){
        return getCurrentQuestion().getAnswer().length();
    }

    private String getAnswer() {
        return getCurrentQuestion().getAnswer();
    }

    public boolean checkAnswer(String userAnswer) {

        if (userAnswer.equalsIgnoreCase(getAnswer().trim().replaceAll(" ", ""))) {

            totalScore = totalScore + currentScore;
            currentScore = maxScore;
            level++;

            return true;
        } else {

            if (currentScore > deltaScore) {
                currentScore = currentScore - deltaScore;
            }
            return false;
        }

    }

    public boolean isEnd() {
        return level>= questions.size();
    }

    public boolean hasNext() {
        return level < questions.size();
    }

    public int getLevel() {
        return level;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }
}