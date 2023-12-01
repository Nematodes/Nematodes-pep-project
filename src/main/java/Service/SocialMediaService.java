package Service;

import DAO.SocialMediaDAO;
import java.util.ArrayList;
import Model.*;

public class SocialMediaService {
    private SocialMediaDAO socialMediaDao;

    /**
     * Creates a new SocialMediaService object
     */
    public SocialMediaService() {
        socialMediaDao = new SocialMediaDAO();
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
        if (socialMediaDao.getAccountByUsername(accountToAdd.getUsername()) != null) {
            return null;
        }

        // Add an account using the desired username and password fields
        return socialMediaDao.addAccount(accountToAdd.getUsername(), accountToAdd.getPassword());
    }

    /**
     * Attempts to add a new message to the application's database.
     * 
     * The attempt will fail under the following conditions:
     * The message is not between 1 and 254 (inclusive) charcters long
     * The message is not posted by an existing user account
     * 
     * @param messageToAdd the message to add to the database, disregarding any present message_id value
     * @return a message with its message_id value set, or null if the attempt fails
     */
    public Message addMessage(Message messageToAdd) {
        // Reject messages that fail to meet the necessary message length conditions
        if (messageToAdd.getMessage_text().length() == 0 || messageToAdd.getMessage_text().length() >= 255) {
            return null;
        }

        // Reject messages that were not posted by an existing user 
        if (socialMediaDao.getAccountById(messageToAdd.getPosted_by()) == null) {
            return null;
        }

        // Add a message using the desired posted_by, message_text, and time_posted_epoch fields
        return socialMediaDao.addMessage(messageToAdd.getPosted_by(), messageToAdd.getMessage_text(), messageToAdd.getTime_posted_epoch());
    }

    /**
     * Attempts to delete a message by ID from the application's database
     * 
     * @param id the ID of the message to delete
     * @return the Message with a matching ID, or null if a matching Message record is not found
     */
    public Message deleteMessageById(int id) {
        // Get the message by ID if it exists - deleting the message would not return it, so it needs to be obtained now
        Message returnedMessage = socialMediaDao.getMessageById(id);

        // If the message exists, then delete it
        if (returnedMessage != null)
        {
            socialMediaDao.deleteMessageById(id);
        }

        return returnedMessage;
    }

    /**
     * Gets all messages from the application's database
     * 
     * @return a list of Message objects
     */
    public ArrayList<Message> getAllMessages() {
        return socialMediaDao.getAllMessages();
    }

    /**
     * Attempts to get a message by ID from the application's database
     * 
     * @param id the ID of the message to query for
     * @return the Message with a matching ID, or null if a matching Message record is not found
     */
    public Message getMessageById(int id) {
        return socialMediaDao.getMessageById(id);
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
        return socialMediaDao.getAccountByCredentials(accountToAdd.getUsername(), accountToAdd.getPassword());
    }
}
