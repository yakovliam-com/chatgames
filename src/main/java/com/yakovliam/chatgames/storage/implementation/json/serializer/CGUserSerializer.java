package com.yakovliam.chatgames.storage.implementation.json.serializer;

import com.yakovliam.chatgames.user.CGUser;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.UUID;

public class CGUserSerializer implements TypeSerializer<CGUser> {

    private static CGUserSerializer instance;

    public static CGUserSerializer getInstance() {
        if (instance == null) {
            instance = new CGUserSerializer();
        }

        return instance;
    }

    /**
     * Deserialize an object (of the correct type) from the given configuration
     * node.
     *
     * @param type the type of return value required
     * @param node the node containing serialized data
     * @return an object
     * @since 4.0.0
     */
    @Override
    public CGUser deserialize(Type type, ConfigurationNode node) {
        UUID uuid = UUID.fromString(Objects.requireNonNull(node.node("uuid").getString()));
        int wins = node.node("wins").getInt();
        return new CGUser(uuid, wins);
    }

    /**
     * Serialize an object to the given configuration node.
     *
     * @param type the type of the input object
     * @param obj  the object to be serialized
     * @param node the node to write to
     * @throws SerializationException if the object cannot be serialized
     * @since 4.0.0
     */
    @Override
    public void serialize(Type type, @Nullable CGUser obj, ConfigurationNode node) throws SerializationException {
        node.node("uuid").set(obj.getUuid());
        node.node("wins").set(obj.getWins());
    }
}
