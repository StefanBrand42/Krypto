package application;

import configuration.Configuration;
import crypto.AlgorithmsTyp;
import crypto.CryptoCreator;
import gui.GUI;


import networkCompany.*;
import persistence.*;


import java.util.ArrayList;
import java.util.List;
// Von Matrikelnummern 3684504 und 5686413
public class Application {
    public static void main(String... args) {
        // Von Matrikelnummern 3684504 und 5686413
        // hsqldb demo
        HSQLDB.instance.setupConnection();

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

        List<String> algoTypsInComponents= Configuration.instance.getAlgoTypsFromFileNames();
        for (String algoTypString:algoTypsInComponents) {
            HSQLTableAlgo.instance.insertDataTableAlgoithms(algoTypString);
        }

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


        CompanyNetControlCenter.instance.startInitMapsFromDB();

        //Start der Gui
        javafx.application.Application.launch(GUI.class, args);

        HSQLDB.instance.shutdown();



    }
}