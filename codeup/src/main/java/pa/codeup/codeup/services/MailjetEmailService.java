package pa.codeup.codeup.services;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;

import com.mailjet.client.transactional.SendContact;
import com.mailjet.client.transactional.SendEmailsRequest;
import com.mailjet.client.transactional.TrackOpens;
import com.mailjet.client.transactional.TransactionalEmail;
import com.mailjet.client.transactional.response.SendEmailsResponse;

import org.springframework.stereotype.Service;


@Service
public class MailjetEmailService implements EmailService {

    private final String MJ_APIKEY_PUBLIC = "388aa9af233dc86e513e63f7647d0060";
    private final String MJ_APIKEY_PRIVATE = "a90e578cbb679cb7fee27c603f79a6d3";
    private final String FROM = "codeup";
    private final String FROM_EMAIL = "rckamiri@gmail.com";

    private final ClientOptions options = ClientOptions.builder()
            .apiKey(MJ_APIKEY_PUBLIC)
            .apiSecretKey(MJ_APIKEY_PRIVATE)
            .build();

    private final MailjetClient client = new MailjetClient(options);

    public boolean sendEmail(String recipient, String recipientEmail, String subject, String content) {
        try {
            TransactionalEmail message1 = TransactionalEmail
                    .builder()
                    .to(new SendContact(recipientEmail, recipient))
                    .from(new SendContact(FROM_EMAIL, FROM))
                    .htmlPart(content)
                    .subject(subject)
                    .trackOpens(TrackOpens.ENABLED)
                    .header("test-header-key", "test-value")
                    .customID("custom-id-value")
                    .build();

            SendEmailsRequest request = SendEmailsRequest
                    .builder()
                    .message(message1) // you can add up to 50 messages per request
                    .build();

            // act
            SendEmailsResponse response = request.sendWith(client);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

