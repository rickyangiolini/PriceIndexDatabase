//Created by Riccardo Angiolini on July 23rd, 2018.
// Copyright. All Rights Reserved. ©

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public Connection con;
    //method to get the connection string in order to make queries to the client database
    public Connection getConnection() {
        try {
            //connection string
            con = DriverManager.getConnection("jdbc:mysql://zeromileinstance.cmhtncckzomm.us-west-1.rds.amazonaws.com:3306/ZeroMileDB?useSSL=false", "client", "Zeromile2018");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

}