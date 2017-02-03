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

    /**
     * Adapter for the  RecyclerView
     */
    public static class Adapter extends RecyclerView.Adapter<MasterDetailView.Adapter.ViewHolder> {

        private Context context;
        private List<String> itemText;
        private Class<?> detailFragmentClass;
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

        public Adapter(Context context, List<String> itemText, Class<?> detailFragmentClass, int itemLayout, int textViewId) {
            this.context = context;
            this.itemText = itemText;
            this.detailFragmentClass = detailFragmentClass;
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
                    Intent intent = new Intent(context, detailFragmentClass);
                    Controller.setDetailPosition(intent, position);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemText.size();
        }
    }

    /**
     * Controller to handle additional logic
     */

    public static class Controller {

        private static final String TAG = "Controller";

        public static final String DETAIL_POSITION_INTENT_KEY = "MasterDetailView.DetailPosition";

        public static void setDetailPosition(Intent intent, int position) {
            intent.putExtra(DETAIL_POSITION_INTENT_KEY, position);
        }

        public static int getDetailPosition(Intent intent) {
            int position = intent.getIntExtra(DETAIL_POSITION_INTENT_KEY, -1);
            if (position == -1) {
                Log.e(TAG, "No position found in intent!");
            }

            return position;
        }
    }
}