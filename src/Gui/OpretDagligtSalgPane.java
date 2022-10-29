package Gui;


import Application.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OpretDagligtSalgPane extends GridPane {

    private ControllerInterface controller;
    private Salg currentSalg = null;

    private Label lbPrisliser = new Label("Vælg Prisliste: ");
    private ComboBox<Prisliste> cBPrislister = new ComboBox<>();

    private Label lbProduktgrupper = new Label("Vælg Produkt Gruppe: ");
    private ListView<ProduktGruppe> lwProduktgrupper = new ListView<>();

    private Label lbProdukt = new Label("Vælg produkt: ");
    private ListView<Produkt> lwProdukter = new ListView<>();


    private Label lbAntal = new Label("Indtast antal: ");
    private TextField txfAntal = new TextField();
    private Button btnTilføj = new Button("Tilføj");

    private Button btnFjern = new Button("Fjern");

    private Label lbKurv = new Label("Kurv: ");
    private ListView<String> lwSalgslinjer = new ListView<>();

    private Label lbTotal = new Label("Total: ");
    private TextField txfTotal = new TextField();
    private HBox hBoxTotal = new HBox(lbTotal, txfTotal);

    private Label lbBetalingsformer = new Label("Vælg Betalingsform:");
    private ComboBox<Salg.Betalingsform> comboBoxbetalingsformer = new ComboBox<>();
    private Button btnBetal = new Button("Betal");
    private VBox vBoxBetalingsFormer = new VBox(lbBetalingsformer, comboBoxbetalingsformer);
    private HBox hBoxBetaling = new HBox(vBoxBetalingsFormer, btnBetal);

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
        btnTilføj.setOnAction(actionEvent -> btnTilføjAction());

        this.add(btnFjern, 3, 6);
        btnFjern.setOnAction(event -> btnFjernAction());

        this.add(lbKurv, 4, 0);
        this.add(lwSalgslinjer, 4, 1,1,7);

        this.add(hBoxTotal, 4, 8);
        hBoxTotal.setSpacing(135);
        txfTotal.setEditable(false);
        txfTotal.setPrefWidth(150);
        //txfTotal.setPrefHeight();

        this.add(hBoxBetaling, 4, 9);
        hBoxBetaling.setSpacing(100);
        btnBetal.setPrefWidth(100);
        btnBetal.setPrefHeight(50);
        comboBoxbetalingsformer.getItems().setAll(Salg.Betalingsform.values());
        btnBetal.setOnAction(actionEvent -> btnBetalAction());

        this.add(hBoxErrorAndSucces, 4, 10);
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
        int antal;
        if(produkt != null) {
            if (!txfAntal.getText().equals("")) {
                try {
                    antal = Integer.parseInt(txfAntal.getText());
                    if (antal > 0) {
                        if (currentSalg == null) {
                            currentSalg = controller.createSalg();
                            lbError.setText("");
                        }
                        controller.createSalgslinje(currentSalg, antal, produkt);
                        lwSalgslinjer.getItems().setAll(controller.printMellemRegning(prisliste, currentSalg));
                        txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(prisliste, currentSalg));
                        lbError.setText("");
                        //TODO fjern mulighed for at vælge klippekort
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
        boolean result = controller.fjernSalgslinje(prisliste, currentSalg, s);
        if(result == true){
            lwSalgslinjer.getItems().setAll(controller.printMellemRegning(prisliste, currentSalg));
            txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(prisliste, currentSalg));
            lbError.setText("Salgslinje fjernet");
            txfAntal.clear();
        } else {
            lwSalgslinjer.getItems().setAll(controller.printMellemRegning(prisliste, currentSalg));
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
}
