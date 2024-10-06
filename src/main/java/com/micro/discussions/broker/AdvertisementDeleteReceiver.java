package com.micro.discussions.broker;

import com.micro.discussions.services.DiscussionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AdvertisementDeleteReceiver {
    private final DiscussionService discussionService;

    public AdvertisementDeleteReceiver(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @RabbitListener(queues = "advertisement")
    public void consumeDeleteAdvertisement(String message) {
        discussionService.deleteById(Integer.parseInt(message));
    }
}
