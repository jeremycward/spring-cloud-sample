package com.isharp.ploniexfeed.polowebsock;



import com.isharp.ploniexfeed.connect.PoloniexWssSubscription;
import com.isharp.ploniexfeed.connect.WebsocketLIstener;
import com.isharp.polozilla.components.RawRecordTImestampExtractor;
import org.apache.kafka.streams.KeyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.function.Consumer;

/**
 * ChatServer Client
 *
 * @author Jiji_Sasidharan
 */

@ClientEndpoint
@Component
public class WebsocketClientEndpoint {
    final Logger logger = LoggerFactory.getLogger(WebsocketClientEndpoint.class);

    Session userSession = null;

    @Value(value="${poloniex.endpoint}")
    private String poloniexEndpoint;

    @Autowired
    private WebsocketLIstener listener;

    @PostConstruct
    public void startSubscription() {
        logger.info("about to start subscription");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(poloniexEndpoint));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        logger.info("opening websocket and subscribing");
        this.userSession = userSession;
        String subMsg = PoloniexWssSubscription.TICKER.toString();
        sendMessage(subMsg);
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason      the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {

        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (message.contains("[1010]") || message.equals("[1002,1]")) {
            logger.info("Subscription admin msg {}", message);
        } else {
            LocalDateTime n = LocalDateTime.now();
            String formatted = RawRecordTImestampExtractor.formatDatePrefix(n);
            listener.consume(message);


        }

    }
    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

}