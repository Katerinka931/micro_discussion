package com.micro.discussions.broker;

import com.micro.discussions.services.DiscussionService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementDeleteReceiver implements ChannelAwareMessageListener {
    private final DiscussionService discussionService;

    public AdvertisementDeleteReceiver(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @Override
    @RabbitListener(queues = "advertisement", ackMode = "MANUAL")
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            discussionService.deleteById(Integer.parseInt(new String(message.getBody())));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
