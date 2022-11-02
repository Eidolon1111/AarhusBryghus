package Gui;


import Application.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class OpretDagligtSalgPane extends GridPane {

    private ControllerInterface controller;
    private SimpeltSalg currentSimpeltSalg = null;

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

    private Button btnFjern = new Button("Fjern salgslinje");
    private Button btnSalgslinjeRabat = new Button("Rabat salgslinje");
    private HBox hBoxRabatogFjern = new HBox(btnFjern, btnSalgslinjeRabat);

    private Label lbTotal = new Label("Total: ");
    private TextField txfTotal = new TextField();
    private HBox hBoxTotal = new HBox(lbTotal, txfTotal);

    private Label lbBetalingsformer = new Label("Vælg Betalingsform:");
    private ComboBox<SimpeltSalg.Betalingsform> comboBoxbetalingsformer = new ComboBox<>();
    private Button btnSalgRabat = new Button("Rabat Salg");
    private Button btnBetal = new Button("Betal");
    private HBox hBoxBetalingsFormer = new HBox(lbBetalingsformer, comboBoxbetalingsformer);
    private HBox hBoxBetalingRabat = new HBox(btnSalgRabat, btnBetal);

    private Label lbError = new Label();
    private Label lbSucces = new Label();
    private HBox hBoxErrorAndSucces = new HBox(lbError, lbSucces);

    public OpretDagligtSalgPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        this.add(lbPrisliser, 0, 0);
        this.add(cBPrislister, 0, 1);
        cBPrislister.getItems().setAll(controller.getPrislister());
        ChangeListener<Prisliste> listenerCBPrislister = (ov, oldPrisliste, newPrisliste) -> this.selectedPrislisteChanged();
        cBPrislister.getSelectionModel().selectedItemProperty().addListener(listenerCBPrislister);

        this.add(lbProduktgrupper, 0, 2);
        this.add(lwProduktgrupper, 0, 3, 1, 14);
        ChangeListener<ProduktGruppe> listenerProduktGruppe = (ov, oldProduktGruppe, newProduktGruppe) -> this.selectedProduktGruppeChanged();
        lwProduktgrupper.getSelectionModel().selectedItemProperty().addListener(listenerProduktGruppe);

        this.add(lbProdukt, 1, 0);
        this.add(lwProdukter, 1, 1,1,14);
        ChangeListener<Produkt> listenerProdukt = (ov, oldProdukt, newProdukt) -> this.selectedProdukt();
        lwProdukter.getSelectionModel().selectedItemProperty().addListener(listenerProdukt);

        this.add(lbAntal, 2, 5);
        this.add(txfAntal, 3, 5);
        txfAntal.setPrefWidth(40);
        this.add(btnTilføj, 2, 6);
        btnTilføj.setOnAction(actionEvent -> btnTilføjAction());

        this.add(hBoxRabatogFjern, 4, 10);
        hBoxRabatogFjern.setSpacing(20);
        btnFjern.setOnAction(event -> btnFjernAction());
        btnSalgslinjeRabat.setOnAction(event -> btnSalgslinjeRabat());

        this.add(lbKurv, 4, 0);
        this.add(lwSalgslinjer, 4, 1,1,9);

        this.add(hBoxTotal, 4, 11);
        hBoxTotal.setSpacing(20);
        txfTotal.setEditable(false);

        this.add(hBoxBetalingsFormer, 4, 12);
        this.add(hBoxBetalingRabat, 4, 13);

        hBoxBetalingsFormer.setSpacing(20);
        hBoxBetalingRabat.setSpacing(20);

        btnBetal.setOnAction(actionEvent -> btnBetalAction());
        btnSalgRabat.setOnAction(event -> btnSalgRabat());

        this.add(hBoxErrorAndSucces, 4, 14);
        lbError.setStyle("-fx-text-fill: red");
        lbSucces.setStyle("-fx-text-fill: green");

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
            lwProduktgrupper.getItems().setAll(controller.getProduktGrupperIPrisliste(prisliste));
            ProduktGruppe produktGruppe = lwProduktgrupper.getSelectionModel().getSelectedItem();
            if(produktGruppe != null){
                lwProdukter.getItems().setAll(controller.getProdukterFraProduktgruppe(produktGruppe));
            }
        }
    }

    public void btnTilføjAction() {
        lbSucces.setText("");
        Produkt produkt = lwProdukter.getSelectionModel().getSelectedItem();
        Prisliste prisliste = cBPrislister.getSelectionModel().getSelectedItem();
        Pris pris = controller.findPrisPaaProdukt(prisliste, produkt);
        int antal;
        if(produkt != null) {
            if (!txfAntal.getText().equals("")) {
                try {
                    antal = Integer.parseInt(txfAntal.getText());
                    if (antal > 0) {
                        if (currentSimpeltSalg == null) {
                            currentSimpeltSalg = controller.createSimpelSalg();
                            lbError.setText("");
                        }
                        controller.createSalgslinje(currentSimpeltSalg, antal, pris);
                        lwSalgslinjer.getItems().setAll(controller.printMellemRegning(currentSimpeltSalg));
                        txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(currentSimpeltSalg));
                        lbError.setText("");
                        comboBoxbetalingsformer.getItems().setAll(controller.getMuligeBetalingsformer(currentSimpeltSalg));
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
    }

    public void btnFjernAction(){
        Prisliste prisliste = cBPrislister.getSelectionModel().getSelectedItem();
        String s = lwSalgslinjer.getSelectionModel().getSelectedItem();
        Salgslinje result = controller.findSalgslinjeFraKurv(prisliste, currentSimpeltSalg, s);
        if(result != null){
            controller.fjernSalgslinje(currentSimpeltSalg, result);
            lwSalgslinjer.getItems().setAll(controller.printMellemRegning(currentSimpeltSalg));
            txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(currentSimpeltSalg));
            lbError.setText("Salgslinje fjernet");
            comboBoxbetalingsformer.getItems().setAll(controller.getMuligeBetalingsformer(currentSimpeltSalg));
            txfAntal.clear();
        } else {
            lwSalgslinjer.getItems().setAll(controller.printMellemRegning(currentSimpeltSalg));
            lbError.setText("fejl");
        }
    }

    public void btnBetalAction(){
        if(currentSimpeltSalg != null){
            SimpeltSalg.Betalingsform betalingsform = comboBoxbetalingsformer.getSelectionModel().getSelectedItem();
            if(betalingsform != null) {
                controller.betalSalg(currentSimpeltSalg, betalingsform);
                lbError.setText("");
                lbSucces.setText("Salg betalt " + betalingsform);
                lwSalgslinjer.getItems().clear();
                currentSimpeltSalg = null;
                txfTotal.clear();
            } else {
                lbError.setText("Vælg betalingsform!");
            }
        }
    }

    public void btnSalgslinjeRabat(){
        Prisliste prisliste = cBPrislister.getSelectionModel().getSelectedItem();
        String target = lwSalgslinjer.getSelectionModel().getSelectedItem();
        Salgslinje salgslinje = controller.findSalgslinjeFraKurv(prisliste, currentSimpeltSalg, target);
        RabatWindowSalgslinje dia = new RabatWindowSalgslinje(controller, "Rabat Salgslinje", salgslinje);
        dia.showAndWait();
        lwSalgslinjer.getItems().setAll(controller.printMellemRegning(currentSimpeltSalg));
        txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(currentSimpeltSalg));
    }

    public void btnSalgRabat(){
        RabatWindowSalg dia = new RabatWindowSalg(controller,"Rabat Salgslinje", currentSimpeltSalg);
        dia.showAndWait();
        updateControls();
        lwSalgslinjer.getItems().setAll(controller.printMellemRegning(currentSimpeltSalg));
        txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(currentSimpeltSalg));
    }

}
