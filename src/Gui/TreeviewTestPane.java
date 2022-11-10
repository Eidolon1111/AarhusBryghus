package Gui;

import Application.Model.Produkt;
import Application.Model.ProduktGruppe;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;


public class TreeviewTestPane extends GridPane {
    private ControllerInterface controller;
    private TreeView<Object> treeViewProdukterIproduktgrupper = new TreeView<>();

    public TreeviewTestPane(ControllerInterface controller) {
        this.controller = controller;
        this.setPadding(new Insets(10));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);




        this.add(treeViewProdukterIproduktgrupper, 0, 0);
        ChangeListener<TreeItem<Object>> objectChangeListener = (ov, o, n) -> treeviewItemSelected();
        treeViewProdukterIproduktgrupper.getSelectionModel().selectedItemProperty().addListener(objectChangeListener);

    }

    public void updateControls(){
        TreeItem<Object> root = new TreeItem<>("ProduktGrupper");
        treeViewProdukterIproduktgrupper.setRoot(root);
        treeViewProdukterIproduktgrupper.setShowRoot(false);
        createProduktGruppeBranches(controller.getProduktGrupper(), root);
    }

    public void treeviewItemSelected(){
        TreeItem<Object> treeItem = treeViewProdukterIproduktgrupper.getSelectionModel().getSelectedItem();
        ProduktGruppe produktGruppe;
        Produkt produkt;
        if(treeItem != null){
            if(treeItem.getValue() instanceof ProduktGruppe){
                produktGruppe = (ProduktGruppe) treeItem.getValue();
                System.out.println(produktGruppe.getNavn());
            } else {
                produkt = (Produkt) treeItem.getValue();
                System.out.println(produkt.getNavn());
            }
        }
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
