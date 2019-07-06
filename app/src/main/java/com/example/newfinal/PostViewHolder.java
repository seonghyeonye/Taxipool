package com.example.newfinal;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView ivname, ivnumber, ivemail;
    public LinearLayout ivrow;
    public PostViewHolder(View itemView) {
        super(itemView);
        ivname= itemView.findViewById(R.id.phoneBookItem_name);
        ivnumber= itemView.findViewById(R.id.phoneBookItem_phoneNumber);
        ivemail=itemView.findViewById(R.id.phoneBookItem_email);
        ivrow=itemView.findViewById(R.id.phoneBookItem_default);
    }
}

