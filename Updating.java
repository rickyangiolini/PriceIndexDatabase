//Created by Riccardo Angiolini on July 23rd, 2018.
// Copyright. All Rights Reserved. ©

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.lang.InterruptedException;


public class Updating {
    private Scanner scanString = new Scanner(System.in);
    private Scanner scanNum = new Scanner(System.in);
    private Connect c = new Connect();
    private Connection con = c.getConnection();

    //method for updating clients
    public void updateClientInfo() throws InterruptedException {
        try {
            //the user can select what they wish to update for a client in the database
            //each if and else if is for updating the particular attribute from 1-7
            //before updating any record the users client ID are displayed so they have the ease of knowing what they can edit
            //each update statement is a transaction
            //every update is logged in the logs table
            //a user os only able to update a client if it belongs to them this is where the userID comes into use
            System.out.println("In order to update your client record please select what you would like to update.");
            System.out.println("1- Update Client Name.");
            System.out.println("2- Update Client Location.");
            System.out.println("3- Update Client Potential Order on Core.");
            System.out.println("4- Update Client Potential Order on Novelties.");
            System.out.println("5- Update Client Type.");
            System.out.println("6- Update the Client's number of stores.");
            System.out.println("7- Update the Client's number of employees. ");
            System.out.println("8- Return to the main menu.");
            while (!scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("In order to update your client record please select what you would like to update.");
                System.out.println("1- Update Client Name.");
                System.out.println("2- Update Client Location.");
                System.out.println("3- Update Client Potential Order on Core.");
                System.out.println("4- Update Client Potential Order on Novelties.");
                System.out.println("5- Update Client Type.");
                System.out.println("6- Update the Client's number of stores.");
                System.out.println("7- Update the Client's number of employees. ");
                System.out.println("8- Return to the main menu.");
                scanNum.next();
            }

            int whatToUpdate = scanNum.nextInt();
            //updating a client name
            if (whatToUpdate == 1) {
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM clients");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();
                    //ResultSetMetaData is used to retrieve the names of the attributes of each column
                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8= rm.getColumnName(8);

                    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";
                    if(!rx.isBeforeFirst()){
                        System.out.println("There are no clients in our database.");


                    }else{
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");

                        while (rx.next()) {
                            System.out.format(format, rx.getInt(1),rx.getString(2), rx.getString(3), rx.getInt(4 ) + " yards", rx.getInt(5) + " yards", rx.getString(6), rx.getInt(7) + " stores", rx.getInt(8) + " employees");
                            System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");

                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the client ID in order to update the information for it, so please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not an accepted client ID!");
                        System.out.println("Enter the client ID in order to update the client's information:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client's name to:");
                    String name = scanString.nextLine();
                    PreparedStatement ps = con.prepareStatement("UPDATE clients SET clientName=? WHERE clientID=?");
                    ps.clearParameters();
                    ps.setString(1, name);
                    ps.setInt(2, inputID);

                    int x = ps.executeUpdate();
//
                    con.commit();

                    if (x > 0) {
                        System.out.println("The name for the client has been updated to " + name + ".");

                        CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                        p2.setString(1, "UPDATE clients SET clientName=" + name + " WHERE UserID=" + inputID);
                        p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                        p2.executeUpdate();
                        con.commit();

                    } else {
                        System.out.println("Update unsuccessful, something went wrong.");
                    }


                } catch (SQLException e) {
                    con.rollback();
                }
            } else if (whatToUpdate == 2) { //updating a client location
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM clients");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();

                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8= rm.getColumnName(8);
                    //String col9=rm.getColumnName(9);
                    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";


                    if(!rx.isBeforeFirst()){
                        System.out.println("There are no clients in our database.");


                    }else{
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");

                        while (rx.next()) {
                            System.out.format(format, rx.getInt(1),rx.getString(2), rx.getString(3), rx.getInt(4 ) + " yards", rx.getInt(5) + " yards", rx.getString(6), rx.getInt(7) + " stores", rx.getInt(8) + " employees");
                            System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");

                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the client ID in order to update the information for it, so please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Enter the client ID in order to update the client's information:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client's location to:");
                    String location = scanString.nextLine();
                    PreparedStatement ps = con.prepareStatement("UPDATE clients SET clientLocation=? WHERE clientID=?");
                    ps.clearParameters();
                    ps.setString(1, location);
                    ps.setInt(2, inputID);
                    int x = ps.executeUpdate();
                    con.commit();

                    if(x>0){
                        System.out.println("The client's location has been updated to " + location + ".");

                    }else{
                        System.out.println("Update unsuccessful.");
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1, "UPDATE clients SET clientLocation=" + location + " WHERE UserID=" + inputID);
                    p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3, LoginOrRegister.primary_keys);
                    p2.executeUpdate();
                    con.commit();


                } catch (SQLException e) {
                    e.printStackTrace();
                    con.rollback();
                }


            } else if (whatToUpdate == 3) { //updating a client potential order on core
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM clients");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();

                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8= rm.getColumnName(8);
                    //String col9=rm.getColumnName(9);
                    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";

                    if(!rx.isBeforeFirst()){
                        System.out.println("There are no clients in our database.");


                    }else{
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");

                        while (rx.next()) {
                            System.out.format(format, rx.getInt(1),rx.getString(2), rx.getString(3), rx.getInt(4 ) + " yards", rx.getInt(5) + " yards", rx.getString(6), rx.getInt(7) + " stores", rx.getInt(8) + " employees");
                            System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------", "------------------");

                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the client ID in order to update the potential order on core, please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a client ID within our database.!");
                        System.out.println("Enter the client ID in order to update the client's information:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the potential order on core to (numbered value in yards):");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Please enter what you would like to update the potential order on core to (numbered value in yards):");
                        scanNum.next();
                    }
                    int onCore = scanNum.nextInt();
                    PreparedStatement ps = con.prepareStatement("UPDATE clients SET potentialOnCorePrograms=? WHERE clientID=?");
                    ps.clearParameters();
                    ps.setInt(1, onCore);
                    ps.setInt(2, inputID);
                    int x = ps.executeUpdate();
                    con.commit();

                    if(x>0){
                        System.out.println("The client's potential order on core has been updated to " + onCore + " yards.");

                    }else{
                        System.out.println("Updating unsuccessful.");
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1, "UPDATE clients SET potentialOnCorePrograms=" + onCore + " WHERE UserID=" + LoginOrRegister.primary_keys + " AND clientID=" + inputID);
                    p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3, LoginOrRegister.primary_keys);
                    p2.executeUpdate();
                    con.commit();


                } catch (SQLException e) {
                    con.rollback();
                }

            } else if (whatToUpdate == 4) { //updating a clients potential order on novelties
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM clients");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();

                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8= rm.getColumnName(8);

                    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";

                    if(!rx.isBeforeFirst()){
                        System.out.println("There are no clients in our database.");


                    }else{
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");

                        while (rx.next()) {
                            System.out.format(format, rx.getInt(1),rx.getString(2), rx.getString(3), rx.getInt(4 ) + " yards", rx.getInt(5) + " yards", rx.getString(6), rx.getInt(7) + " stores", rx.getInt(8) + " employees");
                            System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the client ID in order to update the client's potential order on novelties, please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Enter the client ID in order to update the client's potential order on novelties, please enter it below:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client's potential order on novelties to (numbered value in yards):");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Please enter what you would like to update the client's potential order on novelties to (numbered value in yards):");
                        scanNum.next();
                    }
                    int onNovelties = scanNum.nextInt();
                    PreparedStatement ps = con.prepareStatement("UPDATE clients SET potentialOnNovelties=? WHERE clientID=?");
                    ps.clearParameters();
                    ps.setInt(1, onNovelties);
                    ps.setInt(2, inputID);
                    int x = ps.executeUpdate();
                    con.commit();

                    if(x>0){
                        System.out.println("The client's potential order on novelties has been updated to " + onNovelties + " yards.");

                    }else{
                        System.out.println("Update unsuccessful.");
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1, "UPDATE clients SET potentialOnNovelties=" + onNovelties + " WHERE UserID=" + LoginOrRegister.primary_keys + " AND clientID=" + inputID);
                    p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3, LoginOrRegister.primary_keys);
                    p2.executeUpdate();
                    con.commit();


                } catch (SQLException e) {
                    con.rollback();
                }

            } else if (whatToUpdate == 5) { //updating client type
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM clients");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();

                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8= rm.getColumnName(8);

                    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";

                    if(!rx.isBeforeFirst()){
                        System.out.println("There are no clients in the database.");


                    }else{
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");

                        while (rx.next()) {
                            System.out.format(format, rx.getInt(1),rx.getString(2), rx.getString(3), rx.getInt(4 ) + " yards", rx.getInt(5) + " yards", rx.getString(6), rx.getInt(7) + " stores", rx.getInt(8) + " employees");
                            System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");

                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the client's ID in order to update the  client type, please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("We need the client's ID in order to update the  client type, please enter it below:");
                        scanNum.next();
                    }
                    while (!scanNum.hasNextLine()) {
                        System.out.println("That's not a number!");
                        System.out.println("Please enter what you would like to update the client type to:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client type to:");
                    String type = scanString.nextLine();
                    PreparedStatement ps = con.prepareStatement("UPDATE clients SET clientType=? WHERE clientID=?");
                    ps.clearParameters();
                    ps.setString(1, type);
                    ps.setInt(2, inputID);
                    int x = ps.executeUpdate();
                    con.commit();

                    if(x>0){
                        System.out.println("The client's type has been updated to " + type + ".");

                    }else{
                        System.out.println("Update unsuccessful.");
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1, "UPDATE clients SET clientType=" + type + " WHERE UserID=" + LoginOrRegister.primary_keys + " AND clientID=" + inputID);
                    p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3, LoginOrRegister.primary_keys);
                    p2.executeUpdate();
                    con.commit();


                } catch (SQLException e) {
                    con.rollback();
                }

            } else if (whatToUpdate == 6) { //updating client number of stores
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM clients");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();

                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8= rm.getColumnName(8);

                    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";

                    if(!rx.isBeforeFirst()){
                        System.out.println("No clients were found in our database.");

                    }else{
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");

                        while (rx.next()) {
                            System.out.format(format, rx.getInt(1),rx.getString(2), rx.getString(3), rx.getInt(4 ) + " yards", rx.getInt(5) + " yards", rx.getString(6), rx.getInt(7) + " stores", rx.getInt(8) + " employees");
                            System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the client's ID in order to update client's number of stores, please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Enter the client ID in order to update client's number of stores, please enter it below:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client number of stores to: ");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Please enter what you would like to update the client number of stores to: ");
                        scanNum.next();
                    }
                    int stores = scanNum.nextInt();
                    PreparedStatement ps = con.prepareStatement("UPDATE clients SET numberOfStores=? WHERE clientID=?");
                    ps.clearParameters();
                    ps.setDouble(1, stores);
                    ps.setInt(2, inputID);
                    int x = ps.executeUpdate();
                    con.commit();

                    if(x>0){
                        System.out.println("The client's number of stores has been updated to " + stores + ".");

                    }else{
                        System.out.println("Update unsuccessful.");
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1, "UPDATE clients SET numberOfStores=" + stores + " WHERE UserID=" + LoginOrRegister.primary_keys + " AND clientID=" + inputID);
                    p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3, LoginOrRegister.primary_keys);
                    p2.executeUpdate();
                    con.commit();

                } catch (SQLException e) {
                    con.rollback();
                }

            } else if (whatToUpdate == 7) { //updating client number of employees
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM clients");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();

                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8= rm.getColumnName(8);

                    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";

                    if(!rx.isBeforeFirst()){
                        System.out.println("There were no clients found within our database");

                    }else{
                        System.out.println("Displaying all clients in the database: ");
                        System.out.println("");
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");

                        while (rx.next()) {
                            System.out.format(format, rx.getInt(1),rx.getString(2), rx.getString(3), rx.getInt(4 ) + " yards", rx.getInt(5) + " yards", rx.getString(6), rx.getInt(7) + " stores", rx.getInt(8) + " employees");
                            System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the client ID in order to update the client's number of employees, please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Enter the client ID in order to update the client's number of employees:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update your number of employees to: ");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Please enter what you would like the client's number of employees to.: ");
                        scanNum.next();
                    }
                    int employees = scanNum.nextInt();
                    PreparedStatement ps = con.prepareStatement("UPDATE clients SET numberOfEmployees=? WHERE clientID=?");
                    ps.clearParameters();
                    ps.setDouble(1, employees);
                    ps.setInt(2, inputID);
                    int x = ps.executeUpdate();
                    con.commit();

                    if(x>0){
                        System.out.println("The client's number of employees has been updated to " + employees + ".");

                    }else{
                        System.out.println("Update unsuccessful.");
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1, "UPDATE clients SET numberOfEmployees=" + employees + " WHERE UserID=" + LoginOrRegister.primary_keys + " AND clientID=" + inputID);
                    p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3, LoginOrRegister.primary_keys);
                    p2.executeUpdate();
                    con.commit();

                } catch (SQLException e) {
                    con.rollback();
                }

            } else if (whatToUpdate == 8) {
                theSystem s = new theSystem();
                s.run();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateRetailInfo() throws InterruptedException  {
        try {
            System.out.println("In order to update your client's retail prices please select what you would like to update.");
            System.out.println("1- Update Client's Name.");
            System.out.println("2- Update Client's price for wool pants.");
            System.out.println("3- Update Client's price for poly-visco pants.");
            System.out.println("4- Update Client's price for cotton pants.");
            System.out.println("5- Update Client's price for a viscose dress.");
            System.out.println("6- Update Client's price for a polyester dress.");
            System.out.println("7- Update Client's price for outwear.");
            System.out.println("8- Update Client's price for jeans.");
            System.out.println("9- Update Client's price for white shirt.");
            System.out.println("10- Return to the main menu");
            while (!scanNum.hasNextInt()) {
                System.out.println("In order to update your client's retail prices please select what you would like to update.");
                System.out.println("1- Update Client's Name.");
                System.out.println("2- Update Client's price for wool pants.");
                System.out.println("3- Update Client's price for poly-visco pants.");
                System.out.println("4- Update Client's price for cotton pants.");
                System.out.println("5- Update Client's price for a viscose dress.");
                System.out.println("6- Update Client's price for a polyester dress.");
                System.out.println("7- Update Client's price for outwear.");
                System.out.println("8- Update Client's price for jeans.");
                System.out.println("9- Update Client's price for white shirt.");
                System.out.println("10- Return to the main menu");
                scanNum.next();
            }
            int whatToUpdate = scanNum.nextInt();

            //updating a clients name
            if (whatToUpdate == 1) {
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();
                    //ResultSetMetaData is used to retrieve the names of the attributes of each column
                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8 = rm.getColumnName(8);
                    String col9 = rm.getColumnName(9);
                    String col10 = rm.getColumnName(10);

                    String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";


                    if (!rx.isBeforeFirst()) {
                        System.out.println("No client within the database with retail ID.");


                    } else {
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                        while (rx.next()) {
                            System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                            System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");

                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not an accepted retail ID!");
                        System.out.println("Enter the retail ID in order to update the client's information:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client's name to:");
                    String name = scanString.nextLine();
                    PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET clientName=? WHERE retailID=?");
                    ps.clearParameters();
                    ps.setString(1, name);
                    ps.setInt(2, inputID);

                    int x = ps.executeUpdate();

                    con.commit();

                    if (x > 0) {
                        System.out.println("The name for the client has been updated to " + name + ".");

                        CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                        p2.setString(1, "UPDATE retailPrices SET clientName=" + name + " WHERE retailID=" + inputID);
                        p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                        p2.executeUpdate();
                        con.commit();

                    } else {
                        System.out.println("Update unsuccessful, something went wrong.");
                    }


                } catch (SQLException e) {
                    con.rollback();
                }

            } else if (whatToUpdate == 2) { //updating retail price for wool pants
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();
                    //ResultSetMetaData is used to retrieve the names of the attributes of each column
                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8 = rm.getColumnName(8);
                    String col9 = rm.getColumnName(9);
                    String col10 = rm.getColumnName(10);

                    String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";


                    if (!rx.isBeforeFirst()) {
                        System.out.println("No client within the database with retail ID.");


                    } else {
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                        while (rx.next()) {
                            System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                            System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");

                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not an accepted retail ID!");
                        System.out.println("Enter the retail ID in order to update the client's information:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client's wool pants' price to:");
                    String wool = scanString.nextLine();
                    PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET woolPants=? WHERE retailID=?");
                    ps.clearParameters();
                    ps.setString(1, wool);
                    ps.setInt(2, inputID);

                    int x = ps.executeUpdate();

                    con.commit();

                    if (x > 0) {
                        System.out.println("The price for the client's wool pants has been updated to " + wool + "$.");

                        CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                        p2.setString(1, "UPDATE retailPrices SET woolPants=" + wool + " WHERE retailID=" + inputID);
                        p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                        p2.executeUpdate();
                        con.commit();

                    } else {
                        System.out.println("Update unsuccessful, something went wrong.");
                    }

                } catch (SQLException e) {
                    con.rollback();
                }
            } else if (whatToUpdate == 3) {  //updating price for polyvisco pants
            try {
                PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                ResultSet rx = px.executeQuery();
                ResultSetMetaData rm = rx.getMetaData();
                //ResultSetMetaData is used to retrieve the names of the attributes of each column
                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String col3 = rm.getColumnName(3);
                String col4 = rm.getColumnName(4);
                String col5 = rm.getColumnName(5);
                String col6 = rm.getColumnName(6);
                String col7 = rm.getColumnName(7);
                String col8 = rm.getColumnName(8);
                String col9 = rm.getColumnName(9);
                String col10 = rm.getColumnName(10);

                String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";

                if (!rx.isBeforeFirst()) {
                    System.out.println("No client within the database with retail ID.");


                } else {
                    System.out.println("Displaying all clients in the database:");
                    System.out.println("");
                    System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                    System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                    System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                    while (rx.next()) {
                        System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                        System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");

                    }
                }
                con.setAutoCommit(false);
                System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                while (!scanNum.hasNextInt()) {
                    System.out.println("That's not an accepted retail ID!");
                    System.out.println("Enter the retail ID in order to update the client's information:");
                    scanNum.next();
                }
                int inputID = scanNum.nextInt();
                System.out.println("Please enter what you would like to update the client's poly-visco pants' price to:");
                String visco = scanString.nextLine();
                PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET polyViscoPants=? WHERE retailID=?");
                ps.clearParameters();
                ps.setString(1, visco);
                ps.setInt(2, inputID);

                int x = ps.executeUpdate();

                con.commit();

                if (x > 0) {
                    System.out.println("The price for the client's poly-visco pants has been updated to " + visco + "$.");

                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                    p2.setString(1, "UPDATE retailPrices SET polyViscoPants=" + visco + " WHERE retailID=" + inputID);
                    p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.executeUpdate();
                    con.commit();

                } else {
                    System.out.println("Update unsuccessful, something went wrong.");
                }

            } catch (SQLException e) {
                con.rollback();
            }
            } else if (whatToUpdate == 4) { //updating retail price for cotton pants
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();
                    //ResultSetMetaData is used to retrieve the names of the attributes of each column
                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8 = rm.getColumnName(8);
                    String col9 = rm.getColumnName(9);
                    String col10 = rm.getColumnName(10);

                    String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";


                    if (!rx.isBeforeFirst()) {
                        System.out.println("No client within the database with retail ID.");


                    } else {
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                        while (rx.next()) {
                            System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                            System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");
                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not an accepted retail ID!");
                        System.out.println("Enter the retail ID in order to update the client's information:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client's cotton pants' price to:");
                    String cotton = scanString.nextLine();
                    PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET cottonPants=? WHERE retailID=?");
                    ps.clearParameters();
                    ps.setString(1, cotton);
                    ps.setInt(2, inputID);

                    int x = ps.executeUpdate();

                    con.commit();

                    if (x > 0) {
                        System.out.println("The price for the client's cotton pants has been updated to " + cotton + "$.");

                        CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                        p2.setString(1, "UPDATE retailPrices SET cottonPants=" + cotton + " WHERE retailID=" + inputID);
                        p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                        p2.executeUpdate();
                        con.commit();

                    } else {
                        System.out.println("Update unsuccessful, something went wrong.");
                    }

                } catch (SQLException e) {
                    con.rollback();
                }
            }
                else if (whatToUpdate == 5) { //updating retail price for a dress made of viscose
                try {
                    PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                    ResultSet rx = px.executeQuery();
                    ResultSetMetaData rm = rx.getMetaData();
                    //ResultSetMetaData is used to retrieve the names of the attributes of each column
                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8 = rm.getColumnName(8);
                    String col9 = rm.getColumnName(9);
                    String col10 = rm.getColumnName(10);

                    String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";

                    if (!rx.isBeforeFirst()) {
                        System.out.println("No client within the database with retail ID.");


                    } else {
                        System.out.println("Displaying all clients in the database:");
                        System.out.println("");
                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                        while (rx.next()) {
                            System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                            System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");

                        }
                    }
                    con.setAutoCommit(false);
                    System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not an accepted retail ID!");
                        System.out.println("Enter the retail ID in order to update the client's information:");
                        scanNum.next();
                    }
                    int inputID = scanNum.nextInt();
                    System.out.println("Please enter what you would like to update the client's viscose dress' price to:");
                    String viscose = scanString.nextLine();
                    PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET viscoseDress=? WHERE retailID=?");
                    ps.clearParameters();
                    ps.setString(1, viscose);
                    ps.setInt(2, inputID);

                    int x = ps.executeUpdate();

                    con.commit();

                    if (x > 0) {
                        System.out.println("The price for the client's viscose dress has been updated to " + viscose + "$.");

                        CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                        p2.setString(1, "UPDATE retailPrices SET viscoseDress=" + viscose + " WHERE retailID=" + inputID);
                        p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                        p2.executeUpdate();
                        con.commit();

                    } else {
                        System.out.println("Update unsuccessful, something went wrong.");
                    }

                } catch (SQLException e) {
                    con.rollback();
                }
            }
                     else if (whatToUpdate == 6) { //updating retail price for polyester dress
                        try {
                            PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                            ResultSet rx = px.executeQuery();
                            ResultSetMetaData rm = rx.getMetaData();
                            //ResultSetMetaData is used to retrieve the names of the attributes of each column
                            String col1 = rm.getColumnName(1);
                            String col2 = rm.getColumnName(2);
                            String col3 = rm.getColumnName(3);
                            String col4 = rm.getColumnName(4);
                            String col5 = rm.getColumnName(5);
                            String col6 = rm.getColumnName(6);
                            String col7 = rm.getColumnName(7);
                            String col8 = rm.getColumnName(8);
                            String col9 = rm.getColumnName(9);
                            String col10 = rm.getColumnName(10);

                            String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";


                            if (!rx.isBeforeFirst()) {
                                System.out.println("No client within the database with retail ID.");


                            } else {
                                System.out.println("Displaying all clients in the database:");
                                System.out.println("");
                                System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                                System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                                System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                                while (rx.next()) {
                                    System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                                    System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");

                                }
                            }
                            con.setAutoCommit(false);
                            System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                            while (!scanNum.hasNextInt()) {
                                System.out.println("That's not an accepted retail ID!");
                                System.out.println("Enter the retail ID in order to update the client's information:");
                                scanNum.next();
                            }
                            int inputID = scanNum.nextInt();
                            System.out.println("Please enter what you would like to update the client's polyester dress' price to:");
                            String polyester = scanString.nextLine();
                            PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET polyesterDress=? WHERE retailID=?");
                            ps.clearParameters();
                            ps.setString(1, polyester);
                            ps.setInt(2, inputID);

                            int x = ps.executeUpdate();

                            con.commit();

                            if (x > 0) {
                                System.out.println("The price for the client's polyester dress has been updated to " + polyester + "$.");

                                CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                                p2.setString(1, "UPDATE retailPrices SET polyesterDress=" + polyester + " WHERE retailID=" + inputID);
                                p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                                p2.executeUpdate();
                                con.commit();

                            } else {
                                System.out.println("Update unsuccessful, something went wrong.");
                            }

                        } catch (SQLException e) {
                            con.rollback();
                        }
                    }
                        else if (whatToUpdate == 7) { //updating retail price for outwear
                        try {
                            PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                            ResultSet rx = px.executeQuery();
                            ResultSetMetaData rm = rx.getMetaData();
                            //ResultSetMetaData is used to retrieve the names of the attributes of each column
                            String col1 = rm.getColumnName(1);
                            String col2 = rm.getColumnName(2);
                            String col3 = rm.getColumnName(3);
                            String col4 = rm.getColumnName(4);
                            String col5 = rm.getColumnName(5);
                            String col6 = rm.getColumnName(6);
                            String col7 = rm.getColumnName(7);
                            String col8 = rm.getColumnName(8);
                            String col9 = rm.getColumnName(9);
                            String col10 = rm.getColumnName(10);

                            String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";


                            if (!rx.isBeforeFirst()) {
                                System.out.println("No client within the database with retail ID.");


                            } else {
                                System.out.println("Displaying all clients in the database:");
                                System.out.println("");
                                System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                                System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                                System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                                while (rx.next()) {
                                    System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                                    System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");

                                }
                            }
                            con.setAutoCommit(false);
                            System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                            while (!scanNum.hasNextInt()) {
                                System.out.println("That's not an accepted retail ID!");
                                System.out.println("Enter the retail ID in order to update the client's information:");
                                scanNum.next();
                            }
                            int inputID = scanNum.nextInt();
                            System.out.println("Please enter what you would like to update the client's outwear price to:");
                            String outwear = scanString.nextLine();
                            PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET outWear=? WHERE retailID=?");
                            ps.clearParameters();
                            ps.setString(1, outwear);
                            ps.setInt(2, inputID);

                            int x = ps.executeUpdate();

                            con.commit();

                            if (x > 0) {
                                System.out.println("The price for the client's outwear has been updated to " + outwear + "$.");

                                CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                                p2.setString(1, "UPDATE retailPrices SET outWear=" + outwear + " WHERE retailID=" + inputID);
                                p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                                p2.executeUpdate();
                                con.commit();

                            } else {
                                System.out.println("Update unsuccessful, something went wrong.");
                            }
                        } catch (SQLException e) {
                            con.rollback();
                        }
                    }
                        else if (whatToUpdate == 8) { //updating retail price for jeans
                                try {
                                    PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                                    ResultSet rx = px.executeQuery();
                                    ResultSetMetaData rm = rx.getMetaData();
                                    //ResultSetMetaData is used to retrieve the names of the attributes of each column
                                    String col1 = rm.getColumnName(1);
                                    String col2 = rm.getColumnName(2);
                                    String col3 = rm.getColumnName(3);
                                    String col4 = rm.getColumnName(4);
                                    String col5 = rm.getColumnName(5);
                                    String col6 = rm.getColumnName(6);
                                    String col7 = rm.getColumnName(7);
                                    String col8 = rm.getColumnName(8);
                                    String col9 = rm.getColumnName(9);
                                    String col10 = rm.getColumnName(10);

                                    String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";


                                    if (!rx.isBeforeFirst()) {
                                        System.out.println("No client within the database with retail ID.");


                                    } else {
                                        System.out.println("Displaying all clients in the database:");
                                        System.out.println("");
                                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                                        System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                                        while (rx.next()) {
                                            System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                                            System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");

                                        }
                                    }
                                    con.setAutoCommit(false);
                                    System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                                    while (!scanNum.hasNextInt()) {
                                        System.out.println("That's not an accepted retail ID!");
                                        System.out.println("Enter the retail ID in order to update the client's information:");
                                        scanNum.next();
                                    }
                                    int inputID = scanNum.nextInt();
                                    System.out.println("Please enter what you would like to update the client's jeans price to:");
                                    String jeans = scanString.nextLine();
                                    PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET jeans=? WHERE retailID=?");
                                    ps.clearParameters();
                                    ps.setString(1, jeans);
                                    ps.setInt(2, inputID);

                                    int x = ps.executeUpdate();

                                    con.commit();

                                    if (x > 0) {
                                        System.out.println("The price for the client's jeans has been updated to " + jeans + "$.");

                                        CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                                        p2.setString(1, "UPDATE retailPrices SET jeans=" + jeans + " WHERE retailID=" + inputID);
                                        p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                                        p2.executeUpdate();
                                        con.commit();

                                    } else {
                                        System.out.println("Update unsuccessful, something went wrong.");
                                    }
                                } catch(SQLException e){
                                    con.rollback();
                                }
                            }
                                else if (whatToUpdate == 9) { //updating retail price for white shirt
                                    try {
                                        PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

                                        ResultSet rx = px.executeQuery();
                                        ResultSetMetaData rm = rx.getMetaData();
                                        //ResultSetMetaData is used to retrieve the names of the attributes of each column
                                        String col1 = rm.getColumnName(1);
                                        String col2 = rm.getColumnName(2);
                                        String col3 = rm.getColumnName(3);
                                        String col4 = rm.getColumnName(4);
                                        String col5 = rm.getColumnName(5);
                                        String col6 = rm.getColumnName(6);
                                        String col7 = rm.getColumnName(7);
                                        String col8 = rm.getColumnName(8);
                                        String col9 = rm.getColumnName(9);
                                        String col10 = rm.getColumnName(10);

                                        String format = "\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";


                                        if (!rx.isBeforeFirst()) {
                                            System.out.println("No client within the database with retail ID.");


                                        } else {
                                            System.out.println("Displaying all clients in the database:");
                                            System.out.println("");
                                            System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8, col9, col10);
                                            System.out.format(format, "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++", "+++++++++++++++++");
                                            while (rx.next()) {
                                                System.out.format(format,rx.getInt(1), rx.getString(2),"$"+ rx.getInt(3), "$" +rx.getInt(4), "$" + rx.getInt(5),"$"+ rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), "$" + rx.getInt(10));
                                                System.out.format(format, "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------", "-----------------");

                                            }
                                        }
                                        con.setAutoCommit(false);
                                        System.out.println("We need the retail ID in order to update the information for it, so please enter it below:");
                                        while (!scanNum.hasNextInt()) {
                                            System.out.println("That's not an accepted retail ID!");
                                            System.out.println("Enter the retail ID in order to update the client's information:");
                                            scanNum.next();
                                        }
                                        int inputID = scanNum.nextInt();
                                        System.out.println("Please enter what you would like to update the client's white shirts price to:");
                                        String whites = scanString.nextLine();
                                        PreparedStatement ps = con.prepareStatement("UPDATE retailPrices SET whiteShirt=? WHERE retailID=?");
                                        ps.clearParameters();
                                        ps.setString(1, whites);
                                        ps.setInt(2, inputID);

                                        int x = ps.executeUpdate();

                                        con.commit();

                                        if (x > 0) {
                                            System.out.println("The price for the client's white shirts has been updated to " + whites + "$.");

                                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?)");
                                            p2.setString(1, "UPDATE retailPrices SET whiteShirt=" + whites + " WHERE retailID=" + inputID);
                                            p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                                            p2.executeUpdate();
                                            con.commit();

                                        } else {
                                            System.out.println("Update unsuccessful, something went wrong.");
                                        }
                                    } catch(SQLException e){
                                        con.rollback();
                                    }
                                }
                                    else if (whatToUpdate == 10) {
                                        theSystem s = new theSystem();
                                        s.run();
                                        }
                                } catch(SQLException e){
                                    System.out.println(e.getMessage()); }
        }

            public void updateProfile() throws InterruptedException {
                try {
                    System.out.println("In order to update your profile record please select what you would like to update.");
                    System.out.println("1- Update Username.");
                    System.out.println("2- Update Password.");
                    System.out.println("3- Update First Name.");
                    System.out.println("4- Update Last Name.");
                    System.out.println("5- Update Phone number.");
                    System.out.println("6- Update email address.");
                    System.out.println("7- Return to the main menu.");
                    while (!scanNum.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("In order to update your profile record please select what you would like to update.");
                        System.out.println("1- Update Username.");
                        System.out.println("2- Update Password.");
                        System.out.println("3- Update First Name.");
                        System.out.println("4- Update Last Name.");
                        System.out.println("5- Update Phone number.");
                        System.out.println("6- Update email address.");
                        System.out.println("7- Return to the main menu.");
                        scanNum.next();
                    }

                    int whatToUpdate = scanNum.nextInt();
                    if (whatToUpdate == 1) {  //update username
                        try {
                            con.setAutoCommit(false);
                            System.out.println("Please enter what you would like to update your username to:");
                            String username = scanString.nextLine();
                            PreparedStatement ps = con.prepareStatement("UPDATE users SET Username=? WHERE UserID=?");
                            ps.clearParameters();
                            ps.setString(1, username);
                            ps.setInt(2, LoginOrRegister.primary_keys);
                            int x = ps.executeUpdate();
                            if(x>0){
                                System.out.println("Your username has been updated to " + username + ".");

                            }else{
                                System.out.println("Update unsuccessful.");
                            }
                            con.commit();
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1, "UPDATE users SET Username=" + username + " WHERE UserID=" + LoginOrRegister.primary_keys);
                            p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3, LoginOrRegister.primary_keys);
                            p2.executeUpdate();
                            con.commit();

                        } catch (SQLException e) {
                            System.out.println("This username already exists please try another username.");
                            updateProfile();
                            try {
                                con.rollback();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else if (whatToUpdate == 2) { //update password
                        try {
                            con.setAutoCommit(false);
                            System.out.println("Please enter what you would like to update your password to:");
                            String pass = scanString.nextLine();
                            PreparedStatement ps = con.prepareStatement("UPDATE users SET Password=? WHERE UserID=?");
                            ps.clearParameters();
                            ps.setInt(1, pass.hashCode());
                            ps.setInt(2, LoginOrRegister.primary_keys);
                            int x = ps.executeUpdate();
                            if(x>0){
                                System.out.println("Your password has been updated to " + pass + ".");

                            }else{
                                System.out.println("Update unsuccessful.");
                            }
                            con.commit();
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1, "UPDATE users SET Password=" + pass.hashCode() + " WHERE UserID=" + LoginOrRegister.primary_keys);
                            p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3, LoginOrRegister.primary_keys);
                            p2.executeUpdate();
                            con.commit();

                        } catch (SQLException e) {
                            try {
                                con.rollback();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else if (whatToUpdate == 3) { //update first name
                        try {
                            con.setAutoCommit(false);
                            System.out.println("Please enter what you would like to update your First Name to:");
                            String fname = scanString.nextLine();
                            PreparedStatement ps = con.prepareStatement("UPDATE users SET FirstName=? WHERE UserID=?");
                            ps.clearParameters();
                            ps.setString(1, fname);
                            ps.setInt(2, LoginOrRegister.primary_keys);
                            int x = ps.executeUpdate();
                            if(x>0){
                                System.out.println("Your First name has been updated to " + fname + ".");

                            }else{
                                System.out.println("Update unsuccessful.");
                            }
                            con.commit();
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1, "UPDATE users SET FirstName=" + fname + " WHERE UserID=" + LoginOrRegister.primary_keys);
                            p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3, LoginOrRegister.primary_keys);
                            p2.executeUpdate();
                            con.commit();

                        } catch (SQLException e) {
                            try {
                                con.rollback();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else if (whatToUpdate == 4) { //update last name
                        try {
                            con.setAutoCommit(false);
                            System.out.println("Please enter what you would like to update your Last Name to:");
                            String lname = scanString.nextLine();
                            PreparedStatement ps = con.prepareStatement("UPDATE users SET LastName=? WHERE UserID=?");
                            ps.clearParameters();
                            ps.setString(1, lname);
                            ps.setInt(2, LoginOrRegister.primary_keys);
                            int x = ps.executeUpdate();
                            if(x>0){
                                System.out.println("Your last name has been updated to " + lname + ".");

                            }else{
                                System.out.println("Update unsuccessful.");
                            }
                            con.commit();
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1, "UPDATE users SET LastName=" + lname + " WHERE UserID=" + LoginOrRegister.primary_keys);
                            p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3, LoginOrRegister.primary_keys);
                            p2.executeUpdate();
                            con.commit();

                        } catch (SQLException e) {
                            try {
                                con.rollback();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else if (whatToUpdate == 5) { //update phone number
                        try {
                            con.setAutoCommit(false);
                            System.out.println("Please enter what you would like to update your Phone number to:");
                            long phone = scanNum.nextLong();
                            PreparedStatement ps = con.prepareStatement("UPDATE users SET Phone=? WHERE UserID=?");
                            ps.clearParameters();
                            ps.setLong(1, phone);
                            ps.setInt(2, LoginOrRegister.primary_keys);
                            int x = ps.executeUpdate();
                            if(x>0){
                                System.out.println("Your phone number has been updated to " + phone + ".");

                            }else{
                                System.out.println("Update unsuccessful.");
                            }
                            con.commit();
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1, "UPDATE users SET Phone=" + phone + " WHERE UserID=" + LoginOrRegister.primary_keys);
                            p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3, LoginOrRegister.primary_keys);
                            p2.executeUpdate();
                            con.commit();

                        } catch (SQLException e) {
                            try {
                                con.rollback();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }else if (whatToUpdate == 6) { //update email address
                        try {
                            con.setAutoCommit(false);
                            System.out.println("Please enter what you would like to update your email address to:");
                            String email=scanString.nextLine();
                            PreparedStatement ps = con.prepareStatement("UPDATE users SET Email=? WHERE UserID=?");
                            ps.clearParameters();
                            ps.setString(1, email);
                            ps.setInt(2, LoginOrRegister.primary_keys);
                            int x = ps.executeUpdate();
                            if(x>0){
                                System.out.println("Your email has been updated to " + email + ".");

                            }else{
                                System.out.println("Update unsuccessful.");
                            }
                            con.commit();
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1, "UPDATE users SET Email=" + email + " WHERE UserID=" + LoginOrRegister.primary_keys);
                            p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3, LoginOrRegister.primary_keys);
                            p2.executeUpdate();
                            con.commit();

                        } catch (SQLException e) {
                            try {
                                con.rollback();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }else if (whatToUpdate == 7) {
                        theSystem s = new theSystem();
                        s.run();
                    }

                } catch (Exception r) {
                }


            }
        }
