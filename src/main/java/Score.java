public class Score {
    private int currentPoints;
    private final int pointsForBonus; // Pontos necessários para ativar o bônus
    private boolean bonusActive;

    public Score(int pointsForBonus) {
        this.currentPoints = 0;
        this.pointsForBonus = pointsForBonus;
        this.bonusActive = false;
    }

    public int getPoints() {
        return currentPoints;
    }

    public void addPoints(int points) {
        this.currentPoints += points;
        checkBonusActivation();
    }

    // Verifica se o bônus deve ser ativado
    private void checkBonusActivation() {
        if (currentPoints >= pointsForBonus && !bonusActive) {
            bonusActive = true;
            System.out.println("You hava one remaining bonus");
        }
    }

    public void useBonus(Board board) {
        if (bonusActive) {
            System.out.println("Bonus used");
            bonusActive = false; // Desativa o bônus após ser usado
        } else {
            System.out.println("Você não tem um bônus disponível!");
        }
    }

    public boolean isBonusActive() {
        return bonusActive;
    }

    public void reset() {
        currentPoints = 0;
        bonusActive = false;
    }
}