package ru.usedesk.sdk.data.framework.api.standard;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import ru.usedesk.sdk.domain.entity.chat.OfflineForm;
import ru.usedesk.sdk.utils.LogUtils;

import static ru.usedesk.sdk.utils.LogUtils.LOGD;

public class HttpApi {

    private static final String TAG = HttpApi.class.getSimpleName();

    private static final String ENCODING = "UTF-8";

    private final Gson gson;

    @Inject
    public HttpApi(Gson gson) {
        this.gson = gson;
    }

    public boolean post(String urlString, OfflineForm offlineForm) {
        try {
            String postData = new JSONObject(gson.toJson(offlineForm)).toString();

            URL url = new URL(urlString);

            LOGD(TAG, "URL: " + url);
            LOGD(TAG, "Data: " + postData);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(outputStream, ENCODING));

            String encodedData = URLEncoder.encode(postData, ENCODING);
            LOGD(TAG, "Data (encoded): " + encodedData);
            bufferedWriter.write(encodedData);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            int responseCode = httpURLConnection.getResponseCode();
            boolean success = responseCode == HttpsURLConnection.HTTP_OK;

            LOGD(TAG, "SUCCESS: " + success);

            return success;
        } catch (Exception e) {
            LogUtils.LOGE(TAG, e);
        }

        return false;
    }
}