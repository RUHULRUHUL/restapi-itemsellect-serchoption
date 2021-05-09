package ruhulbdapp.com.blogspot.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ruhulbdapp.com.blogspot.R;
import ruhulbdapp.com.blogspot.model.Continents;

public class Continents_Adapter extends RecyclerView.Adapter<Continents_Adapter.ViewHolder> implements Filterable {

    private In_itemsellect in_itemsellect;
    private Context context;
    private List<Continents> allcontinentslist;
    public List<Continents> allcontinentslist_filtered;



    public Continents_Adapter(In_itemsellect in_itemsellect, Context context, List<Continents> allcontinentslist) {
        this.in_itemsellect = in_itemsellect;
        this.context = context;
        this.allcontinentslist = allcontinentslist;
        this.allcontinentslist_filtered=allcontinentslist;
    }



    public Continents_Adapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(context).inflate(R.layout.continents_item_design,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Continents continents=allcontinentslist_filtered.get(position);

        holder.resource.setText("resource : "+allcontinentslist.get(position).getResource());
        holder.name.setText("continents name : "+continents.getName());
        holder.id.setText("Continents id: "+String.valueOf(continents.getId()));
        holder.updated_at.setText(continents.getUpdated_at());


        Log.d("res",continents.getResource());
    }

    @Override
    public int getItemCount() {

            return allcontinentslist_filtered.size();


    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    allcontinentslist_filtered = allcontinentslist;
                } else {
                    List<Continents> filteredList = new ArrayList<>();
                    for (Continents item : allcontinentslist) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (item.getName().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filteredList.add(item);
                        }
                    }

                    allcontinentslist_filtered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = allcontinentslist_filtered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                allcontinentslist_filtered = (ArrayList<Continents>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface In_itemsellect{
        void sellect(Continents continents);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView resource,name,id,updated_at;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            resource=itemView.findViewById(R.id.resource_id);
            name=itemView.findViewById(R.id.name_id);
            id=itemView.findViewById(R.id.continent_id_id);
            updated_at=itemView.findViewById(R.id.updated_at_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    in_itemsellect.sellect(allcontinentslist_filtered.get(getAdapterPosition()));
                    //Toast.makeText(context, "sellect : "+String.valueOf(allcontinentslist.get(getAdapterPosition())), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
