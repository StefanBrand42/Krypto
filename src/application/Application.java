package application;

import gui.GUI;


import org.hsqldb.lib.HsqlArrayHeap;
import persistence.HSQLDB;
import persistence.ShowChanelDataBase;


import java.util.ArrayList;

public class Application {
    public static void main(String... args) {
        // hsqldb demo
        HSQLDB.instance.setupConnection();


        // am besten Reigenfolgen ander herum loeschen wie erstellt, dann ken Problem mit dem Fremschlueesel verknuepfungen
        // Fremdschlueesel sinnvoll wegen Datenintigritaet
        ArrayList<String> paricipantNames = HSQLDB.instance.showParicipantNames();
        if (paricipantNames != null){
            for (String participantName: paricipantNames) {
                HSQLDB.instance.dropTablePostbox(participantName);
            }
        }


        HSQLDB.instance.dropTableMessages();
        HSQLDB.instance.dropTableChannel();
        HSQLDB.instance.dropTableParticipants();
        HSQLDB.instance.dropTableTypes(); // zum schluss das loeschen wo ander Fremdschluessel verkuepfungen haben
        HSQLDB.instance.dropTableAlgoithms();



        HSQLDB.instance.createTableAlgoithms();
        HSQLDB.instance.createTableTypes();
        HSQLDB.instance.createTableParticipants();
        HSQLDB.instance.createTableChannel();
        HSQLDB.instance.createTableMessages();



        HSQLDB.instance.insertDataTableTypes("normal");
        HSQLDB.instance.insertDataTableTypes("intruder");

        HSQLDB.instance.insertDataTableParticipants("branch_hkg", 1);
        HSQLDB.instance.insertDataTableParticipants("branch_cpt", 1);
        HSQLDB.instance.insertDataTableParticipants("branch_sfo", 1);
        HSQLDB.instance.insertDataTableParticipants("branch_syd", 1);
        HSQLDB.instance.insertDataTableParticipants("branch_wuh", 1);
        HSQLDB.instance.insertDataTableParticipants("msa", 2);

        ArrayList<String> paricipantNames2 = HSQLDB.instance.showParicipantNames();
        if (paricipantNames2 != null){
            for (String participantName: paricipantNames2) {
                HSQLDB.instance.createTablePostbox(participantName);
            }
        }



        HSQLDB.instance.insertDataTableChannel("hkg_wuh", "branch_hkg", "branch_wuh");
        HSQLDB.instance.insertDataTableChannel("hkg_cpt", "branch_hkg", "branch_cpt");
        HSQLDB.instance.insertDataTableChannel("cpt_syd", "branch_cpt", "branch_syd");
        HSQLDB.instance.insertDataTableChannel("syd_sfo", "branch_syd", "branch_sfo");

        //ArrayList<ShowChanelDataBase> showChanelDataBaseArrayList = HSQLDB.instance.showChannels();
        //boolean test12 = HSQLDB.instance.participantExist("branch_hkg");
        //boolean test13 = HSQLDB.instance.participantExist("fdgfdg");




        //Start der Gui
        javafx.application.Application.launch(GUI.class, args);

        //javafx.stage.Screen.getPrimary().getVisualBounds().


        // GUI.getOutputArea().appendText("Huhuhhhh");


        HSQLDB.instance.shutdown();
        System.out.println("Testsdsd");

        //GUI.getPrimaryStage();













    }
}