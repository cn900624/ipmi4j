/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.ipmi.protocol.packet.common;

import com.google.common.primitives.UnsignedBytes;
import java.nio.ByteBuffer;
import javax.annotation.Nonnull;

/**
 *
 * @author shevek
 */
public class Code {

    public static interface Wrapper {

        public byte getCode();
    }

    @Nonnull
    public static <T extends Enum<T> & Code.Wrapper> T fromByte(@Nonnull Class<T> type, @Nonnull byte code) {
        for (T value : type.getEnumConstants())
            if (value.getCode() == code)
                return value;
        throw new IllegalArgumentException("Unknown status code 0x" + UnsignedBytes.toString(code, 16));
    }

    @Nonnull
    public static <T extends Enum<T> & Code.Wrapper> T fromBuffer(@Nonnull Class<T> type, @Nonnull ByteBuffer buffer) {
        return fromByte(type, buffer.get());
    }
}