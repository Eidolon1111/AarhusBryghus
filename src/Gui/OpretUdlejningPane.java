package Gui;

import Application.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class OpretUdlejningPane extends GridPane {

    private ControllerInterface controller;
    private Prisliste prisliste;
    private Udlejning currentSalg;

    private Label lbKunder = new Label("Vælg Kunde: ");
    private ListView<Kunde> lwKunder = new ListView<>();

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
    private ListView<Salgslinje> lwSalgslinjer = new ListView<>();
    private Button btnOpretKunde = new Button("Opret");

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
    private HBox hBoxBetalingsFormer = new HBox(lbBetalingsformer, comboBoxbetalingsformer);
    private HBox hBoxBetalingRabat = new HBox(btnSalgRabat, btnBetal);

    private Label lbError = new Label();
    private Label lbSucces = new Label();
    private HBox hBoxErrorAndSucces = new HBox(lbError, lbSucces);

    public OpretUdlejningPane(ControllerInterface controller){
        this.controller = controller;
        this.prisliste = controller.getPrisliste("Udlejning");
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        this.add(lbKunder, 0, 0);
        this.add(lwKunder, 0, 1,2,8);
        lwKunder.getItems().setAll(controller.getKunder());

        this.add(lbOpretNyKunde, 0, 9);
        this.add(lbNavn, 0, 10);
        this.add(txfNavn,1,10);
        this.add(lbTlf, 0, 11);
        this.add(txfTlf,1,11);
        this.add(lbMail, 0, 12);
        this.add(txfMail,1,12);

        this.add(btnOpretKunde, 0, 13);
        btnOpretKunde.setOnAction(event -> btnOpretKundeAction());

        this.add(lbProduktgrupper, 3, 0);
        this.add(lwProduktgrupper, 3, 1,1,8);
        lwProduktgrupper.getItems().setAll(controller.getProduktGrupperIPrisliste(prisliste));
        ChangeListener<ProduktGruppe> listenerProduktGruppe = (ov, oldProduktGruppe, newProduktGruppe) -> this.selectedProduktGruppeChanged();
        lwProduktgrupper.getSelectionModel().selectedItemProperty().addListener(listenerProduktGruppe);

        this.add(lbProdukt, 4, 0);
        this.add(lwProdukter, 4, 1,2,8);
        ChangeListener<Produkt> listenerProdukt = (ov, oldProdukt, newProdukt) -> this.selectedProdukt();
        lwProdukter.getSelectionModel().selectedItemProperty().addListener(listenerProdukt);

        this.add(lbAntal, 4, 9);
        this.add(txfAntal, 5, 9);
        txfAntal.setPrefWidth(50);
        this.add(btnTilføj, 4, 10);
        btnTilføj.setOnAction(event -> btnTilføjAction());

        this.add(lbKurv, 6, 0);
        this.add(lwSalgslinjer, 6, 1,1,8);

        this.add(hBoxRabatogFjern, 6, 9);
        hBoxRabatogFjern.setSpacing(20);
        this.add(hBoxTotal, 6, 10);
        txfTotal.setEditable(false);
        hBoxTotal.setSpacing(20);
        btnFjern.setOnAction(event -> btnFjernAction());
        btnSalgslinjeRabat.setOnAction(event -> btnSalgslinjeRabat());

        this.add(hBoxBetalingsFormer, 6, 11);
        this.add(hBoxBetalingRabat, 6, 12);
        hBoxBetalingsFormer.setSpacing(20);
        hBoxBetalingRabat.setSpacing(20);
        btnBetal.setOnAction(event -> btnBetalAction());
        btnSalgRabat.setOnAction(event -> btnSalgRabat());

        this.add(hBoxErrorAndSucces, 6, 13);
        lbError.setStyle("-fx-text-fill: red");
        lbSucces.setStyle("-fx-text-fill: green");

    }

    public void selectedProduktGruppeChanged(){
        updateControls();
    }

    public void selectedProdukt(){
       lwProdukter.getSelectionModel().getSelectedItem();
    }

    public void updateControls(){
        ProduktGruppe produktGruppe = lwProduktgrupper.getSelectionModel().getSelectedItem();
        if(produktGruppe != null){
            lwProdukter.getItems().setAll(controller.getProdukterFraProduktgruppe(produktGruppe));
        }
    }

    public void btnOpretKundeAction() {
        if (!txfNavn.getText().equals("") && !txfTlf.getText().equals("") && !txfMail.getText().equals("")){
            controller.createKunde(txfNavn.getText(), txfTlf.getText(), txfMail.getText());
            lwKunder.getItems().setAll(controller.getKunder());
        } else{
            lbError = new Label("Alle felter skal udfyldes!");
        }
    }

    public void btnTilføjAction() {
        lbSucces.setText("");
        Kunde kunde = lwKunder.getSelectionModel().getSelectedItem();
        Produkt produkt = lwProdukter.getSelectionModel().getSelectedItem();
        Pris pris = controller.findPrisPaaProdukt(prisliste, produkt);
        int antal;
        if(kunde != null){
            if(produkt != null) {
                if (!txfAntal.getText().equals("")) {
                    try {
                        antal = Integer.parseInt(txfAntal.getText());
                        if (antal > 0) {
                            if (currentSalg == null) {
                                currentSalg = controller.createUdlejning(kunde);
                                lbError.setText("");
                            }
                            controller.createSalgslinje(currentSalg, antal, pris);
                            lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(currentSalg));
                            txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(currentSalg));
                            lbError.setText("");
                            comboBoxbetalingsformer.getItems().setAll(controller.getMuligeBetalingsformer(currentSalg));
                        } else {
                            lbError.setText("antal skal være over 0");
                        }
                    } catch (NumberFormatException e){
                        lbError.setText("Antal skal være et helt tal!");
                    }
                } else {
                    lbError.setText("indtast et antal!");
                }
            } else {
                lbError.setText("Vælg et produkt!");
            }
        } else {
            lbError.setText("Vælg en kunde først!");
        }
    }

    public void btnFjernAction(){
        Salgslinje salgslinje = lwSalgslinjer.getSelectionModel().getSelectedItem();
        if(salgslinje != null){
            controller.fjernSalgslinje(currentSalg, salgslinje);
            lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(currentSalg));
            txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(currentSalg));
            lbError.setText("Salgslinje fjernet");
            comboBoxbetalingsformer.getItems().setAll(controller.getMuligeBetalingsformer(currentSalg));
            txfAntal.clear();
        } else {
            lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(currentSalg));
            lbError.setText("fejl");
        }
    }

    public void btnBetalAction(){
        if(currentSalg != null){
            Salg.Betalingsform betalingsform = comboBoxbetalingsformer.getSelectionModel().getSelectedItem();
            if(betalingsform != null) {
                controller.betalSalg(currentSalg, betalingsform);
                lbError.setText("");
                lbSucces.setText("Salg betalt " + betalingsform);
                lwSalgslinjer.getItems().clear();
                currentSalg = null;
                txfTotal.clear();
            } else {
                lbError.setText("Vælg betalingsform!");
            }
        }
    }

    public void btnSalgslinjeRabat(){
        Salgslinje salgslinje = lwSalgslinjer.getSelectionModel().getSelectedItem();
        RabatWindowSalgslinje dia = new RabatWindowSalgslinje(controller, "Rabat Salgslinje", salgslinje);
        dia.showAndWait();
        lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(currentSalg));
        txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(currentSalg));
    }

    public void btnSalgRabat(){
        RabatWindowSalg dia = new RabatWindowSalg(controller,"Rabat Salgslinje", currentSalg);
        dia.showAndWait();
        updateControls();
        lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(currentSalg));
        txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(currentSalg));
    }

}
