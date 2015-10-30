package group7.travelomania;

/**
 * Created by thben_000 on 10/30/2015.
 */
public class Question {
    public String question;
    public String answer;
    public String[] wrong_answers;

    public Question(String question, String answer, String[] wrong_ans){
        this.question = question;
        this.answer = answer;
        this.wrong_answers = wrong_ans.clone();
    }

    public boolean isCorrect(String ans){
        return ans.equals(answer);
    }
}
