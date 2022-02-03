package com.padminisys.mailer.coremailer.service.domain.mail;

import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;
import com.padminisys.mailer.coremailer.service.model.Mailer;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HtmlMessageBuilder {

    private final ResourceLoader resourceLoader;
    private final MailTransactionBuilder mailTransactionBuilder;

    public Mailer mimeMessageBuilder(Mailer mailer) throws MessagingException, IOException {
        String resourceRootPath = mailer.getMailRequest().getMailContentPath();
        MimeMessage mimeMessage = mailer.getJavaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mailer.getClient().getMailId());
        helper.setReplyTo(mailer.getClient().getReplyToMailId());
        helper.setSubject("Vikhyaath Developers Launched - RRR-DTCP");
        Resource resource = resourceLoader.getResource("classpath:" + resourceRootPath + "/mail.html");
        MailTransaction mailTransaction = mailTransactionBuilder.build(mailer);
        String html = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        Document document = Jsoup.parse(html);
        Element link = new Element(Tag.valueOf("a"), "")
                .text("Click here to unsubscribe from our mailing list.")
                .attr("href", "https://padmini-mailer.herokuapp.com/contact/unsubscribe?mailTransactionId=" + mailTransaction.getId())
                .attr("target", "_blank");
        Objects.requireNonNull(document.getElementById("unsubscribe")).appendChild(link);
        helper.setText(document.html(), true);
        mailer.setMimeMessageHelper(helper);
        mailer.setMimeMessage(mimeMessage);
        mailer.setMailTransaction(mailTransaction);
        return mailer;
    }
}