package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDao;

    /**
     * Creates a new AccountService object
     */
    public AccountService() {
        accountDao = new AccountDAO();
    }

    /**
     * Attempts to add a new account to the application's database.
     * 
     * The attempt will fail under the following conditions:
     * The username is blank (empty string)
     * The password is less than 4 characters long
     * An account with this username already exists
     * 
     * @param accountToAdd the account to add to the database, disregarding any present account_id value
     * @return an account with its account_id value set, or null if the attempt fails
     */
    public Account addAccount(Account accountToAdd) {
        // Reject accounts that fail to meet the necessary password and username length conditions
        if (accountToAdd.getPassword().length() < 4) {
            return null;
        }

        if (accountToAdd.getUsername().length() == 0) {
            return null;
        }

        // If an account with the desired username already exists, then reject it
        if (accountDao.getAccountByUsername(accountToAdd.getUsername()) != null) {
            return null;
        }

        // Add an account using the desired username and password fields
        return accountDao.addAccount(accountToAdd.getUsername(), accountToAdd.getPassword());
    }

    /**
     * Attempts to login to an account using the supplied Account's username and password
     * 
     * Actual login logic is currently not implemented. This method will successfully return
     * if an account with a matching username and password is found in the application's database.
     * 
     * @param accountToAdd the account with the credentials necessary to login, disregarding any present account_id value
     * @return an account with its account_id value set, or null if the attempt fails
     */
    public Account loginToAccount(Account accountToAdd) {
        // Attempt to get an account with matching username and password credentials
        return accountDao.getAccountByCredentials(accountToAdd.getUsername(), accountToAdd.getPassword());
    }
}