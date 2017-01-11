package moveonit.beautyque;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Niccolò Cald on 20/11/2016.
 */

public class EmployeesActivityFragment extends CuddleFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View view = inflater.inflate(R.layout.fragment_employees, container, false);

        TextView text = (TextView) view.findViewById(R.id.textView2);

        text.setText("Ciaone");

        return view;
        //return inflater.inflate(R.layout.fragment_employees, container, false);
    }


}