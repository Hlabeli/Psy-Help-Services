package za.ac.uj.pyshelp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CampusAdapter extends RecyclerView.Adapter<CampusAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList <Campus> list;

    public CampusAdapter(Context context, ArrayList<Campus> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.campus,parent, false);
        return new MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Campus campus = list.get(position);
        holder.name.setText(campus.getName());
        holder.location.setText(campus.getLocation());
        holder.abbreviation.setText(campus.getAbbreviation());
        holder.block.setText(campus.getBlock());
        //holder.Toll_free.setText(campus.getToll_free());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //CircleImageView campus_id;
        TextView name, location, abbreviation, block;


        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            //campus_id = itemView.findViewById(R.id.campus_id);
            name = itemView.findViewById(R.id.campus_name);
            location = itemView.findViewById(R.id.campus_location);
            abbreviation= itemView.findViewById(R.id.abbr);
            block= itemView.findViewById(R.id.camp_block);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAbsoluteAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }

                }
            });
        }
    }
}
