//Created by Riccardo Angiolini on July 23rd, 2018.
// Copyright. All Rights Reserved. ©

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;


public class Insertion {
    private Scanner scanString = new Scanner(System.in);
    private Scanner scanNum = new Scanner(System.in);
    private Connect c = new Connect();
    private Connection con = c.getConnection();

    public Insertion() {
        this.scanString = new Scanner(System.in);
        this.scanNum = new Scanner(System.in);
        this.c = new Connect();
        this.con = this.c.getConnection();
    }

    //method for inserting a client into the database
    public void insertClientInfo() throws SQLException{
        try {
            //making use of transactions
            this.con.setAutoCommit(false);
            //take in all the criteria from the user that is necessary to add a client to the database
            System.out.println("Please fill out the following fields in order to add a new client to the database.");
            System.out.println("");
            System.out.println("Please enter the company name of the client you wish to add to the database:");
            String name = this.scanString.nextLine();
            System.out.println("Please enter the location of your client:");
            String address= this.scanString.nextLine();

            System.out.println("Please enter the potential order in yards your client can fulfill on core programs (only input a number please):");

            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the potential order your client can fulfill on core programs:");
                this.scanNum.next();
            }
            int onCore = this.scanNum.nextInt();


            System.out.println("Please enter the potential order your client can fulfill on novelties:");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the potential order your client can fulfill on novelties:");
                this.scanNum.next();
            }
            int onNovelties = this.scanNum.nextInt();

            System.out.println("Please enter the the type of client this is (this could be retailer, wholesaler, holding etc etc):");
            while (!this.scanNum.hasNextLine()) {
                System.out.println("That's not an acceptable input!");
                System.out.println("Please enter the the type of client this is (this could be retailer, wholesaler, holding etc etc):");
                this.scanNum.next();
            }
            String type = this.scanString.nextLine();

            System.out.println("Please enter the number of stores this client has:");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the number of stores this client has:");
                this.scanNum.next();
            }
            int stores =scanNum.nextInt();

            System.out.println("Finally, please enter the number of employees this client has:");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the number of employees this client has:");
                this.scanNum.next();
            }
            int employees =scanNum.nextInt();
            //query to insert client info in the database
            PreparedStatement addClient= this.con.prepareStatement("INSERT INTO clients(clientName,clientLocation,potentialOnCorePrograms,potentialOnNovelties, clientType, numberOfStores, numberOfEmployees) VALUES (?,?,?,?,?,?,?)");
            addClient.clearParameters();
            //addClient.setInt(1,LoginOrRegister.primary_keys);
            addClient.setString(1, name);
            addClient.setString(2, address);
            addClient.setInt(3,onCore);
            addClient.setInt(4, onNovelties);
            addClient.setString(5, type);
            addClient.setInt(6, stores);
            addClient.setInt(7, employees);
            addClient.executeUpdate();

            System.out.println("Your client has been successfully added to our database!");
            //log the query
            CallableStatement p2 = this.con.prepareCall("CALL addLog(?,?,?)");
            p2.setString(1,"INSERT INTO clients(clientID,clientName,clientLocation,potentialOnCorePrograms,potentialOnNovelties, clientType, numberOfStores, numberOfEmployees) VALUES ("+name+","+address+","+onCore+","+onNovelties+","+type+","+stores+","+employees+")");
            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
            this.con.commit();

        }catch (SQLException e){
            //rollback if something fails
            this.con.rollback();
            System.out.println("An unknown error occurred.");

        }

    }
    //method for inserting retail prices for clients in the database
    public void insertRetailInfo() throws SQLException{
        try {
            //making use of transactions
            this.con.setAutoCommit(false);
            //take in all the criteria from the user that is necessary to add a client to the database
            System.out.println("Please fill out the following fields in order to add new retail prices for a client to the database.");
            System.out.println("");

            System.out.println("Please enter the name of the client for which you wish to add retail prices for in the database:");
            String client = this.scanString.nextLine();


            System.out.println("Please enter the price in dollars for " + client + "'s wool pants (only input a number please):");

            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the price in dollars for " + client + "'s wool pants (only input a number please):");
                this.scanNum.next();
            }
            int wool = this.scanNum.nextInt();


            System.out.println("Please enter the price in dollars for " + client + "'s polyvisco pants (only input a number please):");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the price in dollars for " + client + "'s poly-visco pants (only input a number please):");
                this.scanNum.next();
            }
            int visco = this.scanNum.nextInt();

            System.out.println("Please enter the price in dollars for " + client + "'s cotton pants (only input a number please):");
            while (!this.scanNum.hasNextLine()) {
                System.out.println("That's not an acceptable input!");
                System.out.println("Please enter the price in dollars for " + client + "'s cotton pants (only input a number please):");
                this.scanNum.next();
            }
            int cotton = this.scanNum.nextInt();

            System.out.println("Please enter the price in dollars for " + client + "'s viscose dress (only input a number please):");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the price in dollars for " + client + "'s the viscose dress (only input a number please):");
                this.scanNum.next();
            }
            int viscose =scanNum.nextInt();

            System.out.println("Please enter the price in dollars for " + client + "'s polyester dress (only input a number please):");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the price in dollars for " + client + "'s polyester dress (only input a number please):");
                this.scanNum.next();
            }
            int polyester =scanNum.nextInt();

            System.out.println("Please enter the price in dollars for " + client + "'s outwear (only input a number please):");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the price in dollars for " + client + "'s the outwear (only input a number please):");
                this.scanNum.next();
            }
            int outwear =scanNum.nextInt();

            System.out.println("Please enter the price in dollars for " + client + "'s jeans (only input a number please):");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the price in dollars for " + client + "'s jeans (only input a number please):");
                this.scanNum.next();
            }
            int jeans =scanNum.nextInt();

            System.out.println("Please enter the price in dollars for " + client + "'s white shirts (only input a number please):");
            while (!this.scanNum.hasNextInt()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter the price in dollars for " + client + "'s white shirts (only input a number please):");
                this.scanNum.next();
            }
            int whiteshirt =scanNum.nextInt();


            //query to insert client info in the database
            PreparedStatement addClient= this.con.prepareStatement("INSERT INTO retailPrices(clientName,woolPants,polyViscoPants,cottonPants, viscoseDress, polyesterDress, outWear, jeans, whiteShirt) VALUES (?,?,?,?,?,?,?,?,?)");
            addClient.clearParameters();
            //addClient.setInt(1,LoginOrRegister.primary_keys);
            addClient.setString(1, client);
            addClient.setInt(2, wool);
            addClient.setInt(3,visco);
            addClient.setInt(4, cotton);
            addClient.setInt(5, viscose);
            addClient.setInt(6, polyester);
            addClient.setInt(7, outwear);
            addClient.setInt(8, jeans);
            addClient.setInt(9, whiteshirt);

            addClient.executeUpdate();

            System.out.println("Your client's retail prices have been successfully added to our database!");
            //log the query
            CallableStatement p2 = this.con.prepareCall("CALL addLog(?,?,?)");
            p2.setString(1,"INSERT INTO retailPrices(retailID,woolPants,polyViscoPants,cottonPants, viscoseDress, polyesterDress, outWear, jeans, whiteShirt) VALUES ("+client+","+wool+","+visco+","+cotton+","+viscose+","+polyester+","+outwear+","+jeans+","+whiteshirt+")");
            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
            this.con.commit();

        }catch (SQLException e){
            //rollback if something fails
            this.con.rollback();
            System.out.println("An unknown error occurred.");

        }

    }

    //method for inserting into the average price for retail client table

    public void addToAverageTable() throws SQLException{
        try{
            this.con.setAutoCommit(false);
            PreparedStatement addAverage = this.con.prepareStatement("INSERT INTO averagePricePerCompany (clientName, averagePrice) SELECT clientName, ROUND((woolPants+polyViscoPants+cottonPants+viscoseDress+polyesterDress+outwear+jeans+whiteShirt)/8, 2)  FROM retailPrices");
            addAverage.clearParameters();
            addAverage.executeUpdate();
            this.con.commit();
        }catch (SQLException e){
            //rollback if something fails
            this.con.rollback();
            System.out.println("An unknown error occurred.");

        }

    }

    //method for inserting a relevant employee into the database
    public void addRelevantEmployee() throws SQLException{
        try {
            //making use of transactions
            this.con.setAutoCommit(false);
            //take in all the criteria from the user that is necessary to add an employee to the database
            System.out.println("Please fill out the following fields in order to add a new employee to the database.");
            System.out.println("");
            System.out.println("Please enter the first name of the employee you wish to add:");
            String firstName = this.scanString.nextLine();
            System.out.println("Please enter the last name of the employee you wish to add:");
            String lastName = this.scanString.nextLine();

            System.out.println("Please enter the email address of the employee you wish to add:");
            String employeeEmail = this.scanString.nextLine();

            System.out.println("Please enter the phone number of the employee you wish to add:");

            while (!this.scanString.hasNextLine()) {
                System.out.println("That's not an accepted number!");
                System.out.println("Please enter the phone number of the employee you wish to add:");
                this.scanNum.next();
            }
            String employeePhone = this.scanString.nextLine();

            System.out.println("Finally, please enter the company of the employee you wish to add:");

            while (!this.scanString.hasNextLine()) {
                System.out.println("That's not an acceptable input!");
                System.out.println("Please enter the company of the employee you wish to add:");
                this.scanString.next();
            }
            String company = this.scanString.nextLine();

            //query to insert relevant employee info in the database
            PreparedStatement addEmployee= this.con.prepareStatement("INSERT INTO relevantEmployees(firstName,lastName,employeeEmail,employeePhoneNumber,employeeCompany) VALUES (?,?,?,?,?)");
            addEmployee.clearParameters();
            //addEmployee.setInt(1,LoginOrRegister.primary_keys);
            addEmployee.setString(1, firstName);
            addEmployee.setString(2, lastName);
            addEmployee.setString(3, employeeEmail);
            addEmployee.setString(4, employeePhone);
            addEmployee.setString(5, company);
            addEmployee.executeUpdate();

            System.out.println("The relevant employee has been successfully added to our client index database!");

            //log the query
            CallableStatement p2 = this.con.prepareCall("CALL addLog(?,?,?)");
            p2.setString(1,"INSERT INTO relevantEmployees(firstName,lastName,employeeEmail,employeePhoneNumber,employeeCompany) VALUES ("+ firstName + "," + lastName + "," + employeeEmail + "," + employeePhone + "," + company + ")");
            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
            this.con.commit();

        }catch (SQLException e){
            //rollback if something fails
            this.con.rollback();

        }

    }
    //method for inserting an agent into the database
    public void addAgent() throws SQLException{
        try {
            //making use of transactions
            this.con.setAutoCommit(false);
            //take in all the criteria from the user that is necessary to add an agent to the database
            System.out.println("Please fill out the following fields in order to add a new agent to the database.");
            System.out.println("");
            System.out.println("Please enter the first name of the agent you wish to add:");
            String agentfirstName = this.scanString.nextLine();
            System.out.println("Please enter the last name of the agent you wish to add:");
            String agentlastName = this.scanString.nextLine();

            System.out.println("Please enter the email address of the agent you wish to add:");
            String agentEmail = this.scanString.nextLine();

            System.out.println("Please enter the phone number of the agent you wish to add:");

            while (!this.scanString.hasNextLine()) {
                System.out.println("That's not an accepted number!");
                System.out.println("Please enter the phone number of the agent you wish to add:");
                this.scanNum.next();
            }
            String agentPhone = this.scanString.nextLine();

            System.out.println("Finally, please enter the location jurisdiction of the agent you wish to add:");

            while (!this.scanString.hasNextLine()) {
                System.out.println("That's not an acceptable input!");
                System.out.println("Please enter the location jurisdiction of the agent you wish to add:");
                this.scanString.next();
            }
            String jurisdiction = this.scanString.nextLine();

            //query to insert relevant employee info in the database
            PreparedStatement addEmployee= this.con.prepareStatement("INSERT INTO agents(agentFirstName,agentLastName,agentEmail,agentPhoneNumber,locationJurisdiction) VALUES (?,?,?,?,?)");
            addEmployee.clearParameters();
            //addEmployee.setInt(1,LoginOrRegister.primary_keys);
            addEmployee.setString(1, agentfirstName);
            addEmployee.setString(2, agentlastName);
            addEmployee.setString(3, agentEmail);
            addEmployee.setString(4, agentPhone);
            addEmployee.setString(5, jurisdiction);
            addEmployee.executeUpdate();

            System.out.println("Your agent has been successfully added to our client index database!");

            //log the query
            CallableStatement p2 = this.con.prepareCall("CALL addLog(?,?,?)");
            p2.setString(1,"INSERT INTO agents(agentFirstName,agentLastName,agentEmail,agentPhoneNumber,locationJurisdiction) VALUES ("+ agentfirstName + "," + agentlastName + "," + agentEmail + "," + agentPhone + "," + jurisdiction + ")");
            p2.setString(2,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            p2.setInt(3,LoginOrRegister.primary_keys);p2.executeUpdate();
            this.con.commit();

        }catch (SQLException e){
            //rollback if something fails
            this.con.rollback();

        }

    }
}


