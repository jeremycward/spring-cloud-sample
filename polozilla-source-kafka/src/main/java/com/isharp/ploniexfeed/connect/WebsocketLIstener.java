package com.isharp.ploniexfeed.connect;

import com.isharp.ploniexfeed.polowebsock.WebsocketClientEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

@Component
public class WebsocketLIstener {
    final Logger logger = LoggerFactory.getLogger(WebsocketLIstener.class);
    public final BlockingQueue<String> q = new SynchronousQueue<String>();
    public void consume(String message){
        try {
            logger.info("listener received message {}", message);
            q.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public BlockingQueue<String> getQ() {
        return q;
    }
}
