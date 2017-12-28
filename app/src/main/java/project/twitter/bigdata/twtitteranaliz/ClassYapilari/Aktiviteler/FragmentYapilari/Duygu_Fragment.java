package project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import project.twitter.bigdata.twtitteranaliz.ClassYapilari.AdapterInclude.TweeterAdapterDuygu;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.DuyguluTweetler;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.R;


public class Duygu_Fragment extends Fragment {

    TextView tv_sonuc;
    TextView mutlu_tv, mutsuz_tv, normal_tv;
    static List<String> posWords = new ArrayList<String>();
    static List<String> negWords = new ArrayList<String>();
    Kullanici kullanici;
    static int[] stats = new int[3];
    ListView testlistesi;

    Button mutlu_goster_button, mutsuz_goster_button;

    ArrayList<DuyguluTweetler> duyguluTweetlers = new ArrayList<>();
    ArrayList<DuyguluTweetler> mutluolanlar = new ArrayList<>();
    ArrayList<DuyguluTweetler> mutsuzolanlar = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_duygu_, container, false);
        gorselAta(v);
        return v;
    }


    void gorselAta(View v) {
        kullanici = (Kullanici) getActivity().getApplicationContext();
        testlistesi = (ListView) v.findViewById(R.id.testlistesi);
        tv_sonuc = (TextView) v.findViewById(R.id.sonuc);
        mutlu_tv = (TextView) v.findViewById(R.id.mutluluk_yuzde);
        mutsuz_tv = (TextView) v.findViewById(R.id.mutsuz_yuzde);
        normal_tv = (TextView) v.findViewById(R.id.alakasiz_yuzde);
        mutsuz_goster_button = (Button) v.findViewById(R.id.mutsuz_goster_button);
        mutlu_goster_button = (Button) v.findViewById(R.id.mutlu_goster_button);

        new BackgroundTask().execute();

    }


    public float getYuzde(int[] toplamgirdi, int index) {
        float yuzde = 0;
        float toplam = 0;
        toplam = toplamgirdi[0] + toplamgirdi[1] + toplamgirdi[2];
        Log.e("YUZDEDEDE", "" + (float) ((float) toplamgirdi[index] / (float) toplam));
        yuzde = ((float) toplamgirdi[index] / toplam) * 100;
        Log.e("Toplam:" + toplam, "yuzde " + yuzde);
        return yuzde;
    }

    public int getSkor(String input) {
        input = input.toLowerCase();
        input = input.trim();
        input = input.replaceAll("[^a-zA-Z0-9\\s]", "");

        int negCounter = 0;
        int posCounter = 0;

        String[] words = input.split(" ");

        for (int i = 0; i < words.length; i++) {
            if (posWords.contains(words[i].trim().toLowerCase())) {
                posCounter++;
            }
            if (negWords.contains(words[i].trim().toLowerCase())) {
                negCounter++;
            }
        }

        int result = (posCounter - negCounter);

        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        }

        return 0;
    }

    public SpannableString getRenk(String input) {


        SpannableString spannableString = new SpannableString(input);

        String[] words = input.split(" ");
        int start = 0;

        for (int i = 0; i < words.length; i++) {
            if (i > 0)
                start += words[i - 1].length() + 1;
            if (posWords.contains(words[i].trim().toLowerCase())) {
                spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), start, start + words[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (negWords.contains(words[i].trim().toLowerCase())) {
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), start, start + words[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), start, start + words[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        Log.e("Donen span", spannableString.toString());
        return spannableString;
    }

    public class BackgroundTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int sira = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Oran hesaplanıyor");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String gelendata = kullanici.getGelenveri();
                posWords = new ArrayList<>();
                negWords = new ArrayList<>();
                String[] tweets = gelendata.split("%%%%%%%");
                long startTime = System.currentTimeMillis();
                BufferedReader negReader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("uzgunsozcukler.txt")));
                BufferedReader posReader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open("mutlusozcukler.txt")));
                String word;
                while ((word = negReader.readLine()) != null) {
                    if (word.equals(turkcedenIngilizce(word)))
                        negWords.add(word);
                    else {
                        negWords.add(word);
                        negWords.add(turkcedenIngilizce(word));
                    }
                }
                while ((word = posReader.readLine()) != null) {
                    if (word.equals(turkcedenIngilizce(word)))
                        posWords.add(word);
                    else {
                        posWords.add(word);
                        posWords.add(turkcedenIngilizce(word));
                    }
                }
                negReader.close();
                posReader.close();
                int score = 0;
                String text;
                for (int i = 0; i < tweets.length; i++) { //
                    text = tweets[i];
                    score = getSkor(text);
                    sira = i;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.setMessage("İşlemden geçen veri sayısı: " + sira);
                        }
                    });
                    SpannableString renkli = getRenk(text);

                    stats[score + 1]++;
                    if (score == -1) {
                        duyguluTweetlers.add(new DuyguluTweetler(renkli, false));
                        mutsuzolanlar.add(new DuyguluTweetler(renkli, false));
                    } else if (score == 1) {
                        duyguluTweetlers.add(new DuyguluTweetler(renkli, true));
                        mutluolanlar.add(new DuyguluTweetler(renkli, true));
                    }
                }
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                System.out.println("Gecen Sure"
                        + TimeUnit.SECONDS.convert(totalTime, TimeUnit.MILLISECONDS)
                        + " saniye");

                System.out.println("Sonuç: " + java.util.Arrays.toString(stats));
            } catch (Exception e) {
                Log.e("Mutluluk hata", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (dialog.isShowing())
                dialog.dismiss();

            mutlu_tv.setText("%" + getYuzde(stats, 2));
            mutsuz_tv.setText("%" + getYuzde(stats, 0));
            normal_tv.setText("%" + getYuzde(stats, 1));

            testlistesi.setAdapter(new TweeterAdapterDuygu(duyguluTweetlers, getActivity()));

            if (stats[2] < stats[0])
                tv_sonuc.setText("MUTSUZ");


            mutlu_goster_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    testlistesi.setAdapter(new TweeterAdapterDuygu(mutluolanlar, getActivity()));
                }
            });

            mutsuz_goster_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    testlistesi.setAdapter(new TweeterAdapterDuygu(mutsuzolanlar, getActivity()));
                }
            });
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

    //ş ü ö İ ğ ı ç
    String turkcedenIngilizce(String input) {
        return input.replace("ş", "s").replace("ü", "u").replace("ö", "o").replace("İ", "i").replace("ğ", "g").replace("ı", "i").replace("ç", "c");
    }

}
