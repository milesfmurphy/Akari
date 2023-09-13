package com.comp301.a09akari;

import com.comp301.a09akari.model.*;
import com.comp301.a09akari.view.AppLauncher;
import javafx.application.Application;

public class Main {
  public static void main(String[] args) {
    //Application.launch(AppLauncher.class);
    int[][] boardy = {{6,6,6},{6,4,6},{6,6,6}};
    Puzzle puzzledad = new PuzzleImpl(boardy);
    PuzzleLibraryImpl lib = new PuzzleLibraryImpl();
    lib.addPuzzle(puzzledad);
    ModelImpl modeler = new ModelImpl(lib);
    modeler.addLamp(0,1);
    modeler.addLamp(1,0);
    modeler.addLamp(1,2);
    modeler.addLamp(2,1);
    modeler.removeLamp(0,1);
    System.out.println(modeler.testLampList());
    System.out.println(modeler.isSolved());
  }
}
