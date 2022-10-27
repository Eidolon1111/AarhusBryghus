package Gui;

import Application.Model.Prisliste;
import Application.Model.Produkt;
import Application.Model.ProduktGruppe;
import Application.Model.Salgslinje;
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
    private ListView<Salgslinje> lwSalgslinjer = new ListView<>();

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

        this.add(lbProduktgrupper, 0, 2);
        this.add(lwProduktgrupper, 0, 3, 1, 8);

        this.add(lbProdukt, 1, 0);
        this.add(lwProdukter, 1, 1,1,10);

        this.add(lbAntal, 2, 5);
        this.add(txfAntal, 3, 5);
        txfAntal.setPrefWidth(40);
        this.add(btnTilføj, 2, 6);

        this.add(lbKurv, 4, 0);
        this.add(lwSalgslinjer, 4, 1,1,7);

        this.add(hBoxTotal, 4, 8);
        hBoxTotal.setSpacing(140);
        this.add(hBoxBetaling, 4, 9);

    }

    public void updateControls(){

    }

}
