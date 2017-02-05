package edu.washington.ksf7.quizdroid.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by keegomyneego on 1/30/17.
 */

public class MasterDetailView {

    static final String TAG = "QuizActivity";

    /**
     * Listener to which event handling logic is delegated
     */
    public interface Listener {
        void onDetailViewClicked(View view, int position);
    }

    /**
     * Adapter for the  RecyclerView
     */
    public static class Adapter extends RecyclerView.Adapter<MasterDetailView.Adapter.ViewHolder> {

        private Listener listener;
        private List<String> itemText;
        private int itemLayout;
        private int textViewId;

        /**
         * Custom view holder for this adapter
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View itemView, TextView textView) {
                super(itemView);
                this.textView = textView;
            }
        }

        public Adapter(Listener listener, List<String> itemText, int itemLayout, int textViewId) {
            this.listener = listener;
            this.itemText = itemText;
            this.itemLayout = itemLayout;
            this.textViewId = textViewId;
        }

        @Override
        public MasterDetailView.Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
            TextView textView = (TextView) itemView.findViewById(textViewId);

            return new ViewHolder(itemView, textView);
        }

        @Override
        public void onBindViewHolder(MasterDetailView.Adapter.ViewHolder holder, final int position) {
            // Set the text for this cell based on its position
            holder.textView.setText(itemText.get(position));

            // Set this cell's onClickListener
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Delegate click logic to listener
                    listener.onDetailViewClicked(view, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemText.size();
        }
    }
}