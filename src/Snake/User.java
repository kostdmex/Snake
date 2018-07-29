package Snake;

class User {
    private String name;
    private int score;
    User(String tempName, int tempScore)
    {
        name = tempName;
        score = tempScore;
    }
    String getName()
    {
        return name;
    }
    int getScore()
    {
        return score;
    }void setScore(int newScore)
    {
        score = newScore;
    }
}
