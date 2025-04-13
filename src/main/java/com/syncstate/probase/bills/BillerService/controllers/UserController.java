package com.syncstate.probase.bills.BillerService.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.mail.smtp.SMTPTransport;
import com.syncstate.probase.bills.BillerService.enums.UserRole;
import com.syncstate.probase.bills.BillerService.enums.UserStatus;
import com.syncstate.probase.bills.BillerService.models.User;
import com.syncstate.probase.bills.BillerService.models.dto.LoggedInUserDTO;
import com.syncstate.probase.bills.BillerService.models.requests.LoginRequest;
import com.syncstate.probase.bills.BillerService.models.requests.NewAdministratorRequest;
import com.syncstate.probase.bills.BillerService.models.requests.RegisterRequest;
import com.syncstate.probase.bills.BillerService.models.requests.ValidateOTPRequest;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponse;
import com.syncstate.probase.bills.BillerService.models.responses.DiademResponseCode;
import com.syncstate.probase.bills.BillerService.models.responses.ErrorMessage;
import com.syncstate.probase.bills.BillerService.services.JwtService;
import com.syncstate.probase.bills.BillerService.services.UserService;
import com.syncstate.probase.bills.BillerService.util.UtilityHelper;
import jakarta.validation.Valid;
import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Value("${email.sender}")
    private String emailSenderPassword;

    @Value("${path.uploads.farmer_templates}")
    private String fileDestinationPath;


    @Autowired
    private JwtService jwtService;

    @Value("${key.decrypt.key}")
    private String decryptKey;


    @RequestMapping(value="/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerNewFarmer(@RequestBody @Valid RegisterRequest registerRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List errorMessageList =  bindingResult.getFieldErrors().stream().map(fe -> {
                return new ErrorMessage(fe.getField(), fe.getDefaultMessage());
            }).collect(Collectors.toList());

            DiademResponse farmersPayResponse = new DiademResponse();
            farmersPayResponse.setResponseData(errorMessageList);
            farmersPayResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            farmersPayResponse.setMessage("Validation of registration form failed");
            return ResponseEntity.badRequest().body(farmersPayResponse);
        }

        String bcryptPassword = UtilityHelper.generateBCryptPassword(registerRequest.getPassword());
        String otp = RandomStringUtils.randomNumeric(6);

        User user = new User();
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setFullName(registerRequest.getFullName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setUserStatus(UserStatus.NOT_ACTIVATED);
        user.setPassword(bcryptPassword);
        user.setMobileNumber(registerRequest.getMobileNumber());
        user.setEmailAddress(registerRequest.getEmailAddress());
        user.setOtp(otp);
        user.setUsername(registerRequest.getMobileNumber());
        user.setUserUniqueId(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

        user = userService.save(user);


        if(registerRequest.getEmailAddress()!=null) {
            String htmlMessage = "<div style='background:#f5f5f5;background-color:#f5f5f5;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                    "style='background:#f5f5f5;background-color:#f5f5f5;width:100%;'>	<tbody><tr>	<td><div style='Margin:0px auto;max-width:780px;'>	" +
                    "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;max-width:780px;background:#F5F5F5;background-color:#F5F5F5;'>" +
                    "<tbody>	<tr><td style='direction:ltr;font-size:0px;padding-bottom:0px;text-align:center;vertical-align:top;'>	" +
                    "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:transparent;background-color:transparent;width:100%;'><tbody>	" +
                    "<tr><td>	<div style='Margin:0px auto;max-width:620px;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;'>	<tbody>" +
                    "<tr>	<td style='direction:ltr;font-size:0px;padding:30px;padding-bottom:0px;text-align:center;vertical-align:top;'>" +
                    "<div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'>  </table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>" +
                    "</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>" +
                    "<table align='center' style='width:100%;max-width:780px;background:#F5F5F5;background-color:#F5F5F5;' '=''>	<tbody><tr style='width:{headerImageWidth}px;'>	" +
                    "<td align='center' style='font-size:0px;padding:0px;word-break:break-word;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                    "style='border-collapse:collapse;border-spacing:0px;'>	<tbody><tr>	<td style='width:780px;padding:0pm 0px 0px 0px;padding-bottom:0px;'>" +
                    "<img alt='Vend' height='auto' src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_header.png' " +
                    "style='border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;' width='{headerImageWidth}'> 	</td></tr>	</tbody></table>	" +
                    "</td></tr>	</tbody></table><div class='main-content' style='background:#fff;background-color:#fff;Margin:0px auto;max-width:780px;'>	" +
                    "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#fff;background-color:#fff;width:100%'><tbody>	<tr>" +
                    "<td colspan='3' style='height:30px'></td>	</tr>	<tr><td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;' class='mktoContainer' " +
                    "id='container'>	<table class='mktoModule' id='textSection' style='width:100%;'><tbody>	<tr><td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	" +
                    "<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                    "style='background:white;background-color:white;width:100%;'>	<tbody><tr>	<td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;'>" +
                    "<div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr>" +
                    "<td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	" +
                    "<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;' class='mktoText' id='textSectionEditable'>" +
                    "<h1 style='text-align: center;'>Welcome to FarmersPay! Let's get&nbsp;started.</h1><p style='padding-bottom: 20px;'>&nbsp;<br>" +
                    "Hello " + user.getFullName() + "! A new Farmer profile has been set up for you. Your password to login is: " +
                    registerRequest.getPassword()
                    + ". Your One-Time Code to Activate your profile is " + user.getOtp() + " </p>	</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table>	" +
                    "<table class='mktoModule' id='textSection4fc63ee1-701e-488b-a2a4-b1d5cdffd971' style='width:100%;'><tbody>	<tr>" +
                    "<td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'>" +
                    "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:white;background-color:white;width:100%;'>	" +
                    "<tbody><tr>	<td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;'>" +
                    "<div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr><td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	" +
                    "<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;' class='mktoText' " +
                    "id='textSectionEditable4fc63ee1-701e-488b-a2a4-b1d5cdffd971'><table width='100%' style='background-color: #e9f6e8;'>	<tbody><tr>	<td width='10%'>&nbsp;</td>	" +
                    "<td width='80%' align='center' style='text-align: center;'><h2 style='font-family: Helvetica, Arial, sans-serif; color: #626262; padding-top: 20px; padding-bottom: 10px;'>" +
                    "Your FarmersPay Login Details</h2><p style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; text-align: center; color: #626262; padding-bottom: 10px;'>" +
                    "Your Username/Email is: " + user.getMobileNumber() + "</p><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                    "style='border-collapse: separate; line-height: 100%;'>	<tbody><tr>	<td align='center' bgcolor='#41af4b' role='presentation' style='border: 2px solid transparent; border-radius: " +
                    "0px; cursor: auto; padding: 14px 24px;' valign='middle'></td></tr>	</tbody></table><p>&nbsp;</p>	</td>	<td width='10%'>&nbsp;</td></tr>	</tbody></table><p>&nbsp;</p>	" +
                    "</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table>	" +
                    "<table class='mktoModule' id='textSection4fc63ee1-701e-488b-a2a4-b1d5cdffd9718b5d4c4a-486c-40c3-9236-43cdf187ad89' style='width:100%;'><tbody>	<tr>" +
                    "<td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'>" +
                    "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:white;background-color:white;width:100%;'>	<tbody><tr>	" +
                    "<td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;'><div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr>" +
                    "<td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;' class='mktoText' " +
                    "id='textSectionEditable4fc63ee1-701e-488b-a2a4-b1d5cdffd9718b5d4c4a-486c-40c3-9236-43cdf187ad89'><table width='100%' style='background-color: #f8f8f5;'>	" +
                    "<tbody><tr>	<td width='15%'>&nbsp;</td>	<td width='70%' align='center' style='text-align: center;'><h2 style='font-family: Helvetica, Arial, sans-serif; color: #626262; " +
                    "padding-top: 20px; padding-bottom: 10px;'>Set up FarmersPay</h2><p style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; " +
                    "text-align: center; color: #626262;'>Get started with FarmersPay by reading these four essential guides from our Help Centre and you'll be selling in no time!</p><p>" +
                    "<img src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_01.png' alt='V2439-Adoption-Onboarding-nurture-email-1_01.png' " +
                    "height='165' width='230' style='padding-top: 15px; width=230px;height: 165px;'></p><h3 style='margin-bottom: 0px!important;'>Set up your outlets and registers</h3>" +
                    "<p style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; text-align: center; color: #626262;'>Learn how to get " +
                    "FarmersPay running on all of your registers and outlets.<br><a href='#'>Learn about registers</a></p><p><img " +
                    "src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_02.png' alt='V2439-Adoption-Onboarding-nurture-email-1_02.png' height='165' " +
                    "width='230' style='padding-top: 15px; width=230px;height: 165px;'></p><h3 style='margin-bottom: 0px!important;'>Organise your sales taxes</h3>" +
                    "<p style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; text-align: center; color: #626262;'>" +
                    "Add any sales or value-added taxes (VAT) that are for your location&nbsp;or&nbsp;products.<br><a href='#'>Learn about taxes</a></p><p>" +
                    "<img src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_03.png' alt='V2439-Adoption-Onboarding-nurture-email-1_03.png' height='165' width='230' " +
                    "style='padding-top: 15px; width=230px;height: 165px;'></p><h3 style='margin-bottom: 0px!important;'>Create different payment types</h3><p>Set up your payment terminal and registers " +
                    "so you can accept cash, cards and other&nbsp;payment&nbsp;types.<br><a href='#'>Learn about integrated payments</a></p><p>" +
                    "<img src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_04.png' alt='V2439-Adoption-Onboarding-nurture-email-1_04.png' height='165' " +
                    "width='230' style='padding-top: 15px; width=230px;height: 165px;'></p><h3 style='margin-bottom: 0px!important;'>Add a product</h3><p>Start adding your products, stock levels and " +
                    "descriptions to&nbsp;your&nbsp;catalog.<br><a href='#'>Learn how to add products</a></p><p>&nbsp;</p>	</td>	<td width='15%'>&nbsp;</td></tr>	</tbody></table>	</div></td>	" +
                    "</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table>	<table class='mktoModule' " +
                    "id='textSection2b16ab9a-73ae-43cf-8972-6db3159390c2' style='width:100%;'><tbody>	<tr><td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	" +
                    "<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                    "style='background:white;background-color:white;width:100%;'>	<tbody><tr>	<td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;'>" +
                    "<div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                    "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr><td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	" +
                    "<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;' class='mktoText' id='textSectionEditable2b16ab9a-73ae-43cf-8972-6db3159390c2'><p>&nbsp;</p>" +
                    "<h2>Your FarmersPay to-do list</h2><p>Like to read ahead? Our <a href='#'>setup checklist</a> gives you a list of steps that you can check off at your own pace to get FarmersPay set up.</p>	" +
                    "</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table>	" +
                    "<table class='mktoModule' id='signoffWithoutProfilePhoto15f0ef74-c2b9-420f-a62b-2e96f521ed08' style='width:100%;'><tbody>	<tr>" +
                    "<td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'>" +
                    "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:white;background-color:white;width:100%;'>	<tbody><tr>	" +
                    "<td style='direction:ltr;font-size:0px;padding:0px;padding-top:30px;text-align:center;vertical-align:top;'><div class='mj-column-per-100 outlook-group-fix' " +
                    "style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	<table border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                    "width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	" +
                    "<tr><td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;'><p>Here's to your retail success, <br><br> <strong>Peters</strong> <br>Director of Adoption <br> </p>	</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table></td>	</tr>	<tr><td colspan='3' style='height:50px'></td>	</tr></tbody>	</table></div><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#f5f5f5;background-color:#f5f5f5;width:100%;'>	<tbody><tr>	<td><div style='Margin:0px auto;max-width:680px;'>	<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;'><tbody>	<tr><td style='direction:ltr;font-size:0px;padding:5px;text-align:center;vertical-align:top;'>	<div style='background:#f5f5f5;background-color:#f5f5f5;Margin:0px auto;max-width:650px;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#f5f5f5;background-color:#f5f5f5;width:100%;'>	<tbody><tr>	<td style='direction:ltr;font-size:0px;padding:15px;text-align:center;vertical-align:top;'><div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr><td align='center' style='font-size:0px;padding:0px;word-break:break-word;'>	<div class='mktoSnippet' id='unsubscribeFooter'><div style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; text-align: center; color: #626262;'>	Farmers Pay HQ, 2-36 Lusaka Street, Lusaka, Zambia 	<br> 	<a style='color: #008cc5 !important; text-decoration: none !important;' href='#' target='_blank'>Unsubscribe</a> ∙ 	<a style='color: #008cc5 !important; text-decoration: none !important;' href='https://email.vendhq.com/Nzc2LVFGTy0zMzQAAAF_hWiDyzkDXU0MPpA_mZQkOV6uelqxQNlKl80Dp7nbfZsoBZZomppxXFRKN_z6O69Y_RlWN_c=' target='_blank'>Privacy Policy</a></div>	</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table></div>";

            try {
                logger.info("=========================");
                Properties props = System.getProperties();
                props.put("mail.smtps.host", "smtp.mailgun.org");
                props.put("mail.smtps.auth", "true");

                Session session = Session.getInstance(props, null);
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("emailer@farmerspay.com", "FarmersPay"));

                InternetAddress[] addrs = InternetAddress.parse(registerRequest.getEmailAddress(), false);
                msg.setRecipients(Message.RecipientType.TO, addrs);

                msg.setSubject("Welcome to FarmersPay");
                msg.setContent(htmlMessage, "text/html; charset=utf-8");

                msg.setSentDate(new Date());

                SMTPTransport t =
                        (SMTPTransport) session.getTransport("smtps");
                t.connect("smtp.mailgun.org", "postmaster@mails.valuenaira.com", emailSenderPassword);
                t.sendMessage(msg, msg.getAllRecipients());

                logger.info("Response: {}", t.getLastServerResponse());

                t.close();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Error Sending Mail ...{}", e);
            }
        }

        DiademResponse farmersPayResponse = new DiademResponse();
        farmersPayResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        farmersPayResponse.setResponseData(user);
        farmersPayResponse.setMessage("Your registration was successful. Please check your SMS messages for a code to complete your registration");
        return ResponseEntity.ok().body(farmersPayResponse);
    }



    @RequestMapping(value="/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest, BindingResult bindingResult) throws JsonProcessingException {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_CUSTOMER");
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        logger.info(username);
        logger.info(password);
        User user = userService.getUserByUsername(username);

        if(!BCrypt.checkpw(password, user.getPassword()))
        {
            DiademResponse farmersPayResponse = new DiademResponse();
            farmersPayResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
            farmersPayResponse.setMessage("Login failed");
            return ResponseEntity.badRequest().body(farmersPayResponse);
        }

        System.out.println(user.getUsername());
        ArrayList arrayList = new ArrayList();
        arrayList.add(authority);



        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
//                            loginUser.getPassword()
                    )
            );
        }
        catch(AuthenticationException e)
        {
            logger.error("err", e);
        }

        logger.info("{}", authentication.isAuthenticated());
        logger.info("{}", authentication.getPrincipal());
        //        logger.info("{}>>>>", loginUser.getEmailAddress());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtService.generateToken(user);
        logger.info("token...{}", token);

        LoggedInUserDTO loggedInUserDTO = new LoggedInUserDTO();
        loggedInUserDTO.setToken(token);
        loggedInUserDTO.setFullName(user.getFullName());

        DiademResponse farmersPayResponse = new DiademResponse();
        farmersPayResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        farmersPayResponse.setResponseData(loggedInUserDTO);
        farmersPayResponse.setMessage("Login was successful");
        return ResponseEntity.ok().body(farmersPayResponse);
    }




    @RequestMapping(value="/otp", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity validateOtp(@RequestBody @Valid ValidateOTPRequest validateOTPRequest, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            List errorMessageList =  bindingResult.getFieldErrors().stream().map(fe -> {
                return new ErrorMessage(fe.getField(), fe.getDefaultMessage());
            }).collect(Collectors.toList());

            DiademResponse farmersPayResponse = new DiademResponse();
            farmersPayResponse.setResponseData(errorMessageList);
            farmersPayResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            farmersPayResponse.setMessage("Validation of registration form failed");
            return ResponseEntity.badRequest().body(farmersPayResponse);
        }



        User user = userService.validateOtp(validateOTPRequest);
        if(user==null)
        {
            DiademResponse farmersPayResponse = new DiademResponse();
            farmersPayResponse.setResponseData(null);
            farmersPayResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            farmersPayResponse.setMessage("OTP validation was not successful. Please ensure the OTP code you provided is correct");
            return ResponseEntity.badRequest().body(farmersPayResponse);
        }


        String htmlMessage = "<div style='background:#f5f5f5;background-color:#f5f5f5;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                "style='background:#f5f5f5;background-color:#f5f5f5;width:100%;'>	<tbody><tr>	<td><div style='Margin:0px auto;max-width:780px;'>	" +
                "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;max-width:780px;background:#F5F5F5;background-color:#F5F5F5;'>" +
                "<tbody>	<tr><td style='direction:ltr;font-size:0px;padding-bottom:0px;text-align:center;vertical-align:top;'>	" +
                "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:transparent;background-color:transparent;width:100%;'><tbody>	" +
                "<tr><td>	<div style='Margin:0px auto;max-width:620px;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;'>	<tbody>" +
                "<tr>	<td style='direction:ltr;font-size:0px;padding:30px;padding-bottom:0px;text-align:center;vertical-align:top;'>" +
                "<div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'>  </table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>" +
                "</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>" +
                "<table align='center' style='width:100%;max-width:780px;background:#F5F5F5;background-color:#F5F5F5;' '=''>	<tbody><tr style='width:{headerImageWidth}px;'>	" +
                "<td align='center' style='font-size:0px;padding:0px;word-break:break-word;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                "style='border-collapse:collapse;border-spacing:0px;'>	<tbody><tr>	<td style='width:780px;padding:0pm 0px 0px 0px;padding-bottom:0px;'>" +
                "<img alt='Vend' height='auto' src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_header.png' " +
                "style='border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;' width='{headerImageWidth}'> 	</td></tr>	</tbody></table>	" +
                "</td></tr>	</tbody></table><div class='main-content' style='background:#fff;background-color:#fff;Margin:0px auto;max-width:780px;'>	" +
                "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#fff;background-color:#fff;width:100%'><tbody>	<tr>" +
                "<td colspan='3' style='height:30px'></td>	</tr>	<tr><td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;' class='mktoContainer' " +
                "id='container'>	<table class='mktoModule' id='textSection' style='width:100%;'><tbody>	<tr><td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	" +
                "<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                "style='background:white;background-color:white;width:100%;'>	<tbody><tr>	<td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;'>" +
                "<div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr>" +
                "<td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	" +
                "<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;' class='mktoText' id='textSectionEditable'>" +
                "<h1 style='text-align: center;'>FarmersPay Profile Activated! Welcome again.</h1><p style='padding-bottom: 20px;'>&nbsp;<br>" +
                "Hello "+user.getFullName()+"! Your FarmersPay profile has been activated. Things you have to do: <br><br>" +
                "1. Create your farm(s) on the platform.<br> "+
                "2. Specify the bank account or mobile money you would prefer to receive funds when paid.<br> "+
                "3. Constantly check your mobile phone and email for updates.<br><br> "
                +"</p>	</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table>	" +
                "<table class='mktoModule' id='textSection4fc63ee1-701e-488b-a2a4-b1d5cdffd971' style='width:100%;'><tbody>	<tr>" +
                "<td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'>" +
                "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:white;background-color:white;width:100%;'>	" +
                "<tbody><tr>	<td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;'>" +
                "<div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr><td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	" +
                "<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;' class='mktoText' " +
                "id='textSectionEditable4fc63ee1-701e-488b-a2a4-b1d5cdffd971'><table width='100%' style='background-color: #e9f6e8;'>	<tbody><tr>	<td width='10%'>&nbsp;</td>	" +
                "<td width='80%' align='center' style='text-align: center;'><h2 style='font-family: Helvetica, Arial, sans-serif; color: #626262; padding-top: 20px; padding-bottom: 10px;'>" +
                "Remember your FarmersPay Login Details</h2><p style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; text-align: center; color: #626262; padding-bottom: 10px;'>" +
                "Your Username/Email is: "+ user.getMobileNumber() +"</p><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                "style='border-collapse: separate; line-height: 100%;'>	<tbody><tr>	<td align='center' bgcolor='#41af4b' role='presentation' style='border: 2px solid transparent; border-radius: " +
                "0px; cursor: auto; padding: 14px 24px;' valign='middle'></td></tr>	</tbody></table><p>&nbsp;</p>	</td>	<td width='10%'>&nbsp;</td></tr>	</tbody></table><p>&nbsp;</p>	" +
                "</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table>	" +
                "<table class='mktoModule' id='textSection4fc63ee1-701e-488b-a2a4-b1d5cdffd9718b5d4c4a-486c-40c3-9236-43cdf187ad89' style='width:100%;'><tbody>	<tr>" +
                "<td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'>" +
                "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:white;background-color:white;width:100%;'>	<tbody><tr>	" +
                "<td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;'><div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr>" +
                "<td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;' class='mktoText' " +
                "id='textSectionEditable4fc63ee1-701e-488b-a2a4-b1d5cdffd9718b5d4c4a-486c-40c3-9236-43cdf187ad89'><table width='100%' style='background-color: #f8f8f5;'>	" +
                "<tbody><tr>	<td width='15%'>&nbsp;</td>	<td width='70%' align='center' style='text-align: center;'><h2 style='font-family: Helvetica, Arial, sans-serif; color: #626262; " +
                "padding-top: 20px; padding-bottom: 10px;'>Set up FarmersPay</h2><p style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; " +
                "text-align: center; color: #626262;'>Get started with FarmersPay by reading these four essential guides from our Help Centre and you'll be selling in no time!</p><p>" +
                "<img src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_01.png' alt='V2439-Adoption-Onboarding-nurture-email-1_01.png' " +
                "height='165' width='230' style='padding-top: 15px; width=230px;height: 165px;'></p><h3 style='margin-bottom: 0px!important;'>Set up your outlets and registers</h3>" +
                "<p style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; text-align: center; color: #626262;'>Learn how to get " +
                "FarmersPay running on all of your registers and outlets.<br><a href='#'>Learn about registers</a></p><p><img " +
                "src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_02.png' alt='V2439-Adoption-Onboarding-nurture-email-1_02.png' height='165' " +
                "width='230' style='padding-top: 15px; width=230px;height: 165px;'></p><h3 style='margin-bottom: 0px!important;'>Organise your sales taxes</h3>" +
                "<p style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; text-align: center; color: #626262;'>" +
                "Add any sales or value-added taxes (VAT) that are for your location&nbsp;or&nbsp;products.<br><a href='#'>Learn about taxes</a></p><p>" +
                "<img src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_03.png' alt='V2439-Adoption-Onboarding-nurture-email-1_03.png' height='165' width='230' " +
                "style='padding-top: 15px; width=230px;height: 165px;'></p><h3 style='margin-bottom: 0px!important;'>Create different payment types</h3><p>Set up your payment terminal and registers " +
                "so you can accept cash, cards and other&nbsp;payment&nbsp;types.<br><a href='#'>Learn about integrated payments</a></p><p>" +
                "<img src='https://retail.vendhq.com/rs/776-QFO-334/images/V2439-Adoption-Onboarding-nurture-email-0A_04.png' alt='V2439-Adoption-Onboarding-nurture-email-1_04.png' height='165' " +
                "width='230' style='padding-top: 15px; width=230px;height: 165px;'></p><h3 style='margin-bottom: 0px!important;'>Add a product</h3><p>Start adding your products, stock levels and " +
                "descriptions to&nbsp;your&nbsp;catalog.<br><a href='#'>Learn how to add products</a></p><p>&nbsp;</p>	</td>	<td width='15%'>&nbsp;</td></tr>	</tbody></table>	</div></td>	" +
                "</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table>	<table class='mktoModule' " +
                "id='textSection2b16ab9a-73ae-43cf-8972-6db3159390c2' style='width:100%;'><tbody>	<tr><td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	" +
                "<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                "style='background:white;background-color:white;width:100%;'>	<tbody><tr>	<td style='direction:ltr;font-size:0px;padding:0px;text-align:center;vertical-align:top;'>" +
                "<div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	" +
                "<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr><td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	" +
                "<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;' class='mktoText' id='textSectionEditable2b16ab9a-73ae-43cf-8972-6db3159390c2'><p>&nbsp;</p>" +
                "<h2>Your FarmersPay to-do list</h2><p>Like to read ahead? Our <a href='#'>setup checklist</a> gives you a list of steps that you can check off at your own pace to get FarmersPay set up.</p>	" +
                "</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table>	" +
                "<table class='mktoModule' id='signoffWithoutProfilePhoto15f0ef74-c2b9-420f-a62b-2e96f521ed08' style='width:100%;'><tbody>	<tr>" +
                "<td style='line-height:0px;font-size:0px;mso-line-height-rule:exactly;'>	<div style='background:white;background-color:white;Margin:0px auto;max-width:680px;'>" +
                "<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:white;background-color:white;width:100%;'>	<tbody><tr>	" +
                "<td style='direction:ltr;font-size:0px;padding:0px;padding-top:30px;text-align:center;vertical-align:top;'><div class='mj-column-per-100 outlook-group-fix' " +
                "style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	<table border='0' cellpadding='0' cellspacing='0' role='presentation' " +
                "width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	" +
                "<tr><td align='left' style='font-size:0px;padding:0px;word-break:break-word;'>	<div style='font-family:Helvetica, Arial, sans-serif;font-size:16px;font-weight:300;line-height:24px;text-align:left;color:#626262;'><p>Here's to your retail success, <br><br> <strong>Peters</strong> <br>Director of Adoption <br> </p>	</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table></td>	</tr>	<tr><td colspan='3' style='height:50px'></td>	</tr></tbody>	</table></div><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#f5f5f5;background-color:#f5f5f5;width:100%;'>	<tbody><tr>	<td><div style='Margin:0px auto;max-width:680px;'>	<table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;'><tbody>	<tr><td style='direction:ltr;font-size:0px;padding:5px;text-align:center;vertical-align:top;'>	<div style='background:#f5f5f5;background-color:#f5f5f5;Margin:0px auto;max-width:650px;'><table align='center' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#f5f5f5;background-color:#f5f5f5;width:100%;'>	<tbody><tr>	<td style='direction:ltr;font-size:0px;padding:15px;text-align:center;vertical-align:top;'><div class='mj-column-per-100 outlook-group-fix' style='font-size:13px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%;'>	<table border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'><tbody>	<tr><td style='vertical-align:top;padding:0px 0px;'>	<table border='0' cellpadding='0' cellspacing='0' role='presentation' style='' width='100%'><tbody>	<tr><td align='center' style='font-size:0px;padding:0px;word-break:break-word;'>	<div class='mktoSnippet' id='unsubscribeFooter'><div style='font-family: Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 24px; text-align: center; color: #626262;'>	Farmers Pay HQ, 2-36 Lusaka Street, Lusaka, Zambia 	<br> 	<a style='color: #008cc5 !important; text-decoration: none !important;' href='#' target='_blank'>Unsubscribe</a> ∙ 	<a style='color: #008cc5 !important; text-decoration: none !important;' href='https://email.vendhq.com/Nzc2LVFGTy0zMzQAAAF_hWiDyzkDXU0MPpA_mZQkOV6uelqxQNlKl80Dp7nbfZsoBZZomppxXFRKN_z6O69Y_RlWN_c=' target='_blank'>Privacy Policy</a></div>	</div></td>	</tr></tbody>	</table></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table>	</div></td>	</tr></tbody>	</table></div>	</td></tr>	</tbody></table></div>";

        try {
            logger.info("=========================");
            Properties props = System.getProperties();
            props.put("mail.smtps.host", "smtp.mailgun.org");
            props.put("mail.smtps.auth", "true");

            Session session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("emailer@farmerspay.com", "FarmersPay"));

            InternetAddress[] addrs = InternetAddress.parse(user.getEmailAddress(), false);
            msg.setRecipients(Message.RecipientType.TO, addrs);

            msg.setSubject("FarmersPay Activated");
            msg.setContent(htmlMessage, "text/html; charset=utf-8");

            msg.setSentDate(new Date());

            SMTPTransport t =
                    (SMTPTransport) session.getTransport("smtps");
            t.connect("smtp.mailgun.org", "postmaster@mails.valuenaira.com", emailSenderPassword);
            t.sendMessage(msg, msg.getAllRecipients());

            logger.info("Response: {}" , t.getLastServerResponse());

            t.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.error("Error Sending Mail ...{}", e);
        }

        DiademResponse farmersPayResponse = new DiademResponse();
        farmersPayResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        farmersPayResponse.setMessage("Profile activated successfully. You can login to proceed with creating your farm(s)");
        return ResponseEntity.badRequest().body(farmersPayResponse);
    }


    @RequestMapping(value="/create-new-administrator", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createNewAdministrator(@RequestBody @Valid NewAdministratorRequest newAdministratorRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List errorMessageList =  bindingResult.getFieldErrors().stream().map(fe -> {
                return new ErrorMessage(fe.getField(), fe.getDefaultMessage());
            }).collect(Collectors.toList());

            DiademResponse farmersPayResponse = new DiademResponse();
            farmersPayResponse.setResponseData(errorMessageList);
            farmersPayResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            farmersPayResponse.setMessage("Validation of the form failed");
            return ResponseEntity.badRequest().body(farmersPayResponse);
        }

        User user = userService.getUserByUsername(newAdministratorRequest.getEmailAddress());
        if(user!=null)
        {
            DiademResponse farmersPayResponse = new DiademResponse();
            farmersPayResponse.setResponseData(null);
            farmersPayResponse.setResponseCode(DiademResponseCode.UNSUCCESSFUL.value);
            farmersPayResponse.setMessage("The email address provided belongs to another user.");
            return ResponseEntity.badRequest().body(farmersPayResponse);
        }

        String password = RandomStringUtils.randomAlphanumeric(8);
        logger.info("password ... {}", password);
        String bcryptPassword = UtilityHelper.generateBCryptPassword(password);
        String otp = RandomStringUtils.randomNumeric(6);

        user = new User();
//        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt(LocalDateTime.now());

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setFullName(newAdministratorRequest.getFullName());
        user.setUserRole(UserRole.ADMINISTRATOR);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setPassword(bcryptPassword);
        user.setMobileNumber(newAdministratorRequest.getMobileNumber());
        user.setUsername(newAdministratorRequest.getEmailAddress());
        user.setOtp(null);

        user = userService.save(user);

        DiademResponse farmersPayResponse = new DiademResponse();
        farmersPayResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        farmersPayResponse.setResponseData(user);
        farmersPayResponse.setMessage("Administrator profile created successfully. A One-Time Code and credentials have been sent to the administrator. Your new administrator " +
                "is required to use the One-Time code to activate their profile");
        return ResponseEntity.ok().body(farmersPayResponse);
    }


    @RequestMapping(value="/get-user-by-id/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserById(@PathVariable Long userId){


        User user = userService.getUserById(userId);

        DiademResponse farmersPayResponse = new DiademResponse();
        farmersPayResponse.setResponseCode(DiademResponseCode.SUCCESS.value);
        farmersPayResponse.setResponseData(user);
        farmersPayResponse.setMessage("User fetched");
        return ResponseEntity.ok().body(farmersPayResponse);
    }






}
