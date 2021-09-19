package io.applianceskeeper.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@Slf4j
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        log.error("Message " + message);

        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message + "!"));
    }

}
