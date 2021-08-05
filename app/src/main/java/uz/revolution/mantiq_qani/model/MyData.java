package uz.revolution.mantiq_qani.model;

public class MyData {
    private String name;
    private String score;
    private String time;
    private String statusLevel;

    public MyData(String name, String score, String time, String statusLevel) {
        this.name = name;
        this.score = score;
        this.time = time;
        this.statusLevel = statusLevel;
    }

    public MyData(String name, String score, String time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatusLevel() {
        return statusLevel;
    }

    public void setStatusLevel(String statusLevel) {
        this.statusLevel = statusLevel;
    }
}
