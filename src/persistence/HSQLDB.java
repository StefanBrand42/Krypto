package persistence;

import configuration.Configuration;
import networkCampany.ParticipantTyp;

import java.sql.*;
import java.util.ArrayList;

public enum HSQLDB {
    instance;

    private Connection connection;

    public void setupConnection() {
        System.out.println("--- setupConnection");

        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String databaseURL = Configuration.instance.driverName + Configuration.instance.databaseFile;
            connection = DriverManager.getConnection(databaseURL, Configuration.instance.username, Configuration.instance.password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private synchronized void update(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlStatement);

            if (result == -1) {
                System.out.println("error executing " + sqlStatement);
            }

            statement.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    private int getNextID(String table) {
        int nextID = 0;

        try {
            String sqlStatement = "SELECT max(id) FROM " + table;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {
                nextID = resultSet.getInt(1);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return nextID;
    }

    public void dropTableAlgoithms() {
        System.out.println("--- dropTableAlgoithms");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE algorithms");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
    }

    public void createTableAlgoithms() {
        System.out.println("--- createTableAlgoithms");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE algorithms ( ");
        sqlStringBuilder01.append("id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("name VARCHAR(10) NOT NULL").append(",");
        sqlStringBuilder01.append("PRIMARY KEY (id)");
        sqlStringBuilder01.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());
        update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("CREATE UNIQUE INDEX idxAlgorithms ON algorithms (name)");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        update(sqlStringBuilder02.toString());
    }

    public void insertDataTableAlgoithms(String name) {
        int nextID = getNextID("algorithms") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO algorithms (").append("id").append(",").append("name").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append(nextID).append(",").append("'").append(name).append("'");
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        update(sqlStringBuilder.toString());
    }

    public void dropTableTypes() {
        System.out.println("--- dropTableTypes");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE types");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
    }

    public void createTableTypes() {
        System.out.println("--- createTableTypes");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE types ( ");
        sqlStringBuilder01.append("id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("name VARCHAR(10) NOT NULL").append(",");
        sqlStringBuilder01.append("PRIMARY KEY (id)");
        sqlStringBuilder01.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());
        update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("CREATE UNIQUE INDEX idxTypes ON types (name)");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        update(sqlStringBuilder02.toString());
    }

    public void insertDataTableTypes(String name) {
        int nextID = getNextID("types") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO types (").append("id").append(",").append("name").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append(nextID).append(",").append("'").append(name).append("'");
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        update(sqlStringBuilder.toString());
    }

    public void dropTableParticipants() {
        System.out.println("--- dropTableParticipants");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE participants");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
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
        update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("CREATE UNIQUE INDEX idxParticipants ON types (name)");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        update(sqlStringBuilder02.toString());

        StringBuilder sqlStringBuilder03 = new StringBuilder();
        sqlStringBuilder03.append("ALTER TABLE participants ADD CONSTRAINT fkParticipants01 ");
        sqlStringBuilder03.append("FOREIGN KEY (type_id) ");
        sqlStringBuilder03.append("REFERENCES types (id) ");
        sqlStringBuilder03.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder03.toString());

        update(sqlStringBuilder03.toString());
    }

    public void insertDataTableParticipants(String name, int typeID) {
        int nextID = getNextID("participants") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO participants (").append("id").append(",").append("name").append(",").append("type_id").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append(nextID).append(",").append("'").append(name).append("'").append(",").append(typeID);
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        update(sqlStringBuilder.toString());
    }
    public void insertDataTableParticipants(String name, ParticipantTyp participantTyp) {
        int id = 0;
        try {
            String sqlStatement = "SELECT id FROM types WHERE name = '"+participantTyp.toString() +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()){
                id = resultSet.getInt(1);

            }
            insertDataTableParticipants(name,id);
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }


    }


    public int selectParticipantID(String participantName){
        int id =0;
        try {
            String sqlStatement = "SELECT id FROM participants WHERE name = '"+participantName +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()){
                id = resultSet.getInt(1);

            }
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
        }

        return id;
    }


    public void dropTableChannel() {
        System.out.println("--- dropTableChannel");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE channel");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
    }

    public void createTableChannel() {
        System.out.println("--- createTableChannel");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE channel ( ");
        sqlStringBuilder01.append("name VARCHAR(25) NOT NULL").append(",");
        sqlStringBuilder01.append("participant_01 TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("participant_02 TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("PRIMARY KEY (name)");
        sqlStringBuilder01.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());
        update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("ALTER TABLE channel ADD CONSTRAINT fkParticipant01 ");
        sqlStringBuilder02.append("FOREIGN KEY (participant_01) ");
        sqlStringBuilder02.append("REFERENCES participants (id) ");
        sqlStringBuilder02.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        update(sqlStringBuilder02.toString());

        StringBuilder sqlStringBuilder03 = new StringBuilder();
        sqlStringBuilder03.append("ALTER TABLE channel ADD CONSTRAINT fkParticipant02 ");
        sqlStringBuilder03.append("FOREIGN KEY (participant_02) ");
        sqlStringBuilder03.append("REFERENCES participants (id) ");
        sqlStringBuilder03.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder03.toString());
        update(sqlStringBuilder03.toString());
    }

    public void insertDataTableChannel(String name, String participant_01Stri, String participant_02Strin) {
        int participant_01 = selectParticipantID(participant_01Stri);
        int participant_02 = selectParticipantID(participant_02Strin);


        int nextID = getNextID("channel") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO channel (").append("name").append(",").append("participant_01").append(",").append("participant_02").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append("'").append(name).append("'").append(",").append("'").append(participant_01).append("'").append(",").append("'").append(participant_02).append("'");
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        update(sqlStringBuilder.toString());
    }

    public void dropTableMessages() {
        System.out.println("--- dropTableMessages");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE messages");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
    }

    public void createTableMessages() {
        System.out.println("--- createTableMessages");

        StringBuilder sqlStringBuilder01 = new StringBuilder();
        sqlStringBuilder01.append("CREATE TABLE messages ( ");
        sqlStringBuilder01.append("id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("participant_from_id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("participant_to_id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("plain_message VARCHAR(50) NOT NULL").append(",");
        sqlStringBuilder01.append("algorithm_id TINYINT NOT NULL").append(",");
        sqlStringBuilder01.append("encrypted_message VARCHAR(50) NOT NULL").append(",");
        sqlStringBuilder01.append("keyfile VARCHAR(20) NOT NULL").append(",");
        sqlStringBuilder01.append("timestamp INTEGER").append(",");
        sqlStringBuilder01.append("PRIMARY KEY (id)");
        sqlStringBuilder01.append(" )");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder01.toString());
        update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("ALTER TABLE messages ADD CONSTRAINT fkParticipant_from_id ");
        sqlStringBuilder02.append("FOREIGN KEY (participant_from_id) ");
        sqlStringBuilder02.append("REFERENCES participants (id) ");
        sqlStringBuilder02.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        update(sqlStringBuilder02.toString());

        StringBuilder sqlStringBuilder03 = new StringBuilder();
        sqlStringBuilder03.append("ALTER TABLE messages ADD CONSTRAINT fkParticipant_to_id ");
        sqlStringBuilder03.append("FOREIGN KEY (participant_to_id) ");
        sqlStringBuilder03.append("REFERENCES participants (id) ");
        sqlStringBuilder03.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder03.toString());
        update(sqlStringBuilder03.toString());

        StringBuilder sqlStringBuilder04 = new StringBuilder();
        sqlStringBuilder04.append("ALTER TABLE messages ADD CONSTRAINT fkAlgorithm_id ");
        sqlStringBuilder04.append("FOREIGN KEY (algorithm_id) ");
        sqlStringBuilder04.append("REFERENCES algorithms (id) ");
        sqlStringBuilder04.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder04.toString());
        update(sqlStringBuilder04.toString());
    }

    public void insertDataTableMessages(int participant_from_id, int participant_to_id, String plain_message, int algorithm_id, String encrypted_message, String keyfile, int timestamp) {
        int nextID = getNextID("messages") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO messages (").append("id ").append(",").append("participant_from_id").append(",").append("participant_to_id ").append(",").append("plain_message").append(",").append("algorithm_id").append(",").append("encrypted_message").append(",").append("keyfile").append(",").append("timestamp").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append(nextID).append(",").append(participant_from_id).append(",").append(participant_to_id).append(",").append("'").append(plain_message).append("'").append(",").append(algorithm_id).append(",").append("'").append(encrypted_message).append("'").append(",").append("'").append(keyfile).append("'").append(",").append(timestamp);
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        update(sqlStringBuilder.toString());
    }


    public void dropTablePostbox(String participant_name) {
        System.out.println("--- dropTablePostbox_"+participant_name);

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE postbox_"+participant_name);
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        update(sqlStringBuilder.toString());
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
        update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("ALTER TABLE postbox_").append(participant_name).append(" ADD CONSTRAINT fkParticipant_from_id ");
        sqlStringBuilder02.append("FOREIGN KEY (participant_from_id) ");
        sqlStringBuilder02.append("REFERENCES participants (id) ");
        sqlStringBuilder02.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        update(sqlStringBuilder02.toString());

    }

    public void insertDataTablePostbox(String participant_name,int participant_from_id, String message, int timestamp) {
        int nextID = getNextID("postbox_"+participant_name) + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO postbox_").append(participant_name).append("(").append("id").append(",").append("participant_from_id").append(",").append("message").append(",").append("timestamp").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append(nextID).append(",").append(participant_from_id).append(",").append("'").append(message).append("'").append(",").append(timestamp);
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        update(sqlStringBuilder.toString());
    }


    public ArrayList<ShowChanelDataBase> showChannels(){

        try {
            String sqlStatement = "SELECT * FROM channel";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            ArrayList<ShowChanelDataBase> showChanelDataBaseArrayList = new ArrayList<>();

            while (resultSet.next()){
                String name = resultSet.getString(1);
                int participant01 = resultSet.getInt(2);
                int participant02 = resultSet.getInt(3);
                String nameparticipant01 = null;
                String nameparticipant02 = null;
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery("SELECT name FROM participants WHERE id = "+participant01);
                while (resultSet2.next()){
                    nameparticipant01= resultSet2.getString(1);
                }
                Statement statement3 = connection.createStatement();
                ResultSet resultSet3 = statement3.executeQuery("SELECT name FROM participants WHERE id = "+participant02);
                while (resultSet3.next()){
                    nameparticipant02= resultSet3.getString(1);
                }

                ShowChanelDataBase showChanelDataBase = new ShowChanelDataBase(name,nameparticipant01,nameparticipant02);
                showChanelDataBaseArrayList.add(showChanelDataBase);
            }
            resultSet.close();
            return showChanelDataBaseArrayList;
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return  null;
        }
    }

    public ArrayList<String> showParicipantNames (){
        try {
            String sqlStatement = "SELECT name FROM participants";
            Statement statement = connection.createStatement();
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


    public boolean dropOneChanel(String channelName){
        try {

            String sqlStatement = "SELECT COUNT(name) FROM channel WHERE name = '"+ channelName +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            resultSet.next();
            int total = resultSet.getInt(1);
            if (total>0){
                String sqlStatement2 = "DELETE FROM channel WHERE name = '"+ channelName +"'";
                ResultSet resultSet2 = statement.executeQuery(sqlStatement2);
                return true;
            }else {
                return false;
            }

        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return false;
        }

    }

    public boolean participantExist (String participantName){
        try {
            String sqlStatement = "SELECT COUNT(name) FROM participants WHERE name = '"+ participantName +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            resultSet.next();
            int total = resultSet.getInt(1);
            if (total>0){
                return true;
            }else {
                return false;
            }
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return false;
        }

    }



    public void shutdown() {
        System.out.println("--- shutdown");

        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            connection.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }
}