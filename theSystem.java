//Created by Riccardo Angiolini on July 23rd, 2018.
// Copyright. All Rights Reserved. ©

import java.sql.SQLException;
import java.util.Scanner;

public class theSystem {

    private Selection s = new Selection();
    private LoginOrRegister l = new LoginOrRegister();
    private Insertion i = new Insertion();
    private Updating u = new Updating();
    private Deletion d = new Deletion();
    private Scanner scanInt = new Scanner(System.in);

    //method propts the start up menu tha leads to login/registration
    public void start() throws InterruptedException {
        System.out.println("WELCOME TO THE 0-MILE CLIENT DATABASE");
        //thread.sleep added for aesthetics
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.print(".");
        Thread.sleep(20);
        System.out.println("");
        System.out.println("1- Login");
        System.out.println("2- Register");
        System.out.println("3- Administrator Login");
        System.out.println("4- Exit the database");

        System.out.println("Please enter 1 to Login, 2 to Register, 3 to Login as an Administrator, or 4 to Exit the system:");
        while (!scanInt.hasNextInt()) {
            System.out.println("Not a valid input!");
            System.out.println("Please enter 1 to Login:");
            System.out.println("2 to register:");
            System.out.println("3 to login as administrator:");
            System.out.println("4 to exit the system:");
            scanInt.next();
        }
        //based on the users choice perform different sections of the application
        int logOrreg = scanInt.nextInt();
        if (logOrreg == 1) {

            l.login();
            run();

        } else if (logOrreg == 2) {
            l.register();
            run();
        } else if (logOrreg == 3) {
            l.Adminlogin();
            runAdmin();
        } else if (logOrreg == 4) {
            System.out.println("Thank you for using the 0-MILE Client Database and goodbye!");
            System.exit(0);
        } else {
            System.out.println("Not a valid option.");
            start();
        }
    }

    //runs the administrator portion of the application
    public void runAdmin() throws InterruptedException {
        int goAgain2 = 1;
        do {
            s.getConnection();
            System.out.println("WELCOME ADMINISTRATOR TO THE 0- MILE CLIENT DATABASE");
            System.out.println("What would you like to do?");
            System.out.println("1- View all the users within the database?");
            System.out.println("2- View the 0-Mile Client Database logs?");
            System.out.println("3- Logout");

            while (!scanInt.hasNextInt()) {
                System.out.println("1- View all the users within the database?");
                System.out.println("2- View the 0-Mile Client Database logs?");
                System.out.println("3- Logout");
                System.out.println("Please select between options 1-3 by entering the number corresponding to the option.");
                scanInt.next();
            }

            int option = scanInt.nextInt();
            if (option == 1) {
                s.viewAllUsers();
                System.out.println("Back to the main menu?");
                System.out.println("1-Yes, 2-No.");
                while (!scanInt.hasNextInt()) {
                    System.out.println("That's not a number!");
                    System.out.println("Back to the main menu?");
                    System.out.println("1-Yes, 2-No.");
                    scanInt.next();
                }
                goAgain2 = scanInt.nextInt();
                if (goAgain2 != 1) {
                    start();
                }
            } else if (option == 2) {
                s.viewLogs();
                System.out.println("Back to the main menu?");
                System.out.println("1-Yes, 2-No.");
                while (!scanInt.hasNextInt()) {
                    System.out.println("That's not a number!");
                    System.out.println("Back to the main menu?");
                    System.out.println("1-Yes, 2-No.");
                    scanInt.next();
                }
                goAgain2 = scanInt.nextInt();
                if (goAgain2 != 1) {
                    start();
                }

            } else if (option == 3) {
                System.out.println("You have been logged out.");
                start();

            } else {
                System.out.println("Not a valid choice. You are being re-directed to the main menu.");
                runAdmin();
            }
        } while (goAgain2 == 1);

    }

    //runs the average users portion of the application
    public void run() throws InterruptedException {
        int goAgain = 1;
        do {
            try {
                s.getConnection();
                System.out.println("WELCOME TO THE 0-MILE CLIENT DATABASE");
                System.out.println("What would you like to do?");
                System.out.println("1- View general client information.");
                System.out.println("2- View retail prices information.");
                System.out.println("3- View relevant employees information.");
                System.out.println("4- Finding an agent to help you with dealing with clients.");
                System.out.println("5- Update Profile Information");
                System.out.println("6- Logout from your account.");
                System.out.println("Please select between options 1-6 by entering the number corresponding to the option.");
                while (!scanInt.hasNextInt()) {
                    System.out.println("Not a valid input");
                    System.out.println("1- View general client information.");
                    System.out.println("2- View retail prices information.");
                    System.out.println("3- View relevant employees information.");
                    System.out.println("4- Finding an agent to help you with dealing with clients.");
                    System.out.println("5- Update Profile Information");
                    System.out.println("6- Logout from your account.");
                    System.out.println("Please select between options 1-6 by entering the number corresponding to the option.");
                    scanInt.next();
                }
                int option = scanInt.nextInt();
                if (option == 1) {
                    System.out.println("WELCOME TO THE CLIENT INFORMATION, HERE ARE ALL YOUR OPTIONS:");
                    //options to choose from
                    System.out.println("1- View information on clients within the database.");
                    System.out.println("2- Add a client to the database.");
                    System.out.println("3- Remove a client from the database.");
                    System.out.println("4- Update a client within the database.");
                    System.out.println("5- Return to main menu.");

                    while (!scanInt.hasNextInt()) {
                        System.out.println("Not a valid input");
                        System.out.println("1- View information on clients within the database.");
                        System.out.println("2- Add a client to the database.");
                        System.out.println("3- Remove a client from the database.");
                        System.out.println("4- Update a client within the database.");
                        System.out.println("5- Return to main menu.");
                        scanInt.next();
                    }

                    int clientInfoChoice = scanInt.nextInt();
                    if (clientInfoChoice == 1) {
                        s.SelectClient();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }

                    } else if (clientInfoChoice == 2) {
                        i.insertClientInfo();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }

                    } else if (clientInfoChoice == 3) {
                        d.deleteClientInfo();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }

                    } else if (clientInfoChoice == 4) {

                        u.updateClientInfo();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }

                    } else if (clientInfoChoice == 5) {
                        run();
                    } else {
                        System.out.println("Not a valid choice. You are being re directed to the main menu.");
                        run();
                    }
                }else if(option ==2 ){
                    System.out.println("WELCOME TO THE RETAIL PRICES INFORMATION, HERE ARE ALL YOUR OPTIONS:");
                    System.out.println("1- View the retail prices for all clients within the database.");
                    System.out.println("2- Add a client's retail prices to the database.");
                    System.out.println("3- Remove all retail prices for a specific client within the database.");
                    System.out.println("4- Update a client's retail prices within the database.");
                    System.out.println("5- View the total average retail price for every client in the database (useful tool to distinguish the more expensive clients from the rest).");
                    System.out.println("6- View clients that have between a minimum and maximum average retail price for their clothing (useful tool to filter clients).");
                    System.out.println("7- View all clients with retail prices in their stores that are above the average for all clients.");
                    System.out.println("8- View all clients with retail prices in their stores that are below the average for all clients.");
                    System.out.println("9- Return to main menu.");

                    while (!scanInt.hasNextInt()) {
                        System.out.println("Not a valid input");
                        System.out.println("1- View the retail prices for any client within the database.");
                        System.out.println("2- Add a client's prices to the database.");
                        System.out.println("3- Remove all retail prices for a specific client within the database.");
                        System.out.println("4- Update a client's retail prices within the database.");
                        System.out.println("5- View the total average retail price for every client in the database (useful to distinguish the more/least expensive clients from the rest).");
                        System.out.println("6- View clients that have between a minimum and maximum average retail price for their clothing (useful tool to filter clients).");
                        System.out.println("7- View all clients with retail prices in their stores that are above the average for all clients.");
                        System.out.println("8- View all clients with retail prices in their stores that are below the average for all clients.");
                        System.out.println("9- Return to main menu.");
                        scanInt.next();
                    }
                    int retailChoice = scanInt.nextInt();
                    if (retailChoice == 1){
                        s.retailPricePerClient();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    }
                    else if (retailChoice == 2){
                        i.insertRetailInfo();
                        d.deleteAvgPriceInfo();
                        i.addToAverageTable();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    }
                     else if (retailChoice == 3){
                        d.deleteRetailInfo();
                        d.deleteAvgPriceInfo();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    }
                    else if (retailChoice == 4){
                        u.updateRetailInfo();
                        d.deleteAvgPriceInfo();
                        i.addToAverageTable();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    }
                    else if (retailChoice == 5) {
                        s.averageRetailPriceByClient();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                    goAgain = scanInt.nextInt();
                    if (goAgain != 1) {
                        start();
                        }
                    }
                    else if (retailChoice == 6) {
                        s.betweenAveragePrice();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    }
                    else if (retailChoice == 7) {
                        s.aboveAvgRetailPrice();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    }
                    else if (retailChoice == 8) {
                        s.belowAvgRetailPrice();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    }
                  else if (retailChoice == 9) {
                    run();
                } else  {
                    System.out.println("Not a valid choice. You are being re-directed to the main menu.");
                    run();
                }
                } else if (option == 3) {
                    System.out.println("WELCOME TO THE RELEVANT EMPLOYEES INFORMATION, HERE ARE ALL YOUR OPTIONS:");
                    System.out.println("1- View all relevant employees available.");
                    System.out.println("2- Add a relevant employee to the database.");
                    System.out.println("3- Filter relevant employees by client name.");
                    System.out.println("4- Remove a relevant employee.");
                    System.out.println("5- Return to main menu.");
                    while (!scanInt.hasNextInt()) {
                        System.out.println("Not a valid input");
                        System.out.println("1- View all relevant employees available.");
                        System.out.println("2- Add a relevant employee to the database.");
                        System.out.println("3- Filter relevant employees by client name.");
                        System.out.println("4- Remove a relevant employee.");
                        System.out.println("5- Return to main menu.");
                        scanInt.next();
                    }
                    int employeeChoice = scanInt.nextInt();
                    if (employeeChoice == 1) {
                        s.viewRelevantEmployees();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    } else if (employeeChoice == 2) {
                        i.addRelevantEmployee();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    } else if (employeeChoice == 3) {
                        s.filterRelevantEmployeesByCompany();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    }else if (employeeChoice == 4) {
                            d.deleteRelevantEmployee();
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            while (!scanInt.hasNextInt()) {
                                System.out.println("That's not a number!");
                                System.out.println("Back to the main menu?");
                                System.out.println("1-Yes, 2-No.");
                                scanInt.next();
                            }
                            goAgain = scanInt.nextInt();
                            if (goAgain != 1) {
                                start();
                            }
                        }
                     else if (employeeChoice == 5) {
                        run();}

                    else  {
                        System.out.println("Not a valid choice. You are being re-directed to the main menu.");
                        run();
                    }

                } else if (option == 4) {
                    //agent table
                    System.out.println("WELCOME TO THE AGENT INFORMATION, HERE ARE ALL YOUR OPTIONS:");
                    System.out.println("1- View all agents available.");
                    System.out.println("2- Add an agent to the database.");
                    System.out.println("3- Find the location of clients along with the corresponding agent to facilitate business. ");
                    System.out.println("4- Delete an agent from the database.");
                    System.out.println("5- Find agents bases on a client's location.");
                    System.out.println("6- Return to main menu.");
                    while (!scanInt.hasNextInt()) {
                        System.out.println("Not a valid input");
                        System.out.println("1- View all agents available.");
                        System.out.println("2- Add an agent to the database.");
                        System.out.println("3- Find the location of clients along with the corresponding agent to facilitate business. ");
                        System.out.println("4- Delete an agent from the database.");
                        System.out.println("5- Find agents bases on a client's location.");
                        System.out.println("6- Return to main menu.");
                        scanInt.next();
                    }
                    int agentChoice = scanInt.nextInt();
                    if (agentChoice == 1) {
                        s.viewAgents();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }

                    } else if (agentChoice == 2) {
                        i.addAgent();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }

                    } else if (agentChoice == 3) {
                        s.AgentsPerClient();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }

                    } else if (agentChoice == 4) {
                        d.deleteAgent();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }

                    } else if (agentChoice == 5) {
                        s.viewAgentsByJurisdiction();
                        System.out.println("Back to the main menu?");
                        System.out.println("1-Yes, 2-No.");
                        while (!scanInt.hasNextInt()) {
                            System.out.println("That's not a number!");
                            System.out.println("Back to the main menu?");
                            System.out.println("1-Yes, 2-No.");
                            scanInt.next();
                        }
                        goAgain = scanInt.nextInt();
                        if (goAgain != 1) {
                            start();
                        }
                    } else if (agentChoice == 6) {
                        run();
                    }
                }
                 else if (option == 5){
                    u.updateProfile();
                } else if (option == 6) {
                    System.out.println("You have been successfully logged out.");
                    start();

                } else {
                    System.out.println("Not a valid choice. You will now be re-directed to the main menu.");
                    run();

                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
            while (goAgain == 1);
        }
    }