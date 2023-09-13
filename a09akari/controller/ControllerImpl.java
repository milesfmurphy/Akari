package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

import java.util.Random;

public class ControllerImpl implements AlternateMvcController{
    Model model;
    public ControllerImpl(Model model) {
        this.model = model;
        // Constructor code goes here
    }
    @Override
    public void clickNextPuzzle() {
        if (model.getActivePuzzleIndex() == model.getPuzzleLibrarySize() - 1) model.setActivePuzzleIndex(0);
        else model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
        model.resetPuzzle();
    }

    @Override
    public void clickPrevPuzzle() {
        if (model.getActivePuzzleIndex() == 0) model.setActivePuzzleIndex((model.getPuzzleLibrarySize() - 1));
        else model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
        model.resetPuzzle();
    }

    @Override
    public void clickRandPuzzle() {
        Random random = new Random();
        model.setActivePuzzleIndex(random.nextInt(model.getPuzzleLibrarySize()));
        model.resetPuzzle();
    }

    @Override
    public void clickResetPuzzle() {
        model.resetPuzzle();
    }

    @Override
    public void clickCell(int r, int c) {
        if(model.isLamp(r,c)){
            model.removeLamp(r,c);
        }
        else{
            model.addLamp(r,c);
        }
    }

    @Override
    public boolean isLit(int r, int c) {
        return model.isLit(r,c);
    }

    @Override
    public boolean isLamp(int r, int c) {
        return model.isLamp(r,c);
    }

    @Override
    public boolean isClueSatisfied(int r, int c) {
        return model.isClueSatisfied(r,c);
    }

    @Override
    public boolean isSolved() {
        return model.isSolved();
    }

    @Override
    public Puzzle getActivePuzzle() {
        return model.getActivePuzzle();
    }
}
