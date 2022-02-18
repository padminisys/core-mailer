package com.padminisys.mailer.coremailer.service.domain.mail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.padminisys.mailer.coremailer.dal.entities.MailTransaction;
import com.padminisys.mailer.coremailer.service.model.Mailer;

import io.micrometer.core.instrument.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HtmlMessageBuilder {

	private final ResourceLoader resourceLoader;

	public MimeMessageHelper mimeMessageHelperBuilder(Mailer mailer, MailTransaction mailTransaction) {
		String resourceRootPath = mailer.getMailRequest().getMailContentPath();
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(mailer.getMimeMessage(), true);
			helper.setFrom(mailer.getClient().getMailId());
			helper.setReplyTo(mailer.getClient().getReplyToMailId());

			Resource resource = resourceLoader.getResource("classpath:" + resourceRootPath + "/mail.html");

			String html = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);

			Element divWrapper = new Element(Tag.valueOf("div"), "").attr("style", "text-align: center");
			Element link = new Element(Tag.valueOf("a"), "").text("Click here to unsubscribe from our mailing list.")
					.attr("href", "https://padmini-mailer.herokuapp.com/contact/unsubscribe?mailTransactionId="
							+ mailTransaction.getId())
					.attr("target", "_blank");
			divWrapper.appendChild(link);
			helper.setSubject(Jsoup.parse(html).title());
			helper.setText(html + divWrapper.outerHtml(), true);
		} catch (Exception exception) {
			log.error("Unexpected exception encountered while creating message.");
			log.error(ExceptionUtils.getStackTrace(exception));
		}
		return helper;
	}

	public Mailer mimeMessageBuilder(Mailer mailer) throws MessagingException, IOException {
		MimeMessage mimeMessage = mailer.getJavaMailSender().createMimeMessage();
		mailer.setMimeMessage(mimeMessage);
		return mailer;
	}

}