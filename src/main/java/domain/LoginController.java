package domain;

public class LoginController {

    private AccountManager manager;
    private AccountController ac;

    public LoginController() {
        manager = new AccountManager();
    }

    public AccountController login(String email, String password) {
        this.ac = manager.login(email, password);
        return ac;
    }

    public AccountController getAc() {
        return this.ac;
    }
}