package edu.washington.ksf7.quizdroid.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.washington.ksf7.quizdroid.TopicOverviewActivity;

/**
 * Created by keegomyneego on 1/30/17.
 */

public class MasterDetailAdapter extends RecyclerView.Adapter<MasterDetailAdapter.ViewHolder> {

    private Context context;
    private List<String> itemText;
    private int itemLayout;
    private int textViewId;

    // Custom view holder for this adapter
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View itemView, TextView textView) {
            super(itemView);
            this.textView = textView;
        }
    }

    public MasterDetailAdapter(Context context, List<String> itemText, int itemLayout, int textViewId) {
        this.context = context;
        this.itemText = itemText;
        this.itemLayout = itemLayout;
        this.textViewId = textViewId;
    }

    @Override
    public MasterDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false);
        TextView textView = (TextView) itemView.findViewById(textViewId);

        return new ViewHolder(itemView, textView);
    }

    @Override
    public void onBindViewHolder(MasterDetailAdapter.ViewHolder holder, final int position) {
        holder.textView.setText(itemText.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TopicOverviewActivity.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemText.size();
    }
}
