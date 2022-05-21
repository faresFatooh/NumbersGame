package ps.school.numbersgamefinalproject;

public class Question {
    private String[][] question;
    private int hiddenNumber;

    public Question(String[][] question, int hiddenNumber) {
        this.question = question;
        this.hiddenNumber = hiddenNumber;
    }

    public String[][] getQuestion() {
        return question;
    }

    public void setQuestion(String[][] question) {
        this.question = question;
    }

    public int getHiddenNumber() {
        return hiddenNumber;
    }

    public void setHiddenNumber(int hiddenNumber) {
        this.hiddenNumber = hiddenNumber;
    }
}