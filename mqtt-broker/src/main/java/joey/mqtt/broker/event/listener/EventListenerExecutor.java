package joey.mqtt.broker.event.listener;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import joey.mqtt.broker.event.message.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;

import static joey.mqtt.broker.Constants.*;

/**
 * 事件触发执行器
 *
 * @author Joey
 * @date 2019/9/8
 */
@Slf4j
public class EventListenerExecutor {
    private final List<IEventListener> eventListenerList;

    private final ExecutorService executorService;

    public EventListenerExecutor(List<IEventListener> eventListenerList) {
        this.eventListenerList = eventListenerList;

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNamePrefix(EVENT_LISTENER_EXECUTOR_THREAD_NAME_PRE).build();

        this.executorService = new ThreadPoolExecutor(EVENT_LISTENER_EXECUTOR_THREAD_CORE_SIZE, EVENT_LISTENER_EXECUTOR_THREAD_MAX_SIZE,
                                                      0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(EVENT_LISTENER_EXECUTOR_THREAD_QUEUE_SIZE),
                                                     threadFactory, new RejectedExecutionHandler() {
                                                                        @Override
                                                                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                                                                            if (r instanceof EventTask) {
                                                                                EventTask task = (EventTask)r;
                                                                                log.warn("EventListenerExecutor-execute reject execution. messageInfo={},type={}", task.message.info(), task.type);
                                                                            }
                                                                        }
        });
    }

    public void execute(EventMessage eventMessage, IEventListener.Type eventType) {
        eventListenerList.forEach(eventListener -> {
            try {
                executorService.execute(new EventTask(eventListener, eventMessage, eventType));

            } catch (Throwable ex) {
                log.error("EventListenerExecutor-execute error.", ex);
            }
        });
    }

    public void close() {
        executorService.shutdown();
    }

    private class EventTask implements Runnable {
        private final IEventListener listener;

        private final EventMessage message;

        private final IEventListener.Type type;

        public EventTask(IEventListener listener, EventMessage message, IEventListener.Type type) {
            this.listener = listener;
            this.message = message;
            this.type = type;
        }

        @Override
        public void run() {
            switch (type) {
                case CONNECT:
                    listener.onConnect((ConnectEventMessage) message);
                    break;

                case DISCONNECT:
                    listener.onDisconnect((DisconnectEventMessage) message);
                    break;

                case CONNECTION_LOST:
                    listener.onConnectionLost((ConnectionLostEventMessage) message);
                    break;

                case PUBLISH:
                    listener.onPublish((PublishEventMessage) message);
                    break;

                case PUB_ACK:
                    listener.onPubAck((PubAckEventMessage) message);
                    break;

                case PUB_REC:
                    listener.onPubRec((PubRecEventMessage) message);
                    break;

                case PUB_REL:
                    listener.onPubRel((PubRelEventMessage) message);
                    break;

                case PUB_COMP:
                    listener.onPubComp((PubCompEventMessage) message);
                    break;

                case SUBSCRIBE:
                    listener.onSubscribe((SubscribeEventMessage) message);
                    break;

                case UNSUBSCRIBE:
                    listener.onUnsubscribe((UnsubscribeEventMessage) message);
                    break;

                case PING:
                    listener.onPing((PingEventMessage) message);
                default:
                    break;
            }
        }
    }
}
