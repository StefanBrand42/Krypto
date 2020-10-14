package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import networkCompany.CompanyNetControlCenter;
import persistence.HSQLTableChannel;

public class Help extends ParserInstruction {
    public Help(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {

        if (commandLine.equals("?")){
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("To Enter a command you can press the \"Execute\" Button or use [F5].");
            gui.writeTextAreaGui("[F3] to enter/leave Debug-Mode.");
            gui.writeTextAreaGui("[F8] to show the most recent logfile.");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("Available Commands are:");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- Show available algorithms. ---");
            gui.writeTextAreaGui("show algorithm");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- Encrypt and decrypt a message. ---");
            gui.writeTextAreaGui("encrypt message \"[message]\" using [algorithm] and keyfile [filename]");
            gui.writeTextAreaGui("decrypt message \"[message]\" using [algorithm] and keyfile [filename]");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- For Cracking there is one with keyfile for RSA and one without for SHIFT, because RSA needs the Publickey. ---");
            gui.writeTextAreaGui("crack encrypted message \"[message]\" using [algorithm] and keyfile [filename]");
            gui.writeTextAreaGui("crack encrypted message \"[message]\" using [algorithm]");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- Register a participant. ---");
            gui.writeTextAreaGui("register participant [name] with type [normal | intruder]");
            gui.writeTextAreaGui("--- For Simulation there are following participants already registered: ---");
            gui.writeTextAreaGui("branch_hkg        normal\nbranch_cpt         normal\nbranch_sfo         normal\nbranch_syd         normal\nbranch_wuh        normal\nmsa                     intruder");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- Create a channel ---");
            gui.writeTextAreaGui("create channel [name] from [participant01] to [participant02]");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- Show available channels ---");
            gui.writeTextAreaGui("show channel");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- Remove a channel ---");
            gui.writeTextAreaGui("drop channel [name]");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- Intrude a channel ---");
            gui.writeTextAreaGui("intrude channel [name] by [participant]");
            gui.writeTextAreaGui("");
            gui.writeTextAreaGui("--- Send a message ---");
            gui.writeTextAreaGui("send message \"[message]\" from [participant01] to [participant02] using [algorithm] and keyfile [filename]");
            gui.writeTextAreaGui("");

        }else{
            super.parse(commandLine,gui);
        }

    }
}
