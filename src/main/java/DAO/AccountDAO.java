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
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO Account(username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while (rs.next()) {
                int resultId = rs.getInt(1);

                return new Account(resultId, username, password);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

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
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Account WHERE username = ?");

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int resultId = rs.getInt(1);
                String resultPassword = rs.getString(3);

                return new Account(resultId, username, resultPassword);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}