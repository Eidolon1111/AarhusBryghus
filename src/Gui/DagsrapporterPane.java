package Gui;

import Application.Model.Salg;
import Application.Model.Salgslinje;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class DagsrapporterPane extends GridPane {

    private ControllerInterface controller;
    private DatePicker datePicker;
    private ListView<Salg> lwDagensSalg;
    private ListView<Salgslinje> lwSalgslinjer;
    private TextField txfAntal, txfDagsomsætning;
    private DatePicker datePickerFra, datePickerTil;
    private TextField txfSolgteKlip, txfBrugteKlip;

    public DagsrapporterPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        Label lblDagsrapport = new Label("DAGSRAPPORT");
        this.add(lblDagsrapport, 0, 0);

        Label lblDato = new Label("Vælg dato:");
        this.add(lblDato, 0, 1);

        datePicker = new DatePicker();
        this.add(datePicker, 0, 2, 2, 1);
        datePicker.setValue(LocalDate.now());
        datePicker.setOnAction(event -> updateControls());

        Label lblDagensSalg = new Label("Dagens Salg:");
        this.add(lblDagensSalg, 0, 3);
        lwDagensSalg = new ListView<>();
        this.add(lwDagensSalg, 0, 4, 2, 10);
        ChangeListener<Salg> salgListener = (ov, o, n) -> salgItemSelected();
        lwDagensSalg.getSelectionModel().selectedItemProperty().addListener(salgListener);

        Label lblAntal = new Label("Antal salg:");
        this.add(lblAntal, 0, 15);
        txfAntal = new TextField();
        this.add(txfAntal, 1, 15);

        Label lblDagsomsætning = new Label("Dagsomsætning:");
        this.add(lblDagsomsætning, 0, 16);
        txfDagsomsætning = new TextField();
        this.add(txfDagsomsætning, 1, 16);

        Label lblSalgslinjer = new Label("Salgslinjer:");
        this.add(lblSalgslinjer, 2, 3);
        lwSalgslinjer = new ListView<>();
        this.add(lwSalgslinjer, 2, 4,1,10);
        lwSalgslinjer.setPrefWidth(350);

        Label lblKlippeStatistik = new Label("KLIPPESTATISTIK");
        this.add(lblKlippeStatistik, 3, 4);

        Label lblVælgPeriode = new Label("Vælg periode:");
        this.add(lblVælgPeriode, 3, 5);

        Label lblFra = new Label("Fra:");
        this.add(lblFra, 3, 6);
        datePickerFra = new DatePicker();
        this.add(datePickerFra, 4, 6);
        datePickerFra.setOnAction(event -> updateControls());

        Label lblTil = new Label("Til:");
        this.add(lblTil, 3, 7);
        datePickerTil = new DatePicker();
        this.add(datePickerTil, 4, 7);
        datePickerTil.setOnAction(event -> updateControls());

        Label lblSolgteKlip = new Label("Antal solgte klip:");
        this.add(lblSolgteKlip, 3, 8);
        txfSolgteKlip = new TextField();
        this.add(txfSolgteKlip, 4, 8);

        Label lblBrugteKlip = new Label("Antal brugte klip:");
        this.add(lblBrugteKlip, 3, 9);
        txfBrugteKlip = new TextField();
        this.add(txfBrugteKlip, 4, 9);

    }

    public void updateControls(){
        LocalDate dato = datePicker.getValue();
        if (dato != null) {
            lwDagensSalg.getItems().setAll(controller.dagsRapport(dato));
            txfAntal.setText("" + controller.dagsRapport(dato).size());
            txfDagsomsætning.setText("" + controller.beregnDagsomsætning(dato));
        }
        LocalDate fraDato = datePickerFra.getValue();
        LocalDate tilDato = datePickerTil.getValue();
        if (fraDato != null && tilDato != null) {
            txfSolgteKlip.setText("" + controller.solgteKlipForPeriode(fraDato, tilDato));
            txfBrugteKlip.setText("" + controller.brugteKlipForPeriode(fraDato, tilDato));
        }
    }

    public void salgItemSelected() {
        Salg salg = lwDagensSalg.getSelectionModel().getSelectedItem();
        if (salg != null) {
            lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(salg));
        }
    }
}
