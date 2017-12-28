package project.twitter.bigdata.twtitteranaliz.Kutuphaneler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import project.twitter.bigdata.twtitteranaliz.R;

/**
 * Created by eGo on 29/10/2017.
 */

public class Kutuphane {

    Activity activity;


    public void netResim(final ImageView imageView,final String url){
        Picasso.with(activity)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(activity)
                                .load(url)
                                .error(R.drawable.twitterikon)
                                .into(imageView, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso","Could not fetch image");
                                    }
                                });
                    }
                });
    }

    public Kutuphane(Activity activity) {
        this.activity = activity;
    }
    public void EvoData(String url, int method, final Callback callback) {


        Log.e("Sorgulanan url", url);
        if (internetKontrol())
            try {

                RequestQueue mRequestQueue;
                StringRequest stringRequest = (StringRequest) new StringRequest(method, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    //response = URLDecoder.decode(URLEncoder.encode(response, "iso8859-1"), "UTF-8");
                                    Log.e("Kutuphane gelen", response);
                                        callback.cevap(response);



                                } catch (Exception e) {
                                    Log.e("Kutuphane Hata", e.toString());
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                callback.hata(error.toString());

                            }
                        }).setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                mRequestQueue = Volley.newRequestQueue(activity.getApplicationContext());
                mRequestQueue.add(stringRequest);  //request ayarlandı çıkarılabilir 07 08 2017
            } catch (Exception e) {
                Log.e("HATA:::", e.toString());

            }
        else
            EvoDialogInterface("Şu anda internet bağlantınız olmadığından programı aktif olarak kullanamıyorsunuz. Lütfen internete bağlı olduğunuzdan emin olun.", "Bağlantı Hatası", new cikisKarari() {
                @Override
                public void karar(boolean bool) {
                    if (bool)
                        wifiEkraniAc();
                }
            });
    }

    public void wifiEkraniAc() {
        activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

    }

    public boolean internetKontrol() {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            Toast.makeText(activity, "İnternet bağlantınızı kontrol edin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (info.isRoaming()) {
            return false;
        }
        return true;
    }

    public void EvoDialogInterface(String icerik, String baslik, final cikisKarari cikisKarari) {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, R.style.AppTheme);
        } else {
            builder = new AlertDialog.Builder(activity);
        }

        final AlertDialog dialog = builder.setTitle(baslik)
                .setMessage(icerik)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        cikisKarari.karar(true);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        cikisKarari.karar(false);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.WHITE);
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
            }
        });
    }

    public interface cikisKarari {
        void karar(boolean bool);
    }


    public static class GetData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String result = "";
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();

                if (code == 200) {
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    if (in != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        String line = "";

                        while ((line = bufferedReader.readLine()) != null)
                            result += line;
                    }
                    in.close();
                }
                Log.e("cvp",result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return result;

        }
    }

}
