package networkCampany;

import com.google.common.eventbus.Subscribe;

public class Participant extends Subscriber {
    private int id;
    private String Name;
    private ParticipantTyp participantTyp;

    public Participant(int id, String name, ParticipantTyp participantTyp) {
        this.id = id;
        Name = name;
        this.participantTyp = participantTyp;
    }

}
