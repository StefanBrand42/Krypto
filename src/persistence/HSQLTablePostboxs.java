package persistence;

import java.time.Instant;

public enum HSQLTablePostboxs {
    instance;

    public void dropTablePostbox(String participant_name) {
        System.out.println("--- dropTablePostbox_"+participant_name);

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE postbox_"+participant_name);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public void createTablePostbox(String participant_name) {
        System.out.println("--- createTablePostbox_"+participant_name);

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE postbox_").append (participant_name).append("(");
        sqlStringBuilder01.append("id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("participant_from_id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("message VARCHAR(50) NOT NULL").append(",");
        sqlStringBuilder01.append("timestamp INTEGER").append(",");
        sqlStringBuilder01.append("PRIMARY KEY (id)");
        sqlStringBuilder01.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());
        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("ALTER TABLE postbox_").append(participant_name).append(" ADD CONSTRAINT fkParticipant_from_id ");
        sqlStringBuilder02.append("FOREIGN KEY (participant_from_id) ");
        sqlStringBuilder02.append("REFERENCES participants (id) ");
        sqlStringBuilder02.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        HSQLDB.instance.update(sqlStringBuilder02.toString());

    }

    public void insertDataTablePostbox(String participant_name,int participant_from_id, String message) {
        int timestamp = (int) Instant.now().getEpochSecond();
        int nextID = HSQLDB.instance.getNextID("postbox_"+participant_name) + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO postbox_").append(participant_name).append("(").append("id").append(",").append("participant_from_id").append(",").append("message").append(",").append("timestamp").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append(nextID).append(",").append(participant_from_id).append(",").append("'").append(message).append("'").append(",").append(timestamp);
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

}
