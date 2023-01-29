package com.ead.course.consumers;

import com.ead.course.dto.UserEventDto;
import com.ead.course.models.UserModel;
import com.ead.course.services.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {

    final
    UserService userService;

    public UserEventConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${api.broker.queue.userEventQueue}", durable = "true"),
            exchange = @Exchange(value = "${api.broker.exchange.userEvent}", type = ExchangeTypes.FANOUT)
    ))
    public void listenUserEvent(UserEventDto userEventDto) {
        var userEvent = new UserModel(userEventDto);
        userEvent.getActionType()
                .performUserEvent(userService, userEvent);
    }
}
