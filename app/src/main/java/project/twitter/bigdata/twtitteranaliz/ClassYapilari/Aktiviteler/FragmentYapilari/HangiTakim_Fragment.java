package project.twitter.bigdata.twtitteranaliz.ClassYapilari.Aktiviteler.FragmentYapilari;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import project.twitter.bigdata.twtitteranaliz.AnaMenu;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.AdapterInclude.TweeterAdapter;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.BJK_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.ESK_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.FB_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.GS_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Attr.Takimlar.TS_K_words;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.DuyguluTweetler;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.TweetClass;
import project.twitter.bigdata.twtitteranaliz.R;


public class HangiTakim_Fragment extends Fragment {

    Kullanici kullanici;
    ArrayList<String> bjkTWT;
    ArrayList<String> trbTWT;
    ArrayList<String> eskTWT;
    ArrayList<String> gsTWT;
    ArrayList<String> fbTWT;

    ListView see_takim_lv;

    Button bjk_btn;
    Button fb_btn;
    Button ts_btn;
    Button gs_btn;
    Button es_btn;

    ImageView tutulanlogo;

    ArrayList<String> all;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hangi_takim_, container, false);
        kullanici = (Kullanici) getActivity().getApplicationContext();
        gorselAta(v);
        hangiTakimliBul(kullanici.getGelenveri());
        return v;
    }

    void gorselAta(View v) {
        tutulanlogo=v.findViewById(R.id.takimlogo);
        see_takim_lv = v.findViewById(R.id.see_takim_lv);
        bjk_btn = v.findViewById(R.id.see_bjk);
        fb_btn = v.findViewById(R.id.see_fb);
        ts_btn = v.findViewById(R.id.see_ts);
        gs_btn = v.findViewById(R.id.see_gs);
        es_btn = v.findViewById(R.id.see_esk);
    }

    void hangiTakimliBul(String cevap) {

        fbTWT = new ArrayList<>();
        gsTWT = new ArrayList<>();
        eskTWT = new ArrayList<>();
        trbTWT = new ArrayList<>();
        bjkTWT = new ArrayList<>();
        all=new ArrayList<>();

        ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setMessage("İşlemden geçen veri sayısı:");
        pd.show();
        HashMap<String, Integer> takimlar = new HashMap<String, Integer>();
        int bjk = 0;
        int gs = 0;
        int fb = 0;
        int ts = 0;
        int es = 0;

        BJK_K_words bjk_k_words = new BJK_K_words();
        FB_K_words fb_k_words = new FB_K_words();
        TS_K_words ts_k_words = new TS_K_words();
        GS_K_words gs_k_words = new GS_K_words();
        ESK_K_words esk_k_words = new ESK_K_words();

        String[] tws = Html.fromHtml(cevap).toString().split("%%%%%%%");


        int islem = 0;

        Log.e("SİZE:", "" + tws.length);


        for (int i = 0; i < tws.length; i++) {
            String[] bolunenKelime=tws[i].split(" ");
            for(int k=0;k<bolunenKelime.length;k++){
                for (int bb = 0; bb < bjk_k_words.getTags().size(); bb++) {
                    if (bolunenKelime[k].contains(bjk_k_words.getTags().get(bb))) {
                        bjk++;
                        bjkTWT.add(tws[i]);
                        all.add(tws[i]);
                    }
                }
                for (int bb = 0; bb < fb_k_words.getTags().size(); bb++) {
                    if (bolunenKelime[k].contains(fb_k_words.getTags().get(bb))) {
                        fb++;
                        fbTWT.add(tws[i]);
                        all.add(tws[i]);
                    }
                }
                for (int bb = 0; bb < ts_k_words.getTags().size(); bb++) {
                    if (bolunenKelime[k].contains(ts_k_words.getTags().get(bb))) {
                        ts++;
                        trbTWT.add(tws[i]);
                        all.add(tws[i]);
                    }
                }
                for (int bb = 0; bb < gs_k_words.getTags().size(); bb++) {
                    if (bolunenKelime[k].contains(gs_k_words.getTags().get(bb))) {
                        gs++;
                        gsTWT.add(tws[i]);
                        all.add(tws[i]);
                    }
                }
                for (int bb = 0; bb < esk_k_words.getTags().size(); bb++) {
                    if (bolunenKelime[k].contains(esk_k_words.getTags().get(bb))) {
                        es++;
                        eskTWT.add(tws[i]);
                        all.add(tws[i]);
                    }
                }
                islem += tws[i].length();
                pd.setMessage("İşlemden geçen veri sayısı: " + islem);
            }

        }

        if (pd.isShowing())
            pd.dismiss();

        takimlar.put("BJK", bjk);
        takimlar.put("GS", gs);
        takimlar.put("TS", ts);
        takimlar.put("FB", fb);
        takimlar.put("ESK", es);

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(takimlar.get("BJK"));
        arrayList.add(takimlar.get("GS"));
        arrayList.add(takimlar.get("TS"));
        arrayList.add(takimlar.get("FB"));
        arrayList.add(takimlar.get("ESK"));

        Collections.sort(arrayList);
        Collections.reverse(arrayList);

        int kararsiz = 0;
        String tutulantakim = "";

        for (Map.Entry<String, Integer> entry : takimlar.entrySet()) {
            if (entry.getValue().equals(arrayList.get(0))) {
                tutulantakim = entry.getKey();
                kararsiz++;
            }
        }
        if (kararsiz == 1) {
            Toast.makeText(getActivity(), tutulantakim, Toast.LENGTH_SHORT).show();
            if(tutulantakim.equals("BJK"))tutulanlogo.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.bjklogo));

            if(tutulantakim.equals("FB"))tutulanlogo.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.fblogo));

            if(tutulantakim.equals("GS"))tutulanlogo.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.gslogo));

            if(tutulantakim.equals("TS"))tutulanlogo.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.tslogo));

            if(tutulantakim.equals("ESK"))tutulanlogo.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.eseslogo));

        } else {
            Toast.makeText(getActivity(), "Analiz için yetersiz bilgi", Toast.LENGTH_SHORT).show();
            tutulanlogo.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.bulunamadi));
        }
        Animation startAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.yan_son);
        tutulanlogo.startAnimation(startAnimation);

        TweeterAdapter tweeterAdapter=new TweeterAdapter(all,getActivity());
        see_takim_lv.setAdapter(tweeterAdapter);

        Log.e("RES:", "BJK:" + bjk + " FB:" + fb + " GS:" + gs + " TS:" + ts + " ESK:" + es);

        bjk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TweeterAdapter tweeterAdapter=new TweeterAdapter(bjkTWT,getActivity());
                see_takim_lv.setAdapter(tweeterAdapter);
                //enCokTekrarEdenleriBul(kullanici.getGelenveri());
            }
        });

        fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TweeterAdapter tweeterAdapter=new TweeterAdapter(fbTWT,getActivity());
                see_takim_lv.setAdapter(tweeterAdapter);
            }
        });

        ts_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TweeterAdapter tweeterAdapter=new TweeterAdapter(trbTWT,getActivity());
                see_takim_lv.setAdapter(tweeterAdapter);
            }
        });

        gs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TweeterAdapter tweeterAdapter=new TweeterAdapter(gsTWT,getActivity());
                see_takim_lv.setAdapter(tweeterAdapter);
            }
        });

        es_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TweeterAdapter tweeterAdapter=new TweeterAdapter(eskTWT,getActivity());
                see_takim_lv.setAdapter(tweeterAdapter);
            }
        });


    }


    public Map<String, Integer> encokTekrarEdenKelime(String input) {
        Map<String, Integer> occurrences = new HashMap<String, Integer>();
        String[] ayrac = input.split(" ");

        for (String word : ayrac) {
            Integer oldCount = occurrences.get(word);
            if (oldCount == null) {
                oldCount = 0;
            }
            occurrences.put(word, oldCount + 1);
        }
        return occurrences;
    }

    void enCokTekrarEdenleriBul(String cevap) {
        Map<String, Integer> tekrarEdenKelimeler = encokTekrarEdenKelime(Html.fromHtml(cevap).toString().replace("%%%%%%%", " "));
        HashMapSirala(tekrarEdenKelimeler);
    }


    void HashMapSirala(Map<String, Integer> map) {
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o1).getValue().compareTo(
                        ((Map.Entry<String, Integer>) o2).getValue());
            }
        });

        for (int i = 0; i < a.length; i++) {
            System.out.println(i + ". Eleman " + ((Map.Entry<String, Integer>) a[i]).getKey() + " : "
                    + ((Map.Entry<String, Integer>) a[i]).getValue());
        }
    }
}
