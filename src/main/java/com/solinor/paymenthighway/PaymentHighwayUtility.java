/**
 * 
 */
package com.solinor.paymenthighway;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.SimpleTimeZone;

import org.apache.http.NameValuePair;

/**
 * General helper methods.
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class PaymentHighwayUtility {

	/**
	 * Cryptographically strong pseudo random number generator.
	 * @return String UUID.
	 */
	public static String createRequestId() {
		return java.util.UUID.randomUUID().toString();
    }
	
	/**
	 * Request timestamp in ISO 8601 combined date and time in UTC.
	 * @return String timestamp Example: 2014-09-18T10:32:59Z
	 */
	public static String getUtcTimestamp() {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        timeFormatter.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));
        return timeFormatter.format(new Date());
    }
	
	/**
	 * Sort alphabetically per key
	 * 
	 * @param List<NameValuePair> nameValuePairs
	 * @return List<NameValuePair> sorted list
	 */
	public static List<NameValuePair> sortParameters(List<NameValuePair> nameValuePairs) {
		Comparator<NameValuePair> comp = new Comparator<NameValuePair>() {
			@Override
			public int compare(NameValuePair p1, NameValuePair p2) {
				return p1.getName().compareTo(p2.getName());
			}
		};
		Collections.sort(nameValuePairs, comp);
		return nameValuePairs;
	}

	/**
	 * Signature is formed from parameters that start with "sph-" Therefore we
	 * remove other parameters from the signature param set.
	 * 
	 * @param List<NameValuePair> map that may include all params           
	 * @return List<NameValuePair> with only params starting "sph-"
	 */
	public static List<NameValuePair> parseSphParameters(List<NameValuePair> map) {
		for (Iterator<NameValuePair> it = map.iterator(); it.hasNext();) {
			NameValuePair entry = it.next();
			if (!entry.getName().toLowerCase().startsWith("sph-")) {
				it.remove();
			}
		}
		return map;
	}
	
	/**
	 * Read properties from file
	 * 
	 * @return Properties
	 * @throws IOException
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
