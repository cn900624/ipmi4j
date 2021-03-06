/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.anarres.ipmi.protocol.packet.common;

import com.google.common.primitives.UnsignedBytes;
import java.nio.ByteBuffer;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * A symbolic name for a byte.
 *
 * @author shevek
 */
public class Code {

    /** An interface for enums which wrap Codes. */
    public static interface Wrapper {

        public byte getCode();
    }

    /** An interface for enums which wrap Codes with a description. */
    public static interface DescriptiveWrapper extends Wrapper {

        @Nonnull
        public String getDescription();
    }

    public static class Utils {

        @Nonnull
        public static <T extends Enum<T> & Code.DescriptiveWrapper> String toString(@Nonnull T value) {
            return value.name() + "(0x" + UnsignedBytes.toString(value.getCode(), 16) + "): " + value.getDescription();
        }
    }

    @Nonnull
    public static <T extends Enum<T> & Code.Wrapper> T fromByte(@Nonnull Class<T> type, byte code) {
        for (T value : type.getEnumConstants())
            if (value.getCode() == code)
                return value;
        throw new IllegalArgumentException("Unknown " + type.getSimpleName() + " code 0x" + UnsignedBytes.toString(code, 16));
    }

    @Nonnull
    public static <T extends Enum<T> & Code.Wrapper> T fromInt(@Nonnull Class<T> type, @Nonnegative int code) {
        return fromByte(type, UnsignedBytes.checkedCast(code));
    }

    @Nonnull
    public static <T extends Enum<T> & Code.Wrapper> T fromBuffer(@Nonnull Class<T> type, @Nonnull ByteBuffer buffer) {
        return fromByte(type, buffer.get());
    }
}
