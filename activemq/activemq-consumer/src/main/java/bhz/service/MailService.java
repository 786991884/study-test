package bhz.service;

import bhz.entity.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage simpleMailMessage;
    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    /**
     * <B>方法名称：</B>发送邮件<BR>
     * <B>概要说明：</B>发送邮件<BR>
     *
     * @param mail
     */
    public void mailSend(final Mail mail) {
        threadPool.execute(new Runnable() {
            public void run() {
                try {
                    simpleMailMessage.setFrom(simpleMailMessage.getFrom());
                    simpleMailMessage.setTo(mail.getTo());
                    simpleMailMessage.setSubject(mail.getSubject());
                    simpleMailMessage.setText(mail.getContent());
                    mailSender.send(simpleMailMessage);
                } catch (MailException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        });
    }
}