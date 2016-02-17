package com.dfirago.swing.sql.runner.domain;

/**
 * @author diankasol
 */
public class ConncetionConfig implements Restorable {

    private String driver;
    private String url;
    private String login;
    private String password;

    public ConncetionConfig(String driver, String url, String login, String password) {
        this.driver = driver;
        this.url = url;
        this.login = login;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
