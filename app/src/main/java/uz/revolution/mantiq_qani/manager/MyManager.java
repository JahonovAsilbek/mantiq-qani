package uz.revolution.mantiq_qani.manager;


import uz.revolution.mantiq_qani.model.MyModel;

import java.util.ArrayList;

public class MyManager {
    private ArrayList<MyModel> questions;
    private int level = 0;
    private int totalScore=0;
    private final int deltaScore = 2;
    private final int maxScore = 10;
    private int currentScore=maxScore;



    public void setQuestions(ArrayList<MyModel> questions) {
        this.questions = questions;
    }



    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public MyModel getCurrentQuestion() {
        return questions.get(level);
    }


    public int getAnswerLength(){
        return getCurrentQuestion().getAnswer().length();
    }

    private String getAnswer() {
        return getCurrentQuestion().getAnswer();
    }

    public String getQuestion() {
        return getCurrentQuestion().getQuestion();
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

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }
}