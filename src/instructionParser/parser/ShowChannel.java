package instructionParser.parser;

import gui.GUI;
import instructionParser.ParserInstruction;
import networkCampany.Channel;
import networkCampany.CompanyNetControlCenter;
import networkCampany.IChannel;
import persistence.HSQLDB;
import persistence.HSQLTableChannel;


import java.util.ArrayList;
import java.util.Map;

public class ShowChannel  extends ParserInstruction {
    public ShowChannel(ParserInstruction sucessor) {
        this.setSucessor(sucessor);
    }

    public void parse(String commandLine, GUI gui) {

        if (commandLine.equals("show channel")){
            gui.writeTextAreaGui("Instruction show channel");
            // DB
            /*ArrayList<ShowChanelDataBase>  showChanelDataBaseArrayList= HSQLTableChannel.instance.showChannels();
            StringBuilder stringBuilder01 = new StringBuilder();
            for (ShowChanelDataBase showChanelData: showChanelDataBaseArrayList) {
               stringBuilder01.append(showChanelData.getNameChanel() +" | "+ showChanelData.getNameParticipant01()+" and "+showChanelData.getNameParticipant02()+"\n") ;
            }

             */
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
