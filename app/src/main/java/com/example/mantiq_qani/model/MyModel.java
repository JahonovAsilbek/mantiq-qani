package com.example.mantiq_qani.model;

import java.util.ArrayList;

public class MyModel {
    private String variant;
    private String answer;
    private ArrayList<Integer> images;

    public MyModel(String variant, String answer, ArrayList<Integer> images) {
        this.variant = variant;
        this.answer = answer;
        this.images = images;
    }


    public String getVariant() {
        return variant;
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<Integer> getImages() {
        return images;
    }}