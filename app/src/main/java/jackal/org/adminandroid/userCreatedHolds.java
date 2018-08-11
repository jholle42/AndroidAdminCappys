package jackal.org.adminandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<hold> userHolds;
    private OnFragmentInteractionListener mListener;

    DatabaseReference mHoldReference;
    FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();


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
        mHoldReference = mFirebaseDatabase.getReference("holds");
        mHoldReference.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_created_holds, container, false);
        mRecyclerView = rootView.findViewById(R.id.sectionHolds);

        initHolds();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RVAdapter(userHolds);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void initHolds(){
         userHolds = new ArrayList<>();

       // userHolds.add(new hold("j","j","j","j","j",0));
       // userHolds.add(new hold("jg","jggg","jg","j","j",0));
       // userHolds.add(new hold("jgg","jgggg","jgg","j","j",0));
       // userHolds.add(new hold("jgg","jggg","jggg","j","j",0));

    }



    ValueEventListener eventListener = new ValueEventListener() {
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
                    mAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };


}
