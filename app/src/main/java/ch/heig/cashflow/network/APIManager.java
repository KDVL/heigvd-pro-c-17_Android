/**
 * The API manager, handle requests
 *
 * @author Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.TokenHolder;

public class APIManager extends AsyncTask<String, Integer, APIManager.Result> {

    private DownloadCallback<Result> mCallback;
    private boolean authNeeded;
    private METHOD method;
    private String postParams;

    /**
     * The APIManager constructor
     *
     * @param callback   The callback object
     * @param authNeeded True if authentication is need with this endpoint
     * @param method     The HTTP method
     */
    public APIManager(DownloadCallback<APIManager.Result> callback, boolean authNeeded, METHOD method) {
        mCallback = callback;
        this.authNeeded = authNeeded;
        this.method = method;

        //Used with https fragment_category_old server (localhost)
        trustEveryone();
    }

    /**
     * Set a JSON String to post
     *
     * @param postParams the string
     */
    public void setPostParams(String postParams) {
        this.postParams = postParams;
    }

    /**
     * Cancel background network operation if we do not have network connectivity
     */
    @Override
    protected void onPreExecute() {
        if (mCallback != null) {
            Context c = mCallback.getContext();

            try {
                ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo == null || !networkInfo.isConnected() ||
                        (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                    // If no connectivity, cancel task and update Callback with null data.
                    mCallback.updateFromDownload(null);
                    cancel(true);
                }
            } catch (Exception e) {
                mCallback.updateFromDownload(null);
                cancel(true);
            }

        }
    }

    /**
     * Defines work to perform on the background thread
     *
     * @param urls The urls
     * @return Result The obtained result
     */
    @Override
    protected APIManager.Result doInBackground(String... urls) {
        Result result = null;
        String urlString = urls[0];
        if (!isCancelled() && urls != null && urls.length > 0) {

            try {
                URL url = new URL(urlString);
                result = downloadUrl(url);
            } catch (Exception e) {
                result = new Result(e);
                result.responseCode = -1;
                System.out.println(urlString);
            }
        }
        result.tag = urlString;
        return result;
    }

    /**
     * Updates the DownloadCallback with the result
     *
     * @param result The result
     */
    @Override
    protected void onPostExecute(Result result) {
        if (mCallback == null) return;
        mCallback.updateFromDownload(result);
    }

    /**
     * Override to add special behavior for cancelled AsyncTask
     *
     * @param result The result
     */
    @Override
    protected void onCancelled(Result result) {
    }

    /**
     * Given a URL, sets up a connection and gets the HTTP response body from the server
     *
     * @param url The specific url
     * @return Result The obtained result
     * @throws IOException
     */
    private Result downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
        int responseCode = -1;

        try {
            connection = (HttpsURLConnection) url.openConnection();

            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod(method.toString());
            connection.addRequestProperty("Content-Type", "application/json; utf-8");
            connection.addRequestProperty("Accept", "application/json");

            if (authNeeded) {
                String token = TokenHolder.getToken(mCallback.getContext());

                if (token != null) //
                    connection.addRequestProperty("Authorization", "Bearer " + TokenHolder.getToken(mCallback.getContext()));

                //System.out.println(token);
            }

            if (postParams != null && !postParams.equals("")) {
                // put JSON content
                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
            } else {
                connection.connect();
            }

            publishProgress(DownloadCallback.Progress.CONNECT_SUCCESS);
            responseCode = connection.getResponseCode();

            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();
            publishProgress(DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS, 0);
            if (stream != null) {
                // Converts Stream to String with max
                result = readStream(stream, 1000000);
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        Result r = new Result(result);
        r.method = method;
        r.responseCode = responseCode;
        return r;
    }

    /**
     * Converts the contents of an InputStream to a String
     *
     * @param stream      The InputStream
     * @param maxReadSize The max size to read
     * @return String The read stream
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }

    /**
     * !!! Used in dev environment only to trust everyone and bypass certificates
     */
    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

    /**
     * Enum that defines the possible HTTP requests
     */
    public enum METHOD {
        GET,
        POST,
        DELETE,
        PUT
    }

    /**
     * The Result inner class
     *
     * <p>
     * Wrapper class that serves the result datas
     */
    static public class Result {

        public Exception exception;
        public String resultString;
        public METHOD method;
        public String tag;
        public int responseCode;

        /**
         * A Result constructor
         *
         * @param result The result
         */
        public Result(String result) {
            resultString = result;
        }

        /**
         * A Result constructor
         *
         * @param e The exception
         */
        public Result(Exception e) {
            exception = e;
        }
    }
}
