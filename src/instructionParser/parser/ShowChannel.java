package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import networkCompany.CompanyNetControlCenter;
import networkCompany.IChannel;


import java.util.Map;

public class ShowChannel  extends ParserInstruction {
    public ShowChannel(ParserInstruction successor) {
        this.setSuccessor(successor);
    }

    public void parse(String commandLine, GUI gui) {

        if (commandLine.equals("show channel")){
            //gui.writeTextAreaGui("Instruction show channel");

            // CompanyNetControlCenter
            StringBuilder stringBuilder01 = new StringBuilder();
            Map<String, IChannel> channelMap = CompanyNetControlCenter.instance.getChannelHashMap();
            for (IChannel channel: channelMap.values()) {
                stringBuilder01.append(channel.getName() +" | "+ channel.getParticipant01().getName()+" and "+channel.getParticipant02().getName()+"\n") ;
            }



            gui.writeTextAreaGui(stringBuilder01.toString());
        }else{
            super.parse(commandLine,gui);
        }

    }
}
