package Gui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class RabatWindow extends Stage {

    private Label lbIndtastRabat = new Label("Indtast Rabat");
    private TextField txfRabat = new TextField();
    private RadioButton rbProcent = new RadioButton("Procent");
    private RadioButton rbDkk = new RadioButton("DKK");
    private ToggleGroup tgRabat = new ToggleGroup();

    private HBox hBoxRadioButtons = new HBox(rbProcent, rbDkk);

    private Button btnGem = new Button("Gem");
    private Button btnCancel = new Button("Cancel");

    private Label lbError = new Label();

    public RabatWindow(String title){
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public void initContent(GridPane pane){
        rbProcent.setToggleGroup(tgRabat);
        rbDkk.setToggleGroup(tgRabat);
        pane.add(hBoxRadioButtons,0,0);

        pane.add(lbIndtastRabat, 0,1);
        pane.add(txfRabat, 1, 1);

        pane.add(btnGem, 0, 2);
        pane.add(btnCancel, 1, 2);

        pane.add(lbError,0,3);
        lbError.setStyle("-fx-text-fill: red");
    }



}
