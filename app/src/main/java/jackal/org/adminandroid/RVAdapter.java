package jackal.org.adminandroid;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jholle42 on 6/20/18.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.holdViewHolder>{

    List<hold> holds;
    FirebaseDatabase mFirebaseDatabase;

    Context c;

    DatabaseReference mHoldReference;


    RVAdapter(List<hold> holds, DatabaseReference ref, Context c){
        this.holds = holds;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        this.mHoldReference = ref;
        this.c = c;
    }








    public static class holdViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView name;
        TextView quantity;
        TextView item;
        TextView status;
        Button deleteButton;
        holdViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView)itemView.findViewById(R.id.sectionHolds);
            name = (TextView)itemView.findViewById(R.id.holdName);
            quantity = (TextView)itemView.findViewById(R.id.holdQuantity);
            item = (TextView)itemView.findViewById(R.id.holdItem);
            status = (TextView)itemView.findViewById(R.id.holdStatus);
            deleteButton = (Button)itemView.findViewById(R.id.holdDelete);
        }
    }

    @Override
    public holdViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hold, viewGroup, false);
        holdViewHolder pvh = new holdViewHolder(v);
        return pvh;
    }

    @Override
    public int getItemCount() {
        if(holds.equals(null)){
            return holds.size();
        }
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final holdViewHolder personViewHolder,final int i) {
        personViewHolder.name.setText(holds.get(i).name);
        personViewHolder.quantity.setText(holds.get(i).quantity);
        personViewHolder.item.setText(holds.get(i).itemName);
        Integer stat = holds.get(i).getApproved();
        if(stat.equals(1)){
            personViewHolder.status.setBackgroundResource(R.color.colorGreen);
        }else if(stat.equals(2)){
            personViewHolder.status.setBackgroundResource(R.color.colorYellow);
        }else{
            personViewHolder.status.setBackgroundResource(R.color.colorRed);
        }



        personViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                hold h = holds.get(i);
                                deleteHold(h);
                                holds.remove(h);
                                notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });




    }


    public void deleteHold(hold h){
        mHoldReference.child(h.getItemName()).removeValue();
    }

/*
    @Override
    public void onBindViewHolder(CoursesViewHolder holder, int position) {
        Player player = mArrayCourses.get(position);
        holder.name.setText(player.getName());
        holder.counter.setText(String.valueOf(player.getCount()));
        holder.voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do your work here
            }
        });

    }*/

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}