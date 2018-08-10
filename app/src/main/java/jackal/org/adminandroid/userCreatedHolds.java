package jackal.org.adminandroid;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class userCreatedHolds extends Fragment {

        TextView mUserFullName, mUserEmail;
        List<hold> userHolds;

        private RecyclerView mRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;

        public userCreatedHolds(){

        }

    //database
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mHoldReference;


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    hold h = new hold();
                    h.setApproved(postSnapshot.child("approved").getValue(Integer.class));
                    h.setItemName(postSnapshot.child("itemName").getValue(String.class));
                    h.setName(postSnapshot.child("name").getValue(String.class));
                    h.setNumber(postSnapshot.child("number").getValue(String.class));
                    h.setQuantity(postSnapshot.child("quantity").getValue(String.class));
                    userHolds.add(h);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        //String name, email, address, phoneNumber, key;

        private OnFragmentInteractionListener mListener;



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mHoldReference = mFirebaseDatabase.getReference("holds");
            mHoldReference.addListenerForSingleValueEvent(eventListener);
        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user_created_holds, container, false);
            mRecyclerView = rootView.findViewById(R.id.sectionHolds);
            mUserFullName = rootView.findViewById(R.id.name);
            mUserEmail = rootView.findViewById(R.id.email);
            initHolds();

            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new RVAdapter(userHolds, mHoldReference, getActivity());
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.notifyDataSetChanged();
            return rootView;


        }

        @Override
        public void onStop() {
            super.onStop();
        }
        @Override
        public void onPause() {
            super.onPause();
        }
        @Override
        public void onResume() {

            super.onResume();
        }

        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }

        public void submit(){

        }

// Attach a listener to read the data at our posts reference


        public void initHolds(){
            userHolds = new ArrayList<>();
            hold tmp= new hold("j","j","j","j","j",0);
            userHolds.add(tmp);
            if(userHolds.isEmpty()){

            }else{
                for(hold h:userHolds){
                    addHold(h,h.getItemName());
                }
            }


        }

        public void addHold(final hold Hold, String key) {
            final DatabaseReference itemLocation = mHoldReference.push();
            mHoldReference.child(key).setValue(Hold);

        }

    }
