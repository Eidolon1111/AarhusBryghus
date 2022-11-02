package Gui;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;

public class KliprapporterPane extends GridPane {

    private ControllerInterface controller;
    private DatePicker datePickerFra, datePickerTil;
    private TextField txfSolgteKlip, txfBrugteKlip;

    public KliprapporterPane(ControllerInterface controller) {
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        Label lblVælgPeriode = new Label("Vælg periode");
        this.add(lblVælgPeriode, 0, 0);

        Label lblFra = new Label("Fra:");
        this.add(lblFra, 0, 1);
        datePickerFra = new DatePicker();
        this.add(datePickerFra, 1, 1);
        datePickerFra.setOnAction(event -> updateControls());

        Label lblTil = new Label("Til:");
        this.add(lblTil, 0, 2);
        datePickerTil = new DatePicker();
        this.add(datePickerTil, 1, 2);
        datePickerTil.setOnAction(event -> updateControls());

        Label lblSolgteKlip = new Label("Antal solgte klip:");
        this.add(lblSolgteKlip, 0, 3);
        txfSolgteKlip = new TextField();
        this.add(txfSolgteKlip, 1, 3);

        Label lblBrugteKlip = new Label("Antal brugte klip:");
        this.add(lblBrugteKlip, 0, 4);
        txfBrugteKlip = new TextField();
        this.add(txfBrugteKlip, 1, 4);
    }

    public void updateControls() {
        LocalDate fraDato = datePickerFra.getValue();
        LocalDate tilDato = datePickerTil.getValue();
        if (fraDato != null && tilDato != null) {

        }
    }
}
