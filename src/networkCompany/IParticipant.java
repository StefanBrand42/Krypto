package networkCompany;

public interface IParticipant {
    int getId();
    String getName();
    ParticipantTyp getParticipantTyp();
    IParticipantIntruderListener getParticipantIntruder();


}
