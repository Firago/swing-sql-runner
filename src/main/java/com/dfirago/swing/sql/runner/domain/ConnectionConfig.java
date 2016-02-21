package com.dfirago.swing.sql.runner.domain;

import com.dfirago.swing.sql.runner.utils.PasswordDeserializer;
import com.dfirago.swing.sql.runner.utils.PasswordSerializer;
import com.dfirago.swing.sql.runner.validation.Required;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author diankasol
 */
public class ConnectionConfig implements Restorable {

    @Required("driver")
    private String driver;
    @Required("URL")
    private String url;
    @Required("username")
    private String login;
    @JsonSerialize(using = PasswordSerializer.class)
    @JsonDeserialize(using = PasswordDeserializer.class)
    private String password;

    public ConnectionConfig() {
    }

    public ConnectionConfig(String driver, String url, String login, String password) {
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
