package project.twitter.bigdata.twtitteranaliz.ClassYapilari.AdapterInclude;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.twitter.bigdata.twtitteranaliz.Kutuphaneler.Kutuphane;
import project.twitter.bigdata.twtitteranaliz.R;

/**
 * Created by eGo on 24/12/2017.
 */

public class EtkilesimAdapter extends BaseAdapter {
    ArrayList<String> strings = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;
    Kutuphane kutuphane;

    public EtkilesimAdapter(ArrayList<String> strings, Activity activity) {
        this.strings = strings;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        kutuphane = new Kutuphane(activity);
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public String getItem(int i) {
        return strings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.kisi_kare, null);

        ImageView pf = v.findViewById(R.id.k_pf);
        TextView id = v.findViewById(R.id.k_id);
        Button go = v.findViewById(R.id.k_go);
        TextView count=v.findViewById(R.id.k_count);
        count.setText(strings.get(i).split("%=%")[1]+" Kere");
        id.setText("" + strings.get(i).split("%=%")[0]);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/"+strings.get(i).split("%=%")[0];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                activity.startActivity(i);
            }
        });
        kutuphane.netResim(pf, "https://twitter.com/" + strings.get(i).split("%=%")[0] + "/profile_image?size=original");

        return v;
    }
}
