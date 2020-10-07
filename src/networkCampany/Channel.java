package networkCampany;

public class Channel implements  IChannel {
    private String name;
    private IParticipant participant01;
    private IParticipant participant02;

    public Channel(String name, IParticipant participant01, IParticipant participant02) {
        this.name = name;
        this.participant01 = participant01;
        this.participant02 = participant02;
    }


    public String getName() {
        return name;
    }


    public IParticipant getParticipant01() {
        return participant01;
    }


    public IParticipant getParticipant02() {
        return participant02;
    }
}
