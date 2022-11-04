package Gui;

import Application.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.time.LocalDateTime;

public class OpretRundvisningPane extends GridPane {

    private TextField txfNavn, txfTlfNr, txfEmail, txfAntalPers,txfTime, txfMinut;
    private ListView lwKunder = new ListView<Kunde>();
    private Label lblKunder, lblEllerOpretNy, lblNavn, lblTlfNr, lblEmail, lblVælgDato, lblAntalPers, lblTime, lblMinut, lblPrisliste, lblRundvisningOprettet;
    private Label lblKundeError, lblRundvisningError, lblTimeError, lblMinutError; //Fejlbeskeder label
    private DatePicker datePicker = new DatePicker();
    private ComboBox<Pris> cbPrislister = new ComboBox<>();

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
        this.add(lwKunder, 0, 1,2,8);
        this.add(lblEllerOpretNy, 0, 9);
        this.add(lblNavn, 0, 10);
        this.add(txfNavn,1,10);
        this.add(lblTlfNr, 0, 11);
        this.add(txfTlfNr,1,11);
        this.add(lblEmail, 0, 12);
        this.add(txfEmail,1,12);
        this.add(btnOpret, 0, 13);

        //Oprettelse af elementer i række 1
        lblVælgDato = new Label("Vælg dato");
        lblAntalPers = new Label("Antal personer ");
        txfAntalPers = new TextField();
        lblTime = new Label("Time");
        txfTime = new TextField();
        lblMinut = new Label("Minut");
        txfMinut = new TextField();
        lblPrisliste = new Label("Vælg produkt");

        Button btnOpretRundvisning = new Button("Opret Rundvisning");
        btnOpretRundvisning.setOnAction(event -> opretRundvisningAction());

        //Tilføjelse af elementer i række 2
        this.add(lblVælgDato, 3, 0,2,1);
        this.add(datePicker, 3, 1);
        this.add(lblAntalPers, 3, 6);
        this.add(txfAntalPers, 3, 7);
        this.add(lblTime, 3, 2);
        this.add(txfTime, 3, 3);
        this.add(lblMinut,3,4);
        this.add(txfMinut, 3, 5);
        this.add(lblPrisliste, 3, 8);
        this.add(cbPrislister, 3, 9);
        this.add(btnOpretRundvisning, 3, 10);

        //Setter Listview Kunder samt tilføjer listener
        lwKunder.getItems().setAll(controller.getKunder());
        ChangeListener<Kunde> listener = (ov, o, v) -> this.selectedKundeChanged();
        lwKunder.getSelectionModel().selectedItemProperty().addListener(listener);

        //Setter muligheder på combobox prislister og tilføjer listener
        cbPrislister.getItems().setAll(controller.getPrisliste("Rundvisning").getPrislisten());
        ChangeListener<Pris> listenerCBPrislister = (ov, oldPrisliste, newPrisliste) -> this.selectedPrisChanged();
        cbPrislister.getSelectionModel().selectedItemProperty().addListener(listenerCBPrislister);
    }

    public void updateControls(){
        lwKunder.getItems().setAll(controller.getKunder());
        txfTlfNr.clear(); txfNavn.clear(); txfEmail.clear(); txfAntalPers.clear(); txfTime.clear(); txfMinut.clear();
        datePicker.getEditor().clear();
    }

    public void selectedKundeChanged() {
        txfTlfNr.clear(); txfNavn.clear(); txfEmail.clear(); txfAntalPers.clear(); txfTime.clear(); txfMinut.clear();
        datePicker.getEditor().clear();
    }

    public void selectedPrisChanged() { }

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

    public void opretRundvisningAction() {
        Kunde kunde = (Kunde) lwKunder.getSelectionModel().getSelectedItem();
        LocalDateTime tidspunkt = null;
        int time = Integer.parseInt(txfTime.getText());
        int minut = Integer.parseInt(txfMinut.getText());
        if (datePicker != null && time>=0 && time<24 && minut>=0 && minut<60) {
            tidspunkt = LocalDateTime.from(
                    datePicker.getValue().atTime(Integer.parseInt(txfTime.getText()),
                            Integer.parseInt(txfMinut.getText())));

            Pris pris = cbPrislister.getSelectionModel().getSelectedItem();
            if (kunde != null && tidspunkt != null && !txfAntalPers.getText().isBlank() && pris != null) {
                controller.createRundvisning(kunde,tidspunkt,pris, Integer.parseInt(txfAntalPers.getText()));
                this.updateControls();
                if(lblRundvisningError != null) {
                    lblRundvisningError.setVisible(false);
                }
                lblRundvisningOprettet = new Label("Rundvisning oprettet");
                lblRundvisningOprettet.setTextFill(Color.color(0, 1, 0));
                this.add(lblRundvisningOprettet, 4, 10);

                if ( (lblMinutError != null)){ lblMinutError.setVisible(false); } if (lblTimeError != null){ lblTimeError.setVisible(false); }
            } else {
                if (lblRundvisningOprettet != null){
                    lblRundvisningOprettet.setVisible(false);
                }
                lblRundvisningError = new Label("Alle felter skal udfyldes!");
                lblRundvisningError.setTextFill(Color.color(1, 0, 0));
                this.add(lblRundvisningError, 4, 10);
            }
        } else if (time<0 || time > 23){
            if ( (lblMinutError != null)){
                lblMinutError.setVisible(false);
            }
            lblTimeError = new Label("Time ugyldig!");
            lblTimeError.setTextFill(Color.color(1, 0, 0));
            this.add(lblTimeError, 4, 3);
        } else if (minut< 0 || minut >59){
            if (lblTimeError != null){
                lblTimeError.setVisible(false);
            }
            lblMinutError = new Label("Minut ugyldig!");
            lblMinutError.setTextFill(Color.color(1, 0, 0));
            this.add(lblMinutError, 4, 5);
        }
    }
}
