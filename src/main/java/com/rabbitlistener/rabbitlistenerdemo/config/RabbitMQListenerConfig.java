package com.rabbitlistener.rabbitlistenerdemo.config;

import com.rabbitlistener.rabbitlistenerdemo.RabbitMQMessageListener;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQListenerConfig {

    private static final String MY_QUEUE = "MyQueue";
    private static final String HOST_NAME = "localhost";
    private static final String USER_NAME = "guest";
    private static final String PASSWORD = "guest";

    @Bean
    public Queue myQueue(){
        return new Queue(MY_QUEUE,true,false,false);
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(HOST_NAME,5672);
        connectionFactory.setUsername(USER_NAME);
        connectionFactory.setPassword(PASSWORD);

        return connectionFactory;
    }
    @Bean
    public MessageListenerContainer messageListenerContainer(){
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setQueues(myQueue());
        messageListenerContainer.setMessageListener(new RabbitMQMessageListener());

        return messageListenerContainer;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
