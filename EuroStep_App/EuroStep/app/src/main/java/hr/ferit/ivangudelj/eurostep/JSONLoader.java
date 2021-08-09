package hr.ferit.ivangudelj.eurostep;

import android.content.Context;

public interface JSONLoader {
    String loadJSONFromAsset(Context context, String name);
}
