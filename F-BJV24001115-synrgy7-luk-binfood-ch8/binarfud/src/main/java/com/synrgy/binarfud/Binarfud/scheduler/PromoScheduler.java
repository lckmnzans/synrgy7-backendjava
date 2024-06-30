package com.synrgy.binarfud.Binarfud.scheduler;

import com.synrgy.binarfud.Binarfud.kafka.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PromoScheduler {
    @Autowired
    MessageProducer messageProducer;

    @Scheduled(cron = "0 */1 * * * *")
    public void cronSendPromo() {
        System.out.println("Promo notif has been sent to kafka");
        StringBuilder message = new StringBuilder();
        message.append("{");
        message.append(messageProducer.setData("title", "Promo 1212"));
        message.append(",");
        message.append(messageProducer.setData("body", "Nikmati promo 12% untuk setiap pembelian di jam 12.00-15.00"));
        message.append(",");
        message.append(messageProducer.setData("topic", "Promo produk"));
        message.append("}");
        messageProducer.sendMessage("promo-notif", message.toString());
    }
}
