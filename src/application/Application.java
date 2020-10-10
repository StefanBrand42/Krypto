package application;

import gui.GUI;


import networkCampany.*;
import org.hsqldb.lib.HsqlArrayHeap;
import persistence.*;


import java.util.ArrayList;

public class Application {
    public static void main(String... args) {
        // hsqldb demo
        HSQLDB.instance.setupConnection();


        // am besten Reigenfolgen ander herum loeschen wie erstellt, dann ken Problem mit dem Fremschlueesel verknuepfungen
        // Fremdschlueesel sinnvoll wegen Datenintigritaet
        ArrayList<String> paricipantNames = HSQLTableParticipants.instance.showParicipantNames();
        if (paricipantNames != null){
            for (String participantName: paricipantNames) {
                HSQLTablePostboxs.instance.dropTablePostbox(participantName);
            }
        }


        HSQLTableMessages.instance.dropTableMessages();
        HSQLTableChannel.instance.dropTableChannel();
        HSQLTableParticipants.instance.dropTableParticipants();
        HSQLTableTypes.instance.dropTableTypes(); // zum schluss das loeschen wo ander Fremdschluessel verkuepfungen haben
        HSQLTableAlgo.instance.dropTableAlgoithms();



        HSQLTableAlgo.instance.createTableAlgoithms();
        HSQLTableTypes.instance.createTableTypes();
        HSQLTableParticipants.instance.createTableParticipants();
        HSQLTableChannel.instance.createTableChannel();
        HSQLTableMessages.instance.createTableMessages();



        HSQLTableTypes.instance.insertDataTableTypes("normal");
        HSQLTableTypes.instance.insertDataTableTypes("intruder");

        HSQLTableParticipants.instance.insertDataTableParticipants("branch_hkg", 1);
        HSQLTableParticipants.instance.insertDataTableParticipants("branch_cpt", 1);
        HSQLTableParticipants.instance.insertDataTableParticipants("branch_sfo", 1);
        HSQLTableParticipants.instance.insertDataTableParticipants("branch_syd", 1);
        HSQLTableParticipants.instance.insertDataTableParticipants("branch_wuh", 1);
        HSQLTableParticipants.instance.insertDataTableParticipants("msa", 2);

        ArrayList<String> paricipantNames2 = HSQLTableParticipants.instance.showParicipantNames();
        if (paricipantNames2 != null){
            for (String participantName: paricipantNames2) {
                HSQLTablePostboxs.instance.createTablePostbox(participantName);
            }
        }



        HSQLTableChannel.instance.insertDataTableChannel("hkg_wuh", "branch_hkg", "branch_wuh");
        HSQLTableChannel.instance.insertDataTableChannel("hkg_cpt", "branch_hkg", "branch_cpt");
        HSQLTableChannel.instance.insertDataTableChannel("cpt_syd", "branch_cpt", "branch_syd");
        HSQLTableChannel.instance.insertDataTableChannel("syd_sfo", "branch_syd", "branch_sfo");

        //ArrayList<ShowChanelDataBase> showChanelDataBaseArrayList = HSQLDB.instance.showChannels();
        //boolean test12 = HSQLDB.instance.participantExist("branch_hkg");
        //boolean test13 = HSQLDB.instance.participantExist("fdgfdg");

        CompanyNetControlCenter.instance.startInitMapsFromDB();


        //Start der Gui
        javafx.application.Application.launch(GUI.class, args);

        //javafx.stage.Screen.getPrimary().getVisualBounds().


        // GUI.getOutputArea().appendText("Huhuhhhh");

        // Test Eventbus
        IParticipant test = new Participant(1,"testParti1", ParticipantTyp.normal);
        IParticipant test2 = new Participant(1,"testParti2", ParticipantTyp.normal);
        IChannel testCh = new Channel("Hallo",test,test2);
        testCh.send("KryptoTest",test2);



        HSQLDB.instance.shutdown();
        System.out.println("Testsdsd");

        //GUI.getPrimaryStage();













    }
}