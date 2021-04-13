package com.javarush.task.task35.task3513;

import java.util.*;

public class Model {
    private static final int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    int maxTile = 0;
    int score = 0;
    private Stack<Integer> previousScores = new Stack<>();
    private Stack<Tile[][]> previousStates = new Stack<>();
    private boolean isSaveNeeded = true;

    public Model() {

        resetGameTiles();
    }

    private void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int index = (int) (Math.random() * emptyTiles.size()) % emptyTiles.size();
            Tile emptyTile = emptyTiles.get(index);
            emptyTile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }

    private List<Tile> getEmptyTiles() {
        final List<Tile> list = new ArrayList<Tile>();
        for (Tile[] tileArray : gameTiles) {
            for (Tile t : tileArray)
                if (t.isEmpty()) {
                    list.add(t);
                }
        }
        return list;
    }

    void resetGameTiles() {
        gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    private boolean compressTiles(Tile[] tiles) {
        int insertPosition = 0;
        boolean madeChanges = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (!tiles[i].isEmpty()) {
                if (i != insertPosition) {
                    tiles[insertPosition] = tiles[i];
                    tiles[i] = new Tile();
                    madeChanges = true;
                }
                insertPosition++;
            }
        }
        return madeChanges;
    }

    private boolean mergeTiles(Tile[] tiles) {
        LinkedList<Tile> tilesList = new LinkedList<>();
        boolean madeChanges = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (tiles[i].isEmpty()) {
                continue;
            }

            if (i < FIELD_WIDTH - 1 && tiles[i].value == tiles[i + 1].value) {
                int updatedValue = tiles[i].value * 2;
                if (updatedValue > maxTile) {
                    maxTile = updatedValue;
                }
                score += updatedValue;
                tilesList.addLast(new Tile(updatedValue));
                tiles[i + 1].value = 0;
                madeChanges = true;
            } else {
                tilesList.addLast(new Tile(tiles[i].value));
            }
            tiles[i].value = 0;
        }

        for (int i = 0; i < tilesList.size(); i++) {
            tiles[i] = tilesList.get(i);
        }
        return madeChanges;
    }

    public void left() {
        if(isSaveNeeded){
            saveState(gameTiles);
        }
        boolean madeChanges = false;
        for (int i = 0; i < FIELD_WIDTH; i++) {
            if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
                madeChanges = true;
            }
        }
        if (madeChanges) {
            addTile();
        }
        isSaveNeeded=true;
    }

    public void right() {
        saveState(gameTiles);
        gameTiles = perevorot(gameTiles);
        gameTiles = perevorot(gameTiles);
        left();
        gameTiles = perevorot(gameTiles);
        gameTiles = perevorot(gameTiles);
    }

    public void up() {
        saveState(gameTiles);
        gameTiles = perevorot(gameTiles);
        gameTiles = perevorot(gameTiles);
        gameTiles = perevorot(gameTiles);
        left();
        gameTiles = perevorot(gameTiles);
    }

    public void down() {
        saveState(gameTiles);
        gameTiles = perevorot(gameTiles);
        left();
        gameTiles = perevorot(gameTiles);
        gameTiles = perevorot(gameTiles);
        gameTiles = perevorot(gameTiles);
    }

    private Tile[][] perevorot(Tile[][] tiles) {
        final int N = tiles.length;
        Tile[][] result = new Tile[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                result[c][N - 1 - r] = tiles[r][c];
            }
        }
        return result;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove() {
        if (getEmptyTiles().size() > 0) {
            return true;
        }
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                Tile tile = gameTiles[i][j];
                if (i < FIELD_WIDTH - 1 && (tile.value == gameTiles[i + 1][j].value)
                        || j < FIELD_WIDTH - 1 && (tile.value == gameTiles[i][j + 1].value)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void saveState(Tile[][] tiles) {
        Tile [][] temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i=0;i<FIELD_WIDTH;i++){
            for (int j=0;j<FIELD_WIDTH;j++){
                temp[i][j]=new Tile(tiles[i][j].value);
            }
        }
        previousStates.push(temp);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback() {
        if (!previousStates.empty() && !previousScores.empty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    public void randomMove(){
        int n = ((int) (Math.random() * 100)) % 4;
        switch(n){
            case 0: left();
            break;
            case 1: right();
            break;
            case 2: up();
            break;
            case 3: down();
            break;
        }
    }

    public boolean hasBoardChanged(){
        for(int i=0;i<FIELD_WIDTH;i++){
            for (int j=0;j<FIELD_WIDTH;j++){
                if(gameTiles[i][j].value!=previousStates.peek()[i][j].value){
                    return true;
                }
            }
        }
        return false;
    }

    public MoveEfficiency getMoveEfficiency(Move move){
        MoveEfficiency m = new MoveEfficiency(-1,0,move);
        move.move();
        if(hasBoardChanged()){
            m=new MoveEfficiency(getEmptyTiles().size(),score,move);
        }
        rollback();
        return m;
    }

    public void autoMove(){
        PriorityQueue<MoveEfficiency> t = new PriorityQueue<>(4,Collections.reverseOrder()) ;
        t.offer(getMoveEfficiency(this::left));
        t.offer(getMoveEfficiency(this::right));
        t.offer(getMoveEfficiency(this::down));
        t.offer(getMoveEfficiency(this::up));
        t.peek().getMove().move();
    }
}

