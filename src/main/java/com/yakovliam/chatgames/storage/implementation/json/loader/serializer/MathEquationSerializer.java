package com.yakovliam.chatgames.storage.implementation.json.loader.serializer;

import com.yakovliam.chatgames.storage.implementation.json.loader.MathEquation;
import com.yakovliam.chatgames.storage.implementation.json.serializer.CGUserSerializer;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class MathEquationSerializer implements TypeSerializer<MathEquation> {


    private static MathEquationSerializer instance;

    public static MathEquationSerializer getInstance() {
        if (instance == null) {
            instance = new MathEquationSerializer();
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
     * @throws SerializationException if the presented data is invalid
     * @since 4.0.0
     */
    @Override
    public MathEquation deserialize(Type type, ConfigurationNode node) throws SerializationException {
        return new MathEquation(node.node("supplied").getString(), node.node("answer").getString());
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
    public void serialize(Type type, @Nullable MathEquation obj, ConfigurationNode node) throws SerializationException {
    }
}
