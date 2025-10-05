package com.andro.microservices.notification_service.service;

import com.andro.microservices.notification_service.order.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final JavaMailSender javaMailSender;
    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent){
        log.info("Got Message from order-placed Topic {}", orderPlacedEvent);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("aswinspringshop@mail.com");
            messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Your Order with OrderNumbe %s is Placed Successfully", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Hi
                    
                    Your Order Has been %s Successfully placed
                    
                    Best Regards
                    Aswin Spring
                    """
                    , orderPlacedEvent.getOrderNumber()));

        };
        try{
            javaMailSender.send(messagePreparator);
            log.info("Mail Sent Successfully");
        }catch (MailException e){
            log.error("Exception Occurred During Mailing", e);
            throw new RuntimeException("Exception Occurred During Mailing", e);
        }
    }
}
