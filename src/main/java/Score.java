public class Score {
    private int score;
    private int bonus;

    public Score() {
        score = 0;
        bonus = 0;
    }

    public void addScore(int score){
        this.score += score;
    }

    public int getScore(){
        return score;
    }

    public void useBonus(){
        if (bonus > 0){
            bonus--;
        }
    }

    public int getBonus() {
        return bonus;
    }

    public void addBonus(){
        this.bonus += bonus;
    }
}
