package joey.mqtt.broker.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 自定义配置
 * 用户可根据自身需求继承此类并自定义任何配置
 *
 * @author Joey
 * @date 2019/7/18
 */
@Getter
@Setter
@ToString
public class CustomConfig {
    /**
     * hazelcast配置
     */
    private HazelcastConfig hazelcastConfig = new HazelcastConfig();

    /**
     * redis配置
     */
    private RedisConfig redisConfig = new RedisConfig();
}
