package de.benjaminborbe.httpdownloader.core.util;

import de.benjaminborbe.httpdownloader.api.HttpHeader;
import de.benjaminborbe.tools.stream.StreamUtil;
import de.benjaminborbe.tools.util.Base64Util;
import de.benjaminborbe.tools.util.Duration;
import de.benjaminborbe.tools.util.DurationUtil;
import de.benjaminborbe.tools.util.Encoding;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Singleton
public class HttpDownloaderImpl implements HttpDownloader {

	private final Logger logger;

	private final StreamUtil streamUtil;

	private final DurationUtil durationUtil;

	private final Base64Util base64Util;

	@Inject
	public HttpDownloaderImpl(final Logger logger, final StreamUtil streamUtil, final DurationUtil durationUtil, final Base64Util base64Util) {
		this.logger = logger;
		this.streamUtil = streamUtil;
		this.durationUtil = durationUtil;
		this.base64Util = base64Util;
	}

	protected HttpDownloadResult doDownloadUrl(
		final URL url,
		final Integer timeout,
		final String username,
		final String password,
		final HttpHeader httpHeader
	) throws IOException {
		logger.trace("downloadUrl started");
		final Duration duration = durationUtil.getDuration();
		InputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		try {
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			if (username != null && password != null || url.getUserInfo() != null) {
				final String stringUserIdPassword = url.getUserInfo() != null ? url.getUserInfo() : username + ":" + password;
				final String base64UserIdPassword = base64Util.encode(stringUserIdPassword.getBytes("ASCII"));
				connection.setRequestProperty("Authorization", "Basic " + base64UserIdPassword);
			}
			// connection.setConnectTimeout(timeout);
			// connection.setReadTimeout(timeout);
			connection.setRequestProperty("User-Agent", USERAGENT);
			if (httpHeader != null) {
				for (final String httpHeaderField : httpHeader.getKeys()) {
					connection.setRequestProperty(httpHeaderField, httpHeader.getValue(httpHeaderField));
				}
			}
			connection.connect();

			final int responseCode;
			try {
				responseCode = connection.getResponseCode();
			} catch (RuntimeException e) {
				final HttpDownloadResult httpDownloadResult = new HttpDownloadResultImpl(url, duration.getTime(), null, null, null, null, -1);
				logger.trace("downloadUrl " + url + " failed");
				return httpDownloadResult;
			}
			try {
				final String contentType = connection.getContentType();
				final Encoding contentEncoding = extractEncoding(connection, contentType);
				final Map<String, List<String>> headers = connection.getHeaderFields();
				try {
					inputStream = connection.getInputStream();
					outputStream = new ByteArrayOutputStream();
					streamUtil.copy(inputStream, outputStream);
					final HttpDownloadResult httpDownloadResult = new HttpDownloadResultImpl(url, duration.getTime(), outputStream.toByteArray(), contentType,
						contentEncoding, headers, responseCode);
					logger.trace("downloadUrl " + url + " finished");
					return httpDownloadResult;
				} catch (FileNotFoundException e) {
					final HttpDownloadResult httpDownloadResult = new HttpDownloadResultImpl(url, duration.getTime(), null, contentType, contentEncoding, headers,
						responseCode);
					logger.trace("downloadUrl " + url + " failed");
					return httpDownloadResult;
				}
			} catch (IOException e) {
				final HttpDownloadResult httpDownloadResult = new HttpDownloadResultImpl(url, duration.getTime(), null, null, null, null, responseCode);
				logger.trace("downloadUrl " + url + " failed");
				return httpDownloadResult;
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
			if (outputStream != null)
				outputStream.close();
		}
	}

	private Encoding extractEncoding(final URLConnection connection, final String contentType) {
		{
			if (connection.getContentEncoding() != null) {
				return new Encoding(connection.getContentEncoding());
			}
		}
		{
			if (contentType != null) {
				final String s = "charset=";
				final int charsetPos = contentType.indexOf(s);
				if (charsetPos != -1) {
					return new Encoding(contentType.substring(charsetPos + s.length()).trim());
				}
			}
		}
		return null;
	}

	@Override
	public HttpDownloadResult getUrlUnsecure(
		final URL url,
		final Integer timeout,
		final String username,
		final String password,
		final HttpHeader httpHeader
	) throws HttpDownloaderException {
		final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManagerAllowAll()};
		final SSLSocketFactory orgSSLSocketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
		final HostnameVerifier orgHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
		try {
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			final HostnameVerifier hv = new HostnameVerifierAllowAll();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			return getUrlSecure(url, timeout, username, password, httpHeader);
		} catch (final NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException", e);
			throw new HttpDownloaderException("NoSuchAlgorithmException", e);
		} catch (final KeyManagementException e) {
			logger.error("KeyManagementException", e);
			throw new HttpDownloaderException("KeyManagementException", e);
		} finally {
			HttpsURLConnection.setDefaultHostnameVerifier(orgHostnameVerifier);
			HttpsURLConnection.setDefaultSSLSocketFactory(orgSSLSocketFactory);
		}
	}

	@Override
	public HttpDownloadResult postUrlSecure(
		final URL url,
		final Integer timeout,
		final Map<String, String> parameter,
		final HttpHeader httpHeader
	) throws HttpDownloaderException {
		try {
			final Duration duration = durationUtil.getDuration();

			// Construct data
			final StringWriter data = new StringWriter();
			boolean first = true;
			for (final Entry<String, String> e : parameter.entrySet()) {
				if (!first) {
					data.append("&");
				} else {
					first = false;
				}
				data.append(URLEncoder.encode(e.getKey(), "UTF-8"));
				data.append("=");
				data.append(URLEncoder.encode(e.getValue(), "UTF-8"));
			}

			// Send data
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestProperty("User-Agent", USERAGENT);
			if (httpHeader != null) {
				for (final String httpHeaderField : httpHeader.getKeys()) {
					connection.setRequestProperty(httpHeaderField, httpHeader.getValue(httpHeaderField));
				}
			}
			connection.setDoOutput(true);
			connection.connect();
			final OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(data.toString());
			wr.flush();
			final InputStream inputStream = connection.getInputStream();
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			streamUtil.copy(inputStream, outputStream);
			final byte[] content = outputStream.toByteArray();
			final String contentType = connection.getContentType();
			final Map<String, List<String>> headers = connection.getHeaderFields();
			final Encoding contentEncoding = extractEncoding(connection, contentType);
			final int responseCode = connection.getResponseCode();
			final HttpDownloadResult httpDownloadResult = new HttpDownloadResultImpl(url, duration.getTime(), content, contentType, contentEncoding, headers,
				responseCode);
			logger.trace("downloadUrl finished");
			return httpDownloadResult;
		} catch (final IOException e) {
			logger.error(e.getClass().getSimpleName(), e);
			throw new HttpDownloaderException(e.getClass().getSimpleName(), e);
		}
	}

	@Override
	public HttpDownloadResult postUrlUnsecure(
		final URL url, final Integer timeOut, final Map<String, String> data, final HttpHeader httpHeader
	) throws HttpDownloaderException {
		final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManagerAllowAll()};
		final SSLSocketFactory orgSSLSocketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
		final HostnameVerifier orgHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
		try {
			final SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			final HostnameVerifier hv = new HostnameVerifierAllowAll();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			return postUrlSecure(url, timeOut, data, httpHeader);
		} catch (final NoSuchAlgorithmException e) {
			logger.error("NoSuchAlgorithmException", e);
			throw new HttpDownloaderException("NoSuchAlgorithmException", e);
		} catch (final KeyManagementException e) {
			logger.error("KeyManagementException", e);
			throw new HttpDownloaderException("KeyManagementException", e);
		} finally {
			HttpsURLConnection.setDefaultHostnameVerifier(orgHostnameVerifier);
			HttpsURLConnection.setDefaultSSLSocketFactory(orgSSLSocketFactory);
		}
	}

	protected String buildCookieString(final Map<String, String> cookies) {
		final StringWriter result = new StringWriter();
		boolean first = true;
		final List<String> keys = new ArrayList<String>(cookies.keySet());
		Collections.sort(keys);
		for (final String key : keys) {
			if (first) {
				first = false;
			} else {
				result.append("; ");
			}
			result.append(key);
			result.append("=");
			result.append(cookies.get(key));
		}
		return result.toString();
	}

	@Override
	public HttpDownloadResult getUrlSecure(final URL url, final Integer timeout, final String username, final String password, final HttpHeader httpHeader)
		throws HttpDownloaderException {
		try {
			return doDownloadUrl(url, timeout, username, password, httpHeader);
		} catch (final IOException e) {
			throw new HttpDownloaderException(e.getClass().getSimpleName() + " for url " + url, e);
		}
	}

	private final class HostnameVerifierAllowAll implements HostnameVerifier {

		@Override
		public boolean verify(final String arg0, final SSLSession arg1) {
			return true;
		}
	}

	private final class X509TrustManagerAllowAll implements X509TrustManager {

		@Override
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
		}

		@Override
		public void checkServerTrusted(final java.security.cert.X509Certificate[] certs, final String authType) {
		}
	}
}
