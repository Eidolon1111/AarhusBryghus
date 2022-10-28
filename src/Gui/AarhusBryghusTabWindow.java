package Gui;

import Application.Controller.Controller;
import Storage.Storage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AarhusBryghusTabWindow extends Application {

    private ControllerInterface controller;

    @Override
    public void init(){
        Storage storage = new Storage();
        controller = new Controller(storage);
        controller.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Aarhus Bryghus");
        BorderPane pane = new BorderPane();
        this.initContent(pane);


        stage.getIcons().add(new Image("Gui/Aarhus Bryghus.png"));
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
        tabPane.setPrefWidth(1010);
        tabPane.setPrefHeight(500);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //OpretDagligtSalg
        Tab tabOpretDagligtSalg = new Tab("Opret DagligtSalg");
        tabPane.getTabs().add(tabOpretDagligtSalg);

        OpretDagligtSalgPane opretDagligtSalgPane = new OpretDagligtSalgPane(controller);
        tabOpretDagligtSalg.setContent(opretDagligtSalgPane);
        tabOpretDagligtSalg.setOnSelectionChanged(event -> opretDagligtSalgPane.updateControls());

        //Opret Prisliste
        Tab tabOpretPris = new Tab("Opret Pris");
        tabPane.getTabs().add(tabOpretPris);

        OpretPrisPane opretPrisPane = new OpretPrisPane(controller);
        tabOpretPris.setContent(opretPrisPane);
        tabOpretPris.setOnSelectionChanged(event -> opretPrisPane.updateControls());

        //Opret Produkt
        Tab tabOpretProdukt = new Tab("Opret Produkt");
        tabPane.getTabs().add(tabOpretProdukt);

        OpretProduktPane opretProduktPane = new OpretProduktPane(controller);
        tabOpretProdukt.setContent(opretProduktPane);
        tabOpretProdukt.setOnSelectionChanged(event -> opretProduktPane.updateControls());


    }
}
