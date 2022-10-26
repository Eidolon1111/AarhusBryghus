package Gui;

import Application.Controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AarhusBryghusTabWindow extends Application {

    @Override
    public void init(){
        Controller.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Aarhus Bryghus");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }


    private void initContent(BorderPane pane){
            TabPane tabPane = new TabPane();
            this.initTabPane(tabPane);
            pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Opret Prisliste
        Tab tabOpretPris = new Tab("Opret Pris");
        tabPane.getTabs().add(tabOpretPris);

        OpretPrisPane opretPrisPane = new OpretPrisPane();
        tabOpretPris.setContent(opretPrisPane);
        tabOpretPris.setOnSelectionChanged(event -> opretPrisPane.updateControls());

        //Opret Produkt
        Tab tabOpretProdukt = new Tab("Opret Produkt");
        tabPane.getTabs().add(tabOpretProdukt);

        OpretProduktPane opretProduktPane = new OpretProduktPane();
        tabOpretProdukt.setContent(opretProduktPane);
        tabOpretProdukt.setOnSelectionChanged(event -> opretProduktPane.updateControls());


    }
}
