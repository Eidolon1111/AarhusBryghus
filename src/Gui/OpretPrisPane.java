package Gui;

import Application.Model.Pris;
import Application.Model.Prisliste;
import Application.Model.Produkt;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class OpretPrisPane extends GridPane {

    private ControllerInterface controller;
    private ListView lwPrislister = new ListView<>();
    private ListView lwProdukter = new ListView<>();
    private ListView lwValgtPrisliste = new ListView<>();
    private TextField txfOpretPrisliste = new TextField();
    private Label lblError = new Label();


    public OpretPrisPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //Oprettelse af elementer i række 1
        Label lblPrislister = new Label("Vælg prisliste: ");
        Label lblEllerOpretNy = new Label("Eller opret ny prisliste: ");
        Label lblOpretPrisListe = new Label("Prisliste navn: ");
        Button btnOpret = new Button("Opret");


        //Tilføjelse af elementer i række 1
        this.add(lblPrislister, 0, 0);
        this.add(lwPrislister, 0, 1,2,16);
        this.add(lblEllerOpretNy, 0, 18);
        this.add(lblOpretPrisListe, 0, 19);
        this.add(txfOpretPrisliste,1,19);
        this.add(btnOpret, 0, 20);
        this.add(lblError, 1, 20);
        lblError.setStyle("-fx-text-fill: red");


        //Oprettelse af elementer i række 2
        Label lblProdukter = new Label("Produkter:");

        //Tilføjelse af elementer i række 2
        this.add(lblProdukter, 2, 0);
        this.add(lwProdukter, 2, 1,2,16);

        //Oprettelse af elementer i række 3
        Button btnTilføj = new Button("Tilføj/Gem");
//
//        Label lblNuDkk = new Label("Nuværende DKK: ");
//        TextField txfNuDkk = new TextField();
//        txfNuDkk.setEditable(false);

        Label lblDkkPris = new Label("DKK pris: ");
        TextField txfDkkPris = new TextField();

//        Label lblNuKlip = new Label("Nuværende klip: ");
//        TextField txfNuKlip = new TextField();
//        txfNuKlip.setEditable(false);

        Label lblKlipPris = new Label("Klip pris: ");
        TextField txfKlipPris = new TextField();


        //Tilføjelse af elementer i række 3
//        this.add(lblNuDkk, 4, 4);
//        this.add(txfNuDkk, 5, 4);
        this.add(lblDkkPris, 4, 5);
        this.add(txfDkkPris, 5, 5);
//        this.add(lblNuKlip, 4, 6);
//        this.add(txfNuKlip, 5, 6);
        this.add(lblKlipPris, 4, 6);
        this.add(txfKlipPris, 5, 6);

        this.add(btnTilføj, 5, 7,2,1);

        //Oprettelse af elementer i række 4
        Label lblValgtPrisliste = new Label("Valgt prisliste: ");
        Button btnFjern = new Button("Fjern");

        //Tilføjelse af elementer i række 4
        this.add(lblValgtPrisliste, 6, 0);
        this.add(lwValgtPrisliste, 6, 1,2,16);
        this.add(btnFjern, 6, 17);
        
        //Vinduestørrelse preset
        this.setPrefHeight(400);
        this.setPrefWidth(1000);
        
        //Indsat data i listviews
        lwPrislister.getItems().setAll(controller.getPrislister());
        ChangeListener<Prisliste> prislisteListener = (ov, o, n) -> PrislisteItemSelected();
        lwPrislister.getSelectionModel().selectedItemProperty().addListener(prislisteListener);

        lwProdukter.getItems().setAll(controller.getProdukter());
        //ChangeListener<Produkt> produktListener = (ov, o, n) -> ProduktItemSelected();
        //lwPrislister.getSelectionModel().selectedItemProperty().addListener(produktListener);

        //Buttons funktionalitet
        btnOpret.setOnAction(event -> this.opretAction());
        btnTilføj.setOnAction(event -> this.tilføjAction());
        btnFjern.setOnAction(event -> this.fjernAction());
    }

    public void opretAction() {
        String navn = txfOpretPrisliste.getText().trim();
        if (navn.length() == 0) {
            lblError.setText("Navn er tom");
        } else {
            controller.createPrisliste(navn);
            txfOpretPrisliste.clear();
            lwPrislister.getItems().setAll(controller.getPrislister());
        }
    }

    public void tilføjAction() {

    }

    public void fjernAction() {

    }

    public void updateControls(){
        Prisliste pl = (Prisliste) lwPrislister.getSelectionModel().getSelectedItem();
        if (pl != null) {
            lwValgtPrisliste.getItems().setAll(pl.getPrislisten());
        }
    }

    public void PrislisteItemSelected() {
        this.updateControls();
    }

}
