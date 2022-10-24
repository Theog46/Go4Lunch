package com.example.go4lunch.fragments.Chat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.Message;
import com.example.go4lunch.R;
import com.example.go4lunch.ViewModel.FirestoreViewModel;
import com.example.go4lunch.ViewModelFactory;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MessagesFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView rv;
    private FirestoreRecyclerAdapter adapter;
    private TextInputLayout textInputLayout;
    private FloatingActionButton floatingActionButton;
    private FirebaseAuth mAuth;
    FirestoreViewModel firestoreViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        rv = v.findViewById(R.id.fragment_chat_recycler_view);
        textInputLayout = v.findViewById(R.id.message);
        floatingActionButton = v.findViewById(R.id.send_message);

        firestoreViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(FirestoreViewModel.class);

        Query query = firebaseFirestore.collection("messages");

        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Message, MessagesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessagesViewHolder holder, int position, @NonNull Message message) {
                holder.username.setText(message.getUsername());
                holder.date.setText(message.getDate());
                holder.content.setText(message.getMessage());
                Glide.with(holder.context)
                        .load(message.getUrlPicture())
                        .apply(new RequestOptions()
                                .circleCrop())
                        .into(holder.picture);
            }

            @NonNull
            @Override
            public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_item, parent, false);
                return new MessagesViewHolder(v);
            }
        };

        rv.setLayoutManager(new LinearLayoutManager(v.getContext()));
        rv.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                String text = textInputLayout.getEditText().getText().toString();
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
                String formattedDate = format.format(date);
                firestoreViewModel.postMessage(user.getUid(), user.getDisplayName(), user.getPhotoUrl().toString(), text, formattedDate);

            }

        });

    return v;
    }

    private class MessagesViewHolder extends RecyclerView.ViewHolder {

        private final TextView username = itemView.findViewById(R.id.message_username);
        private final TextView date = itemView.findViewById(R.id.message_date);
        private final ImageView picture = itemView.findViewById(R.id.message_picture);
        private final TextView content = itemView.findViewById(R.id.message_content);
        private final Context context;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.startListening();
    }
}
