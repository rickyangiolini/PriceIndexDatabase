//Created by Riccardo Angiolini on July 23rd, 2018.
// Copyright. All Rights Reserved. ©

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Selection extends Connect {
    Scanner scan=new Scanner(System.in);
    Scanner scanString =new Scanner(System.in);
    //format string for the majority of tables
    String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-30s\u2503%5$-30s\u2503%6$-18s\u2503%7$-18s\u2503%8$-18s\u2503\n";

        //method to filter a client selection
        //a client can be selected with or without a filter on a particular attribute
        //a client can be filtered by all of its criteria: ID, ame, location, potential orders on core and on novelties, their type, number of stores and number of employees
        //each query can be exported to a csv
        public void SelectClient() {
            StringBuilder s=new StringBuilder();
            System.out.println("Would you like to filter your client selection?");
            System.out.println("1 Yes, 2 No");
            int filter=scan.nextInt();
            if(filter==2) {

                try {
                    PreparedStatement p = con.prepareStatement("SELECT * FROM clients");
                    ResultSet r = p.executeQuery();

                    ResultSetMetaData rm = r.getMetaData();

                    String col1 = rm.getColumnName(1);
                    String col2 = rm.getColumnName(2);
                    String col3 = rm.getColumnName(3);
                    String col4 = rm.getColumnName(4);
                    String col5 = rm.getColumnName(5);
                    String col6 = rm.getColumnName(6);
                    String col7 = rm.getColumnName(7);
                    String col8= rm.getColumnName(8);

                    if(!r.isBeforeFirst()){
                        System.out.println("No client record exists in the database.");


                    }else{
                        System.out.println("Displaying all clients within the databaseL");
                        System.out.println("");
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                        System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                        //appending attributes to a string builder for the sake of exporting to csv
                        s.append(col1);
                        s.append(',');
                        s.append(col2);
                        s.append(',');
                        s.append(col3);
                        s.append(',');
                        s.append(col4);
                        s.append(',');
                        s.append(col5);
                        s.append(',');
                        s.append(col6);
                        s.append(',');
                        s.append(col7);
                        s.append(',');
                        s.append(col8);
                        s.append('\n');

                        while (r.next()) {
                            System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                            System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                            s.append(r.getInt(1));
                            s.append(',');
                            s.append(r.getString(2));
                            s.append(',');
                            s.append(r.getString(3));
                            s.append(',');
                            s.append(r.getInt(4));
                            s.append(',');
                            s.append(r.getInt(5));
                            s.append(',');
                            s.append(r.getString(6));
                            s.append(',');
                            s.append(r.getInt(7));
                            s.append(',');
                            s.append(r.getInt(8));
                            s.append('\n');

                        }

                        CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                        p2.setString(1,"SELECT * FROM clients ");
                        p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                        p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();


                        System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                        while (!scan.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Enter the ID to filter by.");
                            scan.next();
                        }
                        int csv=scan.nextInt();
                        if(csv==1){
                            System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name");
                            String _file=scanString.nextLine();
                            try {
                                PrintWriter pw = new PrintWriter(new File(_file));
                                pw.write(s.toString());
                                pw.close();
                            }catch (IOException e){
                                System.out.println("Unable to create file");
                            }

                            System.out.println("Exported");
                        }else{
                            System.out.println("Not exported");
                        }

                    }


                } catch (SQLException r) {
                    System.out.println(r.getMessage());

                }finally {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }else if(filter==1){

                System.out.println("What would you like to filter clients by?");
                System.out.println("1-ID, 2-Name, 3-Location, 4-Potential Order On Core Programs, 5-Potential Order On Novelties, 6- Client Type, 7-Number of Stores, 8-Number of Employees");
                int filter2=scan.nextInt();
                if(filter2==1){
                    try {
                        PreparedStatement p = con.prepareStatement("SELECT * FROM clients WHERE clientID=?");
                        System.out.println("Enter the ID to filter by.");

                        while (!scan.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Enter the ID to filter by.");
                            scan.next();
                        }
                        int id=scan.nextInt();
                        p.setInt(1,id);
                        ResultSet r = p.executeQuery();
                        ResultSetMetaData rm = r.getMetaData();

                        String col1 = rm.getColumnName(1);
                        String col2 = rm.getColumnName(2);
                        String col3 = rm.getColumnName(3);
                        String col4 = rm.getColumnName(4);
                        String col5 = rm.getColumnName(5);
                        String col6 = rm.getColumnName(6);
                        String col7 = rm.getColumnName(7);
                        String col8= rm.getColumnName(8);

                        if(!r.isBeforeFirst()){
                            System.out.println("No client with ID " + id + "was found in our database.");


                        }else{
                            System.out.println("Displaying all clients with the ClientId "+id);
                            System.out.println("");
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++","++++++++++++++++++","++++++++++++++++++");
                            s.append(col1);
                            s.append(',');
                            s.append(col2);
                            s.append(',');
                            s.append(col3);
                            s.append(',');
                            s.append(col4);
                            s.append(',');
                            s.append(col5);
                            s.append(',');
                            s.append(col6);
                            s.append(',');
                            s.append(col7);
                            s.append(',');
                            s.append(col8);
                            s.append('\n');
                            while (r.next()) {
                                System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                                System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                                s.append(r.getInt(1));
                                s.append(',');
                                s.append(r.getString(2));
                                s.append(',');
                                s.append(r.getString(3));
                                s.append(',');
                                s.append(r.getInt(4));
                                s.append(',');
                                s.append(r.getInt(5));
                                s.append(',');
                                s.append(r.getString(6));
                                s.append(',');
                                s.append(r.getInt(7));
                                s.append(',');
                                s.append(r.getInt(8));
                                s.append('\n');
                            }
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1,"SELECT * FROM clients Where clientID=" + id);
                            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));

                            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();

                            System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                            while (!scan.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Enter the ID to filter by.");
                                scan.next();
                            }
                            int csv=scan.nextInt();
                            if(csv==1){
                                System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name");
                                String _file=scanString.nextLine();
                                try {
                                    PrintWriter pw = new PrintWriter(new File(_file));
                                    pw.write(s.toString());
                                    pw.close();
                                }catch (IOException e){
                                    System.out.println("Unable to create file");
                                }

                                System.out.println("Exported");
                            }else{
                                System.out.println("Not exported");
                            }

                        }


                    } catch (SQLException r) {
                        r.printStackTrace();
                    }finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }else if(filter2==2){  //filter by client name
                    try {
                        PreparedStatement p = con.prepareStatement("SELECT * FROM clients WHERE clientName LIKE ?");
                        System.out.println("Enter the client name you would like to filter by:");
                        String name = scanString.nextLine();
                        p.setString(1,name +'%');
                        ResultSet r = p.executeQuery();
                        ResultSetMetaData rm = r.getMetaData();

                        String col1 = rm.getColumnName(1);
                        String col2 = rm.getColumnName(2);
                        String col3 = rm.getColumnName(3);
                        String col4 = rm.getColumnName(4);
                        String col5 = rm.getColumnName(5);
                        String col6 = rm.getColumnName(6);
                        String col7 = rm.getColumnName(7);
                        String col8= rm.getColumnName(8);

                        if(!r.isBeforeFirst()) {
                            System.out.println("No client with the name of " + name + "was found.");

                        }else{
                            System.out.println("Displaying all clients with name "+ name);
                            System.out.println("");
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            s.append(col1);
                            s.append(',');
                            s.append(col2);
                            s.append(',');
                            s.append(col3);
                            s.append(',');
                            s.append(col4);
                            s.append(',');
                            s.append(col5);
                            s.append(',');
                            s.append(col6);
                            s.append(',');
                            s.append(col7);
                            s.append(',');
                            s.append(col8);
                            //s.append(',');
                            //s.append(col9);
                            s.append('\n');
                            while (r.next()) {
                                System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                                System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                                s.append(r.getInt(1));
                                s.append(',');
                                s.append(r.getString(2));
                                s.append(',');
                                s.append(r.getString(3));
                                s.append(',');
                                s.append(r.getInt(4));
                                s.append(',');
                                s.append(r.getInt(5));
                                s.append(',');
                                s.append(r.getString(6));
                                s.append(',');
                                s.append(r.getInt(7));
                                s.append(',');
                                s.append(r.getInt(8));
                                //s.append(',');
                                //s.append(r.getDouble(9));
                                s.append('\n');
                            }
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1,"SELECT * FROM clients Where Name LIKE "+ name);
                            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));

                            p2.setInt(3,LoginOrRegister.primary_keys);
                            p2.executeUpdate();
                            System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                            while (!scan.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Enter the ID to filter by:");
                                scan.next();
                            }
                            int csv=scan.nextInt();
                            if(csv==1){
                                System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name");
                                String _file=scanString.nextLine();
                                try {
                                    PrintWriter pw = new PrintWriter(new File(_file));
                                    pw.write(s.toString());
                                    pw.close();
                                }catch (IOException e){
                                    System.out.println("Unable to create file.");
                                }

                                System.out.println("Exported.");
                            }else{
                                System.out.println("Not exported.");
                            }

                        }

                    } catch (SQLException r) {
                        r.printStackTrace();
                    }finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }else if(filter2==3){  //filter by client location
                    try {
                        PreparedStatement p = con.prepareStatement("SELECT * FROM clients WHERE clientLocation=?");
                        System.out.println("Enter the client location to filter by:");
                        String location=scanString.nextLine();
                        p.setString(1,location);
                        ResultSet r = p.executeQuery();
                        ResultSetMetaData rm = r.getMetaData();

                        String col1 = rm.getColumnName(1);
                        String col2 = rm.getColumnName(2);
                        String col3 = rm.getColumnName(3);
                        String col4 = rm.getColumnName(4);
                        String col5 = rm.getColumnName(5);
                        String col6 = rm.getColumnName(6);
                        String col7 = rm.getColumnName(7);
                        String col8= rm.getColumnName(8);
                        //String col9=rm.getColumnName(9);


                        if(!r.isBeforeFirst()) {
                            System.out.println("No client was found in the database with the location "+ location + ".");

                        }else{
                            System.out.println("Displaying all clients in the database within " + location + ".");

                            System.out.println("");
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            s.append(col1);
                            s.append(',');
                            s.append(col2);
                            s.append(',');
                            s.append(col3);
                            s.append(',');
                            s.append(col4);
                            s.append(',');
                            s.append(col5);
                            s.append(',');
                            s.append(col6);
                            s.append(',');
                            s.append(col7);
                            s.append(',');
                            s.append(col8);
                            //s.append(',');
                            //s.append(col9);
                            s.append('\n');
                            while (r.next()) {
                                System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                                System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                                s.append(r.getInt(1));
                                s.append(',');
                                s.append(r.getString(2));
                                s.append(',');
                                s.append(r.getString(3));
                                s.append(',');
                                s.append(r.getInt(4));
                                s.append(',');
                                s.append(r.getInt(5));
                                s.append(',');
                                s.append(r.getString(6));
                                s.append(',');
                                s.append(r.getInt(7));
                                s.append(',');
                                s.append(r.getInt(8));
                                //s.append(',');
                                //s.append(r.getDouble(9));
                                s.append('\n');
                            }
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1,"SELECT * FROM clients Where location="+ location);
                            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3,LoginOrRegister.primary_keys);

                            p2.setInt(3,LoginOrRegister.primary_keys);
                            p2.executeUpdate();
                            System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                            while (!scan.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Enter the ID to filter by.");
                                scan.next();
                            }
                            int csv=scan.nextInt();
                            if(csv==1){
                                System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                                String _file=scanString.nextLine();
                                try {
                                    PrintWriter pw = new PrintWriter(new File(_file));
                                    pw.write(s.toString());
                                    pw.close();
                                }catch (IOException e){
                                    System.out.println("Unable to create file.");
                                }

                                System.out.println("Exported.");
                            }else{
                                System.out.println("Not exported.");
                            }

                        }

                    } catch (SQLException r) {
                        r.printStackTrace();
                    }finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }else if(filter2==4){  //filter by potential on core orders
                    try {
                        PreparedStatement p = con.prepareStatement("SELECT * FROM clients WHERE potentialOnCorePrograms=?");
                        System.out.println("Enter the potential order size on core programs to filter by:");

                        while (!scan.hasNextInt()) {
                            System.out.println("That's not a proper filter selection!");
                            System.out.println("Enter the potential order size on core programs to filter by:");
                            scan.next();
                        }


                        int onCorePrograms=scan.nextInt();
                        p.setInt(1,onCorePrograms);
                        ResultSet r = p.executeQuery();
                        ResultSetMetaData rm = r.getMetaData();

                        String col1 = rm.getColumnName(1);
                        String col2 = rm.getColumnName(2);
                        String col3 = rm.getColumnName(3);
                        String col4 = rm.getColumnName(4);
                        String col5 = rm.getColumnName(5);
                        String col6 = rm.getColumnName(6);
                        String col7 = rm.getColumnName(7);
                        String col8= rm.getColumnName(8);

                        if(!r.isBeforeFirst()) {
                            System.out.println("No potential order on core programs of size "+ onCorePrograms + " was found in our database.");

                        }else {
                            System.out.println("Displaying all orders on core programs of size "+ onCorePrograms+ " within our database:");

                            System.out.println("");
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            s.append(col1);
                            s.append(',');
                            s.append(col2);
                            s.append(',');
                            s.append(col3);
                            s.append(',');
                            s.append(col4);
                            s.append(',');
                            s.append(col5);
                            s.append(',');
                            s.append(col6);
                            s.append(',');
                            s.append(col7);
                            s.append(',');
                            s.append(col8);
                            s.append('\n');

                            while (r.next()) {
                                System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                                System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                                s.append(r.getInt(1));
                                s.append(',');
                                s.append(r.getString(2));
                                s.append(',');
                                s.append(r.getString(3));
                                s.append(',');
                                s.append(r.getInt(4));
                                s.append(',');
                                s.append(r.getInt(5));
                                s.append(',');
                                s.append(r.getString(6));
                                s.append(',');
                                s.append(r.getInt(7));
                                s.append(',');
                                s.append(r.getInt(8));
                                s.append('\n');
                            }
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1,"SELECT * FROM clients Where potentialOnCorePrograms="+ onCorePrograms);
                            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                            System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                            while (!scan.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Enter the ID to filter by.");
                                scan.next();
                            }
                            int csv=scan.nextInt();
                            if(csv==1){
                                System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                                String _file=scanString.nextLine();
                                try {
                                    PrintWriter pw = new PrintWriter(new File(_file));
                                    pw.write(s.toString());
                                    pw.close();
                                }catch (IOException e){
                                    System.out.println("Unable to create file.");
                                }

                                System.out.println("Exported.");
                            }else{
                                System.out.println("Not exported.");
                            }

                        }

                    } catch (SQLException r) {
                        r.printStackTrace();
                    }finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }


                }else if(filter2==5){ //filter by potential order on novelties
                    try {
                        PreparedStatement p = con.prepareStatement("SELECT * FROM clients WHERE potentialOnNovelties=?");
                        System.out.println("Enter the potential order on novelties to filter by:");
                        while (!scan.hasNextDouble()) {
                            System.out.println("That's not a proper filter!");
                            System.out.println("Enter the potential order on novelties to filter by:");
                            scan.next();
                        }
                        double sz=scan.nextDouble();
                        p.setDouble(1,sz);
                        ResultSet r = p.executeQuery();
                        ResultSetMetaData rm = r.getMetaData();

                        String col1 = rm.getColumnName(1);
                        String col2 = rm.getColumnName(2);
                        String col3 = rm.getColumnName(3);
                        String col4 = rm.getColumnName(4);
                        String col5 = rm.getColumnName(5);
                        String col6 = rm.getColumnName(6);
                        String col7 = rm.getColumnName(7);
                        String col8= rm.getColumnName(8);

                        if(!r.isBeforeFirst()) {
                            System.out.println("No potential order on novelties of size "+sz + " was found in our database.");


                        }else{
                            System.out.println("Displaying all potential orders on novelties of size "+sz+ " within our database:");

                            System.out.println("");
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            s.append(col1);
                            s.append(',');
                            s.append(col2);
                            s.append(',');
                            s.append(col3);
                            s.append(',');
                            s.append(col4);
                            s.append(',');
                            s.append(col5);
                            s.append(',');
                            s.append(col6);
                            s.append(',');
                            s.append(col7);
                            s.append(',');
                            s.append(col8);
                            s.append('\n');
                            while (r.next()) {
                                System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                                System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                                s.append(r.getInt(1));
                                s.append(',');
                                s.append(r.getString(2));
                                s.append(',');
                                s.append(r.getString(3));
                                s.append(',');
                                s.append(r.getInt(4));
                                s.append(',');
                                s.append(r.getInt(5));
                                s.append(',');
                                s.append(r.getString(6));
                                s.append(',');
                                s.append(r.getInt(7));
                                s.append(',');
                                s.append(r.getInt(8));
                                s.append('\n');
                            }
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1,"SELECT * FROM clients Where potentialOnNovelties=" + sz);
                            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                            System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                            while (!scan.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Enter the ID to filter by.");
                                scan.next();
                            }
                            int csv=scan.nextInt();
                            if(csv==1){
                                System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name");
                                String _file=scanString.nextLine();
                                try {
                                    PrintWriter pw = new PrintWriter(new File(_file));
                                    pw.write(s.toString());
                                    pw.close();
                                }catch (IOException e){
                                    System.out.println("Unable to create file.");
                                }

                                System.out.println("Exported.");
                            }else{
                                System.out.println("Not exported.");
                            }

                        }

                    } catch (SQLException r) {
                        r.printStackTrace();
                    }finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }else if(filter2==6){ //filter by client type
                    try {
                        PreparedStatement p = con.prepareStatement("SELECT * FROM clients WHERE clientType=?");
                        System.out.println("Enter the type of client you would like to filter by:");
                        String type = scanString.nextLine();
                        p.setString(1,type);
                        ResultSet r = p.executeQuery();
                        ResultSetMetaData rm = r.getMetaData();

                        String col1 = rm.getColumnName(1);
                        String col2 = rm.getColumnName(2);
                        String col3 = rm.getColumnName(3);
                        String col4 = rm.getColumnName(4);
                        String col5 = rm.getColumnName(5);
                        String col6 = rm.getColumnName(6);
                        String col7 = rm.getColumnName(7);
                        String col8= rm.getColumnName(8);

                        if(!r.isBeforeFirst()) {
                            System.out.println("No client with type "+ type + " was found within our database.");


                        }else{
                            System.out.println("Displaying clients with type " + type + " within our database:");

                            System.out.println("");
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            s.append(col1);
                            s.append(',');
                            s.append(col2);
                            s.append(',');
                            s.append(col3);
                            s.append(',');
                            s.append(col4);
                            s.append(',');
                            s.append(col5);
                            s.append(',');
                            s.append(col6);
                            s.append(',');
                            s.append(col7);
                            s.append(',');
                            s.append(col8);
                            s.append('\n');

                            while (r.next()) {
                                System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                                System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                                s.append(r.getInt(1));
                                s.append(',');
                                s.append(r.getString(2));
                                s.append(',');
                                s.append(r.getString(3));
                                s.append(',');
                                s.append(r.getInt(4));
                                s.append(',');
                                s.append(r.getInt(5));
                                s.append(',');
                                s.append(r.getString(6));
                                s.append(',');
                                s.append(r.getInt(7));
                                s.append(',');
                                s.append(r.getInt(8));
                                s.append('\n');
                            }
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1,"SELECT * FROM clients Where clientType=" + type);
                            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                            System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                            while (!scan.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Enter the ID to filter by.");
                                scan.next();
                            }
                            int csv=scan.nextInt();
                            if(csv==1){
                                System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                                String _file=scanString.nextLine();
                                try {
                                    PrintWriter pw = new PrintWriter(new File(_file));
                                    pw.write(s.toString());
                                    pw.close();
                                }catch (IOException e){
                                    System.out.println("Unable to create file.");
                                }

                                System.out.println("Exported.");
                            }else{
                                System.out.println("Not exported.");
                            }

                        }

                    } catch (SQLException r) {
                        r.printStackTrace();
                    }finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }


                }else if(filter2==7){ //filter by number of stores
                    try {
                        PreparedStatement p = con.prepareStatement("SELECT * FROM clients WHERE numberOfStores=?");
                        System.out.println("Enter the client number of stores to filter by:");
                        while (!scan.hasNextInt()) {
                            System.out.println("That's not a proper filter selection!");
                            System.out.println("Enter the client number of stores to filter by:");
                            scan.next();
                        }
                        int stores=scan.nextInt();
                        p.setDouble(1,stores);
                        ResultSet r = p.executeQuery();
                        ResultSetMetaData rm = r.getMetaData();

                        String col1 = rm.getColumnName(1);
                        String col2 = rm.getColumnName(2);
                        String col3 = rm.getColumnName(3);
                        String col4 = rm.getColumnName(4);
                        String col5 = rm.getColumnName(5);
                        String col6 = rm.getColumnName(6);
                        String col7 = rm.getColumnName(7);
                        String col8= rm.getColumnName(8);

                        if(!r.isBeforeFirst()) {
                            System.out.println("No client with  "+ stores + " stores was found within our database.");


                        }else{
                            System.out.println("Displaying all clients with "+ stores +" stores:");

                            System.out.println("");
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            s.append(col1);
                            s.append(',');
                            s.append(col2);
                            s.append(',');
                            s.append(col3);
                            s.append(',');
                            s.append(col4);
                            s.append(',');
                            s.append(col5);
                            s.append(',');
                            s.append(col6);
                            s.append(',');
                            s.append(col7);
                            s.append(',');
                            s.append(col8);
                            s.append('\n');
                            while (r.next()) {
                                System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                                System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                                s.append(r.getInt(1));
                                s.append(',');
                                s.append(r.getString(2));
                                s.append(',');
                                s.append(r.getString(3));
                                s.append(',');
                                s.append(r.getInt(4));
                                s.append(',');
                                s.append(r.getInt(5));
                                s.append(',');
                                s.append(r.getString(6));
                                s.append(',');
                                s.append(r.getInt(7));
                                s.append(',');
                                s.append(r.getInt(8));
                                s.append('\n');
                            }
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1,"SELECT * FROM clients Where numberOfStores=" + stores);
                            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                            System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                            while (!scan.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Enter the ID to filter by.");
                                scan.next();
                            }
                            int csv=scan.nextInt();
                            if(csv==1){
                                System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                                String _file=scanString.nextLine();
                                try {
                                    PrintWriter pw = new PrintWriter(new File(_file));
                                    pw.write(s.toString());
                                    pw.close();
                                }catch (IOException e){
                                    System.out.println("Unable to create file.");
                                }

                                System.out.println("Exported.");
                            }else{
                                System.out.println("Not exported.");
                            }
                        }
                    } catch (SQLException r) {
                        r.printStackTrace();
                    }finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }else if(filter2==8){ //filter by number of employees
                    try {
                        PreparedStatement p = con.prepareStatement("SELECT * FROM clients WHERE numberOfEmployees=?");
                        System.out.println("Enter the number of employees to filter by:");
                        while (!scan.hasNextInt()) {
                            System.out.println("That's not a proper filter selection!");
                            System.out.println("Enter the number of employees to filter by:");
                            scan.next();
                        }
                        int numEmployees=scan.nextInt();
                        p.setDouble(1,numEmployees);
                        ResultSet r = p.executeQuery();
                        ResultSetMetaData rm = r.getMetaData();

                        String col1 = rm.getColumnName(1);
                        String col2 = rm.getColumnName(2);
                        String col3 = rm.getColumnName(3);
                        String col4 = rm.getColumnName(4);
                        String col5 = rm.getColumnName(5);
                        String col6 = rm.getColumnName(6);
                        String col7 = rm.getColumnName(7);
                        String col8= rm.getColumnName(8);


                        if(!r.isBeforeFirst()) {
                            System.out.println("No client has "+ numEmployees + " employees within our database.");

                        }else {
                            System.out.println("Displaying all clients for sale with  "+ numEmployees + "employees.");

                            System.out.println("");
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                            System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                            s.append(col1);
                            s.append(',');
                            s.append(col2);
                            s.append(',');
                            s.append(col3);
                            s.append(',');
                            s.append(col4);
                            s.append(',');
                            s.append(col5);
                            s.append(',');
                            s.append(col6);
                            s.append(',');
                            s.append(col7);
                            s.append(',');
                            s.append(col8);
                            s.append('\n');
                            while (r.next()) {
                                System.out.format(format, r.getInt(1),r.getString(2), r.getString(3), r.getInt(4) + " yards", r.getInt(5) + " yards", r.getString(6), r.getInt(7) + " stores", r.getInt(8) + " employees");
                                System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------","------------------","------------------");
                                s.append(r.getInt(1));
                                s.append(',');
                                s.append(r.getString(2));
                                s.append(',');
                                s.append(r.getString(3));
                                s.append(',');
                                s.append(r.getInt(4));
                                s.append(',');
                                s.append(r.getInt(5));
                                s.append(',');
                                s.append(r.getString(6));
                                s.append(',');
                                s.append(r.getInt(7));
                                s.append(',');
                                s.append(r.getInt(8));
                                s.append('\n');
                            }
                            CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                            p2.setString(1,"SELECT * FROM clients Where numEmployees=" + numEmployees);
                            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                            System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                            while (!scan.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Enter the ID to filter by.");
                                scan.next();
                            }
                            int csv=scan.nextInt();
                            if(csv==1){
                                System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                                String _file=scanString.nextLine();
                                try {
                                    PrintWriter pw = new PrintWriter(new File(_file));
                                    pw.write(s.toString());
                                    pw.close();
                                }catch (IOException e){
                                    System.out.println("Unable to create file.");
                                }

                                System.out.println("Exported.");
                            }else{
                                System.out.println("Not exported.");
                            }
                        }
                    } catch (SQLException r) {
                        r.printStackTrace();
                    }finally {
                        try {
                            con.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                }else {
                    System.out.println("Not a valid option please choose again.");
                    SelectClient();
                }

            }else {
                System.out.println("Not a valid option please choose again.");
                SelectClient();
            }
        }

        //this method uses an aggregate function in it to display the average retail price per an individual client
        //it returns a table with the client's name and the average price their clothing cost
        public void averageRetailPriceByClient(){
            StringBuilder s=new StringBuilder();

            try {
                PreparedStatement p = con.prepareStatement("SELECT clientName, ROUND((woolPants+polyViscoPants+cottonPants+viscoseDress+polyesterDress+outwear+jeans+whiteShirt)/8, 2) AS AverageRetailPricePerClient FROM retailPrices");
                ResultSet r=p.executeQuery();
                ResultSetMetaData rm = r.getMetaData();
                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String format ="\u2503%1$-25s\u2503%2$-30s\u2503\n";


                if(!r.isBeforeFirst()){
                    System.out.println("No clients exist in our database." );

                }else{
                    System.out.println("Displaying the average retail prices for all clients within the database:" );

                    System.out.println("");
                    System.out.format(format, "+++++++++++++++++++++++++", "++++++++++++++++++++++++++++++");
                    System.out.format(format, col1, col2);
                    System.out.format(format, "+++++++++++++++++++++++++", "++++++++++++++++++++++++++++++");
                    s.append(col1);
                    s.append(',');
                    s.append(col2);
                    s.append('\n');
                    while (r.next()){
                        System.out.format(format, r.getString(1), " $" + r.getDouble(2));
                        System.out.format(format, "-------------------------", "------------------------------");
                        s.append(r.getString(1));
                        s.append(',');
                        s.append(r.getInt(2));
                        s.append('\n');
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1,"SELECT clientName, ROUND((woolPants+polyViscoPants+cottonPants+viscoseDress+polyesterDress+outwear+jeans+whiteShirt)/8, 2) AS AverageRetailPricePerClient FROM retailPrices");
                    p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                    System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                    while (!scan.hasNextInt()) {
                        System.out.println("That's not a number!");
                        scan.next();
                    }
                    int csv=scan.nextInt();
                    if(csv==1){
                        System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                        String _file=scanString.nextLine();
                        try {
                            PrintWriter pw = new PrintWriter(new File(_file));
                            pw.write(s.toString());
                            pw.close();
                        }catch (IOException e){
                            System.out.println("Unable to create file.");
                        }

                        System.out.println("Exported.");
                    }else{
                        System.out.println("Not exported.");
                    }
                }

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        //method to view all the agents within the database
        public void viewAgents(){
            StringBuilder s = new StringBuilder();

            try{
                PreparedStatement p = con.prepareStatement("SELECT * FROM agents");
                ResultSet r=p.executeQuery();
                ResultSetMetaData rm = r.getMetaData();
                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String col3=rm.getColumnName(3);
                String col4=rm.getColumnName(4);
                String col5=rm.getColumnName(5);
                String col6=rm.getColumnName(6);


                String format ="\u2503%1$-20s\u2503%2$-20s\u2503%3$-20s\u2503%4$-30s\u2503%5$-20s\u2503%6$-25s\u2503\n";



                if(!r.isBeforeFirst()){
                    System.out.println("Records do not exists.");

                }else{
                    System.out.println("Displaying all agents within the database:");

                    System.out.println("");
                    System.out.format(format, "++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","++++++++++++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++");
                    System.out.format(format, col1, col2,col3, col4,col5,col6);
                    System.out.format(format, "++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","++++++++++++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++");
                    s.append(col1);
                    s.append(',');
                    s.append(col2);
                    s.append(',');
                    s.append(col3);
                    s.append(',');
                    s.append(col4);
                    s.append(',');
                    s.append(col5);
                    s.append(',');
                    s.append(col6);
                    s.append('\n');
                    while (r.next()){
                        System.out.format(format, r.getInt(1),r.getString(2), r.getString(3),r.getString(4),r.getString(5), r.getString(6));
                        System.out.format(format, "--------------------", "--------------------","--------------------","------------------------------","--------------------","-------------------------");
                        s.append(r.getInt(1));
                        s.append(',');
                        s.append(r.getString(2));
                        s.append(',');
                        s.append(r.getString(3));
                        s.append(',');
                        s.append(r.getString(4));
                        s.append(',');
                        s.append(r.getString(5));
                        s.append(',');
                        s.append(r.getString(6));
                        s.append('\n');
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1,"SELECT * FROM agents");
                    p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                    System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                    while (!scan.hasNextInt()) {
                        System.out.println("That's not a number!");
                        scan.next();
                    }
                    int csv=scan.nextInt();
                    if(csv==1){
                        System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                        String _file=scanString.nextLine();
                        try {
                            PrintWriter pw = new PrintWriter(new File(_file));
                            pw.write(s.toString());
                            pw.close();
                        }catch (IOException e){
                            System.out.println("Unable to create file.");
                        }

                        System.out.println("Exported.");
                    }else{
                        System.out.println("Not exported.");
                    }
                }

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        //this method displays clients with minimum and maximum average retail prices
        public void betweenAveragePrice() {
            StringBuilder s = new StringBuilder();

            try {
                System.out.println("In order to filter the right client for you, we need a minimum and maximum inputs...");
                System.out.println("What is the minimum average retail price the client must have?");
                while (!scan.hasNextDouble()) {
                    System.out.println("That's not a correct filter selection for minimum average retail price!");
                    System.out.println("What is the minimum average retail price the client must have?");
                    scan.next();
                }
                double min = scan.nextDouble();
                System.out.println("What is the maximum average retail price the client must have?");
                while (!scan.hasNextDouble()) {
                    System.out.println("That's not a correct filter selection for maximum average retail price!");
                    System.out.println("What is the maximum average retail price the client must have?");
                    scan.next();
                }
                double max = scan.nextDouble();
                PreparedStatement p = con.prepareStatement("SELECT * FROM retailPrices WHERE (woolPants+polyViscoPants+cottonPants+viscoseDress+polyesterDress+outWear+jeans+whiteShirt)/8 BETWEEN ? AND ?");
                p.setDouble(1, min);
                p.setDouble(2, max);
                ResultSet r = p.executeQuery();
                ResultSetMetaData rm = r.getMetaData();

                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String col3 = rm.getColumnName(3);
                String col4 = rm.getColumnName(4);
                String col5 = rm.getColumnName(5);
                String col6 = rm.getColumnName(6);
                String col7 = rm.getColumnName(7);
                String col8 = rm.getColumnName(8);

                if (!r.isBeforeFirst()) {
                    System.out.println("No clients with an average retail price between " + min + " and " + max + "were found in our database.");

                } else {
                    System.out.println("Displaying clients with an average retail price between " + min + " and " + max);

                    System.out.println("");
                    System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                    System.out.format(format, col1, col2, col3, col4, col5, col6, col7, col8);
                    System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++");
                    s.append(col1);
                    s.append(',');
                    s.append(col2);
                    s.append(',');
                    s.append(col3);
                    s.append(',');
                    s.append(col4);
                    s.append(',');
                    s.append(col5);
                    s.append(',');
                    s.append(col6);
                    s.append(',');
                    s.append(col7);
                    s.append(',');
                    s.append(col8);
                    s.append(',');
                    s.append('\n');
                    while (r.next()) {
                        System.out.format(format, r.getInt( 1), r.getString( 2), "$" + r.getString( 3), "$" + r.getInt( 4), "$" + r.getInt( 5), "$" + r.getString( 6), "$" + r.getInt( 7), "$" + r.getInt( 8));
                        System.out.format(format, "------------------", "------------------", "------------------", "------------------------------", "------------------------------", "------------------", "------------------", "------------------");
                        s.append(r.getInt(1));
                        s.append(',');
                        s.append(r.getString(2));
                        s.append(',');
                        s.append(r.getString(3));
                        s.append(',');
                        s.append(r.getInt(4));
                        s.append(',');
                        s.append(r.getInt(5));
                        s.append(',');
                        s.append(r.getString(6));
                        s.append(',');
                        s.append(r.getInt(7));
                        s.append(',');
                        s.append(r.getInt(8));
                        s.append('\n');
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1, "SELECT * FROM retailPrices WHERE (woolPants+polyViscoPants+cottonPants+viscoseDress+polyesterDress+outWear+jeans+whiteShirt)/8 BETWEEN ? AND ?");
                    p2.setString(2, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3, LoginOrRegister.primary_keys);
                    p2.executeUpdate();
                    System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                    while (!scan.hasNextInt()) {
                        System.out.println("That's not a number!");
                        scan.next();
                    }
                    int csv = scan.nextInt();
                    if (csv == 1) {
                        System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                        String _file = scanString.nextLine();
                        try {
                            PrintWriter pw = new PrintWriter(new File(_file));
                            pw.write(s.toString());
                            pw.close();
                        } catch (IOException e) {
                            System.out.println("Unable to create file.");
                        }

                        System.out.println("Exported.");
                    } else {
                        System.out.println("Not exported.");
                    }

                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        //this method filter agents based on location Jurisdiction
        //the query also is converted into a view
        public void viewAgentsByJurisdiction(){
            StringBuilder s = new StringBuilder();

            try{
                System.out.println("Please enter the location jurisdiction in which you like to filter agents by.");
                String Jurisdiction=scanString.nextLine();
                PreparedStatement p = con.prepareStatement("SELECT * FROM agents WHERE locationJurisdiction=?");
                p.setString(1, Jurisdiction);
                ResultSet r=p.executeQuery();
                ResultSetMetaData rm = r.getMetaData();
                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String col3=rm.getColumnName(3);
                String col4=rm.getColumnName(4);
                String col5=rm.getColumnName(5);
                String col6=rm.getColumnName(6);


                String format ="\u2503%1$-20s\u2503%2$-20s\u2503%3$-20s\u2503%4$-30s\u2503%5$-20s\u2503%6$-25s\u2503\n";


                if(!r.isBeforeFirst()){
                    System.out.println("No agent exists with jurisdiction over " + Jurisdiction + ".") ;

                }else{
                    System.out.println("Displaying all agent with jurisdiction over " + Jurisdiction + ".");

                    System.out.println("");
                    System.out.format(format, "++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","++++++++++++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++");
                    System.out.format(format, col1, col2,col3, col4,col5,col6);
                    System.out.format(format, "++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","++++++++++++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++");
                    s.append(col1);
                    s.append(',');
                    s.append(col2);
                    s.append(',');
                    s.append(col3);
                    s.append(',');
                    s.append(col4);
                    s.append(',');
                    s.append(col5);
                    s.append(',');
                    s.append(col6);
                    s.append('\n');
                    while (r.next()){
                        System.out.format(format, r.getInt(1),r.getString(2), r.getString(3),r.getString(4),r.getString(5), r.getString(6));
                        System.out.format(format, "--------------------", "--------------------","--------------------","------------------------------","--------------------","-------------------------");
                        s.append(r.getInt(1));
                        s.append(',');
                        s.append(r.getString(2));
                        s.append(',');
                        s.append(r.getString(3));
                        s.append(',');
                        s.append(r.getString(4));
                        s.append(',');
                        s.append(r.getString(5));
                        s.append(',');
                        s.append(r.getString(6));
                        s.append('\n');
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1,"SELECT * FROM agents WHERE locationJurisdiction=" + Jurisdiction);
                    p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                    System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                    while (!scan.hasNextInt()) {
                        System.out.println("That's not a number!");
                        scan.next();
                    }
                    int csv=scan.nextInt();
                    if(csv==1){
                        System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                        String _file=scanString.nextLine();
                        try {
                            PrintWriter pw = new PrintWriter(new File(_file));
                            pw.write(s.toString());
                            pw.close();
                        }catch (IOException e){
                            System.out.println("Unable to create file.");
                        }

                        System.out.println("Exported.");
                    }else{
                        System.out.println("Not exported.");
                    }

                }

            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //method displays client names with the corresponding agent according to their location
        //the query contains a join across 3 tables
        //also contains a view

        public void AgentsPerClient(){
            StringBuilder s=new StringBuilder();

            try{
                PreparedStatement p = con.prepareStatement("SELECT * FROM agentsPerClient");
;
                ResultSet r = p.executeQuery();

                ResultSetMetaData rm = r.getMetaData();
                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String col3=rm.getColumnName(3);
                String col4=rm.getColumnName(4);
                String col5=rm.getColumnName(5);
                String col6=rm.getColumnName(6);

                String format ="\u2503%1$-30s\u2503%2$-20s\u2503%3$-20s\u2503%4$-20s\u2503%5$-30s\u2503%6$-20s\u2503\n";

                if(!r.isBeforeFirst()){
                    System.out.println("Record does not exist.");

                }else{
                    System.out.println("Displaying the client information along with their corresponding agent:");
                    System.out.println("");
                    System.out.format(format, "++++++++++++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","++++++++++++++++++++","++++++++++++++++++++++++++++++","++++++++++++++++++++");
                    System.out.format(format, col1, col2,col3, col4,col5,col6);
                    System.out.format(format, "++++++++++++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","++++++++++++++++++++","++++++++++++++++++++++++++++++","++++++++++++++++++++");
                    s.append(col1);
                    s.append(',');
                    s.append(col2);
                    s.append(',');
                    s.append(col3);
                    s.append(',');
                    s.append(col4);
                    s.append(',');
                    s.append(col5);
                    s.append(',');
                    s.append(col6);
                    s.append(',');
                    s.append('\n');
                    while (r.next()){
                        System.out.format(format, r.getString(1),r.getString(2), r.getString(3),r.getString(4),r.getString(5), r.getString(6));
                        System.out.format(format, "------------------------------", "--------------------","--------------------","--------------------","------------------------------","--------------------");
                        s.append(r.getString(1));
                        s.append(',');
                        s.append(r.getString(2));
                        s.append(',');
                        s.append(r.getString(3));
                        s.append(',');
                        s.append(r.getString(4));
                        s.append(',');
                        s.append(r.getString(5));
                        s.append(',');
                        s.append(r.getString(6));
                        s.append('\n');
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1,"SELECT * FROM agentsPerClient");
                    p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                    System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                    while (!scan.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Enter the ID to filter by.");
                        scan.next();
                    }
                    int csv=scan.nextInt();
                    if(csv==1){
                        System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                        String _file=scanString.nextLine();
                        try {
                            PrintWriter pw = new PrintWriter(new File(_file));
                            pw.write(s.toString());
                            pw.close();
                        }catch (IOException e){
                            System.out.println("Unable to create file.");
                        }
                        System.out.println("Exported.");
                    }else{
                        System.out.println("Not exported.");
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    //method displays client names with the corresponding retail prices
    public void retailPricePerClient(){
        StringBuilder s = new StringBuilder();

        try{
            PreparedStatement p = con.prepareStatement("SELECT * FROM retailPrices");

            ResultSet r = p.executeQuery();

            ResultSetMetaData rm = r.getMetaData();
            String col1 = rm.getColumnName(1);
            String col2 = rm.getColumnName(2);
            String col3=rm.getColumnName(3);
            String col4=rm.getColumnName(4);
            String col5=rm.getColumnName(5);
            String col6=rm.getColumnName(6);
            String col7=rm.getColumnName(7);
            String col8=rm.getColumnName(8);
            String col9=rm.getColumnName(9);
            String col10=rm.getColumnName(10);


            String format ="\u2503%1$-17s\u2503%2$-17s\u2503%3$-17s\u2503%4$-17s\u2503%5$-17s\u2503%6$-17s\u2503%7$-17s\u2503%8$-17s\u2503%9$-17s\u2503%10$-17s\u2503\n";

            if(!r.isBeforeFirst()){
                System.out.println("Record does not exist.");

            }else{
                System.out.println("Displaying all different retail prices for each client:");
                System.out.println("");
                System.out.format(format, "+++++++++++++++++", "+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++");
                System.out.format(format, col1, col2,col3, col4,col5,col6,col7,col8,col9,col10);
                System.out.format(format, "+++++++++++++++++", "+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++","+++++++++++++++++");
                s.append(col1);
                s.append(',');
                s.append(col2);
                s.append(',');
                s.append(col3);
                s.append(',');
                s.append(col4);
                s.append(',');
                s.append(col5);
                s.append(',');
                s.append(col6);
                s.append(',');
                s.append(col7);
                s.append(',');
                s.append(col8);
                s.append(',');
                s.append(col9);
                s.append(',');
                s.append(col10);
                s.append('\n');
                while (r.next()){
                    System.out.format(format,r.getInt(1), r.getString(2),"$"+ r.getInt(3), "$" +r.getInt(4), "$" + r.getInt(5),"$"+ r.getInt(6),"$" + r.getInt(7), "$" + r.getInt(8), "$" + r.getInt(9), "$" + r.getInt(10));
                    System.out.format(format, "-----------------", "-----------------","-----------------","-----------------","-----------------","-----------------","-----------------","-----------------","-----------------","-----------------");
                    s.append(r.getInt(1));
                    s.append(',');
                    s.append(r.getString(2));
                    s.append(',');
                    s.append(r.getInt(3));
                    s.append(',');
                    s.append(r.getInt(4));
                    s.append(',');
                    s.append(r.getInt(5));
                    s.append(',');
                    s.append(r.getInt(6));
                    s.append(',');
                    s.append(r.getInt(7));
                    s.append(',');
                    s.append(r.getInt(8));
                    s.append(',');
                    s.append(r.getInt(9));
                    s.append(',');
                    s.append(r.getInt(10));
                    s.append('\n');
                }
                CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                p2.setString(1,"SELECT * FROM retailPrices");
                p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                while (!scan.hasNextInt()) {
                    System.out.println("That's not a number!");
                    System.out.println("Enter the ID to filter by.");
                    scan.next();
                }
                int csv=scan.nextInt();
                if(csv==1){
                    System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                    String _file=scanString.nextLine();
                    try {
                        PrintWriter pw = new PrintWriter(new File(_file));
                        pw.write(s.toString());
                        pw.close();
                    }catch (IOException e){
                        System.out.println("Unable to create file.");
                    }

                    System.out.println("Exported.");
                }else{
                    System.out.println("Not exported.");
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //method that displays the clients with an average retail price above the average price for all clients in the database
    //this method contains a query with a subquery
        public void aboveAvgRetailPrice(){
            StringBuilder s=new StringBuilder();

            try {
                PreparedStatement p = con.prepareStatement("SELECT * FROM averagePricePerCompany WHERE averagePrice>(SELECT AVG(averagePrice) FROM averagePricePerCompany)");
                ResultSet r = p.executeQuery();
                ResultSetMetaData rm = r.getMetaData();

                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String format ="\u2503%1$-18s\u2503%2$-18s\u2503\n";

                if(!r.isBeforeFirst()){
                    System.out.println("No average retail price information exists for this criteria.");

                }else{
                    System.out.println("Displaying all the clients with retail prices at their stores that is higher than the average.");

                    System.out.println("");
                    System.out.format(format, "++++++++++++++++++", "++++++++++++++++++");
                    System.out.format(format, col1, col2);
                    System.out.format(format, "++++++++++++++++++", "++++++++++++++++++");
                    s.append(col1);
                    s.append(',');
                    s.append(col2);
                    s.append('\n');

                    while (r.next()) {
                        System.out.format(format, r.getString(1),"$" + r.getDouble(2));
                        System.out.format(format, "------------------", "------------------");
                        s.append(r.getString(1));
                        s.append(',');
                        s.append(r.getDouble(2));
                        s.append('\n');
                    }
                    CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                    p2.setString(1,"SELECT * FROM averagePricePerCompany WHERE averagePrice>(SELECT AVG(averagePrice) FROM averagePricePerCompany)");
                    p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                    System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                    while (!scan.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Enter the ID to filter by.");
                        scan.next();
                    }
                    int csv=scan.nextInt();
                    if(csv==1){
                        System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                        String _file=scanString.nextLine();
                        try {
                            PrintWriter pw = new PrintWriter(new File(_file));
                            pw.write(s.toString());
                            pw.close();
                        }catch (IOException e){
                            System.out.println("Unable to create file.");
                        }

                        System.out.println("Exported.");
                    }else{
                        System.out.println("Not exported.");
                    }

                }

            } catch (SQLException r) {
                r.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    //method that displays the clients with an average retail price below the average price for all clients in the database
    //this method contains a query with a subquery
    public void belowAvgRetailPrice(){
        StringBuilder s=new StringBuilder();

        try {
            PreparedStatement p = con.prepareStatement("SELECT * FROM averagePricePerCompany WHERE averagePrice<(SELECT AVG(averagePrice) FROM averagePricePerCompany)");
            ResultSet r = p.executeQuery();
            ResultSetMetaData rm = r.getMetaData();

            String col1 = rm.getColumnName(1);
            String col2 = rm.getColumnName(2);
            String format ="\u2503%1$-18s\u2503%2$-18s\u2503\n";

            if(!r.isBeforeFirst()){
                System.out.println("No average retail price information exists for this criteria.");

            }else{
                System.out.println("Displaying all the clients with retail prices at their stores that is lower than the average.");

                System.out.println("");
                System.out.format(format, "++++++++++++++++++", "++++++++++++++++++");
                System.out.format(format, col1, col2);
                System.out.format(format, "++++++++++++++++++", "++++++++++++++++++");
                s.append(col1);
                s.append(',');
                s.append(col2);
                s.append('\n');

                while (r.next()) {
                    System.out.format(format, r.getString(1),"$" + r.getDouble(2));
                    System.out.format(format, "------------------", "------------------");
                    s.append(r.getString(1));
                    s.append(',');
                    s.append(r.getDouble(2));
                    s.append('\n');
                }
                CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                p2.setString(1,"SELECT * FROM averagePricePerCompany WHERE averagePrice<(SELECT AVG(averagePrice) FROM averagePricePerCompany)");
                p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                while (!scan.hasNextInt()) {
                    System.out.println("That's not a number!");
                    System.out.println("Enter the ID to filter by.");
                    scan.next();
                }
                int csv=scan.nextInt();
                if(csv==1){
                    System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                    String _file=scanString.nextLine();
                    try {
                        PrintWriter pw = new PrintWriter(new File(_file));
                        pw.write(s.toString());
                        pw.close();
                    }catch (IOException e){
                        System.out.println("Unable to create file.");
                    }

                    System.out.println("Exported.");
                }else{
                    System.out.println("Not exported.");
                }

            }

        } catch (SQLException r) {
            r.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
        //method that returns all of the users within the database and their corresponding user information
        //method is intended for administrator use only
        public void viewAllUsers(){
            StringBuilder s=new StringBuilder();

            try {
                PreparedStatement p = con.prepareStatement("SELECT * FROM users");

                ResultSet r = p.executeQuery();
                ResultSetMetaData rm = r.getMetaData();

                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String col3 = rm.getColumnName(3);
                String col4 = rm.getColumnName(4);
                String col5 = rm.getColumnName(5);
                String col6 = rm.getColumnName(6);
                String col7 = rm.getColumnName(7);
                String format = "\u2503%1$-18s\u2503%2$-18s\u2503%3$-18s\u2503%4$-18s\u2503%5$-18s\u2503%6$-18s\u2503\n";



                if(!r.isBeforeFirst()){
                    System.out.println("No users exist");

                }else{
                    System.out.println("Displaying all users in the system.");
                    System.out.println("");
                    System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++++++++++++++");
                    System.out.format(format, col1, col2, col3, col4, col5, col6, col7);
                    System.out.format(format, "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++", "++++++++++++++++++","++++++++++++++++++++++++++++++");
                    s.append(col1);
                    s.append(',');
                    s.append(col2);
                    s.append(',');
                    s.append(col3);
                    s.append(',');
                    s.append(col4);
                    s.append(',');
                    s.append(col5);
                    s.append(',');
                    s.append(col6);
                    s.append(',');
                    s.append(col7);
                    s.append('\n');
                    while (r.next()) {
                        System.out.format(format, r.getInt(1),r.getString(2), r.getInt(3), r.getString(4), r.getString(5), r.getLong(6), r.getString(7));
                        System.out.format(format, "------------------", "------------------", "------------------", "------------------", "------------------", "------------------","------------------------------");
                        s.append(r.getInt(1));
                        s.append(',');
                        s.append(r.getString(2));
                        s.append(',');
                        s.append(r.getInt(3));
                        s.append(',');
                        s.append(r.getString(4));
                        s.append(',');
                        s.append(r.getString(5));
                        s.append(',');
                        s.append(r.getLong(6));
                        s.append(',');
                        s.append(r.getString(7));
                        s.append('\n');
                    }
                    System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                    while (!scan.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Enter the ID to filter by.");
                        scan.next();
                    }
                    int csv=scan.nextInt();
                    if(csv==1){
                        System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name");
                        String _file=scanString.nextLine();
                        try {
                            PrintWriter pw = new PrintWriter(new File(_file));
                            pw.write(s.toString());
                            pw.close();
                        }catch (IOException e){
                            System.out.println("Unable to create file");
                        }

                        System.out.println("Exported");
                    }else{
                        System.out.println("Not exported");
                    }

                }

            } catch (SQLException r) {
                r.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        //method for viewing all the logs generated for the system
        //intended for administrator use only
        //displays the query made, the date/time stamp, as well as the userID of who performed the query
        public void viewLogs(){
            StringBuilder s=new StringBuilder();

            try {
                PreparedStatement p = con.prepareStatement("SELECT * FROM logs");
                ResultSet r=p.executeQuery();
                ResultSetMetaData rm = r.getMetaData();
                String col1 = rm.getColumnName(1);
                String col2 = rm.getColumnName(2);
                String col3 = rm.getColumnName(3);

                String format ="\u2503%1$-170s\u2503%2$-20s\u2503%3$-20s\u2503\n";



                if(!r.isBeforeFirst()){
                    System.out.println("No record exists");

                }else{
                    System.out.println("Displaying the Client Database Index Logs.");

                    System.out.println("");
                    System.out.format(format, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++", "++++++++++++++++++++", "++++++++++++++++++++");
                    System.out.format(format, col1, col2,col3);
                    System.out.format(format, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++", "++++++++++++++++++++", "++++++++++++++++++++");
                    s.append(col1);
                    s.append(',');
                    s.append(col2);
                    s.append(',');
                    s.append(col3);
                    s.append(',');
                    s.append('\n');
                    while (r.next()){
                        System.out.format(format, r.getString(1),r.getString(2),r.getInt(3));
                        System.out.format(format, "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------", "--------------------", "--------------------");
                        s.append(r.getString(1));
                        s.append(',');
                        s.append(r.getString(2));
                        s.append(',');
                        s.append(r.getInt(3));
                        s.append('\n');
                    }
                    System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                    while (!scan.hasNextInt()) {
                        System.out.println("That's not a number!");
                        System.out.println("Enter the ID to filter by.");
                        scan.next();
                    }
                    int csv=scan.nextInt();
                    if(csv==1){
                        System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name");
                        String _file=scanString.nextLine();
                        try {
                            PrintWriter pw = new PrintWriter(new File(_file));
                            pw.write(s.toString());
                            pw.close();
                        }catch (IOException e){
                            System.out.println("Unable to create file");
                        }

                        System.out.println("Exported");
                    }else{
                        System.out.println("Not exported");
                    }

                }


            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    public void viewRelevantEmployees(){
        StringBuilder s = new StringBuilder();

        try{
            PreparedStatement p = con.prepareStatement("SELECT * FROM relevantEmployees");
            ResultSet r =p.executeQuery();
            ResultSetMetaData rm = r.getMetaData();
            String col1 = rm.getColumnName(1);
            String col2 = rm.getColumnName(2);
            String col3=rm.getColumnName(3);
            String col4=rm.getColumnName(4);
            String col5=rm.getColumnName(5);
            String col6=rm.getColumnName(6);


            String format ="\u2503%1$-20s\u2503%2$-20s\u2503%3$-20s\u2503%4$-25s\u2503%5$-20s\u2503%6$-25s\u2503\n";


            if(!r.isBeforeFirst()){
                System.out.println("There are no relevant employees in our database right now. ") ;

            }else{
                System.out.println("Displaying all relevant employees: " );

                System.out.println("");
                System.out.format(format, "++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++");
                System.out.format(format, col1, col2,col3, col4,col5,col6);
                System.out.format(format, "++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++");
                s.append(col1);
                s.append(',');
                s.append(col2);
                s.append(',');
                s.append(col3);
                s.append(',');
                s.append(col4);
                s.append(',');
                s.append(col5);
                s.append(',');
                s.append(col6);
                s.append('\n');
                while (r.next()){
                    System.out.format(format, r.getInt(1),r.getString(2), r.getString(3),r.getString(4),r.getString(5), r.getString(6));
                    System.out.format(format, "--------------------", "--------------------","--------------------","-------------------------","--------------------","-------------------------");
                    s.append(r.getInt(1));
                    s.append(',');
                    s.append(r.getString(2));
                    s.append(',');
                    s.append(r.getString(3));
                    s.append(',');
                    s.append(r.getString(4));
                    s.append(',');
                    s.append(r.getString(5));
                    s.append(',');
                    s.append(r.getString(6));
                    s.append('\n');
                }
                CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                p2.setString(1,"SELECT * FROM relevantEmployees");
                p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                while (!scan.hasNextInt()) {
                    System.out.println("That's not a number!");
                    scan.next();
                }
                int csv=scan.nextInt();
                if(csv==1){
                    System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                    String _file=scanString.nextLine();
                    try {
                        PrintWriter pw = new PrintWriter(new File(_file));
                        pw.write(s.toString());
                        pw.close();
                    }catch (IOException e){
                        System.out.println("Unable to create file.");
                    }

                    System.out.println("Exported.");
                }else{
                    System.out.println("Not exported.");
                }
            }
            }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void filterRelevantEmployeesByCompany(){
        StringBuilder s = new StringBuilder();

        try{
            System.out.println("Please enter the company name for which you would like to filter the employee by:");
            String company=scanString.nextLine();
            PreparedStatement p = con.prepareStatement("SELECT * FROM relevantEmployees WHERE employeeCompany=?");
            p.setString(1, company);
            ResultSet r=p.executeQuery();
            ResultSetMetaData rm = r.getMetaData();
            String col1 = rm.getColumnName(1);
            String col2 = rm.getColumnName(2);
            String col3=rm.getColumnName(3);
            String col4=rm.getColumnName(4);
            String col5=rm.getColumnName(5);
            String col6=rm.getColumnName(6);


            String format ="\u2503%1$-20s\u2503%2$-20s\u2503%3$-20s\u2503%4$-25s\u2503%5$-20s\u2503%6$-25s\u2503\n";


            if(!r.isBeforeFirst()){
                System.out.println("No relevant employees exists who work for " + company + ".") ;

            }else{
                System.out.println("Displaying all relevant employees who work for " + company + ".");

                System.out.println("");
                System.out.format(format, "++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++");
                System.out.format(format, col1, col2,col3, col4,col5,col6);
                System.out.format(format, "++++++++++++++++++++", "++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++","++++++++++++++++++++","+++++++++++++++++++++++++");
                s.append(col1);
                s.append(',');
                s.append(col2);
                s.append(',');
                s.append(col3);
                s.append(',');
                s.append(col4);
                s.append(',');
                s.append(col5);
                s.append(',');
                s.append(col6);
                s.append('\n');
                while (r.next()){
                    System.out.format(format, r.getInt(1),r.getString(2), r.getString(3),r.getString(4),r.getString(5), r.getString(6));
                    System.out.format(format, "--------------------", "--------------------","--------------------","-------------------------","--------------------","-------------------------");
                    s.append(r.getInt(1));
                    s.append(',');
                    s.append(r.getString(2));
                    s.append(',');
                    s.append(r.getString(3));
                    s.append(',');
                    s.append(r.getString(4));
                    s.append(',');
                    s.append(r.getString(5));
                    s.append(',');
                    s.append(r.getString(6));
                    s.append('\n');
                }
                CallableStatement p2 = con.prepareCall("CALL addLog(?,?,?)");
                p2.setString(1,"SELECT * relevantEmployees WHERE employeeCompany=" + company);
                p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
                System.out.println("Would you like to export this data to a CSV file press 1 to export press 2 to continue without exporting?");
                while (!scan.hasNextInt()) {
                    System.out.println("That's not a number!");
                    scan.next();
                }
                int csv=scan.nextInt();
                if(csv==1){
                    System.out.println("Please enter the name of the file you wish to export this data to. Please put .csv at the end of your file's name.");
                    String _file=scanString.nextLine();
                    try {
                        PrintWriter pw = new PrintWriter(new File(_file));
                        pw.write(s.toString());
                        pw.close();
                    }catch (IOException e){
                        System.out.println("Unable to create file.");
                    }

                    System.out.println("Exported.");
                }else{
                    System.out.println("Not exported.");
                }

            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}









