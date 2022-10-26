package Gui;

import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class OpretPrislisteWindow extends GridPane {

    private ListView lwPrislister = new ListView<>();

    public OpretPrislisteWindow(){
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);

        this.add(lwPrislister, 0, 0);

    }

    public void updateControls(){

    }

}
