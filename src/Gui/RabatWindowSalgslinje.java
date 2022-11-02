package Gui;

import Application.Model.Salgslinje;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RabatWindowSalgslinje extends Stage{
    private Salgslinje salgslinje;
    private ControllerInterface controller;

    private Label lbIndtastRabat = new Label("Indtast Rabat:");
    private TextField txfRabat = new TextField();

    private HBox hBoxLabelTxtfield = new HBox(lbIndtastRabat, txfRabat);

    private RadioButton rbProcent = new RadioButton("Procent");
    private RadioButton rbDkk = new RadioButton("DKK");
    private ToggleGroup tgRabat = new ToggleGroup();

    private HBox hBoxRadioButtons = new HBox(rbProcent, rbDkk);

    private Button btnGem = new Button("Gem");
    private Button btnCancel = new Button("Cancel");

    private HBox hBoxGemCancel = new HBox(btnGem, btnCancel);

    private Label lbError = new Label();

    public RabatWindowSalgslinje(ControllerInterface controller, String title, Salgslinje salgslinje){
        this.controller = controller;
        this.salgslinje = salgslinje;
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);
        Scene scene = new Scene(pane);setScene(scene);
    }

    public void initContent(GridPane pane){
        pane.setPrefWidth(200);
        pane.setPrefHeight(100);
        pane.setPadding(new Insets(10));
        pane.setVgap(20);
        rbProcent.setToggleGroup(tgRabat);
        rbDkk.setToggleGroup(tgRabat);
        pane.add(hBoxRadioButtons,0,0);
        hBoxRadioButtons.setSpacing(20);
        tgRabat.selectToggle(rbProcent);

        pane.add(hBoxLabelTxtfield, 0, 1);
        hBoxLabelTxtfield.setSpacing(20);
        txfRabat.setPrefWidth(50);

        pane.add(hBoxGemCancel, 0, 3);
        hBoxGemCancel.setSpacing(20);
        btnGem.setOnAction(event -> btnGemAction());
        btnCancel.setOnAction(event -> btnCancel());

        pane.add(lbError,0,4);
        lbError.setStyle("-fx-text-fill: red");
    }

    //TODO tjek for ugyldigt data!
    public void btnGemAction(){
        try {
            double rabat = Double.parseDouble(txfRabat.getText());
            if(rbProcent.isSelected()){
                rabat = (rabat) / 100.00;
            }
            controller.setRabatSalgslinje(salgslinje, rabat);
            this.close();
        } catch (NumberFormatException e){
            lbError.setText("rabat skal være et tal!");
        }
    }

    public void btnCancel(){
        this.close();
    }

}
