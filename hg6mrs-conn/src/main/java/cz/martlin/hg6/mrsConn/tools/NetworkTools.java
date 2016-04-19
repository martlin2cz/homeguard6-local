package cz.martlin.hg6.mrsConn.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class NetworkTools {

	private static final String ENCODING = "UTF-8";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	public URL createGetUrl(String base, String path, Object[] params)
			throws UnsupportedEncodingException, MalformedURLException {

		StringBuilder stb = new StringBuilder();

		stb.append(base);
		stb.append(path);

		appendParams(stb, params);

		URL url = new URL(stb.toString());
		return url;
	}

	private void appendParams(StringBuilder stb, Object[] params) throws IllegalArgumentException {
		List<Object> list = Arrays.asList(params);
		Iterator<Object> iter = list.iterator();

		if (iter.hasNext()) {
			stb.append("?");
		}
		try {
			while (iter.hasNext()) {
				Object key = iter.next();
				Object value = iter.next();

				if (value == null) {
					continue;
				}

				String keyStr = key.toString();
				String valStr = URLEncoder.encode(value.toString(), ENCODING);

				stb.append(keyStr);
				stb.append("=");
				stb.append(valStr);

				if (iter.hasNext()) {
					stb.append("&");
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot make params from " + list, e);
		}
	}

	public String connectAndGetResponse(URL url) throws IOException {
		InputStream ins = null;
		try {
			URLConnection conn = url.openConnection();
			ins = conn.getInputStream();
			return IOUtils.toString(ins);
		} catch (IOException e) {
			throw new IOException("Cannot send request/get response", e);
		} finally {
			IOUtils.closeQuietly(ins);
		}
	}

	public String formatCalendar(Calendar calendar) {
		if (calendar == null) {
			return null;
		} else {
			String date = DATE_FORMAT.format(calendar.getTime());
			try {
				return URLEncoder.encode(date, ENCODING);
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException("Unsupported encoding", e);
			}
		}
	}

	public Calendar parseCalendar(String string) {
		if (string == null || string.isEmpty() || "null".equals(string)) {
			return null;
		}

		try {
			String decoded = URLDecoder.decode(string, ENCODING);
			Date date = DATE_FORMAT.parse(decoded);

			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			return cal;
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Cannot parse date " + string + " over the pattern " + DATE_FORMAT.toPattern(), e);
		}

	}

}
