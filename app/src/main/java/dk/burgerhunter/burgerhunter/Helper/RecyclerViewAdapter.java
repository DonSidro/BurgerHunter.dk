package dk.burgerhunter.burgerhunter.Helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dk.burgerhunter.burgerhunter.R;

/**
 * Created by SidonKK on 27/06/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    ArrayList<Burger> burgerArrayList;


    public RecyclerViewAdapter(ArrayList<Burger> burgerArrayList){
        this.burgerArrayList = burgerArrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.burger_list_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.burgerName.setText(burgerArrayList.get(position).getName());
        //holder.burgerImage.setImageBitmap(burgerArrayList.get(position).getImageString());  // lave om til bitmap og smid ind
        holder.burgerSub.setText(burgerArrayList.get(position).getInfo());
        holder.burgerRating.setText(burgerArrayList.get(position).getRating());
        holder.burgerLocation.setText(burgerArrayList.get(position).getLatitude()+ ""); // lave om til meter from current location.


    }

    @Override
    public int getItemCount() {
        return burgerArrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView burgerImage;
        TextView burgerName;
        TextView burgerSub;
        TextView burgerRating;
        TextView burgerLocation;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            burgerImage = (ImageView) itemView.findViewById(R.id.iv_item_img);
            burgerName = (TextView) itemView.findViewById(R.id.title);
            burgerSub = (TextView) itemView.findViewById(R.id.subtitle);
            burgerRating = (TextView) itemView.findViewById(R.id.rating);
            burgerLocation = (TextView) itemView.findViewById(R.id.locationMeter);
            
        }
    }
}
