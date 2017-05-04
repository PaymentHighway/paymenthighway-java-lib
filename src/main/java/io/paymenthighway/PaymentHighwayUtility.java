package io.paymenthighway;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * General helper methods.
 */
public class PaymentHighwayUtility {

  /**
   * Cryptographically strong pseudo random number generator.
   *
   * @return String UUID.
   */
  public static String createRequestId() {
    return java.util.UUID.randomUUID().toString();
  }

  /**
   * Request timestamp in ISO 8601 combined date and time in UTC.
   *
   * @return String timestamp Example: 2014-09-18T10:32:59Z
   */
  public static String getUtcTimestamp() {
    SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    timeFormatter.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
    return timeFormatter.format(new Date());
  }

  /**
   * Convert map to list of name value pairs.
   * @param map Map of name value pairs
   * @return List
   */
  public static List<NameValuePair> mapToList(final Map<String,String> map) {
    List<NameValuePair> pairs=new ArrayList<>();
    for (Map.Entry<String,String> entry : map.entrySet()) {
      pairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
    }
    return pairs;
  }

  /**
   * Read properties from file
   *
   * @return Properties
   * @throws IOException Exception
   */
  public static Properties getProperties() throws IOException {

    Properties props = new Properties();
    BufferedReader br = null;
    String propFilename = "config.properties";

    try {
      br = new BufferedReader(new FileReader(propFilename));
    } catch (FileNotFoundException ex) {
      // Failed to find the file,
      // so lets try to find the file from resources
      InputStream file = ClassLoader.getSystemResourceAsStream(propFilename);
      try {
        br = new BufferedReader(new InputStreamReader(file, "UTF-8"));
      } catch (Exception e) {
        System.err.println("Could not find property File.");
        e.printStackTrace();
      }
    }

    if (br != null) {
      try {
        props.load(br);
      } catch (IOException ex) {
        System.err.println("Property file reading error.");
        ex.printStackTrace();
      }
    }
    return props;
  }
}
