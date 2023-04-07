package com.example.notecast.utils;
import com.example.notecast.models.database.*;

import java.util.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class DatabaseHandler {

    public static String getHash(String password)
    {
        String hashValue = "";
        byte[] pass = password.getBytes(StandardCharsets.UTF_8);
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(pass);
            byte[] digestBytes = messageDigest.digest();
            hashValue = new String(digestBytes, StandardCharsets.UTF_8);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hashValue;
    }

    public static User login(String user_email, String user_password)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notecastdbbeta", "root", "");
//            Connection connection = DriverManager.getConnection("jdbc:neo4j+s://a1264504.databases.neo4j.io:7687/notecastdbbeta",
//                    "neo4j", "9l54kNHf5dMxrTgswgUt3guVSJ_Mm3z9ad3tYEn4dw4");

            Statement statement = connection.createStatement();
            String query;
            PreparedStatement statement1;
            User user;

            query = "select * from userinfo where user_email = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setString(1, user_email);
            ResultSet resultSet = statement1.executeQuery();

            String useremail = new String(), password = new String(), username = new String(), userprofession = new String(), userqosid = new String();
            boolean isFound = false;



            while(resultSet.next())
            {
                useremail = resultSet.getString("user_email");
                password = resultSet.getString("user_password");

                if(useremail.equals(user_email)) {
                    if (password.equals(user_password)) {
                        System.out.println("User email & password matched");
                        username = resultSet.getString("user_name");
                        userprofession = resultSet.getString("user_profession");
                        userqosid = resultSet.getString("qos_id");
                        isFound = true;
                        break;
                    }
                }
            }

            if(!isFound)
            {
                System.out.println("User email & password don't match");
                return null;
            }

            else
            {
                // add notebooks
                ArrayList<Notebook> notebooks = new ArrayList<>();


                query = "select * from notebookinfo where user_email = ?";
                statement1 = connection.prepareStatement(query);
                statement1.setString(1, user_email);
                ResultSet resultSetNotebook = statement1.executeQuery();


                while(resultSetNotebook.next())
                {
                    ArrayList<Topic> topics = new ArrayList<>();
                    //topics = null;
                    int notebookid = resultSetNotebook.getInt("notebook_id");
                    String notebooktitle = resultSetNotebook.getString("notebook_title");
                    int notebookpriority = resultSetNotebook.getInt("notebook_priority");
                    Timestamp notebooktimeCreated = resultSetNotebook.getTimestamp("notebook_date_created");
                    Timestamp notebooklastEdited = resultSetNotebook.getTimestamp("notebook_last_edit");

                    //add topics

                    query = "select * from topicinfo where notebook_id = ?";
                    statement1 = connection.prepareStatement(query);
                    statement1.setInt(1, notebookid);
                    ResultSet resultSetTopic = statement1.executeQuery();



                    while(resultSetTopic.next())
                    {
                        int topicid = resultSetTopic.getInt("topic_id");
                        String topic_title = resultSetTopic.getString("topic_title");
                        Timestamp topic_date_created = resultSetTopic.getTimestamp("topic_date_created");
                        Timestamp topic_last_edit = resultSetTopic.getTimestamp("topic_last_edit");
                        //int content_id = resultSetTopic.getInt("contents_id");

                        query = "select * from contentsinfo where topic_id = ?";
                        statement1 = connection.prepareStatement(query);
                        statement1.setInt(1, topicid);
                        ResultSet resultSetContent = statement1.executeQuery();

                        Content content = null;

                        // add contents

                        while(resultSetContent.next())
                        {
                            int contentid = resultSetContent.getInt("contents_id");
                            String baseStyles = resultSetContent.getString("contents_base_styles");
                            String basehtml = resultSetContent.getString("contents_base_html");
                            String basejs = resultSetContent.getString("contents_base_js");
                            String rootfolderlocation = resultSetContent.getString("contents_root_folder_location");

                            content = new Content(contentid, basehtml, baseStyles,basejs, rootfolderlocation);
                        }
                        topics.add(new Topic(topicid , topic_title , topic_date_created , topic_last_edit , notebookid, content));
                    }

                    notebooks.add(new Notebook(notebookid, notebooktitle, notebookpriority, notebooktimeCreated , notebooklastEdited , useremail, topics));
                }

                // add qos

                query = "select * from quality_of_services where qos_id = ?";
                statement1 = connection.prepareStatement(query);
                statement1.setString(1, userqosid);
                ResultSet resultSetQos = statement1.executeQuery();

                QualityOfService qos = null;

                while(resultSetQos.next())
                {
                    String qos_id = resultSetQos.getString("qos_id");
                    String qos_service_type = resultSetQos.getString("qos_service_type");
                    qos = new QualityOfService(1, qos_service_type);
                }

                user = new User(username , useremail, user_password, userprofession , qos, notebooks);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static User signup(String user_name, String user_email, String user_password , String user_profession)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notecastdbbeta", "root", "");
           // Connection connection = DriverManager.getConnection("jdbc:neo4j+s://a1264504.databases.neo4j.io:7687/notecastdbbeta",
                  //  "neo4j", "9l54kNHf5dMxrTgswgUt3guVSJ_Mm3z9ad3tYEn4dw4");

            Statement statement = connection.createStatement();

            User user;
            String query;
            PreparedStatement statement1;

            query = "select * from userinfo where user_email = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setString(1, user_email);
            ResultSet resultSet = statement1.executeQuery();

            if(resultSet.next())
            {
                System.out.println("User email already in use");
                return null;
            }

            QualityOfService qualityOfService;
            if(user_profession.toLowerCase().equals("student")) {
                qualityOfService = new QualityOfService(1, null);
            }
            else if(user_profession.toLowerCase().equals("businessman"))
            {
                qualityOfService = new QualityOfService(0, null);
            }
            else
            {
                qualityOfService = new QualityOfService(2, null);
            }

            //query = "insert into quality_of_services (qos_id, qos_service_type) values ( ? , ?)";
            //statement1 = connection.prepareStatement(query);
            //statement1.setInt(1, qualityOfService.getId());
            //statement1.setString(2, qualityOfService.getType());
            //statement1.executeUpdate();

            user = new User(user_name, user_email, user_password , user_profession, qualityOfService, null);

            query = "insert into userinfo (user_name ,user_email, user_password, user_profession, qos_id) values (? , ? , ? , ? , ?)";
            if (query != null && !query.isEmpty()) {
                statement1 = connection.prepareStatement(query);
                statement1.setString(1, user.getName());
                statement1.setString(2, user.getEmail());
                statement1.setString(3, user.getPassword());
                statement1.setString(4, user.getProfession());
                statement1.setInt(5, qualityOfService.getId());
                statement1.executeUpdate();
            }

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean delete()
    {
        // return user object
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notecastdbbeta", "root", "");
            Statement statement = connection.createStatement();

            String query;
            PreparedStatement statement1;




            String useremail = new String(), password = new String(),username = new String(), userprofession = new String(), userqosid = new String();
            boolean isFound = false;
            User user;

            query = "select * from userinfo where user_email = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setString(1, useremail);
            ResultSet resultSet = statement1.executeQuery();

            while(resultSet.next())
            {
                //
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean save(User user)
    {
        // write from user object to database
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notecastdbbeta", "root", "8664");
            Statement statement = connection.createStatement();

            String query;
            PreparedStatement statement1;
            ResultSet resultSet;

            // updating user table
            // changing user email is not implemented
            query = "update userinfo set user_name = ?, user_password = ?, user_profession = ? where user_email = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setString(1, user.getName());
            statement1.setString(2, user.getPassword());
            statement1.setString(3, user.getProfession());
            statement1.setString(4, user.getEmail());
            statement1.executeUpdate();

            // updating qos table

            query = "update quality_of_services set qos_service_type = ? where qos_id = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setString(1, user.getQos().getType());
            statement1.setInt(2, user.getQos().getId());
            statement1.executeUpdate();

            // updating notebooks
            // using loops to iterate notebooks one by one
            if(user.getNotebooks() != null) {
                for (var notebook : user.getNotebooks()) {
                    // updating topic
                    if(notebook.getTopics()!= null) {
                        for (var topic : notebook.getTopics()) {
                            // updating content
                            var content = topic.getContent();
                            query = "update contentsinfo set contents_base_html = ? , contents_base_styles = ?  ,contents_base_js = ?,contents_root_folder_location = ?   where contents_id = ?";
                            statement1 = connection.prepareStatement(query);
                            statement1.setString(1, content.getBaseHTML());
                            statement1.setString(2, content.getBaseStyles());
                            statement1.setString(3, content.getBaseJS());
                            statement1.setString(4, content.getRootLocation());
                            statement1.setInt(5, content.getId());
                            statement1.executeUpdate();

                            // updating topic

                            query = "update topicinfo set topic_title = ? , topic_last_edit = ?  where topic_id = ?";
                            statement1 = connection.prepareStatement(query);
                            statement1.setString(1, topic.getTitle());
                            statement1.setTimestamp(2, topic.getLastEdited());
                            statement1.setInt(3, topic.getId());
                            statement1.executeUpdate();
                        }
                    }

                    // updating notebook

                    query = "update notebookinfo set notebook_title = ? , notebook_priority = ?, notebook_last_edit = ?   where notebook_id = ?";
                    statement1 = connection.prepareStatement(query);
                    statement1.setString(1, notebook.getTitle());
                    statement1.setInt(2, notebook.getPriority());
                    statement1.setTimestamp(3, notebook.getLastEdited());
                    statement1.setInt(4, notebook.getId());
                    statement1.executeUpdate();

                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static Content createContent(String baseHtml, String baseStyle, String baseJs, String rootFolderLocation, int topicID)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notecastdbbeta", "root", "");
            Statement statement = connection.createStatement();

            String query;
            PreparedStatement statement1;
            ResultSet resultSet;

            query = "insert into contentsinfo (contents_base_html,contents_base_styles,contents_base_js,contents_root_folder_location, topic_id) values (? , ? , ? , ?, ?)";
            statement1 = connection.prepareStatement(query);
            statement1.setString(1, baseHtml);
            statement1.setString(2, baseStyle);
            statement1.setString(3, baseJs);
            statement1.setString(4, rootFolderLocation);
            statement1.setInt(5, topicID);
            statement1.executeUpdate();

            query = "select * from contentsinfo where topic_id = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setInt(1, topicID);
            resultSet = statement1.executeQuery();

            int contentId;
            resultSet.next();

            contentId = resultSet.getInt("contents_id");

            return new Content(contentId , baseHtml , baseJs, baseStyle , rootFolderLocation);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Topic createTopic(String title, Timestamp dateCreated, Timestamp lastEdit , int notebookid, Content content)
    {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notecastdbbeta", "root", "");
            Statement statement = connection.createStatement();

            String query;
            PreparedStatement statement1;
            ResultSet resultSet;

            query = "insert into topicinfo (topic_title ,topic_date_created,topic_last_edit,notebook_id) values ( ? , ? , ? , ?)";
            statement1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, title);
            statement1.setTimestamp(2, dateCreated);
            statement1.setTimestamp(3, lastEdit);
            statement1.setInt(4, notebookid);
            statement1.executeUpdate();

            resultSet = statement1.getGeneratedKeys();
            resultSet.next();

            int topicid = resultSet.getInt(1);

            return new Topic(topicid, title, dateCreated, lastEdit, notebookid, content);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Notebook createNotebook(String title,int priority, Timestamp dateCreated, Timestamp lastEdit , String usermail, Topic topic)
    {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notecastdbbeta", "root", "");
            Statement statement = connection.createStatement();

            String query;
            PreparedStatement statement1;
            ResultSet resultSet;

            query = "insert into notebookinfo (notebook_title ,notebook_priority,notebook_date_created,notebook_last_edit,user_email) values ( ? , ? , ? , ?, ?)";
            statement1 = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement1.setString(1, title);
            statement1.setInt(2, priority);
            statement1.setTimestamp(3, dateCreated);
            statement1.setTimestamp(4, lastEdit);
            statement1.setString(5,usermail);
            statement1.executeUpdate();

            resultSet = statement1.getGeneratedKeys();
            resultSet.next();

            int notebookid = resultSet.getInt(1);

            return new Notebook(notebookid, title, priority,dateCreated, lastEdit,usermail, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean writeContent(Content content)
    {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/notecastdbbeta", "root", "8664");
            Statement statement = connection.createStatement();

            String query;
            PreparedStatement statement1;
            ResultSet resultSet;

            query = "select * from contentsinfo where content_id = ?";
            statement1 = connection.prepareStatement(query);
            statement1.setInt(1, content.getId());
            resultSet = statement1.executeQuery();

            while(resultSet.next())
            {
                query = "update contentsinfo set contents_base_html = ? , contents_base_styles = ?  ,contents_base_js = ?,contents_root_folder_location = ?   where contents_id = ?";
                statement1 = connection.prepareStatement(query);
                statement1.setString(1, content.getBaseHTML());
                statement1.setString(2, content.getBaseStyles());
                statement1.setString(3, content.getBaseJS());
                statement1.setString(4, content.getRootLocation());
                statement1.setInt(5, content.getId());
                statement1.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
