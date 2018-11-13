//Created by Riccardo Angiolini on July 23rd, 2018.
// Copyright. All Rights Reserved. ©

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

//class for deleting records from the database
public class Deletion {
    private Scanner scanString = new Scanner(System.in);
    private Scanner scanInt = new Scanner(System.in);
    Selection select = new Selection();
    Connect c = new Connect();
    Connection con = c.getConnection();

    //format needed for displaying the record in an organized way
    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";

    //deletes client information within the database
    public void deleteClientInfo() throws SQLException {
        try {
            System.out.println("In order to remove a client from the database please enter the following information.");
            //query to display the inputs that belong to the user
            PreparedStatement px = con.prepareStatement("SELECT * FROM clients");

            //px.setInt(1, LoginOrRegister.primary_keys);
            ResultSet rx = px.executeQuery();
            ResultSetMetaData rm = rx.getMetaData();

            String col1 = rm.getColumnName(1);
            String col2 = rm.getColumnName(2);
            String col3 = rm.getColumnName(3);
            String col4 = rm.getColumnName(4);
            String col5 = rm.getColumnName(5);
            String col6 = rm.getColumnName(6);
            String col7 = rm.getColumnName(7);
            String col8 = rm.getColumnName(8);


            if (!rx.isBeforeFirst()) {
                System.out.println("No client within the database with ID.");


            } else {
                System.out.println("Displaying all clients within the database: ");
                System.out.println("");
                System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");

                while (rx.next()) {
                    System.out.format(format, rx.getInt(1), rx.getString(2), rx.getString(3), rx.getInt(4) + " yards", rx.getInt(5) + " yards", rx.getString(6), rx.getInt(7) + " stores", rx.getInt(8) + " employees");
                    System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------", "------------------", "------------------");

                }
            }
            //start the deletion transaction
            con.setAutoCommit(false);
            System.out.println("Please enter the client ID for the company you wish to remove or enter 0 to return to the main menu:");
            while (!scanInt.hasNextInt()) {
                System.out.println("That's not an accepted input for client ID!");
                System.out.println("Please enter the client ID for the company you wish to remove or enter 0 to return to the main menu:");
                scanInt.next();
            }
            int cID =scanInt.nextInt();
            if(cID ==0) {

            }else{

                //query that actually deletes the record from the db
                PreparedStatement ps = con.prepareStatement("DELETE FROM clients WHERE clientID=?");
                ps.setInt(1, cID);
                //ps.setInt(2, LoginOrRegister.primary_keys);
                ps.executeUpdate();
                con.commit();
                System.out.println("Record officially deleted from our records.");

                //log the queries in the log tables
                //uses a callable statement
                CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                p2.setString(1, "DELETE FROM clients WHERE clientID= " + cID + ".");
                p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                p2.setInt(3, LoginOrRegister.primary_keys);
                p2.executeUpdate();
                con.commit();
            }


        } catch (SQLException e) {
            con.rollback();
        }
    }
    //deletes relevant employee information within the database
    public void deleteRelevantEmployee() throws SQLException {
        try {
            System.out.println("In order to remove a relevant employee from the database please enter the following information.");
            //query to display the inputs that belong to the user
            PreparedStatement px = con.prepareStatement("SELECT * FROM relevantEmployees");

            //px.setInt(1, LoginOrRegister.primary_keys);
            ResultSet rx = px.executeQuery();
            ResultSetMetaData rm = rx.getMetaData();

            String col1 = rm.getColumnName(1);
            String col2 = rm.getColumnName(2);
            String col3 = rm.getColumnName(3);
            String col4 = rm.getColumnName(4);
            String col5 = rm.getColumnName(5);

            String format ="\u2503%1$-20s\u2503%2$-20s\u2503%3$-20s\u2503%4$-25s\u2503%5$-20s\u2503\n";

            if (!rx.isBeforeFirst()) {
                System.out.println("No relevant employee within the database.");


            } else {
                System.out.println("Displaying all employees within the database: ");
                System.out.println("");
                System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                System.out.format(format, col1, col2, col3, col4, col5);
                System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");

                while (rx.next()) {
                    System.out.format(format, rx.getString(1), rx.getString(2), rx.getString(3), rx.getString(4), rx.getString(5));
                    System.out.format(format, "------------------", "------------------", "------------------------------", "------------------", "------------------");

                }
            }
            //start the deletion transaction
            con.setAutoCommit(false);
            System.out.println("Please enter the ID for the employee you wish to remove or enter 0 to return to the main menu:");
            while (!scanInt.hasNextInt()) {
                System.out.println("That's not an accepted input for employee ID!");
                System.out.println("Please enter the ID for the employee you wish to remove or enter 0 to return to the main menu:");
                scanInt.next();
            }
            int eID =scanInt.nextInt();
            if(eID ==0) {

            }else{

                //query that actually deletes the record from the db
                PreparedStatement ps = con.prepareStatement("DELETE FROM relevantEmployees WHERE employeeID=?");
                ps.setInt(1, eID);
                //ps.setInt(2, LoginOrRegister.primary_keys);
                ps.executeUpdate();
                con.commit();
                System.out.println("Record officially deleted from our records.");



                //log the queries in the log tables
                //uses a callable statement
                CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                p2.setString(1, "DELETE FROM relevantEmployees WHERE employeeID= " + eID + ".");
                p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                p2.setInt(3, LoginOrRegister.primary_keys);
                p2.executeUpdate();
                con.commit();
            }


        } catch (SQLException e) {
            con.rollback();
        }
    }
    //deletes agent information within the database
    public void deleteAgent() throws SQLException {
        try {
            System.out.println("In order to remove an agent from the database please enter the following information.");
            //query to display the inputs that belong to the user
            PreparedStatement px = con.prepareStatement("SELECT * FROM agents");

            //px.setInt(1, LoginOrRegister.primary_keys);
            ResultSet rx = px.executeQuery();
            ResultSetMetaData rm = rx.getMetaData();

            String col1 = rm.getColumnName(1);
            String col2 = rm.getColumnName(2);
            String col3 = rm.getColumnName(3);
            String col4 = rm.getColumnName(4);
            String col5 = rm.getColumnName(5);

            String format ="\u2503%1$-20s\u2503%2$-20s\u2503%3$-20s\u2503%4$-25s\u2503%5$-20s\u2503\n";

            if (!rx.isBeforeFirst()) {
                System.out.println("No agents within the database.");


            } else {
                System.out.println("Displaying all agents within the database: ");
                System.out.println("");
                System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                System.out.format(format, col1, col2, col3, col4, col5);
                System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");

                while (rx.next()) {
                    System.out.format(format, rx.getString(1), rx.getString(2), rx.getString(3), rx.getString(4), rx.getString(5));
                    System.out.format(format, "------------------", "------------------", "------------------------------", "------------------", "------------------");

                }
            }
            //start the deletion transaction
            con.setAutoCommit(false);
            System.out.println("Please enter the ID for the agent you wish to remove or enter 0 to return to the main menu:");
            while (!scanInt.hasNextInt()) {
                System.out.println("That's not an accepted input for employee ID!");
                System.out.println("Please enter the ID for the agent you wish to remove or enter 0 to return to the main menu:");
                scanInt.next();
            }
            int aID =scanInt.nextInt();
            if(aID ==0) {

            }else{

                //query that actually deletes the record from the db
                PreparedStatement ps = con.prepareStatement("DELETE FROM agents WHERE agentID=?");
                ps.setInt(1, aID);
                //ps.setInt(2, LoginOrRegister.primary_keys);
                ps.executeUpdate();
                con.commit();
                System.out.println("Record officially deleted from our records.");



                //log the queries in the log tables
                //uses a callable statement
                CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                p2.setString(1, "DELETE FROM agents WHERE agentID= " + aID + ".");
                p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                p2.setInt(3, LoginOrRegister.primary_keys);
                p2.executeUpdate();
                con.commit();
            }

        } catch (SQLException e) {
            con.rollback();
        }
    }
    //deletes client information within the database
    public void deleteRetailInfo() throws SQLException {
        try {
            System.out.println("In order to remove a client's retail information from the database please enter the following information.");
            //query to display the inputs that belong to the user
            PreparedStatement px = con.prepareStatement("SELECT * FROM retailPrices");

            //px.setInt(1, LoginOrRegister.primary_keys);
            ResultSet rx = px.executeQuery();
            ResultSetMetaData rm = rx.getMetaData();

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

            String format ="\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";


            if (!rx.isBeforeFirst()) {
                System.out.println("No client within the database with retail ID.");


            } else {
                System.out.println("Displaying all clients within the database: ");
                System.out.println("");
                System.out.format(format, "+++++++++++++++++", "+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++");
                System.out.format(format, col1, col2,col3, col4,col5,col6,col7,col8,col9,col10);
                System.out.format(format, "+++++++++++++++++", "+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++");

                while (rx.next()) {
                    System.out.format(format,rx.getInt(1), rx.getString(2),"$" + rx.getInt(3),"$" + rx.getInt(4),"$" + rx.getInt(5),"$" + rx.getInt(6),"$" + rx.getInt(7), "$" + rx.getInt(8), "$" + rx.getInt(9), rx.getInt(10));
                    System.out.format(format, "-----------------", "-----------------","-----------------","-----------------","-----------------","-----------------","-----------------","-----------------","-----------------","-----------------");

                }
            }
            //start the deletion transaction
            con.setAutoCommit(false);
            System.out.println("Please enter the retail ID for the client's prices you wish to remove or enter 0 to return to the main menu:");
            while (!scanInt.hasNextInt()) {
                System.out.println("That's not an accepted input for client ID!");
                System.out.println("Please enter the retail ID for the client's prices you wish to remove or enter 0 to return to the main menu:");
                scanInt.next();
            }
            int rID =scanInt.nextInt();
            if(rID ==0) {

            }else{

                //query that actually deletes the record from the db
                PreparedStatement ps = con.prepareStatement("DELETE FROM retailPrices WHERE retailID=?");
                ps.setInt(1, rID);
                //ps.setInt(2, LoginOrRegister.primary_keys);
                ps.executeUpdate();
                con.commit();
                System.out.println("Record officially deleted from our records.");

                //log the queries in the log tables
                //uses a callable statement
                CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                p2.setString(1, "DELETE FROM retailPrices WHERE retailID= " + rID + ".");
                p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                p2.setInt(3, LoginOrRegister.primary_keys);
                p2.executeUpdate();
                con.commit();
            }


        } catch (SQLException e) {
            con.rollback();
        }
    }
    public void deleteAvgPriceInfo() throws SQLException{
        try{
            con.setAutoCommit(false);
            PreparedStatement pz = con.prepareStatement("DELETE FROM averagePricePerCompany ");
            pz.executeUpdate();
            con.commit();

    } catch (SQLException e) {
            System.out.println("An unknown error occured.");
            con.rollback();
    }
    }
}