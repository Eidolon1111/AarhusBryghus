package Gui;

import Application.Controller.Controller;
import Application.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class OpretDagligtSalgPane extends GridPane {

    private ControllerInterface controller;

    private Label lbPrisliser = new Label("Vælg Prisliste: ");
    private ComboBox<Prisliste> cBPrislister = new ComboBox<>();

    private Label lbProduktgrupper = new Label("Vælg Produkt Gruppe: ");
    private ListView<ProduktGruppe> lwProduktgrupper = new ListView<>();

    private Label lbProdukt = new Label("Vælg produkt: ");
    private ListView<Produkt> lwProdukter = new ListView<>();


    private Label lbAntal = new Label("Indtast antal: ");
    private TextField txfAntal = new TextField();
    private Button btnTilføj = new Button("Tilføj");

    private Label lbKurv = new Label("Kurv: ");
    private ListView<String> lwSalgslinjer = new ListView<>();

    private Label lbTotal = new Label("Total: ");
    private TextField txfTotal = new TextField();
    private HBox hBoxTotal = new HBox(lbTotal, txfTotal);

    private Button btnKontant = new Button("Kontant");
    private Button btnDankort = new Button("Dankort");
    private Button btnMp = new Button("MobilePay");
    private Button btnKlip = new Button("Klippekort");
    private Button btnRegning = new Button("Regning");

    private HBox hBoxBetaling = new HBox(btnKontant, btnDankort, btnMp, btnKlip, btnRegning);

    public OpretDagligtSalgPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(800);
        this.setPrefHeight(350);

        this.add(lbPrisliser, 0, 0);
        this.add(cBPrislister, 0, 1);
        cBPrislister.getItems().setAll(controller.getPrislister());
        ChangeListener<Prisliste> listenerCBPrislister = (ov, oldPrisliste, newPrisliste) -> this.selectedPrislisteChanged();
        cBPrislister.getSelectionModel().selectedItemProperty().addListener(listenerCBPrislister);

        this.add(lbProduktgrupper, 0, 2);
        this.add(lwProduktgrupper, 0, 3, 1, 8);
        ChangeListener<ProduktGruppe> listenerProduktGruppe = (ov, oldProduktGruppe, newProduktGruppe) -> this.selectedProduktGruppeChanged();
        lwProduktgrupper.getSelectionModel().selectedItemProperty().addListener(listenerProduktGruppe);

        this.add(lbProdukt, 1, 0);
        this.add(lwProdukter, 1, 1,1,10);
        ChangeListener<Produkt> listenerProdukt = (ov, oldProdukt, newProdukt) -> this.selectedProdukt();
        lwProdukter.getSelectionModel().selectedItemProperty().addListener(listenerProdukt);

        this.add(lbAntal, 2, 5);
        this.add(txfAntal, 3, 5);
        txfAntal.setPrefWidth(40);
        this.add(btnTilføj, 2, 6);
        btnTilføj.setOnAction(actionEvent -> setBtnTilføjAction());

        this.add(lbKurv, 4, 0);
        this.add(lwSalgslinjer, 4, 1,1,7);

        this.add(hBoxTotal, 4, 8);
        hBoxTotal.setSpacing(130);
        this.add(hBoxBetaling, 4, 9);
    }

    public void selectedPrislisteChanged(){
        updateControls();
    }

    public void selectedProduktGruppeChanged(){
        updateControls();
    }

    public void selectedProdukt(){
        updateControls();
    }


    public void updateControls(){
        Prisliste prisliste = cBPrislister.getSelectionModel().getSelectedItem();
        if(prisliste != null){
            lwProduktgrupper.getItems().setAll(controller.getProduktGupperIPrisliste(prisliste));
            ProduktGruppe produktGruppe = lwProduktgrupper.getSelectionModel().getSelectedItem();
            if(produktGruppe != null){
                lwProdukter.getItems().setAll(controller.getProdukterIProduktGruppe(produktGruppe));
                Produkt produkt = lwProdukter.getSelectionModel().getSelectedItem();
            }
        }
    }

    public void setBtnTilføjAction(){
        Salg currentSalg = null;
        Produkt produkt = lwProdukter.getSelectionModel().getSelectedItem();
        Prisliste prisliste = cBPrislister.getSelectionModel().getSelectedItem();
        int antal = Integer.parseInt(txfAntal.getText());
        if(produkt != null && antal > 0){
            currentSalg = controller.createSalg();
            controller.createSalgslinje(currentSalg, antal, produkt);
        }
        if(currentSalg != null){
            lwSalgslinjer.getItems().setAll(controller.printMellemRegning(prisliste, currentSalg));
        }
    }
}
