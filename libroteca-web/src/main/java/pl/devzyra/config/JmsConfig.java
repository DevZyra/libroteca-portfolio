package pl.devzyra.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
@EnableJms
public class JmsConfig {

    public static final String ORDER_QUEUE = "order-queue";
    public static final String BROKER_URL = "tcp://localhost:61616";


    @Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setUser("artemis");
        connectionFactory.setPassword("simetraehcapa");
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws JMSException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(ORDER_QUEUE);
        return template;
    }
    @Bean
    SimpleMessageConverter converter(){
        return new SimpleMessageConverter();
    }

}
