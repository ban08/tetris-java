package model;

public interface BoardObserver {
    void onLineCleared(int linesCleared);
    void onGameOver();
}
