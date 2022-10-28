package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class OpretUdlejningPane extends GridPane {

    private ControllerInterface controller;

    public OpretUdlejningPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);
    }

    public void updateControls(){

    }
}
