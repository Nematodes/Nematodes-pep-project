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
        if (accountToAdd.getPassword().length() < 4) {
            return null;
        }

        if (accountToAdd.getUsername().length() == 0) {
            return null;
        }

        if (accountDao.getAccountByUsername(accountToAdd.getUsername()) != null) {
            return null;
        }

        return accountDao.addAccount(accountToAdd.getUsername(), accountToAdd.getPassword());
    }
}
