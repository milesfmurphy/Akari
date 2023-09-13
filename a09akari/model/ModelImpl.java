package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
    private final PuzzleLibrary puzzleLibrary;
    private final Puzzle activePuzzle;
    private  List<Lamp> lampList;
    private int activePuzzleIndex = 0;
    private final List<ModelObserver> modelObserverList;
    public ModelImpl(PuzzleLibrary library) {
        this.puzzleLibrary = library;
        this.activePuzzle = puzzleLibrary.getPuzzle(activePuzzleIndex);
        this.lampList = new ArrayList<>();
        this.modelObserverList = new ArrayList<>();
    }
    @Override
    public void addLamp(int r, int c) {
        Lamp addLamp = new Lamp(r,c);
        if(r < 0 || c < 0 || r >= activePuzzle.getHeight() || c >= activePuzzle.getWidth()){
            throw new IndexOutOfBoundsException();
        }
        else if(getActivePuzzle().getCellType(r,c)!= CellType.CORRIDOR){
            throw new IllegalArgumentException();
        }
        if (!isLamp(r,c)) lampList.add(addLamp);
        notifyObservers();
    }

    @Override
    public void removeLamp(int r, int c) {
        if(r < 0 || c < 0 || r >= activePuzzle.getHeight() || c >= activePuzzle.getWidth()){
            throw new IndexOutOfBoundsException();
        }
        else if(getActivePuzzle().getCellType(r,c)!= CellType.CORRIDOR){
            throw new IllegalArgumentException();
        }
        for(Lamp lamp : lampList){
            if(lamp.equals(new Lamp(r, c))){
                lampList.remove(lamp);
                break;
            }
        }
        notifyObservers();
    }

    @Override
    public boolean isLit(int r, int c) {
        Puzzle current = getActivePuzzle();
        if(r < 0 || c >= current.getWidth() || r >= current.getHeight() || c < 0){
            throw new IndexOutOfBoundsException();
        }
        if(current.getCellType(r, c) != CellType.CORRIDOR){
            throw new IllegalArgumentException("AhAHAH");
        }
        if(isLamp(r, c)){
            return true;
        }
        for(int columnCheckLeft = 1; c - columnCheckLeft >= 0 && current.getCellType(r, c -columnCheckLeft) == CellType.CORRIDOR; columnCheckLeft++){
            if(isLamp(r, c - columnCheckLeft)){return true;}
        }
        for(int columnCheckRight = 1; c + columnCheckRight < current.getWidth() && current.getCellType(r, c +columnCheckRight) == CellType.CORRIDOR; columnCheckRight++){
            if(isLamp(r, c + columnCheckRight)){return true;}
        }
        for(int rowCheckDown = 1; r + rowCheckDown < current.getHeight() && current.getCellType(r +rowCheckDown, c) == CellType.CORRIDOR; rowCheckDown++){
            if(isLamp(r + rowCheckDown, c)){return true;}
        }
        for(int rowCheckUp = 1; r- rowCheckUp >= 0 && current.getCellType(r - rowCheckUp, c) == CellType.CORRIDOR;rowCheckUp++){
            if(isLamp(r - rowCheckUp, c)){return true;}
        }
        return false;
    }

    @Override
    public boolean isLamp(int r, int c) {
        Puzzle current = getActivePuzzle();
        if(r < 0 || c < 0 || r >= current.getHeight() || c >= current.getWidth()){
            throw new IndexOutOfBoundsException();
        }
        if(current.getCellType(r,c)!=CellType.CORRIDOR){
            throw new IllegalArgumentException();
        }
        for(Lamp lamp : lampList){
            if(lamp.equals(new Lamp(r,c))){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isLampIllegal(int r, int c) {
        Puzzle current = getActivePuzzle();
        if(r < 0 || c >= current.getWidth() || r >= current.getHeight() || c < 0){
            throw new IndexOutOfBoundsException();
        }
        if(isLamp(r, c)){
            for(int columnCheckLeft = 1; c - columnCheckLeft >= 0 && current.getCellType(r, c -columnCheckLeft) == CellType.CORRIDOR; columnCheckLeft++){
                if(isLamp(r, c - columnCheckLeft)){return true;}
            }
            for(int columnCheckRight = 1; c + columnCheckRight < current.getWidth() && current.getCellType(r, c +columnCheckRight) == CellType.CORRIDOR; columnCheckRight++){
                if(isLamp(r, c + columnCheckRight)){return true;}
            }
            for(int rowCheckDown = 1; r + rowCheckDown < current.getHeight() && current.getCellType(r +rowCheckDown, c) == CellType.CORRIDOR; rowCheckDown++){
                if(isLamp(r + rowCheckDown, c)){return true;}
            }
            for(int rowCheckUp = 1; r- rowCheckUp >= 0 && current.getCellType(r - rowCheckUp, c) == CellType.CORRIDOR;rowCheckUp++){
                if(isLamp(r - rowCheckUp, c)){return true;}
            }
            return false;
        }
        else throw new IllegalArgumentException("Not a lamp");
    }

    @Override
    public Puzzle getActivePuzzle() {
        return puzzleLibrary.getPuzzle(activePuzzleIndex);
    }

    @Override
    public int getActivePuzzleIndex() {
        return activePuzzleIndex;
    }

    @Override
    public void setActivePuzzleIndex(int index) {
        if(index >= puzzleLibrary.size()|| index < 0){
            throw new IllegalArgumentException();
        }
        activePuzzleIndex = index;
        resetPuzzle();
    }

    @Override
    public int getPuzzleLibrarySize() {
        return puzzleLibrary.size();
    }

    @Override
    public void resetPuzzle() {
        lampList.clear();
        notifyObservers();
    }

    @Override
    public boolean isSolved() {
        Puzzle current = getActivePuzzle();
        for (int r = 0; r < current.getHeight(); r++) {
            for (int c = 0; c < current.getWidth(); c++) {
                if (current.getCellType(r, c) == CellType.CORRIDOR) {
                    if (!isLit(r, c)) {
                        return false;
                    }
                    if (isLamp(r, c)) {
                        if (isLampIllegal(r, c)) {
                            return false;
                        }
                    }
                }
                if(current.getCellType(r, c) == CellType.CLUE) {
                    if (!isClueSatisfied(r, c)) {
                        return false;
                    }
                }
            }
        }
        notifyObservers();
        return true;
    }


    @Override
    public boolean isClueSatisfied(int r, int c) {
        Puzzle current = getActivePuzzle();
        if (r < 0 || c < 0 || r >= current.getHeight() || c >= current.getWidth()) {
            throw new IndexOutOfBoundsException();
        }
        if (current.getCellType(r, c) != CellType.CLUE) {
            throw new IllegalArgumentException();
        }
        int clueCounter = 0;
        int clue = current.getClue(r, c);
            if (r + 1 < current.getHeight() && isLamp(r + 1, c)) clueCounter++;
            if (r - 1 >= 0 && isLamp(r - 1, c)) clueCounter++;
            if (c + 1 < current.getWidth() && isLamp(r, c + 1)) clueCounter++;
            if (c - 1 >= 0 && isLamp(r, c - 1)) clueCounter++;
        return clueCounter == clue;
    }


    @Override
    public void addObserver(ModelObserver observer) {
        modelObserverList.add(observer);
    }

    @Override
    public void removeObserver(ModelObserver observer) {
        modelObserverList.remove(observer);
    }
    public void notifyObservers(){
        for(ModelObserver observer : modelObserverList){
            observer.update(this);
        }
    }

    public List<Lamp> testLampList(){
        return lampList;
    }
}
