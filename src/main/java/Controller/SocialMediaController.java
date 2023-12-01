package Controller;

import com.fasterxml.jackson.databind.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;
import Model.*;
import Service.*;

public class SocialMediaController {
    private SocialMediaService socialMediaService;

    /**
     * Creates a new SocialMediaController object
     */
    public SocialMediaController() {
        this.socialMediaService = new SocialMediaService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        app.post("/register", this::addAccount);

        app.post("/login", this::loginToAccount);
        
        app.post("/messages", this::addMessage);

        app.get("/messages", this::getAllMessages);

        app.get("/messages/{message_id}", this::getMessageById);

        app.delete("/messages/{message_id}", this::deleteMessageById);

        app.patch("/messages/{message_id}", this::updateMessageById);

        return app;
    }

    /**
     * Attempts to add a new account to the application's database.
     * 
     * The attempt will fail under the following conditions:
     * The username is blank (empty string)
     * The password is less than 4 characters long
     * An account with this username already exists
     * 
     * On success, the HTTP response status is set to 200.
     * On failure, the HTTP response status is set to 400.
     * 
     * @param context the Javalin Context object manages information about both the HTTP request and response.
     */
    private void addAccount(Context context) {
        // Get the Account object from the Javalin context
        Account accountFromBody = context.bodyAsClass(Account.class);

        // Insert the account into the application's database
        Account accountInserted = socialMediaService.addAccount(accountFromBody);

        // Set the HTTP response based on whether or not the account was successfully added
        if (accountInserted != null) {
            context.json(accountInserted).status(200);
        }
        else {
            context.status(400);
        }
    }

    /**
     * Attempts to add a new message to the application's database.
     * 
     * The attempt will fail under the following conditions:
     * The message is not between 1 and 254 (inclusive) charcters long
     * The message is not posted by an existing user account
     * 
     * On success, the HTTP response status is set to 200.
     * On failure, the HTTP response status is set to 400.
     * 
     * @param context the Javalin Context object manages information about both the HTTP request and response.
     */
    private void addMessage(Context context) {
        // Get the Message object from the Javalin context
        Message messageFromBody = context.bodyAsClass(Message.class);

        // Insert the message into the application's database
        Message messageInserted = socialMediaService.addMessage(messageFromBody);

        // Set the HTTP response based on whether or not the message was successfully added
        if (messageInserted != null) {
            context.json(messageInserted).status(200);
        }
        else {
            context.status(400);
        }
    }

    /**
     * Deletes a message by ID from the application's database
     * 
     * If the message is deleted, then the response body contains the deleted Message.
     * Otherwise, the response body is left empty.
     * 
     * Always sets the HTTP response status to 200
     * 
     * @param context the Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageById(Context context) {
        // Get the message ID from the endpoint's path
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));

        // Delete the message with a matching ID
        Message deletedMessage = socialMediaService.deleteMessageById(idFromPath);

        if (deletedMessage != null) {
            // Set the HTTP response status to 200 and return the obtained Message
            context.json(deletedMessage).status(200);
        }
        else {
            // Set the HTTP response status to 200 while leaving the response body blank
            context.status(200);
        }
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Gets all messages from the application's database
     * 
     * Always sets the HTTP response status to 200
     * 
     * @param context the Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessages(Context context) {
        // Get all messages from the application's database
        ArrayList<Message> messageList = socialMediaService.getAllMessages();

        // Set the HTTP response status to 200
        context.json(messageList).status(200);
    }

    /**
     * Gets a message by ID from the application's database
     * 
     * If no message is found, then the HTTP response body is left empty.
     * 
     * Always sets the HTTP response status to 200
     * 
     * @param context the Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageById(Context context) {
        // Get the message ID from the endpoint's path
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));

        // Get the message with a matching ID
        Message returnedMessage = socialMediaService.getMessageById(idFromPath);

        if (returnedMessage != null) {
            // Set the HTTP response status to 200 and return the obtained Message
            context.json(returnedMessage).status(200);
        }
        else {
            // Set the HTTP response status to 200 while leaving the response body blank
            context.status(200);
        }
    }

    /**
     * Attempts to login to an account using the supplied username and password
     * 
     * Actual login logic is currently not implemented. This method will succeed if an account
     * with a matching username and password is found in the application's database.
     * 
     * On success, the HTTP response status is set to 200.
     * On failure, the HTTP response status is set to 401.
     * 
     * @param context the Javalin Context object manages information about both the HTTP request and response.
     */
    private void loginToAccount(Context context) {
        // Get the Account object from the Javalin context
        Account accountFromBody = context.bodyAsClass(Account.class);

        // Insert the account into the application's database
        Account accountLoggedIn = socialMediaService.loginToAccount(accountFromBody);

        // Set the HTTP response based on whether or not the account was successfully logged in to
        if (accountLoggedIn != null) {
            context.json(accountLoggedIn).status(200);
        }
        else {
            context.status(401);
        }
    }

    /**
     * Attempts to update the text of a message by ID from the application's database
     * 
     * The attempt will fail under the following conditions:
     * The message is not between 1 and 254 (inclusive) charcters long
     * The message specified by the ID does not exist
     * 
     * On success, the HTTP response status is set to 200.
     * On failure, the HTTP response status is set to 400.
     * 
     * @param context the Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMessageById(Context context) {
        // Get the message ID from the endpoint's path
        int idFromPath = Integer.parseInt(context.pathParam("message_id"));

        // Get the Message object from the Javalin context
        Message messageFromBody = context.bodyAsClass(Message.class);

        // Update the text of the message with a matching ID
        Message updatedMessage = socialMediaService.updateMessageById(idFromPath, messageFromBody.getMessage_text());

        if (updatedMessage != null) {
            // Set the HTTP response status to 200 and return the updated Message
            context.json(updatedMessage).status(200);
        }
        else {
            // Set the HTTP response status to 400 while leaving the response body blank
            context.status(400);
        }
    }
}