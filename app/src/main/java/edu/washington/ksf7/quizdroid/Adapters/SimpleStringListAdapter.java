package edu.washington.ksf7.quizdroid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.washington.ksf7.quizdroid.R;

/**
 * Created by keegomyneego on 1/30/17.
 */

public class SimpleStringListAdapter extends RecyclerView.Adapter<SimpleStringListAdapter.ViewHolder> {

    private List<String> titles;
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

    public SimpleStringListAdapter(List<String> titles, int itemLayout, int textViewId) {
        this.titles = titles;
        this.itemLayout = itemLayout;
        this.textViewId = textViewId;
    }

    @Override
    public SimpleStringListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false);
        TextView textView = (TextView) itemView.findViewById(textViewId);

        return new ViewHolder(itemView, textView);
    }

    @Override
    public void onBindViewHolder(SimpleStringListAdapter.ViewHolder holder, int position) {
        holder.textView.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
