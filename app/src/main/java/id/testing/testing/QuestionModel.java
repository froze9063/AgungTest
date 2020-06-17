package id.testing.testing;

public class QuestionModel {
    private String id;
    private Boolean isFirst;
    private String fromID;
    private int type;
    private String question;
    private Boolean isClicked;

    public QuestionModel(String id, Boolean isFirst, String fromID, int type, String question, Boolean isClicked) {
        this.id = id;
        this.isFirst = isFirst;
        this.fromID = fromID;
        this.type = type;
        this.question = question;
        this.isClicked = isClicked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getClicked() {
        return isClicked;
    }

    public void setClicked(Boolean clicked) {
        isClicked = clicked;
    }
}
