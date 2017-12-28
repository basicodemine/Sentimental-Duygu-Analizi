package project.twitter.bigdata.twtitteranaliz.ClassYapilari.AdapterInclude;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import project.twitter.bigdata.twtitteranaliz.ClassYapilari.DuyguluTweetler;
import project.twitter.bigdata.twtitteranaliz.ClassYapilari.Kullanici;
import project.twitter.bigdata.twtitteranaliz.Kutuphaneler.Kutuphane;
import project.twitter.bigdata.twtitteranaliz.R;

/**
 * Created by eGo on 20/11/2017.
 */

public class TweeterAdapterDuygu extends BaseAdapter {
    ArrayList<DuyguluTweetler> list = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;
    Kutuphane kutuphane;
    Kullanici kullanici;

    public TweeterAdapterDuygu(ArrayList<DuyguluTweetler> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        kutuphane = new Kutuphane(activity);
        kullanici = (Kullanici) activity.getApplicationContext();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DuyguluTweetler getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.tweet_row, null);
        TextView tv_tweet = (TextView) v.findViewById(R.id.tweet_icerik);
        ImageView img_pf = (ImageView) v.findViewById(R.id.kisi_resim);
//        https://twitter.com/basicodemine/profile_image?size=original
        LinearLayout farkindalik = (LinearLayout) v.findViewById(R.id.karar);
        tv_tweet.setText(list.get(i).getTweet());

        if (list.get(i).isMutlumu()) {
            farkindalik.setBackgroundColor(Color.parseColor("#B2DFDB"));
        } else {
            farkindalik.setBackgroundColor(Color.parseColor("#FFCDD2"));
        }


        kutuphane.netResim(img_pf, "https://twitter.com/" + kullanici.getId() + "/profile_image?size=original");


        return v;
    }
}
