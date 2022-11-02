package Gui;

import Application.Model.SimpeltSalg;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class DagsrapporterPane extends GridPane {

    private ControllerInterface controller;

    private ListView<SimpeltSalg> lWSalg = new ListView<>();

    public DagsrapporterPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        //this.add(lWSalg, 0, 0);
    }

    public void updateControls(){

    }
}
