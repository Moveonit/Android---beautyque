package moveonit.beautyque.Adapter;

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

import moveonit.beautyque.R;
import moveonit.beautyque.model.Spa;

public class CustomListSpaAdapter extends ArrayAdapter<Spa> implements Filterable {
    private ArrayList<Spa> original;
    private ArrayList<Spa> fitems;
    private Filter filter;

    public CustomListSpaAdapter(Context context, int textViewResourceId, List<Spa> objects) {
        super(context, textViewResourceId, objects);
        this.original = new ArrayList<Spa>(objects);
        this.fitems = new ArrayList<Spa>(objects);
        this.filter = new spaFilter();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_row_list_spas, null);
        TextView nome = (TextView)convertView.findViewById(R.id.textViewName);
        TextView numero = (TextView)convertView.findViewById(R.id.textViewCity);
        Spa c = getItem(position);
        nome.setText(c.getCompanyName());
        numero.setText(c.getAddress());
        return convertView;
    }

    @Override
    public Filter getFilter(){

        if(filter == null){
            filter = new spaFilter();
        }
        return filter;
    }

    public class spaFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String prefix = constraint.toString().toLowerCase();

            if (prefix == null || prefix.length() == 0) {
                ArrayList<Spa> list = new ArrayList<Spa>(original);
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<Spa> list = new ArrayList<Spa>(original);
                final ArrayList<Spa> nlist = new ArrayList<Spa>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final Spa spa = list.get(i);
                    final String value = spa.getCompanyName().toLowerCase();

                    if (value.contains(prefix)) {
                        nlist.add(spa);
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
            fitems = (ArrayList<Spa>) results.values;
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
