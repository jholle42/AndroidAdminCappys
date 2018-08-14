package jackal.org.adminandroid;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link userCreatedHolds.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link userCreatedHolds#newInstance} factory method to
 * create an instance of this fragment.
 */
public class userCreatedHolds extends Fragment {

    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<hold> userHolds;
    NotificationCompat.Builder mBuilder;
    String newHoldName, newHoldUser;
    DatabaseReference mHoldReference;
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    NotificationManagerCompat notificationManager;
    String chanId, chanDescr;

    public userCreatedHolds() {

    }

    static userCreatedHolds newInstance(String param1, String param2) {
        userCreatedHolds fragment = new userCreatedHolds();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHoldReference = mFirebaseDatabase.getReference("adminHolds");
        //mHoldReference.addListenerForSingleValueEvent(eventListener);
        mHoldReference.addChildEventListener(childEventListener);
        if (android.os.Build.VERSION.SDK_INT>= 26) {
            NotificationChannel notChannel;
            notChannel = new NotificationChannel("admin", "Channel for Admins", NotificationManager.IMPORTANCE_HIGH);
            createNotificationChannel();
            chanId = notChannel.getId().toString();
            chanDescr = notChannel.getDescription().toString();
        }
        setBuilder();
        notificationManager = NotificationManagerCompat.from(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_created_holds, container, false);
        mRecyclerView = rootView.findViewById(R.id.sectionHolds);
        mRecyclerView.setHasFixedSize(true);
        initHolds();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RVAdapter(userHolds);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onPause(){
        super.onPause();

    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void initHolds(){
         userHolds = new ArrayList<>();
    }

    public Boolean checkNew(hold h){
        for(hold hold:userHolds) {
            if (hold.getNumber() == h.getNumber())
                return false;
        }
        return true;
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot Snapshot, String s) {
                hold h = new hold();
                h.setApproved(Snapshot.child("approved").getValue(Integer.class));
                h.setItemName(Snapshot.child("itemName").getValue(String.class));
                h.setName(Snapshot.child("name").getValue(String.class));
                h.setNumber(Snapshot.child("number").getValue(String.class));
                h.setQuantity(Snapshot.child("quantity").getValue(String.class));
                userHolds.add(h);
                newHoldName = h.getName();
                mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



    public NotificationCompat.Builder setBuilder(){
        if (android.os.Build.VERSION.SDK_INT< 26) {
            mBuilder = new NotificationCompat.Builder(getActivity())
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("New Hold")
                    .setContentText(newHoldUser +" created a new hold for "+newHoldName+"!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH).setChannelId("newHold");
        }else{
            mBuilder = new NotificationCompat.Builder(getActivity(),chanId)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle("New Hold")
                    .setContentText(newHoldUser+" created a new hold for "+newHoldName+"!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH).setChannelId("newHold");
        }
        mBuilder.setSound(alarmSound);
        return mBuilder;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = chanId;
            String description = chanDescr;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(chanId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}

/* ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot Snapshot: dataSnapshot.getChildren()) {
                for(DataSnapshot postSnapshot: Snapshot.getChildren()) {
                    hold h = new hold();
                    h.setApproved(postSnapshot.child("approved").getValue(Integer.class));
                    h.setItemName(postSnapshot.child("itemName").getValue(String.class));
                    h.setName(postSnapshot.child("name").getValue(String.class));
                    h.setNumber(postSnapshot.child("number").getValue(String.class));
                    h.setQuantity(postSnapshot.child("quantity").getValue(String.class));
                    userHolds.add(h);
                    newHoldName = h.getName();
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
        */