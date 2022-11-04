package Gui;

import Application.Model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class AfregnRundvisningPane extends GridPane {

    private Salgslinje førsteSalgLinje;
    private int rest;
    private Rundvisning rundvisning;

    private ListView<Rundvisning> lwRundvisninger = new ListView<>();
    private Label lblRundvisninger, lblValgteRundvisning;
    private TextField txfSalgslinje;
    private Label lblRest;

    private Label lbAntal = new Label("Indtast antal: ");
    private TextField txfAntal = new TextField();
    private Button btnTilføj = new Button("Tilføj");

    private Label lbKurv = new Label("Kurv: ");
    private ListView<Salgslinje> lwSalgslinjer = new ListView<>();

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

    private ControllerInterface controller;

    public AfregnRundvisningPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);
        this.setPrefWidth(1000);
        this.setPrefHeight(800);

        //Initialisering af elementer til række 1
        lwRundvisninger.getItems().setAll(controller.getRegistreredeRundvisninger());
        lblRundvisninger = new Label("Rundvisninger: ");

        //Tilføjelse af elementer til række 1
        this.add(lblRundvisninger, 0, 0);
        this.add(lwRundvisninger, 0, 1,1,14);

        //Setter Listview Runvisninger samt tilføjer listener
        ChangeListener<Rundvisning> listener = (ov, o, v) -> this.selectedRundvisningChanged();
        lwRundvisninger.getSelectionModel().selectedItemProperty().addListener(listener);

        //Initialisering af elementer til række 2
        lblValgteRundvisning = new Label("Valgte rundvisning: ");
        txfSalgslinje = new TextField();
        txfSalgslinje.setMinWidth(160);
        txfSalgslinje.setEditable(false);
        lblRest = new Label("Antal ikke afregnede: " + rest);



        //Tilføjelse af elementer til række 2
        this.add(lblValgteRundvisning, 1, 0);
        this.add(txfSalgslinje, 1, 1);
        this.add(lblRest, 1, 2);


        /////////////////////////////////////////////////////////////

        this.add(lbAntal, 1, 4);
        this.add(txfAntal, 1, 5);
        txfAntal.setPrefWidth(40);
        this.add(btnTilføj, 1, 6);
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

    public void updateControls(){
        lwRundvisninger.getItems().setAll(controller.getRegistreredeRundvisninger());
    }

    public void selectedRundvisningChanged() {
        Rundvisning rundvisning = lwRundvisninger.getSelectionModel().getSelectedItem();
        if (rundvisning != null) {
            this.førsteSalgLinje = rundvisning.getSalgslinjer().get(0);
        }
        rundvisning = lwRundvisninger.getSelectionModel().getSelectedItem();
        if (rundvisning != null) {
            this.førsteSalgLinje = rundvisning.getSalgslinjer().get(0);
            this.rest = rundvisning.getSalgslinjer().get(0).getAntal();
            lblRest.setText("Antal ikke afregnede: " + rest);
        } if (rundvisning != null) {
            txfSalgslinje.setText(førsteSalgLinje.toString());
        }
    }

    public void btnTilføjAction() {
        lbSucces.setText("");
        int antal;
        rundvisning = lwRundvisninger.getSelectionModel().getSelectedItem();
        if(rundvisning != null) {
            if (!txfAntal.getText().equals("")) {
                try {
                    antal = Integer.parseInt(txfAntal.getText());
                    if (antal > 0) {
                        controller.fjernSalgslinje(rundvisning, førsteSalgLinje);
                        lwRundvisninger.setDisable(true);
                        controller.createSalgslinje(rundvisning, antal, førsteSalgLinje.getPris());
                        lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(rundvisning));
                        txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(rundvisning));
                        lbError.setText("");
                        comboBoxbetalingsformer.getItems().setAll(controller.getMuligeBetalingsformer(rundvisning));
                        rest = rest - antal;
                        lblRest.setText("Antal ikke afregnede: " + rest);
                        txfAntal.clear();
                    } else {
                        lbError.setText("Antal skal være over 0");
                    }
                } catch (NumberFormatException e){
                    lbError.setText("Antal skal være et helt tal!");
                }
            } else {
                lbError.setText("Indtast et antal!");
            }
        } else {
            lbError.setText("Vælg en rundvisning!");
        }
    }

    public void btnFjernAction(){
        Salgslinje salgslinje = lwSalgslinjer.getSelectionModel().getSelectedItem();
        if(salgslinje != null){
            controller.fjernSalgslinje(rundvisning, salgslinje);
            rest = rest + salgslinje.getAntal();
            lblRest.setText("Antal ikke afregnede: " + rest);
            lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(rundvisning));
            txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(rundvisning));
            lbError.setText("Salgslinje fjernet");
            comboBoxbetalingsformer.getItems().setAll(controller.getMuligeBetalingsformer(rundvisning));
            txfAntal.clear();
        } else {
            lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(rundvisning));
            lbError.setText("fejl");
        }
    }

    public void btnBetalAction(){
        Rundvisning rundvisning = lwRundvisninger.getSelectionModel().getSelectedItem();
        if(rundvisning != null){
            Salg.Betalingsform betalingsform = comboBoxbetalingsformer.getSelectionModel().getSelectedItem();
            if(betalingsform != null) {
                controller.betalSalg(rundvisning, betalingsform);
                lbError.setText("");
                lbSucces.setText("Salg betalt " + betalingsform);
                lwSalgslinjer.getItems().clear();
                txfTotal.clear(); txfAntal.clear(); txfSalgslinje.clear(); lblRest.setText("Antal ikke afregnede: ");
                lwRundvisninger.setDisable(false);
                this.updateControls();
            } else {
                lbError.setText("Vælg betalingsform!");
            }
        }
    }

    public void btnSalgslinjeRabat(){
        Rundvisning rundvisning = lwRundvisninger.getSelectionModel().getSelectedItem();
        Salgslinje salgslinje = lwSalgslinjer.getSelectionModel().getSelectedItem();
        RabatWindowSalgslinje dia = new RabatWindowSalgslinje(controller, "Rabat Salgslinje", salgslinje);
        dia.showAndWait();
        lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(rundvisning));
        txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(rundvisning));
    }

    public void btnSalgRabat(){
        if (rundvisning != null) {
            RabatWindowSalg dia = new RabatWindowSalg(controller, "Rabat Salg", rundvisning);
            dia.showAndWait();
            lwSalgslinjer.getItems().setAll(controller.getSalgslinjerPaaSalg(rundvisning));
            txfTotal.setText("" + controller.printSamletPrisDKKOgKlip(rundvisning));
        }
    }
}
