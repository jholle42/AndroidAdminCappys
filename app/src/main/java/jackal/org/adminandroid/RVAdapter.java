package jackal.org.adminandroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jholle42 on 8/10/18.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.holdViewHolder>  {

    public static class holdViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView name;
        TextView quantity;
        TextView item;
        TextView status;
        holdViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView)itemView.findViewById(R.id.sectionHolds);
            name = (TextView)itemView.findViewById(R.id.holdName);
            quantity = (TextView)itemView.findViewById(R.id.holdQuantity);
            item = (TextView)itemView.findViewById(R.id.holdItem);
            status = (TextView)itemView.findViewById(R.id.holdStatus);
        }
    }

    List<hold> userHolds;

    RVAdapter(List<hold> holds){
        userHolds = holds;
    }

    @Override
    public holdViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hold, viewGroup, false);
        holdViewHolder pvh = new holdViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(holdViewHolder ViewHolder, int i) {
        ViewHolder.name.setText(userHolds.get(i).getName());
        ViewHolder.quantity.setText(userHolds.get(i).getQuantity());
        ViewHolder.item.setText(userHolds.get(i).getItemName());
        Integer stat = userHolds.get(i).getApproved();
        if(stat.equals(1)){
            ViewHolder.status.setBackgroundResource(R.color.colorGreen);
        }else if(stat.equals(2)){
            ViewHolder.status.setBackgroundResource(R.color.colorYellow);
        }else{
            ViewHolder.status.setBackgroundResource(R.color.colorRed);
        }
    }

    TextView name;
    TextView quantity;
    TextView item;
    TextView status;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return userHolds.size();
    }

}
