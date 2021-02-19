package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    public String credentialError = null;
    public String credentialSuccess = null;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String createCredential(@ModelAttribute Credential credential, Authentication authentication, Model model){
        this.credentialError = null;
        this.credentialSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
        int rowsAdded = credentialService.createCredential(credential);
        if (rowsAdded < 0){
            this.credentialError = "There was an error for adding a credential. Please try again";
        }
        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You successfully added a new note");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "redirect:/home";
    }

    @PutMapping
    public String updateCredential(@ModelAttribute Credential credential, Authentication authentication, Model model){
        System.out.println("i m in update note");
        this.credentialError = null;
        this.credentialSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
        int rowsUpdated = credentialService.updateCredential(credential);
        if (rowsUpdated < 0){
            this.credentialError = "There was an error for updating a credential. Please try again";
        }
        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You successfully updated a note");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "redirect:/home";
    }

    @DeleteMapping
    public String deleteCredential(@ModelAttribute Credential credential, Authentication authentication, Model model){
        System.out.println("i m in delete note");
        this.credentialError = null;
        this.credentialSuccess = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
        int rowsUpdated = credentialService.deleteCredential(credential.getCredentialId());
        if (rowsUpdated < 0){
            this.credentialError = "There was an error deleting a credential. Please try again";
        }
        if (this.credentialError == null) {
            model.addAttribute("credentialSuccess", "You successfully deleted a credential");
        } else {
            model.addAttribute("credentialError", this.credentialError);
        }

        return "redirect:/home";

    }
}

