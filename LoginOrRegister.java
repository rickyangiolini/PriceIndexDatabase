//Created by Riccardo Angiolini on July 23rd, 2018.
// Copyright. All Rights Reserved. ©

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class LoginOrRegister {
    private Scanner scan;
    private Scanner scanNum;
    private Connect c;
    private Connection con;
    public static int primary_keys;

    public LoginOrRegister() {
        this.scan = new Scanner(System.in);
        this.scanNum = new Scanner(System.in);
        this.c = new Connect();
        this.con = this.c.getConnection();
    }

    public void login() {
        try {
            System.out.println("Please enter your username to login:");
            String username = this.scan.nextLine();
            System.out.println("Please enter your password to login:");
            String pass = this.scan.nextLine();
            PreparedStatement ps = this.con.prepareStatement("SELECT DISTINCT UserID FROM users WHERE Username=? AND Password=? ");
            ps.setString(1, username);
            ps.setInt(2, pass.hashCode());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                primary_keys = rs.getInt(1);
                System.out.println("UserID: " + primary_keys);
            } else {
                System.out.println("Incorrect password, please try again.");
                this.login();
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    public void Adminlogin() {
        try {
            System.out.println("Please enter your admin username to login:");
            String username = this.scan.nextLine();
            System.out.println("Please enter your administrator password to login:");
            String pass = this.scan.nextLine();
            CallableStatement ps = this.con.prepareCall("CALL adminLogin(?,?)");
            ps.setString(1, username);
            ps.setInt(2, pass.hashCode());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                primary_keys = rs.getInt(1);
                System.out.println("AdminID: " + primary_keys);
            } else {
                System.out.println("Incorrect password, please try again.");
                this.Adminlogin();
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    public void register() {
        try {
            System.out.println("Please enter your desired username:");
            String username = this.scan.nextLine();
            System.out.println("Please enter your desired password:");
            String pass = this.scan.nextLine();
            System.out.println("Please enter your first name:");
            String fname = this.scan.nextLine();
            System.out.println("Please enter your last name:");
            String lname = this.scan.nextLine();
            System.out.println("Please enter your phone number:");

            while(!this.scanNum.hasNextLong()) {
                System.out.println("That's not a number!");
                System.out.println("Please enter your phone number:");
                this.scanNum.next();
            }

            long num = this.scanNum.nextLong();
            System.out.println("Please enter your email address:");
            String email = this.scan.nextLine();
            PreparedStatement ps = this.con.prepareStatement("INSERT INTO users(Username,Password, FirstName, LastName, Phone, Email) VALUES (?,?,?,?,?,?)", 1);
            ps.clearParameters();
            ps.setString(1, username);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, fname);
            ps.setString(4, lname);
            ps.setLong(5, num);
            ps.setString(6, email);
            ps.executeUpdate();
            ResultSet rs_key = ps.getGeneratedKeys();
            if (rs_key.next()) {
                primary_keys = rs_key.getInt(1);
            }

            System.out.println("UserID: " + primary_keys);
            CallableStatement p2 = this.con.prepareCall("CALL addLog(?,?,?)");
            p2.setString(1, "INSERT INTO users(Username,Password, FirstName, LastName, Phone, Email) VALUES (" + username + "," + pass.hashCode() + "," + fname + "," + lname + "," + num + "," + email + ")");
            p2.setString(2, (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(Calendar.getInstance().getTime()));
            p2.setInt(3, primary_keys);
            p2.executeUpdate();
        } catch (Exception var11) {
            System.out.println("You successfully created an account. You will be redirected to the main menu.");
            //this.register();
        }

    }
}
