package Gui;

import Application.Model.Prisliste;
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
    private ListView<String> lwSalgslinjer;
    private TextField txfAntal, txfDagsomsætning, txfSamletPris, txfBetalingsform;

    public DagsrapporterPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        Label lblDato = new Label("Vælg dato:");
        this.add(lblDato, 0, 0);

        datePicker = new DatePicker();
        this.add(datePicker, 0, 1, 2, 1);
        datePicker.setOnAction(event -> updateControls());

        Label lblDagensSalg = new Label("Dagens Salg:");
        this.add(lblDagensSalg, 0, 2);
        lwDagensSalg = new ListView<>();
        this.add(lwDagensSalg, 0, 3, 2, 15);
        ChangeListener<Salg> salgListener = (ov, o, n) -> salgItemSelected();
        lwDagensSalg.getSelectionModel().selectedItemProperty().addListener(salgListener);

        Label lblAntal = new Label("Antal salg:");
        this.add(lblAntal, 0, 19);
        txfAntal = new TextField();
        this.add(txfAntal, 1, 19);

        Label lblDagsomsætning = new Label("Dagsomsætning:");
        this.add(lblDagsomsætning, 0, 20);
        txfDagsomsætning = new TextField();
        this.add(txfDagsomsætning, 1, 20);

        Label lblSalgslinjer = new Label("Salgslinjer:");
        this.add(lblSalgslinjer, 2, 2);
        lwSalgslinjer = new ListView<>();
        this.add(lwSalgslinjer, 2, 3,1,15);
        lwSalgslinjer.setPrefWidth(350);
    }

    public void updateControls(){
        LocalDate dato = datePicker.getValue();
        if (dato != null) {
            lwDagensSalg.getItems().setAll(controller.dagsRapport(dato));
            txfAntal.setText("" + controller.dagsRapport(dato).size());
            txfDagsomsætning.setText("" + controller.beregnDagsomsætning(dato));
        }
    }

    public void salgItemSelected() {
        Salg salg = lwDagensSalg.getSelectionModel().getSelectedItem();
        if (salg != null) {
            lwSalgslinjer.getItems().setAll(controller.printMellemRegning(salg));
            txfSamletPris.setText(controller.printSamletPrisDKKOgKlip(salg));
            txfBetalingsform.setText("" + salg.getBetalingsform());
        }
    }
}
