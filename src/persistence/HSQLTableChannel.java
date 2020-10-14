package persistence;

import networkCompany.Channel;
import networkCompany.CompanyNetControlCenter;
import networkCompany.IChannel;
import networkCompany.IParticipant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public enum HSQLTableChannel {
    instance;


    public void dropTableChannel() {
        System.out.println("--- dropTableChannel");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE channel");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());

        HSQLDB.instance.update(sqlStringBuilder.toString());
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
        HSQLDB.instance.update(sqlStringBuilder01.toString());

        StringBuilder sqlStringBuilder02 = new StringBuilder();
        sqlStringBuilder02.append("ALTER TABLE channel ADD CONSTRAINT fkParticipant01 ");
        sqlStringBuilder02.append("FOREIGN KEY (participant_01) ");
        sqlStringBuilder02.append("REFERENCES participants (id) ");
        sqlStringBuilder02.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder02.toString());
        HSQLDB.instance.update(sqlStringBuilder02.toString());

        StringBuilder sqlStringBuilder03 = new StringBuilder();
        sqlStringBuilder03.append("ALTER TABLE channel ADD CONSTRAINT fkParticipant02 ");
        sqlStringBuilder03.append("FOREIGN KEY (participant_02) ");
        sqlStringBuilder03.append("REFERENCES participants (id) ");
        sqlStringBuilder03.append("ON DELETE CASCADE");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder03.toString());
        HSQLDB.instance.update(sqlStringBuilder03.toString());
    }

    public void insertDataTableChannel(String name, String participant_01Stri, String participant_02Strin) {
        int participant_01 = HSQLTableParticipants.instance.selectParticipantID(participant_01Stri);
        int participant_02 = HSQLTableParticipants.instance.selectParticipantID(participant_02Strin);


        int nextID = HSQLDB.instance.getNextID("channel") + 1;
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("INSERT INTO channel (").append("name").append(",").append("participant_01").append(",").append("participant_02").append(")");
        sqlStringBuilder.append(" VALUES ");
        sqlStringBuilder.append("(").append("'").append(name).append("'").append(",").append(participant_01).append(",").append(participant_02);
        sqlStringBuilder.append(")");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder.toString());
        HSQLDB.instance.update(sqlStringBuilder.toString());
    }

    public ArrayList<IChannel> getAllChannels(){

        try {
            String sqlStatement = "SELECT * FROM channel";
            Statement statement = HSQLDB.instance.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            ArrayList<IChannel> channelArrayList = new ArrayList<>();

            while (resultSet.next()){
                String name = resultSet.getString(1);
                int participant01Id = resultSet.getInt(2);
                int participant02Id = resultSet.getInt(3);

                IParticipant participant01 = CompanyNetControlCenter.instance.getParticipantById(participant01Id);
                IParticipant participant02 = CompanyNetControlCenter.instance.getParticipantById(participant02Id);
                IChannel channel = new Channel(name,participant01,participant02);
                channelArrayList.add(channel);

            }
            resultSet.close();
            return channelArrayList;
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return  null;
        }
    }


    public boolean dropOneChanel(String channelName) {
        try {

            String sqlStatement = "SELECT COUNT(name) FROM channel WHERE name = '" + channelName + "'";
            Statement statement = HSQLDB.instance.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);
            resultSet.next();
            int total = resultSet.getInt(1);
            if (total > 0) {
                String sqlStatement2 = "DELETE FROM channel WHERE name = '" + channelName + "'";
                ResultSet resultSet2 = statement.executeQuery(sqlStatement2);
                return true;
            } else {
                return false;
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            return false;
        }

    }



}
