package Gui;

import Application.Model.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OpretUdlejningPane extends GridPane {

    private ControllerInterface controller;

    private Label lbKunder = new Label("Vælg Kunde: ");
    private ListView lwKunder = new ListView<Kunde>();

    private Label lbOpretNyKunde = new Label("Eller opret ny Kunde: ");

    private Label lbNavn = new Label("Navn: ");
    private TextField txfNavn = new TextField();

    private Label lbTlf = new Label("Tlf: ");
    private TextField txfTlf = new TextField();

    private Label lbMail = new Label("Email: ");
    private TextField txfMail = new TextField();

    private Label lbProduktgrupper = new Label("Vælg Produkt Gruppe: ");
    private ListView<ProduktGruppe> lwProduktgrupper = new ListView<>();

    private Label lbProdukt = new Label("Vælg produkt: ");
    private ListView<Produkt> lwProdukter = new ListView<>();

    private Label lbAntal = new Label("Indtast antal: ");
    private TextField txfAntal = new TextField();
    private Button btnTilføj = new Button("Tilføj til kurv");

    private Label lbKurv = new Label("Kurv: ");
    private ListView<String> lwSalgslinjer = new ListView<>();
    Button btnOpret = new Button("Opret");

    private Button btnFjern = new Button("Fjern salgslinje");
    private Button btnSalgslinjeRabat = new Button("Rabat salgslinje");
    private HBox hBoxRabatogFjern = new HBox(btnFjern, btnSalgslinjeRabat);

    private Label lbTotal = new Label("Total: ");
    private TextField txfTotal = new TextField();
    private HBox hBoxTotal = new HBox(lbTotal, txfTotal);

    private Label lbBetalingsformer = new Label("Vælg Betalingsform:");
    private ComboBox<Salg.Betalingsform> comboBoxbetalingsformer = new ComboBox<>();
    private Button btnSalgRabat = new Button("Rabat Salg");
    private Button btnBetal = new Button("Betal");
    private VBox vBoxBetalingsFormer = new VBox(lbBetalingsformer, comboBoxbetalingsformer);
    private VBox vBoxBetalingRabat = new VBox(btnSalgRabat, btnBetal);
    private HBox hBoxBetaling = new HBox(vBoxBetalingsFormer, vBoxBetalingRabat);

    private Label lbError = new Label();
    private Label lbSucces = new Label();
    private HBox hBoxErrorAndSucces = new HBox(lbError, lbSucces);

    //TODO
    public OpretUdlejningPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        this.add(lbKunder, 0, 0);
        this.add(lwKunder, 0, 1,2,8);
        this.add(lbOpretNyKunde, 0, 9);
        this.add(lbNavn, 0, 10);
        this.add(txfNavn,1,10);
        this.add(lbTlf, 0, 11);
        this.add(txfTlf,1,11);
        this.add(lbMail, 0, 12);
        this.add(txfMail,1,12);
        this.add(btnOpret, 0, 13);

        this.add(lbProduktgrupper, 3, 0);
        this.add(lwProduktgrupper, 3, 1,1,8);

        this.add(lbProdukt, 4, 0);
        this.add(lwProdukter, 4, 1,2,8);

        this.add(lbAntal, 4, 9);
        this.add(txfAntal, 5, 9);
        txfAntal.setPrefWidth(50);
        this.add(btnTilføj, 4, 10);

        this.add(lbKurv, 6, 0);
        this.add(lwSalgslinjer, 6, 1,1,8);

        this.add(hBoxRabatogFjern, 6, 9);
        hBoxRabatogFjern.setSpacing(20);
        this.add(hBoxTotal, 6, 10);
        txfTotal.setEditable(false);
        hBoxTotal.setSpacing(20);
        this.add(hBoxBetaling, 6, 11);
        vBoxBetalingsFormer.setSpacing(10);
        vBoxBetalingRabat.setSpacing(10);
        hBoxBetaling.setSpacing(20);
        this.add(hBoxErrorAndSucces, 6, 12);

    }
    //TODO
    public void updateControls(){

    }
}
