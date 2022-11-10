package Gui;

import Application.Model.Pris;
import Application.Model.Prisliste;
import Application.Model.Produkt;
import Application.Model.ProduktGruppe;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class OpretPrisPane extends GridPane {
    
    private ControllerInterface controller;
    private ListView<Prisliste> lwPrislister = new ListView<>();
    private TreeView<Object> treeViewProdukterIproduktgrupper = new TreeView<>();
    private TreeItem<Object> root = new TreeItem<>("ProduktGrupper");
    private ListView lwValgtPrisliste = new ListView<>();
    private TextField txfOpretPrisliste = new TextField();
    private TextField txfDkkPris, txfKlipPris;
    private Label lblErrorPrisliste = new Label();
    private Label lblErrorPris = new Label();
    private CheckBox checkBoxTilBrugIDagligtSalg = new CheckBox("må bruges i dagligt salg");


    public OpretPrisPane(ControllerInterface controller){
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblPrislister = new Label("Vælg prisliste: ");
        Label lblEllerOpretNy = new Label("Eller opret ny prisliste: ");
        Label lblOpretPrisListe = new Label("Prisliste navn: ");
        Button btnOpret = new Button("Opret");

        this.add(lblPrislister, 0, 0);
        this.add(lwPrislister, 0, 1,2,16);
        this.add(lblEllerOpretNy, 0, 18);
        this.add(lblOpretPrisListe, 0, 19);
        this.add(txfOpretPrisliste,1,19);
        this.add(checkBoxTilBrugIDagligtSalg, 0, 20,2,1);
        this.add(btnOpret, 0, 21);
        this.add(lblErrorPrisliste, 1, 21);
        lblErrorPrisliste.setStyle("-fx-text-fill: red");

        Label lblProdukter = new Label("Produkter:");

        this.add(lblProdukter, 2, 0);
        this.add(treeViewProdukterIproduktgrupper, 2, 1,2,16);

        Button btnTilføj = new Button("Tilføj/Gem");

        Label lblDkkPris = new Label("DKK pris: ");
        txfDkkPris = new TextField();

        Label lblKlipPris = new Label("Klip pris: ");
        txfKlipPris = new TextField();

        this.add(lblDkkPris, 4, 5);
        this.add(txfDkkPris, 5, 5);
        this.add(lblKlipPris, 4, 6);
        this.add(txfKlipPris, 5, 6);

        this.add(btnTilføj, 5, 7,2,1);
        this.add(lblErrorPris, 5, 8);
        lblErrorPris.setStyle("-fx-text-fill: red");

        Label lblValgtPrisliste = new Label("Valgt prisliste: ");
        Button btnFjern = new Button("Fjern");


        this.add(lblValgtPrisliste, 6, 0);
        this.add(lwValgtPrisliste, 6, 1,2,16);
        this.add(btnFjern, 6, 17);

        this.setPrefHeight(400);
        this.setPrefWidth(1000);

        lwPrislister.getItems().setAll(controller.getPrislister());
        ChangeListener<Prisliste> prislisteListener = (ov, o, n) -> PrislisteItemSelected();
        lwPrislister.getSelectionModel().selectedItemProperty().addListener(prislisteListener);

        treeViewProdukterIproduktgrupper.setRoot(root);
        treeViewProdukterIproduktgrupper.setShowRoot(false);
        ChangeListener<TreeItem<Object>> objectChangeListener = (ov, o, n) -> treeviewItemSelectedChanged();
        treeViewProdukterIproduktgrupper.getSelectionModel().selectedItemProperty().addListener(objectChangeListener);

        btnOpret.setOnAction(event -> this.opretAction());
        btnTilføj.setOnAction(event -> this.tilføjAction());
        btnFjern.setOnAction(event -> this.fjernAction());
    }

    public void opretAction() {
        String navn = txfOpretPrisliste.getText().trim();
        if (navn.length() == 0) {
            lblErrorPrisliste.setText("Navn er tom");
        } else {
            if(checkBoxTilBrugIDagligtSalg.isSelected()){
                controller.createPrisliste(navn, true);
                lwPrislister.getItems().setAll(controller.getPrislister());
                updateControls();
            } else {
                controller.createPrisliste(navn, false);
                lwPrislister.getItems().setAll(controller.getPrislister());
                updateControls();
            }
        }
    }

    public void tilføjAction() {
        Prisliste pl = lwPrislister.getSelectionModel().getSelectedItem();
        TreeItem<Object> treeItem = treeViewProdukterIproduktgrupper.getSelectionModel().getSelectedItem();
        Produkt p;
        if(treeItem.getValue() instanceof Produkt){
            p = (Produkt) treeItem.getValue();
            if (p != null && pl != null) {
                if (txfDkkPris.getText().isEmpty()) {
                    lblErrorPris.setText("Pris mangler");
                } else if (txfKlipPris.getText().isEmpty() && Integer.parseInt(txfDkkPris.getText()) >= 0) {
                    double pris = Integer.parseInt(txfDkkPris.getText());
                    controller.createPris(pl, p, pris, 0);
                    updateControls();
                } else if (Integer.parseInt(txfDkkPris.getText()) <= 0){
                    lblErrorPris.setText("Pris skal være et positivt tal");
                }else if (Integer.parseInt(txfDkkPris.getText()) >= 0 && Integer.parseInt(txfKlipPris.getText()) >= 0){
                    double pris = Integer.parseInt(txfDkkPris.getText());
                    int klip = Integer.parseInt(txfKlipPris.getText());
                    controller.createPris(pl,p,pris, klip);
                    updateControls();
                }
            } else {
                lblErrorPris.setText("Der mangler at blive valgt prisliste og/eller produkt");
            }
        } else {
            lblErrorPris.setText("Du skal vælge et produkt");
        }
    }

    public void fjernAction() {
        Prisliste pl = lwPrislister.getSelectionModel().getSelectedItem();
        Pris p = (Pris) lwValgtPrisliste.getSelectionModel().getSelectedItem();
        if (p != null) {
            controller.fjernPris(pl,p);
            updateControls();
        }
    }

    public void updateControls(){
        root.getChildren().clear();
        createProduktGruppeBranches(controller.getProduktGrupper(), root);
        Prisliste pl = lwPrislister.getSelectionModel().getSelectedItem();
        if (pl != null) {
            lwValgtPrisliste.getItems().setAll(pl.getPrislisten());
        }
        txfDkkPris.clear();
        txfKlipPris.clear();
        txfOpretPrisliste.clear();
        lblErrorPris.setText("");
        lblErrorPrisliste.setText("");
    }

    public void PrislisteItemSelected() {
        this.updateControls();
    }

    public void treeviewItemSelectedChanged(){
        treeViewProdukterIproduktgrupper.getSelectionModel().getSelectedItem();
    }

    public void createProduktGruppeBranches(ArrayList<ProduktGruppe> list, TreeItem<Object> parent){
        for(ProduktGruppe produktGruppe : list){
            TreeItem<Object> branchItem = new TreeItem<>(produktGruppe);
            parent.getChildren().add(branchItem);
            createProduktLeafs(produktGruppe.getProdukts(), branchItem);

        }
    }

    public void createProduktLeafs(ArrayList<Produkt> list, TreeItem<Object> parent){
        for(Produkt produkt : list){
            TreeItem<Object> leafItem = new TreeItem<>(produkt);
            parent.getChildren().add(leafItem);
        }
    }

}
