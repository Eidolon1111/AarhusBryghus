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
        Storage storage = Storage.getInstance();
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
        tabPane.setPrefWidth(1050);
        tabPane.setPrefHeight(500);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //OpretDagligtSalg
        Tab tabOpretDagligtSalg = new Tab("Opret DagligtSalg");
        tabPane.getTabs().add(tabOpretDagligtSalg);

        OpretDagligtSalgPane opretDagligtSalgPane = new OpretDagligtSalgPane(controller);
        tabOpretDagligtSalg.setContent(opretDagligtSalgPane);
        tabOpretDagligtSalg.setOnSelectionChanged(event -> opretDagligtSalgPane.updateControls());

        //Opret Rundvisning
        Tab tabOpretRundvisning = new Tab("Opret Rundvisning");
        tabPane.getTabs().add(tabOpretRundvisning);

        OpretRundvisningPane opretRundvisningPane = new OpretRundvisningPane(controller);
        tabOpretRundvisning.setContent(opretRundvisningPane);
        tabOpretRundvisning.setOnSelectionChanged(event -> opretRundvisningPane.updateControls());

        //Afregn Rundvisning
        Tab tabAfregnRundvisning = new Tab("Afregn Rundvisning");
        tabPane.getTabs().add(tabAfregnRundvisning);

        AfregnRundvisningPane afregnRundvisningPane = new AfregnRundvisningPane(controller);
        tabAfregnRundvisning.setContent(afregnRundvisningPane);
        tabAfregnRundvisning.setOnSelectionChanged(event -> afregnRundvisningPane.updateControls());

        //Opret Udlejning
        Tab tabOpretUdlejning = new Tab("Opret Udlejning");
        tabPane.getTabs().add(tabOpretUdlejning);

        OpretUdlejningPane opretUdlejningPane = new OpretUdlejningPane(controller);
        tabOpretUdlejning.setContent(opretUdlejningPane);
        tabOpretUdlejning.setOnSelectionChanged(event -> opretUdlejningPane.updateControls());

        //Afregn Udjelning
        Tab tabAfregnUdlejning = new Tab("Afregn Udlejning");
        tabPane.getTabs().add(tabAfregnUdlejning);

        AfregnUdlejningPane afregnUdlejningPane = new AfregnUdlejningPane(controller);
        tabAfregnUdlejning.setContent(afregnUdlejningPane);
        tabAfregnUdlejning.setOnSelectionChanged(event -> afregnUdlejningPane.updateControls());

        //Opret Produkt
        Tab tabOpretProdukt = new Tab("Opret ProduktGruppe/ Produkt");
        tabPane.getTabs().add(tabOpretProdukt);

        OpretProduktPane opretProduktPane = new OpretProduktPane(controller);
        tabOpretProdukt.setContent(opretProduktPane);
        tabOpretProdukt.setOnSelectionChanged(event -> opretProduktPane.updateControls());

        //Opret Prisliste
        Tab tabOpretPris = new Tab("Opret Prisliste/ Pris");
        tabPane.getTabs().add(tabOpretPris);

        OpretPrisPane opretPrisPane = new OpretPrisPane(controller);
        tabOpretPris.setContent(opretPrisPane);
        tabOpretPris.setOnSelectionChanged(event -> opretPrisPane.updateControls());

        //Dagsrapport
        Tab tabDagsrapporter = new Tab("Dagsrapporter");
        tabPane.getTabs().add(tabDagsrapporter);

        DagsrapporterPane dagsrapporterPane = new DagsrapporterPane(controller);
        tabDagsrapporter.setContent(dagsrapporterPane);
        tabDagsrapporter.setOnSelectionChanged(event -> dagsrapporterPane.updateControls());
    }
}
