package uz.revolution.mantiq_qani.model;

public class MyModel {
    private int _id;
    private String variant;
    private String question;
    private String answer;
    private String comment;
    private String source;
    private String author;

    public MyModel(int _id, String variant, String question, String answer, String comment, String source, String author) {
        this._id = _id;
        this.variant = variant;
        this.question = question;
        this.answer = answer;
        this.comment = comment;
        this.source = source;
        this.author = author;
    }

    public MyModel(String variant, String question, String answer, String comment, String source, String author) {
        this.variant = variant;
        this.question = question;
        this.answer = answer;
        this.comment = comment;
        this.source = source;
        this.author = author;
    }

    public MyModel(int _id, String question, String answer, String comment, String source, String author) {
        this._id = _id;
        this.question = question;
        this.answer = answer;
        this.comment = comment;
        this.source = source;
        this.author = author;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "MyModel{" +
                "_id=" + _id +
                ", variant='" + variant + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", comment='" + comment + '\'' +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}