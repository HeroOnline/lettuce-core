/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lambdaworks.redis.api;

import java.util.List;
import java.util.Map;

import com.lambdaworks.redis.*;
import com.lambdaworks.redis.XReadArgs.Consumer;
import com.lambdaworks.redis.XReadArgs.Stream;

/**
 * ${intent} for Streams.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 * @author Mark Paluch
 * @since 4.5
 */
public interface RedisStreamCommands<K, V> {

    /**
     * Acknowledge one or more messages as processed.
     *
     * @param key the stream key.
     * @param group name of the consumer group.
     * @param messageIds message Ids to acknowledge.
     * @return simple-reply the lenght of acknowledged messages.
     */
    Long xack(K key, String group, String... messageIds);

    /**
     * Append a message to the stream {@code key}.
     *
     * @param key the stream key.
     * @param body message body.
     * @return simple-reply the message Id.
     */
    String xadd(K key, Map<K, V> body);

    /**
     * Append a message to the stream {@code key}.
     *
     * @param key the stream key.
     * @param args
     * @param body message body.
     * @return simple-reply the message Id.
     */
    String xadd(K key, XAddArgs args, Map<K, V> body);

    /**
     * Append a message to the stream {@code key}.
     *
     * @param key the stream key.
     * @param keysAndValues message body.
     * @return simple-reply the message Id.
     */
    String xadd(K key, Object... keysAndValues);

    /**
     * Append a message to the stream {@code key}.
     *
     * @param key the stream key.
     * @param args
     * @param keysAndValues message body.
     * @return simple-reply the message Id.
     */
    String xadd(K key, XAddArgs args, Object... keysAndValues);

    /**
     * Gets ownership of one or multiple messages in the Pending Entries List of a given stream consumer group.
     *
     * @param key the stream key.
     * @param consumer consumer identified by group name and consumer key.
     * @param messageIds message Ids to claim.
     * @param args
     * @return simple-reply the {@link StreamMessage}
     */
    StreamMessage<K, V> xclaim(K key, Consumer consumer, XClaimArgs args, String... messageIds);

    /**
     * Create a consumer group.
     *
     * @param key the stream key.
     * @param group name of the consumer group.
     * @param offset read offset or {@literal $}.
     * @return simple-reply {@literal true} if successful.
     */
    Boolean xgroupCreate(K key, String group, String offset);

    /**
     * Delete a consumer from a consumer group.
     *
     * @param key the stream key.
     * @param consumer consumer identified by group name and consumer key.
     * @return simple-reply the number of pending messages
     */
    Boolean xgroupDelconsumer(K key, Consumer consumer);

    /**
     * Set the current {@code group} id.
     *
     * @param key the stream key.
     * @param group name of the consumer group.
     * @param offset read offset or {@literal $}.
     * @return simple-reply the lenght of the stream.
     */
    Boolean xgroupSetid(K key, String group, String offset);

    /**
     * Get the length of a steam.
     *
     * @param key the stream key.
     * @return simple-reply the lenght of the stream.
     */
    Long xlen(K key);

    /**
     * Read pending messages from a stream within a specific {@link Range}.
     *
     * @param key the stream key.
     * @param group name of the consumer group.
     * @return List&lt;PendingEntry&gt; array-reply list pending entries.
     */
    List<PendingEntry> xpending(K key, String group);

    /**
     * Read pending messages from a stream within a specific {@link Range}.
     *
     * @param key the stream key.
     * @param consumer consumer identified by group name and consumer key.
     * @return List&lt;PendingEntry&gt; array-reply list pending entries.
     */
    List<PendingEntry> xpending(K key, Consumer consumer);

    /**
     * Read pending messages from a stream within a specific {@link Range}.
     *
     * @param key the stream key.
     * @param group name of the consumer group.
     * @param range must not be {@literal null}.
     * @param limit must not be {@literal null}.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xpending(K key, String group, Range<String> range, Limit limit);

    /**
     * Read pending messages from a stream within a specific {@link Range}.
     *
     * @param key the stream key.
     * @param consumer consumer identified by group name and consumer key.
     * @param range must not be {@literal null}.
     * @param limit must not be {@literal null}.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xpending(K key, Consumer consumer, Range<String> range, Limit limit);

    /**
     * Read messages from a stream within a specific {@link Range}.
     *
     * @param key the stream key.
     * @param range must not be {@literal null}.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xrange(K key, Range<String> range);

    /**
     * Read messages from a stream within a specific {@link Range} applying a {@link Limit}.
     *
     * @param key the stream key.
     * @param range must not be {@literal null}.
     * @param limit must not be {@literal null}.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xrange(K key, Range<String> range, Limit limit);

    /**
     * Read messages from a stream within a specific {@link Range} in reverse order.
     *
     * @param key the stream key.
     * @param range must not be {@literal null}.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xrevrange(K key, Range<String> range);

    /**
     * Read messages from a stream within a specific {@link Range} applying a {@link Limit} in reverse order.
     *
     * @param key the stream key.
     * @param range must not be {@literal null}.
     * @param limit must not be {@literal null}.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xrevrange(K key, Range<String> range, Limit limit);

    /**
     * Read messages from one or more {@link Stream}s.
     *
     * @param streams the streams to read from.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xread(Stream<K>... streams);

    /**
     * Read messages from one or more {@link Stream}s.
     *
     * @param args read arguments.
     * @param streams the streams to read from.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xread(XReadArgs args, Stream<K>... streams);

    /**
     * Read messages from one or more {@link Stream}s using a consumer group.
     *
     * @param consumer consumer/group.
     * @param streams the streams to read from.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xreadgroup(Consumer consumer, Stream<K>... streams);

    /**
     * Read messages from one or more {@link Stream}s using a consumer group.
     *
     * @param consumer consumer/group.
     * @param args read arguments.
     * @param streams the streams to read from.
     * @return List&lt;StreamMessage&gt; array-reply list with members of the resulting stream.
     */
    List<StreamMessage<K, V>> xreadgroup(Consumer consumer, XReadArgs args, Stream<K>... streams);
}
