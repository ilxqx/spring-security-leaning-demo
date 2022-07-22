package com.ilxqx.springsecuritylearning.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis配置
 *
 * @author venus
 * @version 1.0.0
 * @since 2022/7/9 23:07
 */
@Configuration(proxyBeanMethods = false)
public class RedisConfig implements BeanClassLoaderAware {

    private ClassLoader classLoader;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        RedisSerializer<Object> objectRedisSerializer = RedisSerializer.java(this.classLoader);
        redisTemplate.setValueSerializer(objectRedisSerializer);
        redisTemplate.setHashValueSerializer(objectRedisSerializer);

        return redisTemplate;
    }

    /**
     * Callback that supplies the bean {@link ClassLoader class loader} to
     * a bean instance.
     * <p>Invoked <i>after</i> the population of normal bean properties but
     * <i>before</i> an initialization callback such as
     * {@link InitializingBean InitializingBean's}
     * {@link InitializingBean#afterPropertiesSet()}
     * method or a custom init-method.
     *
     * @param classLoader the owning class loader
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
