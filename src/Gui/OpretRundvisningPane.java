package Gui;

import Application.Model.KomplekstSalg;
import Application.Model.Kunde;
import Application.Model.Produkt;
import Application.Model.ProduktGruppe;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OpretRundvisningPane extends GridPane {

    private TextField txfNavn, txfTlfNr, txfEmail, txfAntalPers;
    private ListView lwKunder = new ListView<Kunde>();
    private Label lblKunder, lblEllerOpretNy, lblNavn, lblTlfNr, lblEmail, lblVælgDato, lblAntalPers;
    private Label lblKundeError; //Fejlbesked label
    private DatePicker datePicker = new DatePicker();

    private ControllerInterface controller;

    public OpretRundvisningPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        //Oprettelse af elementer i række 1
        lblKunder = new Label("Vælg kunde:");
        lblEllerOpretNy = new Label("Eller opret ny kunde: ");
        lblNavn = new Label("Navn: ");
        lblTlfNr = new Label("Tlf nr: ");
        lblEmail = new Label("Email: ");
        Button btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> this.opretKundeAction());
        txfNavn = new TextField();
        txfEmail = new TextField();
        txfTlfNr = new TextField();

        //Tiføjelse af elementer i række 2
        this.add(lblKunder, 0, 0);
        this.add(lwKunder, 0, 1,2,9);
        this.add(lblEllerOpretNy, 0, 11);
        this.add(lblNavn, 0, 12);
        this.add(txfNavn,1,12);
        this.add(lblTlfNr, 0, 13);
        this.add(txfTlfNr,1,13);
        this.add(lblEmail, 0, 14);
        this.add(txfEmail,1,14);
        this.add(btnOpret, 0, 15);

        //Oprettelse af elementer i række 1
        lblVælgDato = new Label("Vælg dato");
        lblAntalPers = new Label("Antal personer: ");
        txfAntalPers = new TextField();
        Button btnOpretRundvisning = new Button("Opret Rundvisning");

        //Tilføjelse af elementer i række 2
        this.add(lblVælgDato, 3, 0,2,1);
        this.add(datePicker, 3, 1);
        this.add(lblAntalPers, 3, 2);
        this.add(txfAntalPers, 3, 3);
        this.add(btnOpretRundvisning, 3, 5);

        //Setter Listview Kunder samt tilføjer listener
        lwKunder.getItems().setAll(controller.getKunder());
        ChangeListener<Kunde> listener = (ov, o, v) -> this.selectedKundeChanged();
        lwKunder.getSelectionModel().selectedItemProperty().addListener(listener);
    }

    public void updateControls(){
        lwKunder.getItems().setAll(controller.getKunder());
        txfTlfNr.clear(); txfNavn.clear(); txfEmail.clear();
    }

    public void selectedKundeChanged() { this.updateControls(); }

    public void opretKundeAction() {
        if (!txfNavn.getText().equals("") && !txfTlfNr.getText().equals("") && !txfEmail.getText().equals("")){
            controller.createKunde(txfNavn.getText(), txfTlfNr.getText(), txfEmail.getText());
            this.updateControls();
            if(lblKundeError != null) {
                lblKundeError.setVisible(false);
            }
        } else{
            lblKundeError = new Label("Alle felter skal udfyldes!");
            lblKundeError.setTextFill(Color.color(1, 0, 0));
            this.add(lblKundeError, 3, 12);
        }
    }

//    public void opretRundvisningAction() {
//        Kunde kunde = (Kunde) lwKunder.getSelectionModel().getSelectedItem();
//        LocalDateTime tidspunkt = LocalDateTime.from(datePicker.getValue());
//        if (kunde != null && tidspunkt != null && !txfAntalPers.equals("")) {
//            KomplekstSalg ks = controller.createKompleksSalg(kunde);
//
//        }
//    }
}
