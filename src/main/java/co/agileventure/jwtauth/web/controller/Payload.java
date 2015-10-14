/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.agileventure.jwtauth.web.controller;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Carlos
 */
/*
 * Inner classes for entity wrappers
 */
public class Payload {

    @NotBlank
    String clientId;

    @NotBlank
    String redirectUri;

    @NotBlank
    String code;

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getCode() {
        return code;
    }
}
