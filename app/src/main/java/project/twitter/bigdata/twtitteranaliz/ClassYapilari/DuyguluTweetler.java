package project.twitter.bigdata.twtitteranaliz.ClassYapilari;

import android.text.SpannableString;

/**
 * Created by eGo on 21/11/2017.
 */

public class DuyguluTweetler {

    SpannableString tweet;

    boolean mutlumu;

    public SpannableString getTweet() {
        return tweet;
    }

    public void setTweet(SpannableString tweet) {
        this.tweet = tweet;
    }

    public boolean isMutlumu() {
        return mutlumu;
    }

    public void setMutlumu(boolean mutlumu) {
        this.mutlumu = mutlumu;
    }

    public DuyguluTweetler(SpannableString tweet, boolean mutlumu) {
        this.tweet = tweet;
        this.mutlumu = mutlumu;
    }

}
