package joey.mqtt.broker.provider.adapter;

import cn.hutool.core.collection.CollUtil;
import joey.mqtt.broker.auth.AuthUser;
import joey.mqtt.broker.auth.IAuth;
import joey.mqtt.broker.auth.impl.DefaultAuthImpl;
import joey.mqtt.broker.config.CustomConfig;
import joey.mqtt.broker.event.listener.IEventListener;
import joey.mqtt.broker.event.listener.adapter.EventListenerAdapter;
import joey.mqtt.broker.inner.IInnerTraffic;
import joey.mqtt.broker.inner.InnerPublishEventProcessor;
import joey.mqtt.broker.inner.hazelcast.HazelcastInnerTraffic;
import joey.mqtt.broker.provider.IExtendProvider;
import joey.mqtt.broker.store.*;
import joey.mqtt.broker.store.memory.*;

import java.util.List;

/**
 * 默认扩展实现适配器
 *
 * @author Joey
 * @date 2019/7/23
 */
public class ExtendProviderAdapter implements IExtendProvider {
    protected CustomConfig customConfig;

    /**
     * 默认适配器 反射调用此构造方法
     * @param customConfig
     */
    public ExtendProviderAdapter(CustomConfig customConfig) {
        this.customConfig = customConfig;
    }

    @Override
    public IMessageIdStore initMessageIdStore() {
        return new MemoryMessageIdStore();
    }

    @Override
    public ISessionStore initSessionStore() {
        return new MemorySessionStore(customConfig);
    }

    @Override
    public ISubscriptionStore initSubscriptionStore(ISessionStore sessionStore) {
        return new MemorySubscriptionStore(customConfig);
    }

    @Override
    public IRetainMessageStore initRetainMessageStore() {
        return new MemoryRetainMessageStore(customConfig);
    }

    @Override
    public IDupPubMessageStore initDupPubMessageStore() {
        return new MemoryDupPubMessageStore(customConfig);
    }

    @Override
    public IDupPubRelMessageStore initDupPubRelMessageStore() {
        return new MemoryDupPubRelMessageStore(customConfig);
    }

    @Override
    public IAuth initAuthManager(List<AuthUser> userList) {
        return new DefaultAuthImpl(userList, customConfig);
    }

    @Override
    public IInnerTraffic initInnerTraffic(InnerPublishEventProcessor innerPublishEventProcessor) {
        return new HazelcastInnerTraffic(innerPublishEventProcessor, customConfig);
    }

    @Override
    public List<IEventListener> initEventListeners() {
        return CollUtil.newArrayList(new EventListenerAdapter());
    }
}
