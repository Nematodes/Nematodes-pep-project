package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.*;
import Service.*;

public class SocialMediaController {
    private AccountService accountService;

    /**
     * Creates a new SocialMediaController object
     */
    public SocialMediaController() {
        this.accountService = new AccountService();
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
        Account accountInserted = accountService.addAccount(accountFromBody);

        // Set the HTTP response based on whether or not the account was successfully added
        if (accountInserted != null)
        {
            context.json(accountInserted).status(200);
        }
        else
        {
            context.status(400);
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
        Account accountLoggedIn = accountService.loginToAccount(accountFromBody);

        // Set the HTTP response based on whether or not the account was successfully logged in to
        if (accountLoggedIn != null)
        {
            context.json(accountLoggedIn).status(200);
        }
        else
        {
            context.status(401);
        }
    }
}