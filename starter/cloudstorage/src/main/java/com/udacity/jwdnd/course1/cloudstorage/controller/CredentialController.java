package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;
    public String ifError = null;
    public String ifSuccess = null;
    public String successMessage = null;
    public String errorMessage = null;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping
    public String createCredential(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirectAttributes){
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        credential.setKey(encodedKey);
        System.out.println(credential.getKey());
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
//        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
        int rowsAdded = credentialService.createCredential(credential);
        if (rowsAdded < 0){
            this.errorMessage = "There was an error for adding a credential. Please try again";
        }
        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess",true);
            redirectAttributes.addFlashAttribute("successMessage", "You successfully added a new credential");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage",this.errorMessage);
        }

        return "redirect:/home";
    }

    @PutMapping
    public String updateCredential(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirectAttributes){
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
//        System.out.println(model.getAttribute("credential-password"));
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        int rowsUpdated = credentialService.updateCredential(credential);
        if (rowsUpdated < 0){
            this.errorMessage = "There was an error for updating a credential. Please try again";
        }
        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess",true);
            redirectAttributes.addFlashAttribute("successMessage", "You successfully updated a credential");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage",this.errorMessage);
        }

        return "redirect:/home";
    }

    @DeleteMapping
    public String deleteCredential(@ModelAttribute Credential credential, Authentication authentication, RedirectAttributes redirectAttributes){
        this.ifError = null;
        this.ifSuccess = null;
        this.errorMessage = null;
        this.successMessage = null;
        User user = userService.getUser(authentication.getName());
        Integer userId = user.getUserId();
        credential.setUserId(userId);
        int rowsUpdated = credentialService.deleteCredential(credential.getCredentialId());
        if (rowsUpdated < 0){
            this.errorMessage = "There was an error for deleting a credential. Please try again";
        }
        if (this.ifError == null) {
            redirectAttributes.addFlashAttribute("ifSuccess",true);
            redirectAttributes.addFlashAttribute("successMessage", "You successfully deleted a credential");
        } else {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage",this.errorMessage);
        }

        return "redirect:/home";

    }
}

