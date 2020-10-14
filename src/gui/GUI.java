package gui;


import configuration.Configuration;
import crypto.CryptoCreator;
import crypto.ICryptoCreator;
import instructionParser.IInstructionExecute;
import instructionParser.InstructionExecute;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logging.GetLatestLog;
import networkCompany.CompanyNetControlCenter;

public class GUI extends Application {

    private TextArea outputArea;
    private ICryptoCreator cryptoCreator;


    public void start(Stage primaryStage) {

        IInstructionExecute iInstructionExecute = new InstructionExecute(this);
        cryptoCreator = new CryptoCreator();
        //For Participant
        CompanyNetControlCenter.instance.setGui(this);







        primaryStage.setTitle("MSA | Mosbach Security Agency");

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #336699;");

        Button executeButton = new Button("Execute");
        executeButton.setPrefSize(100, 20);

        Button closeButton = new Button("Close");
        closeButton.setPrefSize(100, 20);

        TextArea commandLineArea = new TextArea();
        commandLineArea.setWrapText(true);



        outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);
        //outputArea.appendText("testestt");

        //------------------



        executeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                System.out.println("--- execute ---");
                iInstructionExecute.instructionExecute(commandLineArea.getText().trim());
                /*
                ParserInstruction showAlgo = new ShowAlgo();
                showAlgo.parse(commandLineArea.getText());

                System.out.println(commandLineArea.getText());
                String[] test = commandLineArea.getText().split(" ");
*/
               /* if (commandLineArea.getText().matches("Test (.+) huhu") && test.length == 3){
                    System.out.println("GehtSuper");
                }
*/
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //System.exit(0); // veraendert
                primaryStage.close();  // veraendert
            }
        });


        hBox.getChildren().addAll(executeButton, closeButton);

        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(25, 25, 25, 25));
        vbox.getChildren().addAll(hBox, commandLineArea, outputArea);

        Scene scene = new Scene(vbox, 950, 500);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                    switch (keyEvent.getCode()){
                        case F3:
                            System.out.println("F3: Debug-Mode");
                            Configuration.instance.debug = !Configuration.instance.debug ;
                            if (Configuration.instance.debug) {
                                writeTextAreaGui("Debug-Mode on");
                            } else {
                                writeTextAreaGui("Debug-Mode off");
                            }
                            break;
                        case F5:
                            System.out.println("F5 wurde gedrueckt -->Execute");
                            iInstructionExecute.instructionExecute(commandLineArea.getText().trim());
                            break;

                        case F8:
                            // Aktuellstes LogFile ausdrucken
                            System.out.println("F8 wurde gedrueckt -->Logfile");
                            writeTextAreaGui(GetLatestLog.instance.getLatestLog());

                            break;



                    }

            }
        });



        primaryStage.setScene(scene);
        primaryStage.show();




    }

    public void writeTextAreaGui(String text ){
       // outputArea.clear();
        outputArea.appendText(text+"\n");
    }

    public ICryptoCreator getCryptoCreator() {
        return cryptoCreator;
    }
}