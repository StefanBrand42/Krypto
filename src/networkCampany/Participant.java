package networkCampany;

public class Participant extends Subscriber implements IParticipant {
    private int id;
    private String name;
    private ParticipantTyp participantTyp;

    public Participant(int id, String name, ParticipantTyp participantTyp) {
        this.id = id;
        this.name = name;
        this.participantTyp = participantTyp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ParticipantTyp getParticipantTyp() {
        return participantTyp;
    }
}
