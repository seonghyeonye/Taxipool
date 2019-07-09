package com.example.newfinal;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ChatHolder extends RecyclerView.ViewHolder {
    ImageView image_message_profile;
    public TextView text_message_name, text_message_body;
    public ChatHolder(View itemView) {
        super(itemView);
        image_message_profile=itemView.findViewById(R.id.image_message_profile);
        text_message_name=itemView.findViewById(R.id.text_message_name);
        text_message_body=itemView.findViewById(R.id.text_message_body);
    }
}
