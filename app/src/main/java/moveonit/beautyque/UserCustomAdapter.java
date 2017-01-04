package moveonit.beautyque;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moveonit.beautyque.model.User;

public class UserCustomAdapter extends ArrayAdapter<User> implements Filterable {
    private ArrayList<User> original;
    private ArrayList<User> fitems;
    private Filter filter;

    public UserCustomAdapter(Context context, int textViewResourceId, List<User> objects) {
        super(context, textViewResourceId, objects);
        this.original = new ArrayList<User>(objects);
        this.fitems = new ArrayList<User>(objects);
        this.filter = new clientFilter();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.rowcustom, null);
        TextView nome = (TextView)convertView.findViewById(R.id.textViewName);
        TextView email = (TextView)convertView.findViewById(R.id.textViewEmail);
        User c = getItem(position);
        nome.setText(c.getType());
        email.setText(c.getEmail());
        return convertView;
    }

    @Override
    public Filter getFilter(){

        if(filter == null){
            filter = new clientFilter();
        }
        return filter;
    }

    public class clientFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0) {
                ArrayList<User> list = new ArrayList<User>(original);
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<User> list = new ArrayList<User>(original);
                final ArrayList<User> nlist = new ArrayList<User>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final User User = list.get(i);
                    //final String value = User.getName().toLowerCase();
                    final String value = "";
                    if (value.contains(prefix)) {
                        nlist.add(User);
                    }
                    results.values = nlist;
                    results.count = nlist.size();
                }
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            fitems = (ArrayList<User>) results.values;
            notifyDataSetChanged();
            clear();
            int count = fitems.size();
            for (int i = 0; i < count; i++) {
                add(fitems.get(i));
                notifyDataSetInvalidated();
            }
        }
    }
}
