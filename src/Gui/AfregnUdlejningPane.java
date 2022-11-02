package Gui;

import Application.Model.KomplekstSalg;
import Application.Model.Prisliste;
import Application.Model.Salgslinje;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AfregnUdlejningPane extends GridPane {

    private ControllerInterface controller;
    private Prisliste prisliste;

    private Label lbUdlejninger = new Label("Vælg Udlejning");
    private ListView<KomplekstSalg> lWUafsluttedeUdlejninger = new ListView<>();

    private Label lbSalgslinjeriUdlejning = new Label("Udlejede produkter");
    private ListView<String> lWSalgslinjeriUdlejning = new ListView<>();

    private Label lbAntal = new Label("Antal");
    private TextField txfAntal = new TextField();
    private Button btnAfregn = new Button("Afregn");

    private Label lbIndleveredeProdukter = new Label("Indleverede Produkter");
    private ListView<String> lwIndleveredeSalgslinjer = new ListView<>();

    private Label lbTotal = new Label("Total: ");
    private TextField txfTotal = new TextField();
    private HBox hBoxTotal = new HBox(lbTotal, txfTotal);
    private Button btnUdbetal = new Button("Udbetal");

    private Label lbError = new Label();
    private Label lbSucces = new Label();
    private HBox hBoxErrorAndSucces = new HBox(lbError, lbSucces);

    public AfregnUdlejningPane(ControllerInterface controller){
        this.controller = controller;
        this.prisliste = controller.getPrisliste("Udlejning");
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        this.add(lbUdlejninger, 0, 0);
        this.add(lWUafsluttedeUdlejninger, 0, 1, 1, 12);
        lWUafsluttedeUdlejninger.getItems().setAll(controller.getUadsluttedeUdlejninger());
        ChangeListener<KomplekstSalg> listenerUafsluttedeUdlejninger =
                (ov, oldUdlejning, newUdlejning) -> this.selectedUafsluttedeUdlejningerChanged();
        lWUafsluttedeUdlejninger.getSelectionModel().selectedItemProperty().addListener(listenerUafsluttedeUdlejninger);

        this.add(lbSalgslinjeriUdlejning, 1, 0);
        this.add(lWSalgslinjeriUdlejning, 1, 1, 1, 12);
        ChangeListener<String> listenerSalgslinjer =
                (ov, oldSalgslinje, newSalgslinje) -> this.selectedSalgslinjeChanged();
        lWSalgslinjeriUdlejning.getSelectionModel().selectedItemProperty().addListener(listenerSalgslinjer);

        this.add(lbAntal, 2, 3);
        this.add(txfAntal, 3, 3);
        txfAntal.setPrefWidth(50);
        this.add(btnAfregn, 2, 4);
        btnAfregn.setPrefWidth(75);
        btnAfregn.setOnAction(event -> btnAfregnAction());

        this.add(lbIndleveredeProdukter, 4, 0);
        this.add(lwIndleveredeSalgslinjer, 4, 1, 1, 9);

        this.add(hBoxTotal, 4, 10);
        hBoxTotal.setSpacing(20);

        this.add(btnUdbetal, 4, 11);

        this.add(hBoxErrorAndSucces, 4, 12);
        lbError.setStyle("-fx-text-fill: red");
        lbSucces.setStyle("-fx-text-fill: green");

    }

    public void updateControls(){
        lWUafsluttedeUdlejninger.getItems().setAll(controller.getUadsluttedeUdlejninger());
        KomplekstSalg udlejning = lWUafsluttedeUdlejninger.getSelectionModel().getSelectedItem();
        if(udlejning != null){
            lWSalgslinjeriUdlejning.getItems().setAll(controller.printMellemRegning(udlejning));
        }
    }

    public void selectedUafsluttedeUdlejningerChanged(){
        updateControls();
    }

    public void selectedSalgslinjeChanged(){
    }

    public void btnAfregnAction(){
        KomplekstSalg udlejning = lWUafsluttedeUdlejninger.getSelectionModel().getSelectedItem();
        String stringSalgslinje = lWSalgslinjeriUdlejning.getSelectionModel().getSelectedItem();
        int antal;
        if(udlejning != null){
            if(stringSalgslinje != null){
                Salgslinje salgslinje = controller.findSalgslinjeFraKurv(prisliste, udlejning, stringSalgslinje);
                try {
                    antal = Integer.parseInt(txfAntal.getText());
                        if(salgslinje != null){
                            Salgslinje modregning = controller.createModregning(udlejning, salgslinje, antal);;
                            lwIndleveredeSalgslinjer.getItems().add(controller.printMellemRegningSalgslinje(modregning));
                            txfTotal.setText("" + controller.beregnReturBeløbUdlejning(udlejning));
                        } else {
                        lbError.setText("Felj");
                        }
                } catch (NumberFormatException e) {

                }
            }else {
                lbError.setText("Vælg en salgslinje!");
            }
        } else {
            lbError.setText("Vælg Udlejning!");
        }
    }
}
