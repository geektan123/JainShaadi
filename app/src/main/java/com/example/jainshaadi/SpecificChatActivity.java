package com.example.jainshaadi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class SpecificChatActivity extends AppCompatActivity {

    EditText mgetmessage;
    ImageButton msendmessagebutton;

    CardView msendmessagecardview;
    androidx.appcompat.widget.Toolbar mtoolbarofspecificchat;
    ImageView mimageviewofspecificuser;
    TextView mnameofspecificuser;

    private String enteredmessage;
    Intent intent;
    String mrecievername, sendername, mrecieveruid, msenderuid;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderroom, recieverroom;

    ImageButton mbackbuttonofspecificchat;

    RecyclerView mmessagerecyclerview;

    String currenttime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    MessagesAdapter messagesAdapter;
    ArrayList<Messages> messagesArrayList;

    private static final int PAGE_SIZE = 7;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private long lastMessageKey = Long.MAX_VALUE;
    private long lastRecivedTimestamp = Long.MIN_VALUE;
    private ChildEventListener childEventListener;
    DatabaseReference newMessages;
    LinearLayout Layout01 , Layout02 ,Layout03 ,Layout04 ,Layout05 ,Layout06 ;

    String Invitation = "";
    boolean check ;
    TextView swipeMessageTextView;




    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specificchat);
        getSupportActionBar().hide();
        swipeMessageTextView = findViewById(R.id.swipeMessageTextView);
        check = true;


        mgetmessage = findViewById(R.id.getmessage);
        Layout01 = findViewById(R.id.one);
        Layout02 = findViewById(R.id.two);
        Layout03 = findViewById(R.id.three);
        Layout04 = findViewById(R.id.four);
        Layout05 = findViewById(R.id.five);
        Layout06 = findViewById(R.id.six);
        mgetmessage.requestFocus();
        msendmessagecardview = findViewById(R.id.carviewofsendmessage);
        msendmessagebutton = findViewById(R.id.imageviewsendmessage);
        mtoolbarofspecificchat = findViewById(R.id.toolbarofspecificchat);
        mnameofspecificuser = findViewById(R.id.Nameofspecificuser);
        mimageviewofspecificuser = findViewById(R.id.specificuserimageinimageview);
        mbackbuttonofspecificchat = findViewById(R.id.backbuttonofspecificchat);

        messagesArrayList = new ArrayList<>();
        mmessagerecyclerview = findViewById(R.id.recyclerviewofspecific);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessagerecyclerview.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(SpecificChatActivity.this, messagesArrayList);
        mmessagerecyclerview.setAdapter(messagesAdapter);

        intent = getIntent();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        msenderuid = firebaseAuth.getUid();
        mrecieveruid = getIntent().getStringExtra("profileId");
        mrecievername = getIntent().getStringExtra("Name");

        senderroom = msenderuid + mrecieveruid;
        recieverroom = mrecieveruid + msenderuid;
        newMessages = firebaseDatabase.getReference().child("chats").child(senderroom).child("messages");


        mbackbuttonofspecificchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mnameofspecificuser.setText(mrecievername);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");

        DatabaseReference currentProfileRef = databaseRef.child(mrecieveruid);

        currentProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String url = dataSnapshot.child("imageUrl1").getValue(String.class);
                    Glide.with(getApplicationContext())
                            .load(url)
                            .into(mimageviewofspecificuser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference SenderProfileRef = databaseRef.child(msenderuid);
        SenderProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DataSnapshot InviteStatus = snapshot.child("invite").child(mrecieveruid).child("status");
                    if (!(snapshot.child("status").getValue(String.class).equals("Completed"))) {
                        Layout04.setVisibility(View.VISIBLE);
                        Layout01.setVisibility(View.GONE);
                        Layout02.setVisibility(View.GONE);
                        Layout03.setVisibility(View.GONE);
                        Layout05.setVisibility(View.GONE);
                        Layout06.setVisibility(View.GONE);
                    } else {
                        if (InviteStatus.exists()) {
                            Invitation = InviteStatus.getValue(String.class);
                            if (Invitation.equals("Sent")) {
                                Layout02.setVisibility(View.VISIBLE);
                                Layout01.setVisibility(View.GONE);
                                Layout03.setVisibility(View.GONE);
                                Layout04.setVisibility(View.GONE);
                                Layout05.setVisibility(View.GONE);
                                Layout06.setVisibility(View.GONE);

                            } else if (Invitation.equals("Recieved")) {
                                Layout05.setVisibility(View.VISIBLE);
                                Layout02.setVisibility(View.GONE);
                                Layout01.setVisibility(View.GONE);
                                Layout03.setVisibility(View.GONE);
                                Layout04.setVisibility(View.GONE);
                                Layout06.setVisibility(View.GONE);
                            }else if (Invitation.equals("Rejected")) {
                                Layout03.setVisibility(View.VISIBLE);
                                Layout02.setVisibility(View.GONE);
                                Layout01.setVisibility(View.GONE);
                                Layout04.setVisibility(View.GONE);
                                Layout05.setVisibility(View.GONE);
                                Layout06.setVisibility(View.GONE);
                            }
                            else if (Invitation.equals("Accepted")) {
                                Layout06.setVisibility(View.VISIBLE);
                                Layout02.setVisibility(View.GONE);
                                Layout01.setVisibility(View.GONE);
                                Layout03.setVisibility(View.GONE);
                                Layout04.setVisibility(View.GONE);
                                Layout05.setVisibility(View.GONE);
                            }
                        } else {
                            Layout06.setVisibility(View.VISIBLE);
                            Layout01.setVisibility(View.VISIBLE);
                            Layout02.setVisibility(View.GONE);
                            Layout03.setVisibility(View.GONE);
                            Layout04.setVisibility(View.GONE);
                            Layout05.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!isLoading && !isLastPage && linearLayoutManager != null) {
                    loadMoreMessages();
                }
                else
                {
                    swipeRefreshLayout.setRefreshing(false);
                    swipeMessageTextView.setVisibility(View.GONE);
                    swipeMessageTextView.setText("No more Conversations to load.");
                }
            }
        });
        LinearLayout Accept = findViewById(R.id.accept);
        Accept.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(!(Invitation.isEmpty()))
                {
                    Date date = new Date();
                    DatabaseReference SenderInvite = FirebaseDatabase.getInstance().getReference("users").child(msenderuid).child("invite").child(mrecieveruid);
                    DatabaseReference ReciverInvite = FirebaseDatabase.getInstance().getReference("users").child(mrecieveruid).child("invite").child(msenderuid);
                    HashMap<String, Object> Inv = new HashMap<>();
                    Inv.put("status","Accepted");
                    Inv.put("timestamp",(long)date.getTime());

                    HashMap<String, Object> Inv2 = new HashMap<>();
                    Inv2.put("status","Accepted");
                    Inv2.put("timestamp",(long)date.getTime());
                    SenderInvite.updateChildren(Inv);
                    ReciverInvite.updateChildren(Inv2);

                }

            }
        });
        LinearLayout Decline = findViewById(R.id.decline);
        Decline.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if(!(Invitation.isEmpty()))
                {
                    Date date = new Date();
                    DatabaseReference SenderInvite = FirebaseDatabase.getInstance().getReference("users").child(msenderuid).child("invite").child(mrecieveruid);
                    DatabaseReference ReciverInvite = FirebaseDatabase.getInstance().getReference("users").child(mrecieveruid).child("invite").child(msenderuid);
                    HashMap<String, Object> Inv = new HashMap<>();
                    Inv.put("status","Rejected");
                    Inv.put("timestamp",(long)date.getTime());

                    HashMap<String, Object> Inv2 = new HashMap<>();
                    Inv2.put("status","Sent");
                    Inv2.put("timestamp",(long)date.getTime());
                    SenderInvite.updateChildren(Inv);
                    ReciverInvite.updateChildren(Inv2);

                }

            }
        });
        mimageviewofspecificuser.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                if((mrecieveruid != null) && (msenderuid != null) && (mrecievername != null))
                {
                    Intent intent = new Intent(SpecificChatActivity.this, ViewProfile.class);
                    intent.putExtra("currentUserId", msenderuid);
                    intent.putExtra("profileId", mrecieveruid);
                    intent.putExtra("Name", mrecievername);
                    startActivity(intent);
                }

            }
        });


        msendmessagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredmessage = mgetmessage.getText().toString();
                mgetmessage.getText().clear();
                if (enteredmessage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter message first", Toast.LENGTH_SHORT).show();
                } else {
                    currenttime = simpleDateFormat.format(calendar.getTime());
                    Date date = new Date();
                    if(Invitation.isEmpty())
                    {
                        DatabaseReference SenderInvite = FirebaseDatabase.getInstance().getReference("users").child(msenderuid).child("invite").child(mrecieveruid);
                        DatabaseReference ReciverInvite = FirebaseDatabase.getInstance().getReference("users").child(mrecieveruid).child("invite").child(msenderuid);
                        HashMap<String, Object> Inv = new HashMap<>();
                        Inv.put("status","Sent");
                        Inv.put("timestamp",(long)date.getTime());

                        HashMap<String, Object> Inv2 = new HashMap<>();
                        Inv2.put("status","Recieved");
                        Inv2.put("timestamp",(long)date.getTime());
                        SenderInvite.updateChildren(Inv);
                        ReciverInvite.updateChildren(Inv2);

                    }
                    else
                    {
                        DatabaseReference SenderInvite = FirebaseDatabase.getInstance().getReference("users").child(msenderuid).child("invite").child(mrecieveruid);
                        DatabaseReference ReciverInvite = FirebaseDatabase.getInstance().getReference("users").child(mrecieveruid).child("invite").child(msenderuid);
                        HashMap<String, Object> Inv = new HashMap<>();
//                        Inv.put("status","Sent");
                        Inv.put("timestamp",(long)date.getTime());

                        HashMap<String, Object> Inv2 = new HashMap<>();
//                        Inv2.put("status","Recieved");
                        Inv2.put("timestamp",(long)date.getTime());
                        SenderInvite.updateChildren(Inv);
                        ReciverInvite.updateChildren(Inv2);
                    }

                    Messages messages = new Messages(enteredmessage, firebaseAuth.getUid(), date.getTime() , getApplicationContext());
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference senderReference = firebaseDatabase.getReference().child("chats").child(senderroom).child("messages").push();
                    DatabaseReference receiverReference = firebaseDatabase.getReference().child("chats").child(recieverroom).child("messages").push();

                    senderReference.setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            receiverReference.setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    scrollToLastMessage();

                                }
                            });
                        }
                    });
                }
            }
        });

        mmessagerecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(check) {
                    // Check if the user is at the top of the list
                    if (layoutManager != null && layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                        // User is at the top, show the swipe-up message
                        swipeMessageTextView.setVisibility(View.VISIBLE);
                    } else {
                        // User is not at the top, hide the swipe-up message
                        swipeMessageTextView.setVisibility(View.GONE);
                    }
                }
            }
        });

//        mmessagerecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//
//                if (!isLoading && !isLastPage && linearLayoutManager != null && linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
//                    loadMoreMessages();
//                }
//            }
//        });

        loadInitialMessages();

    }

    private void setUpMessageListener() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                Messages newMessage = snapshot.getValue(Messages.class);
                if (newMessage != null) {
                    // Check if the message is newer than the last received message
                    if (newMessage.getTimestamp() > lastRecivedTimestamp) {
                        messagesArrayList.add(newMessage);
                        messagesAdapter.notifyItemInserted(messagesArrayList.size() - 1);
                        mmessagerecyclerview.scrollToPosition(messagesArrayList.size() - 1);

                        // Update the lastReceivedTimestamp
                        lastRecivedTimestamp = newMessage.getTimestamp();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Handle message changes if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle message removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Handle message movement if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        };

        // Order by timestamp and start at the last received timestamp
        Query messageQuery = firebaseDatabase
                .getReference()
                .child("chats")
                .child(senderroom)
                .child("messages")
                .orderByChild("timestamp")
                .startAt(lastRecivedTimestamp + 1); // Add 1 to exclude the last received timestamp

        messageQuery.addChildEventListener(childEventListener);
    }


    private void loadMoreMessages() {
        isLoading = true;

        DatabaseReference databaseReference = firebaseDatabase
                .getReference()
                .child("chats")
                .child(senderroom)
                .child("messages");

        databaseReference.orderByChild("timestamp")
                .endBefore(lastMessageKey)
                .limitToLast(PAGE_SIZE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Messages> newMessages = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Messages message = dataSnapshot.getValue(Messages.class);
                            if (message != null) {
                                message.setTimestamp(message.getTimestamp(), getApplicationContext());
                                newMessages.add(message);
                                lastMessageKey = Math.min(lastMessageKey, message.getTimestamp());
                            }
                        }

                        if (!newMessages.isEmpty()) {
//                            newMessages.remove(newMessages.size() - 1);

                            messagesArrayList.addAll(0, newMessages);
                            messagesAdapter.notifyDataSetChanged();

                            isLoading = false;

                            if (newMessages.size() < PAGE_SIZE) {
                                isLastPage = true;
                            }
                        } else {
                            isLastPage = true;
                            check = false;
                            swipeMessageTextView.setVisibility(View.GONE);

                        }
                        swipeRefreshLayout.setRefreshing(false);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        isLoading = false;
                    }
                });

    }

    private void loadInitialMessages() {
        DatabaseReference databaseReference = firebaseDatabase
                .getReference()
                .child("chats")
                .child(senderroom)
                .child("messages");

        databaseReference.orderByChild("timestamp")
                .limitToLast(PAGE_SIZE)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Messages> initialMessages = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Messages message = dataSnapshot.getValue(Messages.class);
                            if (message != null) {
                                initialMessages.add(message);
                                lastMessageKey = Math.min(lastMessageKey, message.getTimestamp());
                                lastRecivedTimestamp = Math.max(lastRecivedTimestamp,message.getTimestamp());
                            }
                        }
                        setUpMessageListener();

                        messagesArrayList.addAll(0,initialMessages);
                        messagesAdapter.notifyDataSetChanged();
                        if(initialMessages.size() < PAGE_SIZE)
                        {
                            check = false;
                            swipeMessageTextView.setVisibility(View.GONE);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
    }

    private void scrollToLastMessage() {
        mmessagerecyclerview.scrollToPosition(messagesArrayList.size() - 1);
    }

    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.notifyDataSetChanged();
//        if (childEventListener == null) {
//            // Re-add Firebase listeners or perform setup tasks if needed
//            setUpMessageListener();
//        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (childEventListener != null) {
            DatabaseReference messageReference = firebaseDatabase
                    .getReference()
                    .child("chats")
                    .child(senderroom)
                    .child("messages");
            messageReference.removeEventListener(childEventListener);
        }
        onBackPressed();
    }
}
