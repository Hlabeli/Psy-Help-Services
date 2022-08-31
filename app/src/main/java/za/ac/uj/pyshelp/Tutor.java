package za.ac.uj.pyshelp;

public class Tutor {
    String title, answer, ans2, ans3;

    public Tutor() {
    }


    public Tutor(String title, String answer, String ans2, String ans3) {
        this.title = title;
        this.answer = answer;
        this.ans2 = ans2;
        this.ans3= ans3;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }
}


