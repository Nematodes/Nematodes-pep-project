package DAO;

import java.sql.*;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    /**
     * Adds an account to the application's database
     * 
     * @param username the username of the account
     * @param password the password of the account
     * @return an Account with its account_id field populated, or null if the Account was not inserted.
     */
    public Account addAccount(String username, String password) {
        try {
            // Get a connection to the application's database
            Connection connection = ConnectionUtil.getConnection();

            // Create a SQL statement that inserts an Account
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Account(username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

            // Set the username and password parameters of the SQL statement
            ps.setString(1, username);
            ps.setString(2, password);

            // Run the SQL statement
            ps.executeUpdate();

            // Get the data returned from the SQL statement
            ResultSet rs = ps.getGeneratedKeys();

            // Create and return an Account based on the information returned from the SQL statement (if an account was inserted at all)
            while (rs.next()) {
                int resultId = rs.getInt(1);

                return new Account(resultId, username, password);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // If a SQLException occurred or the Account was not successfully inserted, then return null
        return null;
    }

    /**
     * Gets an Account with a matching username and password from the application's database
     * 
     * @param username the username of the account to find in the database
     * @param password the password of the account to find in the database
     * @return the Account with the specified username and password, or null if the account is not in the database
     */
    public Account getAccountByCredentials(String username, String password) {
        try {
            // Get a connection to the application's database
            Connection connection = ConnectionUtil.getConnection();

            // Create a SQL statement that gets all accounts matching the supplied credentials (there should only be one)
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Account WHERE username = ? AND password = ?");

            // Set the username and passord parameters of the SQL statement
            ps.setString(1, username);
            ps.setString(2, password);

            // Run the SQL statement
            ResultSet rs = ps.executeQuery();

            // If account data was found by the query, then create and return an Account using that data
            while (rs.next()) {
                int resultId = rs.getInt(1);

                return new Account(resultId, username, password);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // If a SQLException occurred or an account with the desired credentials was not found, then return null
        return null;
    }

    /**
     * Gets an Account with a matching username from the application's database
     * 
     * @param username the username of the account to find in the database
     * @return the Account with the specified username, or null if the account is not in the database
     */
    public Account getAccountByUsername(String username) {
        try {
            // Get a connection to the application's database
            Connection connection = ConnectionUtil.getConnection();

            // Create a SQL statement that gets all accounts with the desired username (there should only be one)
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Account WHERE username = ?");

            // Set the username parameter of the SQL statement
            ps.setString(1, username);

            // Run the SQL statement
            ResultSet rs = ps.executeQuery();

            // If account data was found by the query, then create and return an Account using that data
            while (rs.next()) {
                int resultId = rs.getInt(1);
                String resultPassword = rs.getString(3);

                return new Account(resultId, username, resultPassword);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // If a SQLException occurred or an account with the desired username was not found, then return null
        return null;
    }
}