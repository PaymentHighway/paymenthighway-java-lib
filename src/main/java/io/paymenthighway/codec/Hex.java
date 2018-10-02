package io.paymenthighway.codec;

/**
 * Replaces javax.xml.bind.DatatypeConverter for Java 9+ without need for Apache Common Codec's
 */
public class Hex {

  public static final char[] HEX_CODE_LOWER = "0123456789ABCDEF".toLowerCase().toCharArray();

  public static final char[] HEX_CODE_UPPER = "0123456789ABCDEF".toUpperCase().toCharArray();

  public static String printHexBinary(final byte[] bytes, final char[] hexCode) {
    char[] hexChars = new char[bytes.length * 2];

    int i = 0;

    for (byte b : bytes) {
      hexChars[i++] = hexCode[(b & 0xF0) >>> 4];
      hexChars[i++] = hexCode[(b & 0x0F)];
    }

    return new String(hexChars);
  }
}
