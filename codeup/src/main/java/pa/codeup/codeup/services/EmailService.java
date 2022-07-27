package pa.codeup.codeup.services;

import org.springframework.stereotype.Service;


@Service
public interface EmailService {
    public boolean sendEmail(String recipient, String recipientEmail, String subject, String content);
}
