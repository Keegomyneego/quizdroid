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
        private String[] itemTitleText;
        private String[] itemDescriptionText;
        private int itemLayout;
        private int titleTextViewId;
        private int descriptionTextViewId;

        /**
         * Custom view holder for this adapter
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView titleTextView;
            public TextView descriptionTextView;

            public ViewHolder(View itemView, TextView titleTextView, TextView descriptionTextView) {
                super(itemView);
                this.titleTextView = titleTextView;
                this.descriptionTextView = descriptionTextView;
            }
        }

        public Adapter(Listener listener, String[] itemTitleText, String[] itemDescriptionText, int itemLayout, int titleTextViewId, int descriptionTextViewId) {
            this.listener = listener;
            this.itemTitleText = itemTitleText;
            this.itemDescriptionText = itemDescriptionText;
            this.itemLayout = itemLayout;
            this.titleTextViewId = titleTextViewId;
            this.descriptionTextViewId = descriptionTextViewId;
        }

        @Override
        public MasterDetailView.Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
            TextView titleTextView = (TextView) itemView.findViewById(titleTextViewId);
            TextView descriptionTextView = (TextView) itemView.findViewById(descriptionTextViewId);

            return new ViewHolder(itemView, titleTextView, descriptionTextView);
        }

        @Override
        public void onBindViewHolder(MasterDetailView.Adapter.ViewHolder holder, final int position) {
            // Set the text for this cell based on its position
            holder.titleTextView.setText(itemTitleText[position]);
            holder.descriptionTextView.setText(itemDescriptionText[position]);

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
            return itemTitleText.length;
        }
    }
}