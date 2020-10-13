package networkCompany;

import gui.GUI;
import persistence.HSQLTableChannel;
import persistence.HSQLTableParticipants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum CompanyNetControlCenter {
    instance;

    private Map<Integer, IParticipant> participantHashMap = new HashMap<>();
    private Map<String, IChannel> channelHashMap = new HashMap<>();
    private GUI gui;





    public void addParicipantToMap (Participant participant){
        participantHashMap.put(participant.getId(),participant);
    }

    public void addChannelToMap (Channel channel){
        channelHashMap.put(channel.getName(), channel);


    }

    public boolean isChannelExist(String channelName){
        return channelHashMap.containsKey(channelName);
    }

    public boolean isChannelExist(String nameParticipant01, String nameParticipant02){
        for (IChannel channel: channelHashMap.values()) {
            if (channel.getParticipant01().getName().equals(nameParticipant01) && channel.getParticipant02().getName().equals(nameParticipant02)){
                return  true;
            }if (channel.getParticipant01().getName().equals(nameParticipant02) && channel.getParticipant02().getName().equals(nameParticipant01)){
                return true;
            }
        }
        return false;

    }


    public  boolean isParticipantExist(String participantName){
        for (IParticipant participant: participantHashMap.values()) {
            if (participant.getName().equals(participantName)){
                return true;
            }
        }
        return  false;
    }


    public  IParticipant getParticipantById(int id){
        return participantHashMap.get(id);
    }

    public IParticipant getParticipantByName(String nameParticipant){
        for (IParticipant participant: participantHashMap.values()) {
            if (participant.getName().equals(nameParticipant)){
                return participant;
            }
        }
        return  null;
    }

    public IChannel getChannelByNamePartic01Part02(String nameParticipant01, String nameParticipant02){
        for (IChannel channel: channelHashMap.values()) {
            if (channel.getParticipant01().getName().equals(nameParticipant01) && channel.getParticipant02().getName().equals(nameParticipant02)){
                return  channel;
            }if (channel.getParticipant01().getName().equals(nameParticipant02) && channel.getParticipant02().getName().equals(nameParticipant01)){
                return channel;
            }
        }

        return null;

    }

    public boolean delateChannel(String channelName){
        boolean exist= isChannelExist(channelName);
        if (exist){
            channelHashMap.remove(channelName);
            return true;
        }else{
            return  false;
        }
    }

    public void createAndAddNewPatricipant(int idPar, String namePart, ParticipantTyp participantTyp){
        IParticipant paricipant = new Participant(idPar,namePart,participantTyp);
        participantHashMap.put(idPar,paricipant);
    }

    public void createAndAddNewChannel(String nameChannel, IParticipant participant01, IParticipant participant02){
        IChannel channel = new Channel(nameChannel,participant01,participant02);
        channelHashMap.put(channel.getName(),channel);

    }


    public void startInitMapsFromDB(){
        ArrayList<IParticipant> participantArrayList = HSQLTableParticipants.instance.getAllParticipant();
        for (IParticipant participant: participantArrayList) {
            participantHashMap.put(participant.getId(),participant);
        }

        ArrayList<IChannel> channelArrayList= HSQLTableChannel.instance.getAllChannels();
        for (IChannel channel:channelArrayList) {
            channelHashMap.put(channel.getName(),channel);

        }

        

    }

    public Map<String, IChannel> getChannelHashMap() {
        return channelHashMap;
    }


    public GUI getGui() {
        return gui;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
