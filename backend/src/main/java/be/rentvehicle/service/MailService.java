package be.rentvehicle.service;

import be.rentvehicle.config.SendGridConfiguration;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * MailService for sending emails.
 */

// @Profile("prod")
@Service
public class MailService {

    private final String sendGridAPI;

    public MailService(SendGridConfiguration sendGridConfiguration) {
        this.sendGridAPI = sendGridConfiguration.getApiKey();
    }

    public void sendEmailConfirmation(String emailTo, String key) {
        Email from = new Email("he201718@students.ephec.be", "rent vehicle");
        String subject = "Confirm Your Email !";
        Email to = new Email(emailTo);
        Content content = new Content("text/html", emailConfirmationTemplate()
                .replace("xxx-key-xxx", key));
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            SendGrid sg = new SendGrid(sendGridAPI);
            Response response = sg.api(request);
            System.out.println(response.getStatusCode()); // 202 if ok
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendReservationEMail(String emailTo, String bookingRef ,String carName, String retunDate,
                                     String pickUpDate, String costPerDay, String total) {
        Email from = new Email("he201718@students.ephec.be", "rent vehicle");
        String subject = "Thank You For Your Reservation !";
        Email to = new Email(emailTo);
        Content content = new Content("text/html", reservationTemplate()
                .replace("xxx-bookingRef-xxx", bookingRef)
                .replace("xxx-pickUpDate-xxx", pickUpDate)
                .replace("xxx-returnDate-xxx", retunDate)
                .replace("xxx-carName-xxx", carName)
                .replace("xxx-DatePickUp-xxx", pickUpDate)
                .replace("xxx-total-xxx", total)
                .replace("xxx-cost-xxx", costPerDay));
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            SendGrid sg = new SendGrid(sendGridAPI);
            Response response = sg.api(request);
            System.out.println(response.getStatusCode()); // 202 if ok
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendContactMessage(String firstName, String lastName ,String phone, String email, String message) {
        Email from = new Email("he201718@students.ephec.be", "rent vehicle");
        String subject = "Customer Message!";
        Email to = new Email("danbarca955@gmail.com");
        Content content = new Content("text/html", contactMessage()
                .replace("xxx-first-xxx", firstName)
                .replace("xxx-last-xxx", lastName)
                .replace("xxx-message-xxx", message)
                .replace("xxx-phone-xxx", phone)
                .replace("xxx-email-xxx", email));
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            SendGrid sg = new SendGrid(sendGridAPI);
            Response response = sg.api(request);
            System.out.println(response.getStatusCode()); // 202 if ok
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendResetPasswordMail(String emailTo, String key) {
        Email from = new Email("he201718@students.ephec.be", "rent vehicle");
        String subject = "Reset Your Password !";
        Email to = new Email(emailTo);
        Content content = new Content("text/html", resetPasswordMail()
                .replace("xxx-key-xxx", key));
        Mail mail = new Mail(from, subject, to, content);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            SendGrid sg = new SendGrid(sendGridAPI);
            Response response = sg.api(request);
            System.out.println(response.getStatusCode()); // 202 if ok
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String emailConfirmationTemplate() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Please confirm your e-mail</title>
                    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <style type="text/css">
                        body, table, td, a {
                            -webkit-text-size-adjust: 100%;
                            -ms-text-size-adjust: 100%;
                        }
                               \s
                        table, td {
                            mso-table-lspace: 0pt;
                            mso-table-rspace: 0pt;
                        }
                               \s
                        img {
                            -ms-interpolation-mode: bicubic;
                        }
                               \s
                        img {
                            border: 0;
                            height: auto;
                            line-height: 100%;
                            outline: none;
                            text-decoration: none;
                        }
                               \s
                        table {
                            border-collapse: collapse !important;
                        }
                               \s
                        body {
                            height: 100% !important;
                            margin: 0 !important;
                            padding: 0 !important;
                            width: 100% !important;
                        }
                               \s
                        a[x-apple-data-detectors] {
                            color: inherit !important;
                            text-decoration: none !important;
                            font-size: inherit !important;
                            font-family: inherit !important;
                            font-weight: inherit !important;
                            line-height: inherit !important;
                        }
                               \s
                        a {
                            color: #00bc87;
                            text-decoration: underline;
                        }
                               \s
                        * img[tabindex=
                               \s
                               \s
                               \s
                        0
                        ]
                        + div {
                            display: none !important;
                        }
                               \s
                        @media screen and (max-width: 350px) {
                            h1 {
                                font-size: 24px !important;
                                line-height: 24px !important;
                            }
                        }
                               \s
                        div[style*=margin:  16  px  0  ;  ]
                        {
                            margin: 0 !important
                        ;
                        }
                        @media screen and (min-width: 360px) {
                            .headingMobile {
                                font-size: 40px !important;
                            }
                               \s
                            .headingMobileSmall {
                                font-size: 28px !important;
                            }
                        }
                    </style>
                </head>
                <body bgcolor="#ffffff" style="background-color: #ffffff; margin: 0 !important; padding: 0 !important;">
                <div style="display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;">
                    - to finish signing up, you just need to confirm that we got your e-mail right within 48 hours. To confirm please
                    click the VERIFY button.
                </div>
                <center>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" valign="top">
                        <tbody>
                        <tr>
                            <td>
                                <table border="0" cellpadding="0" cellspacing="0" align="center" valign="top" bgcolor="#ffffff"
                                       style="padding: 0 20px !important;max-width: 500px;width: 90%;">
                                    <tbody>
                                    <tr>
                                        <td bgcolor="#ffffff" align="center" style="padding: 10px 0 0px 0;"><!--[if (gte mso 9)|(IE)]>
                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                <tr>
                                                    <td align="center" valign="top" width="350">
                                            <![endif]-->
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                                                   style="max-width: 500px;border-bottom: 1px solid #e4e4e4 ;">
                                                <tbody>
                                                  <tr>
                                                       <td bgcolor="#ffffff" align="left" valign="middle"
                                                           style="padding: 0px; color: #111111; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 62px;padding:0 0 15px 0;">
                                                           <a href="https://rent-vehicle.herokuapp.com" target="_blank">
                                                               <img width="20" height="30" alt="logo"
                                                                    src="https://raw.githubusercontent.com/danny00747/vms/main/frontend/src/favicon.ico?token=AIQ7LICID73NHVKDHDS3LSDARPTMW"></a>
                                                       </td>
                                                       <td bgcolor="#ffffff" align="right" valign="middle"
                                                           style="padding: 0px; color: #111111; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 48px;padding:0 0 15px 0;">
                                                           <a href="https://rent-vehicle.herokuapp.com" target="_blank"
                                                              style="font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;color: #797979;font-size: 12px;font-weight:400;-webkit-font-smoothing:antialiased;text-decoration: none;">
                                                               Vms App</a></td>
                                                  </tr>
                                                </tbody>
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                            <![endif]-->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td bgcolor="#ffffff" align="center" style="padding: 0;"><!--[if (gte mso 9)|(IE)]>
                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                <tr>
                                                    <td align="center" valign="top" width="350">
                                            <![endif]-->
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                                                   style="max-width: 500px;border-bottom: 1px solid #e4e4e4;">
                                                <tbody>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="left"
                                                        style="padding: 20px 0 0 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400;-webkit-font-smoothing:antialiased;">
                                                        <p class="headingMobile"
                                                           style="margin: 0;color: #171717;font-size: 26px;font-weight: 200;line-height: 130%;margin-bottom:5px;">
                                                           Verify your email to finish signing up for VmsApp </p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td height="20"></td>
                                                </tr>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="left"
                                                        style="padding:0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400;-webkit-font-smoothing:antialiased;">
                                                        <p style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                            Hello there,</p>
                                                        <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                        <p style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                            Here is the confirmation key for your online form :\s
                                                            <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                            xxx-key-xxx
                                                            <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                            All you have to do is copy the confirmation key and paste it to your form to complete the\s
                                                            email verification process.\s
                                                        </p>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                            <![endif]-->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td bgcolor="#ffffff" align="center" style="padding: 0;"><!--[if (gte mso 9)|(IE)]>
                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                <tr>
                                                    <td align="center" valign="top" width="350">
                                            <![endif]-->
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 500px;">
                                                <tbody>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="center"
                                                        style="padding: 30px 0 30px 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;">
                                                            Need help? Ask at <a href="mailto:HE201718@students.ephec.be"
                                                                                 style="color: #00bc87;text-decoration: underline;"
                                                                                 target="_blank">HE201718@students.ephec.be</a> or visit our <a
                                                                href="https://rent-vehicle.herokuapp.com"
                                                                style="color: #00bc87;text-decoration: underline;" target="_blank">Help
                                                            Center</a></p>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="center"
                                                        style="padding: 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;"></p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="center"
                                                        style="padding: 15px 0 30px 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;">
                                                            Vms App, Inc.<br> Rue Wiertz 60, B 1047<br> Brussels, Belgium</p>
                                                    </td>
                                                </tr>
                                                </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                            <![endif]-->
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </center>
                </body>
                </html>
                """;
    }

    private String reservationTemplate() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Thank Your Reservation</title>
                    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <style type="text/css">
                        body, table, td, a {
                            -webkit-text-size-adjust: 100%;
                            -ms-text-size-adjust: 100%;
                        }
                                
                        .green {
                            color: #4CAF50;
                            font-weight: 680
                        }
                                
                        table, td {
                            mso-table-lspace: 0pt;
                            mso-table-rspace: 0pt;
                        }
                                
                        img {
                            -ms-interpolation-mode: bicubic;
                        }
                                
                        img {
                            border: 0;
                            height: auto;
                            line-height: 100%;
                            outline: none;
                            text-decoration: none;
                        }
                                
                        table {
                            border-collapse: collapse !important;
                        }
                                
                        body {
                            height: 100% !important;
                            margin: 0 !important;
                            padding: 0 !important;
                            width: 100% !important;
                        }
                                
                        a[x-apple-data-detectors] {
                            color: inherit !important;
                            text-decoration: none !important;
                            font-size: inherit !important;
                            font-family: inherit !important;
                            font-weight: inherit !important;
                            line-height: inherit !important;
                        }
                                
                        a {
                            color: #4CAF50;
                            text-decoration: underline;
                        }
                                
                        * img[tabindex=
                                
                                
                                
                                
                                
                        0
                        ]
                        + div {
                            display: none !important;
                        }
                                
                        @media screen and (max-width: 350px) {
                            h1 {
                                font-size: 24px !important;
                                line-height: 24px !important;
                            }
                        }
                                
                        div[style*=margin:
                                
                        16
                        px
                                
                        0
                        ;
                        ]
                        {
                            margin: 0 !important
                        ;
                        }
                        @media screen and (min-width: 360px) {
                            .headingMobile {
                                font-size: 40px !important;
                            }
                                
                            .headingMobileSmall {
                                font-size: 28px !important;
                            }
                                
                            .mobile {
                                padding-top: 40px
                            }
                        }
                    </style>
                </head>
                <body bgcolor="#ffffff" style="background-color: #ffffff; margin: 0 !important; padding: 0 !important;">
                <div style="display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;">
                    - to finish signing up, you just need to confirm that we got your e-mail right within 48 hours. To confirm please
                    click the VERIFY button.
                </div>
                <section>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" valign="top">
                        <tbody>
                        <tr>
                            <td>
                                <table border="0" cellpadding="0" cellspacing="0" align="center" valign="top" bgcolor="#ffffff"
                                       style="padding: 0 20px !important;max-width: 500px;width: 90%;">
                                    <tbody>
                                    <tr>
                                        <td bgcolor="#ffffff" align="center" style="padding: 10px 0 0px 0;"><!--[if (gte mso 9)|(IE)]>
                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                <tr>
                                                    <td align="center" valign="top" width="350">
                                            <![endif]-->
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                                                   style="max-width: 500px;border-bottom: 1px solid #e4e4e4 ;">
                                                <tbody>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="left" valign="middle"
                                                        style="padding: 0px; color: #111111; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 62px;padding:0 0 15px 0;">
                                                        <a href="https://cms-cars.herokuapp.com" target="_blank">
                                                            <img width="20" height="30" alt="logo"
                                                                 src="https://raw.githubusercontent.com/danny00747/vms/main/frontend/src/favicon.ico?token=AIQ7LICID73NHVKDHDS3LSDARPTMW"></a>
                                                    </td>
                                                    <td bgcolor="#ffffff" align="right" valign="middle"
                                                        style="padding: 0px; color: #111111; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 48px;padding:0 0 15px 0;">
                                                        <a href="https://cms-cars.herokuapp.com" target="_blank"
                                                           style="font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;color: #797979;font-size: 12px;font-weight:400;-webkit-font-smoothing:antialiased;text-decoration: none;">
                                                            Vms App</a></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                            <![endif]-->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td bgcolor="#ffffff" align="center" style="padding: 0;"><!--[if (gte mso 9)|(IE)]>
                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                <tr>
                                                    <td align="center" valign="top" width="350">
                                            <![endif]-->
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                                                   style="max-width: 500px;border-bottom: 1px solid #e4e4e4;">
                                                <tbody>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="left"
                                                        style="padding: 20px 0 0 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400;-webkit-font-smoothing:antialiased;">
                                                        <p class="headingMobile"
                                                           style="margin: 0;color: #171717;font-size: 26px;font-weight: 200;line-height: 130%;margin-bottom:5px;">
                                                            Thank you for your booking and for having chosen our company. </p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td height="20"></td>
                                                </tr>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="left"
                                                        style="padding:0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400;-webkit-font-smoothing:antialiased;">
                                                        <p style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                            Hello there,</p>
                                                        <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                        <p style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                            We have received your reservation.
                                                        </p>
                                                        <p style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                            On xxx-DatePickUp-xxx, when you come to pick up the car, we will need the following
                                                            information :
                                                        </p>
                                
                                                        <p>
                                                        <ul style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                            <li>Identity card</li>
                                                            <li>Driving license</li>
                                                            <li>Official address</li>
                                                            <li>Phone number</li>
                                                        </ul>
                                                        </p>
                                
                                                        <p style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                            Here is a recap of your reservation :
                                
                                                        </p>
                                
                                
                                                        <hr>
                                
                                                        <div class="rounded bg-white">
                                                            <div class="justify-content-center">
                                
                                                                <div style="background-color: ghostwhite" class="bg-light rounded flex-column">
                                                                    <p></p>
                                                                    <div>
                                                                        <h4 style="text-align: center">Reservation Recap</h4>
                                                                    </div>
                                                                    <p>
                                                                        <ul style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                                            <li>Booking Reference : xxx-bookingRef-xxx</li>
                                                                            <li>Car Name : xxx-carName-xxx</li>
                                                                            <li>Pick up date : xxx-pickUpDate-xxx</li>
                                                                            <li>Return date : xxx-returnDate-xxx</li>
                                                                            <li>Cost per day : xxx-cost-xxx</li>
                                                                            <li>Total : xxx-total-xxx</li>
                                                                        </ul>
                                                                    </p>
                                                                </div>
                                                            </div>
                                                        </div>
                                
                                
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                            <![endif]-->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td bgcolor="#ffffff" align="center" style="padding: 0;"><!--[if (gte mso 9)|(IE)]>
                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                <tr>
                                                    <td align="center" valign="top" width="350">
                                            <![endif]-->
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 500px;">
                                                <tbody>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="center"
                                                        style="padding: 30px 0 30px 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;">
                                                            Need help? Ask at <a href="mailto:HE201718@students.ephec.be"
                                                                                 style="color: #00bc87;text-decoration: underline;"
                                                                                 target="_blank">HE201718@students.ephec.be</a> or visit
                                                            our <a
                                                                href="https://cms-cars.herokuapp.com/faq"
                                                                style="color: #00bc87;text-decoration: underline;" target="_blank">Help
                                                            Center</a></p>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="center"
                                                        style="padding: 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;"></p>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td bgcolor="#ffffff" align="center"
                                                        style="padding: 15px 0 30px 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;">
                                                            Vms App, Inc.<br> Rue Wiertz 60, B 1047<br> Brussels, Belgium</p>
                                                    </td>
                                                </tr>
                                                </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                            <![endif]-->
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </section>
                </body>
                </html>
                                
                """;
    }

    private String contactMessage(){
        return """
                <!DOCTYPE html>
                                <html>
                                <head>
                                    <title>Contact Message</title>
                                    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1">
                                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                                    <style type="text/css">
                                        body, table, td, a {
                                            -webkit-text-size-adjust: 100%;
                                            -ms-text-size-adjust: 100%;
                                        }
                                               s
                                        table, td {
                                            mso-table-lspace: 0pt;
                                            mso-table-rspace: 0pt;
                                        }
                                               s
                                        img {
                                            -ms-interpolation-mode: bicubic;
                                        }
                                               s
                                        img {
                                            border: 0;
                                            height: auto;
                                            line-height: 100%;
                                            outline: none;
                                            text-decoration: none;
                                        }
                                               s
                                        table {
                                            border-collapse: collapse !important;
                                        }
                                               s
                                        body {
                                            height: 100% !important;
                                            margin: 0 !important;
                                            padding: 0 !important;
                                            width: 100% !important;
                                        }
                                               s
                                        a[x-apple-data-detectors] {
                                            color: inherit !important;
                                            text-decoration: none !important;
                                            font-size: inherit !important;
                                            font-family: inherit !important;
                                            font-weight: inherit !important;
                                            line-height: inherit !important;
                                        }
                                               s
                                        a {
                                            color: #00bc87;
                                            text-decoration: underline;
                                        }
                                               s
                                        * img[tabindex=
                                               s
                                               s
                                               s
                                        0
                                        ]
                                        + div {
                                            display: none !important;
                                        }
                                               s
                                        @media screen and (max-width: 350px) {
                                            h1 {
                                                font-size: 24px !important;
                                                line-height: 24px !important;
                                            }
                                        }
                                               s
                                        div[style*=margin:  16  px  0  ;  ]
                                        {
                                            margin: 0 !important
                                        ;
                                        }
                                        @media screen and (min-width: 360px) {
                                            .headingMobile {
                                                font-size: 40px !important;
                                            }
                                               s
                                            .headingMobileSmall {
                                                font-size: 28px !important;
                                            }
                                        }
                                    </style>
                                </head>
                                <body bgcolor="#ffffff" style="background-color: #ffffff; margin: 0 !important; padding: 0 !important;">
                                <div style="display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;">
                                    A customer sent a message via contact page
                                </div>
                                <center>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" valign="top">
                                        <tbody>
                                        <tr>
                                            <td>
                                                <table border="0" cellpadding="0" cellspacing="0" align="center" valign="top" bgcolor="#ffffff"
                                                       style="padding: 0 20px !important;max-width: 500px;width: 90%;">
                                                    <tbody>
                                                    <tr>
                                                        <td bgcolor="#ffffff" align="center" style="padding: 10px 0 0px 0;"><!--[if (gte mso 9)|(IE)]>
                                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                                <tr>
                                                                    <td align="center" valign="top" width="350">
                                                            <![endif]-->
                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                                                                   style="max-width: 500px;border-bottom: 1px solid #e4e4e4 ;">
                                                                <tbody>
                                                                    <tr>
                                                                        <td bgcolor="#ffffff" align="left" valign="middle"
                                                                            style="padding: 0px; color: #111111; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 62px;padding:0 0 15px 0;">
                                                                            <a href="https://rent-vehicle.herokuapp.com" target="_blank">
                                                                                <img width="20" height="30" alt="logo"
                                                                                     src="https://raw.githubusercontent.com/danny00747/vms/main/frontend/src/favicon.ico?token=AIQ7LICID73NHVKDHDS3LSDARPTMW"></a>
                                                                        </td>
                                                                        <td bgcolor="#ffffff" align="right" valign="middle"
                                                                            style="padding: 0px; color: #111111; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 48px;padding:0 0 15px 0;">
                                                                            <a href="https://rent-vehicle.herokuapp.com" target="_blank"
                                                                               style="font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;color: #797979;font-size: 12px;font-weight:400;-webkit-font-smoothing:antialiased;text-decoration: none;">
                                                                                Vms App</a></td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                                            <![endif]-->
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td bgcolor="#ffffff" align="center" style="padding: 0;"><!--[if (gte mso 9)|(IE)]>
                                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                                <tr>
                                                                    <td align="center" valign="top" width="350">
                                                            <![endif]-->
                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                                                                   style="max-width: 500px;border-bottom: 1px solid #e4e4e4;">
                                                                <tbody>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="left"
                                                                        style="padding: 20px 0 0 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400;-webkit-font-smoothing:antialiased;">
                                                                        <p class="headingMobile"
                                                                           style="margin: 0;color: #171717;font-size: 26px;font-weight: 200;line-height: 130%;margin-bottom:5px;">
                                                                           A customer message </p>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td height="20"></td>
                                                                </tr>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="left"
                                                                        style="padding:0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400;-webkit-font-smoothing:antialiased;">
                                                                        <p style="margin:0;color:#585858;font-size:18px;font-weight:400;line-height:170%;">
                                                                            xxx-first-xxx xxx-last-xxx sent this message,</p>
                                                                        <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                                        <p style="margin:0;color:#585858;font-size:16px;font-weight:400;line-height:170%;">
                                                                            xxx-message-xxx
                                                                            <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                                            ---
                                                                            <p style="margin:0;margin-top:16px;line-height:0;">
                                                                                Customer email : xxx-email-xxx
                                                                            </p>
                                                                            <p></p>
                                                                            <p style="margin:0;margin-top:16px;line-height:0;">
                                                                                Customer phone number : xxx-phone-xxx
                                                                            </p>
                                                                        </p>
                                                                    </td>
                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                                            <![endif]-->
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td bgcolor="#ffffff" align="center" style="padding: 0;"><!--[if (gte mso 9)|(IE)]>
                                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                                <tr>
                                                                    <td align="center" valign="top" width="350">
                                                            <![endif]-->
                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 500px;">
                                                                <tbody>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="center"
                                                                        style="padding: 30px 0 30px 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;">
                                                                            Need help? Ask at <a href="mailto:HE201718@students.ephec.be"
                                                                                                 style="color: #00bc87;text-decoration: underline;"
                                                                                                 target="_blank">HE201718@students.ephec.be</a> or visit our <a
                                                                                href="https://rent-vehicle.herokuapp.com"
                                                                                style="color: #00bc87;text-decoration: underline;" target="_blank">Help
                                                                            Center</a></p>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="center"
                                                                        style="padding: 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;"></p>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="center"
                                                                        style="padding: 15px 0 30px 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;">
                                                                            Vms App, Inc.<br> Rue Wiertz 60, B 1047<br> Brussels, Belgium</p>
                                                                    </td>
                                                                </tr>
                                                                </td>
                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                                            <![endif]-->
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </center>
                                </body>
                                </html>
                """;
    }

    private String resetPasswordMail(){
        return """
                <!DOCTYPE html>
                                <html>
                                <head>
                                    <title>Reset your password</title>
                                    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1">
                                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                                    <style type="text/css">
                                        body, table, td, a {
                                            -webkit-text-size-adjust: 100%;
                                            -ms-text-size-adjust: 100%;
                                        }
                                               s
                                        table, td {
                                            mso-table-lspace: 0pt;
                                            mso-table-rspace: 0pt;
                                        }
                                               s
                                        img {
                                            -ms-interpolation-mode: bicubic;
                                        }
                                               s
                                        img {
                                            border: 0;
                                            height: auto;
                                            line-height: 100%;
                                            outline: none;
                                            text-decoration: none;
                                        }
                                               s
                                        table {
                                            border-collapse: collapse !important;
                                        }
                                               s
                                        body {
                                            height: 100% !important;
                                            margin: 0 !important;
                                            padding: 0 !important;
                                            width: 100% !important;
                                        }
                                               s
                                        a[x-apple-data-detectors] {
                                            color: inherit !important;
                                            text-decoration: none !important;
                                            font-size: inherit !important;
                                            font-family: inherit !important;
                                            font-weight: inherit !important;
                                            line-height: inherit !important;
                                        }
                                               s
                                        a {
                                            color: #00bc87;
                                            text-decoration: underline;
                                        }
                                               s
                                        * img[tabindex=
                                               s
                                               s
                                               s
                                        0
                                        ]
                                        + div {
                                            display: none !important;
                                        }
                                               s
                                        @media screen and (max-width: 350px) {
                                            h1 {
                                                font-size: 24px !important;
                                                line-height: 24px !important;
                                            }
                                        }
                                               s
                                        div[style*=margin:  16  px  0  ;  ]
                                        {
                                            margin: 0 !important
                                        ;
                                        }
                                        @media screen and (min-width: 360px) {
                                            .headingMobile {
                                                font-size: 40px !important;
                                            }
                                               s
                                            .headingMobileSmall {
                                                font-size: 28px !important;
                                            }
                                        }
                                    </style>
                                </head>
                                <body bgcolor="#ffffff" style="background-color: #ffffff; margin: 0 !important; padding: 0 !important;">
                                <div style="display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;">
                                    - Reset your password
                                </div>
                                <center>
                                    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center" valign="top">
                                        <tbody>
                                        <tr>
                                            <td>
                                                <table border="0" cellpadding="0" cellspacing="0" align="center" valign="top" bgcolor="#ffffff"
                                                       style="padding: 0 20px !important;max-width: 500px;width: 90%;">
                                                    <tbody>
                                                    <tr>
                                                        <td bgcolor="#ffffff" align="center" style="padding: 10px 0 0px 0;"><!--[if (gte mso 9)|(IE)]>
                                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                                <tr>
                                                                    <td align="center" valign="top" width="350">
                                                            <![endif]-->
                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                                                                   style="max-width: 500px;border-bottom: 1px solid #e4e4e4 ;">
                                                                <tbody>
                                                                  <tr>
                                                                       <td bgcolor="#ffffff" align="left" valign="middle"
                                                                           style="padding: 0px; color: #111111; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 62px;padding:0 0 15px 0;">
                                                                           <a href="https://rent-vehicle.herokuapp.com" target="_blank">
                                                                               <img width="20" height="30" alt="logo"
                                                                                    src="https://raw.githubusercontent.com/danny00747/vms/main/frontend/src/favicon.ico?token=AIQ7LICID73NHVKDHDS3LSDARPTMW"></a>
                                                                       </td>
                                                                       <td bgcolor="#ffffff" align="right" valign="middle"
                                                                           style="padding: 0px; color: #111111; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; line-height: 48px;padding:0 0 15px 0;">
                                                                           <a href="https://rent-vehicle.herokuapp.com" target="_blank"
                                                                              style="font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;color: #797979;font-size: 12px;font-weight:400;-webkit-font-smoothing:antialiased;text-decoration: none;">
                                                                               Vms App</a></td>
                                                                  </tr>
                                                                </tbody>
                                                            </table>
                                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                                            <![endif]-->
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td bgcolor="#ffffff" align="center" style="padding: 0;"><!--[if (gte mso 9)|(IE)]>
                                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                                <tr>
                                                                    <td align="center" valign="top" width="350">
                                                            <![endif]-->
                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%"
                                                                   style="max-width: 500px;border-bottom: 1px solid #e4e4e4;">
                                                                <tbody>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="left"
                                                                        style="padding: 20px 0 0 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400;-webkit-font-smoothing:antialiased;">
                                                                        <p class="headingMobile"
                                                                           style="margin: 0;color: #171717;font-size: 26px;font-weight: 200;line-height: 130%;margin-bottom:5px;">
                                                                           Reset your password </p>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td height="20"></td>
                                                                </tr>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="left"
                                                                        style="padding:0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400;-webkit-font-smoothing:antialiased;">
                                                                        <p style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                                            Hello there,</p>
                                                                        <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                                        <p style="margin:0;color:#585858;font-size:14px;font-weight:400;line-height:170%;">
                                                                            Here is the confirmation key for your reset password process :
                                                                            <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                                            xxx-key-xxx
                                                                            <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                                            All you have to do is copy the confirmation key and paste it into your form to complete
                                                                            your reset password process.
                                                                            <p style="margin:0;margin-top:20px;line-height:0;"></p>
                                                                            Ps : This key is valid for 24 hours.
                                                                        </p>
                                                                    </td>
                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                                            <![endif]-->
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td bgcolor="#ffffff" align="center" style="padding: 0;"><!--[if (gte mso 9)|(IE)]>
                                                            <table align="center" border="0" cellspacing="0" cellpadding="0" width="350">
                                                                <tr>
                                                                    <td align="center" valign="top" width="350">
                                                            <![endif]-->
                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 500px;">
                                                                <tbody>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="center"
                                                                        style="padding: 30px 0 30px 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;">
                                                                            Need help? Ask at <a href="mailto:HE201718@students.ephec.be"
                                                                                                 style="color: #00bc87;text-decoration: underline;"
                                                                                                 target="_blank">HE201718@students.ephec.be</a> or visit our <a
                                                                                href="https://rent-vehicle.herokuapp.com"
                                                                                style="color: #00bc87;text-decoration: underline;" target="_blank">Help
                                                                            Center</a></p>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="center"
                                                                        style="padding: 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;"></p>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td bgcolor="#ffffff" align="center"
                                                                        style="padding: 15px 0 30px 0; color: #666666; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 18px;">
                                                                        <p style="margin: 0;color: #585858;font-size: 12px;font-weight: 400;-webkit-font-smoothing:antialiased;line-height: 170%;">
                                                                            Vms App, Inc.<br> Rue Wiertz 60, B 1047<br> Brussels, Belgium</p>
                                                                    </td>
                                                                </tr>
                                                                </td>
                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                            <!--[if (gte mso 9)|(IE)]></td></tr></table>
                                                            <![endif]-->
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </center>
                                </body>
                                </html>
                """;
    }
}
