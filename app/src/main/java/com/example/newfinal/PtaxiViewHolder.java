package com.example.newfinal;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PtaxiViewHolder extends RecyclerView.ViewHolder {
    public TextView ivStime,ivEtime, ivlimit, ivpeople, ivtaxirow;
    public Button iventer;

    public PtaxiViewHolder(View itemView) {
        super(itemView);
        ivStime = itemView.findViewById(R.id.starttime);
        ivEtime = itemView.findViewById(R.id.endtime);
        ivlimit = itemView.findViewById(R.id.limit);
        ivpeople= itemView.findViewById(R.id.people);
        iventer = itemView.findViewById(R.id.enter);
        ivtaxirow = itemView.findViewById(R.id.datarow);
    }
}
