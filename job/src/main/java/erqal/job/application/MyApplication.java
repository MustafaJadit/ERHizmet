package erqal.job.application;
import android.app.Application;
/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        onConfigurationChanged(getResources().getConfiguration());
    }
    public static MyApplication getStaticInstance() {
        if (instance == null) {
            // throw new IllegalStateException("Not initialized yet");
        }
        return instance;
    }
    private static MyApplication instance = null;
}
