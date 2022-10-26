package Gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class OpretProduktPane extends GridPane {

    public OpretProduktPane(){
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setPrefHeight(400);
        this.setPrefWidth(800);
    }

    public void updateControls(){

    }
}
