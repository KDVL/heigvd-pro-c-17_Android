package ch.heig.cashflow.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.google.gson.Gson;


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
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class APIManager extends AsyncTask<String, Integer, APIManager.Result> {

    private DownloadCallback<APIManager.Result> mCallback;
    private boolean authNeeded;
    private METHOD method;
    private HashMap<String, String> postParams;

    public enum METHOD {
            GET,
            POST,
            DELETE,
            PUT
    }

    public APIManager(DownloadCallback<APIManager.Result> callback, boolean authNeeded, METHOD m) {
        setCallback(callback);
        this.authNeeded = authNeeded;
        method = m;

        //Used with https test server (localhost)
        trustEveryone();
    }

    public void setPostParams(HashMap<String, String> postParams) {
        this.postParams = postParams;
    }

    private String getPostParams(){
        Gson gson = new Gson();
        return gson.toJson(postParams);
    }

    void setCallback(DownloadCallback<APIManager.Result> callback) {
        mCallback = callback;
    }

    /**
     * Cancel background network operation if we do not have network connectivity.
     */
    @Override
    protected void onPreExecute() {
        if (mCallback != null) {
            Context c = mCallback.getContext();
            ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected() ||
                    (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                            && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                // If no connectivity, cancel task and update Callback with null data.
                mCallback.updateFromDownload(null);
                cancel(true);
            }
        }
    }

    /**
     * Defines work to perform on the background thread.
     */
    @Override
    protected APIManager.Result doInBackground(String... urls) {
        Result result = null;
        String urlString = urls[0];
        if (!isCancelled() && urls != null && urls.length > 0) {

            try {
                URL url = new URL(urlString);
                result = downloadUrl(url);
            } catch(Exception e) {
                result = new Result(e);
                result.responseCode = -1;
                System.out.println(urlString);
            }
        }
        result.tag = urlString;
        return result;
    }

    /**
     * Updates the DownloadCallback with the result.
     * */
    @Override
    protected void onPostExecute(Result result) {
        //return result
        mCallback.updateFromDownload(result);
    }

    /**
     * Override to add special behavior for cancelled AsyncTask.
     */
    @Override
    protected void onCancelled(Result result) {}

    /**
     * Given a URL, sets up a connection and gets the HTTP response body from the server.
     * If the network request is successful, it returns the response body in String form. Otherwise,
     * it will throw an IOException.
     */
    private Result downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
        int responseCode = -1;

        try {
            connection = (HttpsURLConnection) url.openConnection();

            if(authNeeded) {
                String token = TokenHolder.getToken(mCallback.getContext());

                if(token != null)
                    connection.addRequestProperty("Authorization", "Bearer " + token);
            }

            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod(method.toString());
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");

            if(method != METHOD.GET){
                // Send post request
                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(getPostParams());
                wr.flush();
                wr.close();
            }else{
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
            System.out.println(e);
        }   finally
         {
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
     * Converts the contents of an InputStream to a String.
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
     * Wrapper class that serves as a union of a result value and an exception. When the download
     * task has completed, either the result value or exception can be a non-null value.
     * This allows you to pass exceptions to the UI thread that were thrown during doInBackground().
     */
    static public class Result {
        public Exception exception;
        public String resultString;
        public int responseCode;
        public METHOD method;
        public String tag;

        public Result(String result) {
            resultString = result;
        }

        public Result(Exception e) {
            exception = e;
        }
    }


    //Used for testing
    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}



