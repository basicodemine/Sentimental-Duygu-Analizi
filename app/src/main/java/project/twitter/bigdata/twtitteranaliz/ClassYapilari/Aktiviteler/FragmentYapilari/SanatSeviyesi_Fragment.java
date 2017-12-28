package project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Hobi.SanatsalKelimeListesi;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.DuyguluTweetler;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.R;


public class SanatSeviyesi_Fragment extends Fragment {

    Kullanici kullanici;
    TextView tv_bulunan, tv_taranan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sanat_seviyesi_, container, false);
        gorselAta(v);
        return v;
    }

    void gorselAta(View v) {
        kullanici = (Kullanici) getActivity().getApplicationContext();

        tv_bulunan = v.findViewById(R.id.tv_bulunan);
        tv_taranan = v.findViewById(R.id.tv_taranan);

        new BackgroundTask().execute();
    }


    public class BackgroundTask extends AsyncTask<Void, Void, Void> {
        int taranan = 1;
        int bulunan = 1;
        ProgressDialog pd;
        String[] ayrac;
        String[] havuz;
        int toplamts = 0;
        int kackeregirdi = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String girdi = Html.fromHtml(kullanici.getGelenveri()).toString().replace("%%%%%%%", " ");
            ayrac = girdi.split(" ");
            toplamts = kullanici.getCekilentweetSayisi();
            havuz = SanatsalKelimeListesi.sanatsalKelimeler;
            Log.e("Toplam kelime", "" + ayrac.length);
//            pd = new ProgressDialog(getActivity());
//            pd.setMessage("İşlenen Kelime: " + taranan + " Bulunan Kelime: " + bulunan + " Puan: " + (float) (bulunan * 15) / toplamts);
//            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < ayrac.length; i++) {
                for (int j = 0; j < havuz.length; j++) {
                    taranan++;
                    if (ayrac[i].toLowerCase().contains(havuz[j])) {
                        bulunan++;
                    }
                    if (taranan % 1000 == 0) {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                                        tv_bulunan.getLayoutParams();
                                float a = (float) (bulunan * 15) / toplamts;
                                int s = (int) (a * 100);
                                params.weight = s;
                                Log.e("1. Sonuc", "" + (float) ((bulunan * 15) / toplamts) * 100);
                                tv_bulunan.setLayoutParams(params);
                                tv_bulunan.setText("%" + s);

                                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams)
                                        tv_taranan.getLayoutParams();
                                int ts = (ayrac.length * havuz.length);
                                Log.e("Total:", "" + ts+" "+taranan);
                                float g = (float) (taranan) / ts;
                                int ss = (int) (g * 100);
                                params2.weight = ss;

                                Log.e("2. Sonuc", "" + ss);
                                tv_taranan.setText("%" + ss);
                                tv_taranan.setLayoutParams(params2);
                            }
                        });
                        Log.e("Kac kere", "" + kackeregirdi++);
                    }

                }
            }
            Log.e("Son Sonuç:", "TARANAN: " + taranan + " BULUNAN: " + bulunan + " YÜZDE: " + (float) (bulunan * 15) / toplamts);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }

    }

}
