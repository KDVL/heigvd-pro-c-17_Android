/**
 * Generic download callback used by APIManager
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.network.APIManager
 */

package ch.heig.cashflow.network.callbacks;

import android.content.Context;

public interface DownloadCallback<T> {

    /**
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     *
     * @param result The result
     */
    void updateFromDownload(T result);

    /**
     * Get the application context
     *
     * @return Context The application context
     */
    Context getContext();

    /**
     * Interface that defines the different progress status
     */
    interface Progress {
        int ERROR = -1;
        int CONNECT_SUCCESS = 0;
        int GET_INPUT_STREAM_SUCCESS = 1;
        int PROCESS_INPUT_STREAM_IN_PROGRESS = 2;
        int PROCESS_INPUT_STREAM_SUCCESS = 3;
    }
}
