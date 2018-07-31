package Snake;

public class User {
    private String name;
    private int score;
    User(String tempName, int tempScore)
    {
        name = tempName;
        score = tempScore;
    }
    public String getName()
    {
        return name;
    }
    public int getScore()
    {
        return score;
    }
    public void setScore(int newScore)
    {
        score = newScore;
    }
}
