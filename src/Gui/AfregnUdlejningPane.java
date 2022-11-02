package Gui;

import Application.Model.KomplekstSalg;
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
                (ov, oldProduktGruppe, newProduktGruppe) -> this.selectedUafsluttedeUdlejninger();
        lWUafsluttedeUdlejninger.getSelectionModel().selectedItemProperty().addListener(listenerUafsluttedeUdlejninger);

        this.add(lbSalgslinjeriUdlejning, 1, 0);
        this.add(lWSalgslinjeriUdlejning, 1, 1, 1, 12);

        this.add(lbAntal, 2, 3);
        this.add(txfAntal, 3, 3);
        txfAntal.setPrefWidth(50);
        this.add(btnAfregn, 2, 4);
        btnAfregn.setPrefWidth(75);

        this.add(lbIndleveredeProdukter, 4, 0);
        this.add(lwIndleveredeSalgslinjer, 4, 1, 1, 9);

        this.add(hBoxTotal, 4, 10);
        hBoxTotal.setSpacing(20);

        this.add(btnUdbetal, 4, 11);

        this.add(hBoxErrorAndSucces, 4, 12);

    }

    public void updateControls(){
        lWUafsluttedeUdlejninger.getItems().setAll(controller.getUadsluttedeUdlejninger());
        KomplekstSalg udlejning = lWUafsluttedeUdlejninger.getSelectionModel().getSelectedItem();
        if(udlejning != null){
            //TODO
            lWSalgslinjeriUdlejning.getItems().setAll(controller.printMellemRegning(udlejning));
        }
    }

    public void selectedUafsluttedeUdlejninger(){
        updateControls();
    }
}
