package pl.devzyra.listeners;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import pl.devzyra.model.entities.OrderEntity;

import static pl.devzyra.config.JmsConfig.ORDER_QUEUE;

@Component
public class OrderListener {

    @JmsListener(destination = ORDER_QUEUE)
    public void listen(@Payload OrderEntity order, @Headers MessageHeaders headers,
                       Message message){

        System.out.println("!@#!@#!#!@#!@#!#!@#! Received Order");
        System.out.println(order.getUser().getUsername());

    }

}
