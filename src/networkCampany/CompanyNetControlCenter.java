package networkCampany;

import persistence.HSQLTableChannel;
import persistence.HSQLTableParticipants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum CompanyNetControlCenter {
    instance;

    private Map<Integer, IParticipant> participantHashMap = new HashMap<>();
    private Map<String, IChannel> channelHashMap = new HashMap<>();


    public void addParicipantToMap (Participant participant){
        participantHashMap.put(participant.getId(),participant);
    }

    public void addChannelToMap (Channel channel){
        channelHashMap.put(channel.getName(), channel);


    }

    public boolean isChannelExist(String channelName){
        return channelHashMap.containsKey(channelName);
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
}
