/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agileventure.jwtauth.model.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;

/**
 *
 * @author Carlos Ortiz Urshela
 */
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private String picture;
    private String displayName;
    private String name;
    private String givenName;
    private String familyName;
    /**Facebook user id*/
    private String facebook;
    /**Google user id*/
    private String google;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public enum Provider {

        FACEBOOK("facebook"), GOOGLE("google"), LINKEDIN("linkedin"), GITHUB("github"), FOURSQUARE(
                "foursquare"), TWITTER("twitter");

        String name;

        Provider(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public String capitalize() {
            return StringUtils.capitalize(this.name);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", password=" + password + ", displayName=" + displayName + ", facebook=" + facebook + ", google=" + google + "'}'";
    }

}
