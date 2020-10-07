package networkCampany;

import java.util.HashMap;
import java.util.Map;

public enum CompanyNetControlCenter {
    instance;

    private Map<Integer,Participant> participantHashMap = new HashMap<>();
    private Map<Integer, Channel> channelHashMap = new HashMap<>();




}
