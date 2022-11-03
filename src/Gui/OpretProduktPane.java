package Gui;

import Application.Model.ProduktGruppe;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class OpretProduktPane extends GridPane {

    private ControllerInterface controller;
    private ListView lwProduktgrupper = new ListView<>();
    private ListView lwProdukter = new ListView<>();
    private TextField txfNavn, txfAntalEnheder, txfEnhed, txfBeskrivelse, txfOpretProduktgruppe;
    private Label produktError, produktGruppeError1, produktGruppeError2;


    public OpretProduktPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        //Oprettelse af elementer i række 1
        Label lblProduktgrupper = new Label("Vælg produktgruppe: ");
        Label lblEllerOpretNy = new Label("Eller opret ny produktgruppe: ");
        Label lblOpretProduktgruppe = new Label("Produktgruppe navn: ");
        txfOpretProduktgruppe = new TextField();
        Button btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> this.createProduktgruppeAction());


        //Tilføjelse af elementer i række 1
        this.add(lblProduktgrupper, 0, 0);
        this.add(lwProduktgrupper, 0, 1,2,9);
        this.add(lblEllerOpretNy, 0, 11);
        this.add(lblOpretProduktgruppe, 0, 12);
        this.add(txfOpretProduktgruppe,1,12);
        this.add(btnOpret, 0, 13);

        //Oprettelse af elementer i række 2
        Label lblOpretNytProdukt = new Label("Opret nyt produkt: ");

        Label lblNavn = new Label("Navn: ");
        txfNavn = new TextField();

        Label lblAntalEnheder = new Label("Antal enheder: ");
        txfAntalEnheder = new TextField();

        Label lblEnhed = new Label("Enhed: ");
        txfEnhed = new TextField();

        Label lblBeskrivelse = new Label("Beskrivelse: ");
        txfBeskrivelse = new TextField();

        Button btnTilføj = new Button("Tilføj");
        btnTilføj.setOnAction(event -> this.createProduktAction());

        Label lblProdukter = new Label("Produkter:");


        //Tilføjelse af elementer i række 2
        this.add(lblOpretNytProdukt, 2, 0);
        this.add(lblNavn, 2, 1);
        this.add(txfNavn, 3, 1,3,1);
        this.add(lblAntalEnheder, 2, 2);
        this.add(txfAntalEnheder, 3, 2);
        this.add(lblEnhed, 4, 2);
        this.add(txfEnhed, 5, 2);
        this.add(lblBeskrivelse, 2, 3);
        this.add(txfBeskrivelse, 3, 3,3,1);
        this.add(btnTilføj, 2, 4);

        this.add(lblProdukter, 2, 6);
        this.add(lwProdukter, 2, 7,4,3);

        //Vinduestørrelse preset
        this.setPrefHeight(600);
        this.setPrefWidth(1000);

        //Setter Listview Produktgrupper, skaber og tilføjer listener til listviewet
        lwProduktgrupper.getItems().setAll(controller.getProduktGrupper());
        ChangeListener<ProduktGruppe> listener = (ov, oldProduktGruppe, newProduktGruppe) -> this.selectedProduktGruppeChanged();
        lwProduktgrupper.getSelectionModel().selectedItemProperty().addListener(listener);
    }

    //
    public void updateControls(){
        ProduktGruppe produktGruppe = (ProduktGruppe) lwProduktgrupper.getSelectionModel().getSelectedItem();
        if (produktGruppe != null) {
            lwProdukter.getItems().setAll(controller.getProdukterFraProduktgruppe((ProduktGruppe) lwProduktgrupper.getSelectionModel().getSelectedItem()));
        }
    }

    public void selectedProduktGruppeChanged(){
        this.updateControls();
    }

    public void createProduktgruppeAction() {
        if (!txfOpretProduktgruppe.getText().equals("") && !dublet()){
            controller.createProduktGruppe(txfOpretProduktgruppe.getText());
            txfOpretProduktgruppe.clear();
            this.updateControls();
            if(produktGruppeError1 != null) {
                produktGruppeError1.setVisible(false);
            }
            if (produktGruppeError2 != null) {
                produktGruppeError2.setVisible(false);
            }
        } else if (dublet()){
            if (produktGruppeError2!=null){
                produktGruppeError2.setVisible(false);
            }
            produktGruppeError1 = new Label("Produktgruppe eksisterer!");
            produktGruppeError1.setTextFill(Color.color(1, 0, 0));
            this.add(produktGruppeError1, 1, 13);
        } else {
            if(produktGruppeError1!=null) {
                produktGruppeError1.setVisible(false);
            }
            produktGruppeError2 = new Label("Felt skal udfyldes!");
            produktGruppeError2.setTextFill(Color.color(1, 0, 0));
            this.add(produktGruppeError2, 1, 13);
        }
    }

    public void createProduktAction(){
        ProduktGruppe produktGruppe = (ProduktGruppe) lwProduktgrupper.getSelectionModel().getSelectedItem();
        if (!txfNavn.getText().equals("")){
            if (txfAntalEnheder.getText().isEmpty()) {
                controller.createSimpelProdukt(produktGruppe, txfNavn.getText(), txfBeskrivelse.getText(), 0, "");

            } else {
                controller.createSimpelProdukt(produktGruppe, txfNavn.getText(), txfBeskrivelse.getText(), Integer.parseInt(txfAntalEnheder.getText()), txfBeskrivelse.getText());
                txfEnhed.clear();
                txfNavn.clear();
                txfBeskrivelse.clear();
                lwProdukter.getItems().setAll(controller.getProdukterFraProduktgruppe((ProduktGruppe) lwProduktgrupper.getSelectionModel().getSelectedItem()));
                if (produktError != null) {
                    produktError.setVisible(false);
                }
            }
        } else {
            produktError = new Label("Produktnavn skal udfyldes!");
            produktError.setTextFill(Color.color(1, 0, 0));
            this.add(produktError, 7, 1);
        }
        updateControls();
    }

    public boolean dublet(){
        boolean res = false;
        for (ProduktGruppe pg : controller.getProduktGrupper()){
            if (txfOpretProduktgruppe.getText().equals(pg.getNavn())){
                res = true;
            }
        } return res;
    }
}
