package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency>{
    private int numberOfEmptyTiles;
    private int score;

    public Move getMove() {
        return move;
    }

    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        if(numberOfEmptyTiles>o.numberOfEmptyTiles){
            return 1;
        } else if (numberOfEmptyTiles<o.numberOfEmptyTiles){
            return -1;
        } else if (numberOfEmptyTiles==o.numberOfEmptyTiles){
            if(score>o.score){
                return 1;
            } else if (score<o.score){
                return -1;
            }
        }
        return 0;
    }
}
