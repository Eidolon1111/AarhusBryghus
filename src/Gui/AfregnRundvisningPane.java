package Gui;

import Application.Model.Salg;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;

public class AfregnRundvisningPane extends GridPane {

    private ListView lwAlleSalg = new ListView<Salg>();

    private ControllerInterface controller;

    public AfregnRundvisningPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        Button btnUpdate = new Button();
        btnUpdate.setOnAction(event -> updateControls());

        lwAlleSalg.getItems().setAll(controller.getSalg());
        this.add(btnUpdate, 0, 1);
        this.add(lwAlleSalg, 0, 2);
    }

    public void updateControls(){
        lwAlleSalg.getItems().setAll(controller.getSalg());
    }
}
