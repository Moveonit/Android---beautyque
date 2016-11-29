package moveonit.beautyque;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DANIELE on 24/11/2016.
 */

public class SignupActivityFragment extends Fragment {

    public SignupActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_fragment, container, false);
    }
}
