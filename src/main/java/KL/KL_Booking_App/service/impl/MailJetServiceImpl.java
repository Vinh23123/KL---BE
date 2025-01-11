package KL.KL_Booking_App.service.impl;

import KL.KL_Booking_App.config.MailJetConfig;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MailJetServiceImpl {

    private final MailJetConfig mailJetConfig;


    public MailJetServiceImpl(MailJetConfig mailJetConfig) {
        this.mailJetConfig = mailJetConfig;
    }

    private MailjetClient createMailJetClient(){
        ClientOptions options = ClientOptions.builder()
                .apiKey(mailJetConfig.getApiKey())
                .apiSecretKey(mailJetConfig.getApiSecret())
                .build();

       return new MailjetClient(options);
    }

    public void sendMailTo() throws MailjetException {
        MailjetClient client = createMailJetClient();

        MailjetRequest mailjetRequest = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "vinhhoangvh11a@gmail.com" )
                                        .put("Admin", "Admin"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", "hoangvh167@gmail.com")
                                                .put("Name", "You")))
                                .put(Emailv31.Message.TEMPLATEID, 6619664)
                                .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                .put(Emailv31.Message.SUBJECT, "Your email password change!")
                                .put("Variables", new JSONObject()
                                        .put("code", "123456"))
                        ));
        MailjetResponse response = client.post(mailjetRequest);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
    }
}
