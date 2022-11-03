package Gui;

import Application.Model.Prisliste;
import Application.Model.Salgslinje;
import Application.Model.Udlejning;
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
    private ListView<Udlejning> lWUafsluttedeUdlejninger = new ListView<>();

    private Label lbSalgslinjeriUdlejning = new Label("Udlejede produkter");
    private ListView<Salgslinje> lWSalgslinjeriUdlejning = new ListView<>();

    private Label lbAntal = new Label("Antal");
    private TextField txfAntal = new TextField();
    private Button btnModregn = new Button("Modregn");

    private Label lbIndleveredeProdukter = new Label("Indleverede Produkter");
    private ListView<Salgslinje> lwIndleveredeSalgslinjer = new ListView<>();

    private Button btnFjernModregning = new Button("Fjern Modregning");

    private Label lbTotal = new Label("Total: ");
    private TextField txfTotal = new TextField();
    private HBox hBoxTotal = new HBox(lbTotal, txfTotal);
    private Button btnUdbetal = new Button("Udbetal");
    private Button btnFortryd = new Button("Fortryd");
    private HBox hBoxUdbetalFortyd = new HBox(btnUdbetal, btnFortryd);

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
        ChangeListener<Udlejning> listenerUafsluttedeUdlejninger =
                (ov, oldUdlejning, newUdlejning) -> this.selectedUafsluttedeUdlejningerChanged();
        lWUafsluttedeUdlejninger.getSelectionModel().selectedItemProperty().addListener(listenerUafsluttedeUdlejninger);

        this.add(lbSalgslinjeriUdlejning, 1, 0);
        this.add(lWSalgslinjeriUdlejning, 1, 1, 1, 12);
        ChangeListener<Salgslinje> listenerSalgslinjer =
                (ov, oldSalgslinje, newSalgslinje) -> this.selectedSalgslinjeChanged();
        lWSalgslinjeriUdlejning.getSelectionModel().selectedItemProperty().addListener(listenerSalgslinjer);

        this.add(lbAntal, 2, 3);
        this.add(txfAntal, 3, 3);
        txfAntal.setPrefWidth(50);
        this.add(btnModregn, 2, 4);
        btnModregn.setPrefWidth(75);
        btnModregn.setOnAction(event -> btnAfregnAction());

        this.add(lbIndleveredeProdukter, 4, 0);
        this.add(lwIndleveredeSalgslinjer, 4, 1, 1, 9);

        this.add(btnFjernModregning, 4, 10);
        btnFjernModregning.setOnAction(event -> btnFjernModregningAction());

        this.add(hBoxTotal, 4, 11);
        hBoxTotal.setSpacing(20);

        this.add(hBoxUdbetalFortyd, 4, 12);
        hBoxUdbetalFortyd.setSpacing(20);
        btnUdbetal.setOnAction(event -> setBtnUdbetalAction());


        this.add(hBoxErrorAndSucces, 4, 13);
        lbError.setStyle("-fx-text-fill: red");
        lbSucces.setStyle("-fx-text-fill: green");

    }

    public void updateControls(){
        lWUafsluttedeUdlejninger.getItems().setAll(controller.getUadsluttedeUdlejninger());
        Udlejning udlejning = lWUafsluttedeUdlejninger.getSelectionModel().getSelectedItem();
        if(udlejning != null){
            lWSalgslinjeriUdlejning.getItems().setAll(controller.getSalgslinjerPaaSalg(udlejning));
        }
    }

    public void selectedUafsluttedeUdlejningerChanged(){
        updateControls();
    }

    public void selectedSalgslinjeChanged(){
    }

    public void btnAfregnAction(){
        Udlejning udlejning = lWUafsluttedeUdlejninger.getSelectionModel().getSelectedItem();
        Salgslinje salgslinje = lWSalgslinjeriUdlejning.getSelectionModel().getSelectedItem();
        int antal;
        if(udlejning != null){
            lWUafsluttedeUdlejninger.setDisable(true);
            try {
                antal = Integer.parseInt(txfAntal.getText());
                if(salgslinje != null){
                    Salgslinje modregning = controller.createModregning(udlejning, salgslinje, antal);
                    lwIndleveredeSalgslinjer.getItems().setAll(controller.getModregningerPaaUdlejning(udlejning));
                    txfTotal.setText("" + controller.beregnReturBeløbUdlejning(udlejning));
                } else {
                    lbError.setText("vælg Salgslinje!");
                }
            } catch (NumberFormatException e) {

            }

        } else {
            lbError.setText("Vælg Udlejning!");
        }
    }

    public void btnFjernModregningAction(){
        Udlejning udlejning = lWUafsluttedeUdlejninger.getSelectionModel().getSelectedItem();
        Salgslinje salgslinje = lwIndleveredeSalgslinjer.getSelectionModel().getSelectedItem();
        if(salgslinje != null){
            controller.fjernSalgslinje(udlejning, salgslinje);
            lwIndleveredeSalgslinjer.getItems().setAll(controller.getModregningerPaaUdlejning(udlejning));
            txfTotal.setText("" + controller.beregnReturBeløbUdlejning(udlejning));
            lbError.setText("Salgslinje fjernet");
            txfAntal.clear();
        } else {
            lwIndleveredeSalgslinjer.getItems().setAll(controller.getModregningerPaaUdlejning(udlejning));
            lbError.setText("fejl");
        }
    }


    public void setBtnUdbetalAction(){
        Udlejning udlejning = lWUafsluttedeUdlejninger.getSelectionModel().getSelectedItem();
        if (udlejning != null){
            controller.udbetalModregning(udlejning);
            txfTotal.clear();
            lWSalgslinjeriUdlejning.getItems().clear();
            lwIndleveredeSalgslinjer.getItems().clear();
            txfAntal.clear();
            lWUafsluttedeUdlejninger.setDisable(false);
            updateControls();
            lbSucces.setText("Modregning Udbetalt!");
        } else {
            lbError.setText("Vælg udlejning");
        }
    }
}
