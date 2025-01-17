package joey.mqtt.broker;

/**
 * 通用常量类
 * @author Joey
 * @date 2019/7/18
 */
public class Constants {
    public enum ServerProtocolType {
        TCP("tcp"),
        WEB_SOCKET("webSocket"),
        ;

        public final String name;

        ServerProtocolType(String name) {
            this.name = name;
        }
    }

    public static final String MQTT_CONFIG = "mqtt.conf";

    public static final String MQTT_CONFIG_PROPS_PRE = "mqtt";

    public static final String MQTT_SUB_PROTOCOL_CSV_LIST = "mqtt, mqttv3.1, mqttv3.1.1";

    public static final Integer INT_ZERO = 0;

    public static final Integer INT_ONE = 1;

    public static final Long LONG_ZERO = 0L;

    public static final Long LONG_ONE = 1L;

    /**
     * netty handler名称常量
     */
    public static final String HANDLER_IDLE_STATE = "idleStateHandler";

    public static final String HANDLER_MQTT_ENCODER = "mqttEncoderHandler";

    public static final String HANDLER_MQTT_DECODER = "mqttDecoderHandler";

    public static final String HANDLER_MQTT_MAIN = "mqttMainHandler";

    /**
     * topic token常量
     */
    public static final String TOKEN_ROOT = "joRoot";

    public static final String TOKEN_MULTI = "#";

    public static final String TOKEN_SINGLE = "+";

    /**
     * 使用hazelcast作为集群通信时的topic
     */
    public static final String HAZELCAST_INNER_TRAFFIC_TOPIC = "joMqtt";

    /**
     * redis相关基本设置
     */
    public static final int REDIS_EACH_SCAN_COUNT = 500;

    public static final String REDIS_KEY_PRE = "joMqtt:";

    public static final String REDIS_MSG_ID_KEY_PRE = REDIS_KEY_PRE + "msg:";

    public static final String REDIS_MSG_ID_FIELD = "id";

    public static final String REDIS_MSG_RETAIN_KEY = REDIS_KEY_PRE + "msgRetain";

    public static final String REDIS_MSG_DUP_PUB_KEY_PRE = REDIS_KEY_PRE + "msgDupPub:";

    public static final String REDIS_MSG_DUP_PUB_REL_KEY_PRE = REDIS_KEY_PRE + "msgDupPubRel:";

    /**
     * event listener executor 线程池基本属性设置
     */
    public static final String EVENT_LISTENER_EXECUTOR_THREAD_NAME_PRE = "eventListenerExecutor-pool-";

    public static final int EVENT_LISTENER_EXECUTOR_THREAD_CORE_SIZE = 10;

    public static final int EVENT_LISTENER_EXECUTOR_THREAD_MAX_SIZE = 200;

    public static final int EVENT_LISTENER_EXECUTOR_THREAD_QUEUE_SIZE = 1024;
}
