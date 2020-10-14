package persistence;

import networkCompany.IParticipant;
import networkCompany.Participant;
import networkCompany.ParticipantTyp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public enum HSQLTableParticipants {
    instance;


    public void dropTableParticipants() {
        System.out.println("--- dropTableParticipants");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE participants");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public void createTableParticipants() {
        System.out.println("--- createTableParticipants");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE participants ( ");
        sqlStringBuilder01.append("id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("name VARCHAR(50) NOT NULL").append(",");
        sqlStringBuilder01.append("type_id TINYINT NULL").append(",");
        sqlStringBuilder01.append("PRIMARY KEY (id)");
        sqlStringBuilder01.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());
        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("CREATE UNIQUE INDEX idxParticipants ON types (name)");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        HSQLDB.instance.update(sqlStringBuilder02.toString());

        StringBuilder sqlStringBuilder03 = new StringBuilder();
        sqlStringBuilder03.append("ALTER TABLE participants ADD CONSTRAINT fkParticipants01 ");
        sqlStringBuilder03.append("FOREIGN KEY (type_id) ");
        sqlStringBuilder03.append("REFERENCES types (id) ");
        sqlStringBuilder03.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder03.toString());

        HSQLDB.instance.update(sqlStringBuilder03.toString());
    }

    public int insertDataTableParticipants(String name, int typeID) {
        int nextID = HSQLDB.instance.getNextID("participants") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO participants (").append("id").append(",").append("name").append(",").append("type_id").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append(nextID).append(",").append("'").append(name).append("'").append(",").append(typeID);
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        HSQLDB.instance.update(sqlStringBuilder.toString());
        return nextID;
    }
    public int insertDataTableParticipants(String name, ParticipantTyp participantTyp) {
        int id = 0;
        try {
            String sqlStatement = "SELECT id FROM types WHERE name = '"+participantTyp.toString() +"'";
            Statement statement = HSQLDB.instance.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()){
                id = resultSet.getInt(1);

            }
            int idParticipant = insertDataTableParticipants(name,id);
            return idParticipant;
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return  0;
        }


    }


    public int selectParticipantID(String participantName){
        int id =0;
        try {
            String sqlStatement = "SELECT id FROM participants WHERE name = '"+participantName +"'";
            Statement statement = HSQLDB.instance.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()){
                id = resultSet.getInt(1);

            }
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }

        return id;
    }



    public ArrayList<String> showParicipantNames (){
        try {
            String sqlStatement = "SELECT name FROM participants";
            Statement statement = HSQLDB.instance.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            ArrayList<String> showPartName = new ArrayList<>();

            while (resultSet.next()){
                showPartName.add(resultSet.getString(1)) ;
            }
            return showPartName;
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return  null;
        }

    }


    public ArrayList<IParticipant> getAllParticipant(){
        try {
            String sqlStatement = "SELECT * FROM participants";
            Statement statement = HSQLDB.instance.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            ArrayList<IParticipant> participantArrayList = new ArrayList<>();

            while (resultSet.next()) {

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                ParticipantTyp participantTyp = null;

                Statement statement2 = HSQLDB.instance.getConnection().createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT name FROM types WHERE id = "+resultSet.getInt(3));
                while (resultSet2.next()){
                    if (resultSet2 != null){
                        participantTyp= ParticipantTyp.valueOf(resultSet2.getString(1));
                    }

                }
                IParticipant participant = new Participant(id,name,participantTyp);

                participantArrayList.add(participant);


            }

            return participantArrayList;



        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return null;
        }
    }


    public boolean participantExist (String participantName){
        try {
            String sqlStatement = "SELECT COUNT(name) FROM participants WHERE name = '"+ participantName +"'";
            Statement statement = HSQLDB.instance.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            resultSet.next();
            int total = resultSet.getInt(1);
            return total > 0;
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return false;
        }

    }

}
