package com.example.user.materialdesignsample.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.materialdesignsample.R;

/**
 * <h1><font color="orange">ModelBottomsheetFragment</font></h1>
 * Fragment class for showing Model Bottomsheet with recycler view.
 *
 * @author Divya Khanduri
 */

public class ModelBottomsheetFragment extends BottomSheetDialogFragment {

    private static final String ARG_ITEMS = "items";

    public static ModelBottomsheetFragment newInstance(int[] itemCount) {
        final ModelBottomsheetFragment fragment = new ModelBottomsheetFragment();
        final Bundle args = new Bundle();
        args.putIntArray(ARG_ITEMS, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ItemAdapter(getArguments().getIntArray(ARG_ITEMS)));
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView mImageView;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_item_list_dialog_item, parent, false));
            mImageView = itemView.findViewById(R.id.iv_background);
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        private int[] mDataset;

        ItemAdapter(int[] myDataset) {
            this.mDataset = myDataset;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mImageView.setImageResource(mDataset[position]);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }

    }

}
