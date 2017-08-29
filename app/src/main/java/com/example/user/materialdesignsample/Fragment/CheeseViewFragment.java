package com.example.user.materialdesignsample.Fragment;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.user.materialdesignsample.Models.Cheeses;
import com.example.user.materialdesignsample.R;

/**
 * <h1><font color="orange">CheeseViewFragment</font></h1>
 * Fragment class for showing list of Cheese Images.
 *
 * @author Divya Khanduri
 */
public class CheeseViewFragment extends Fragment {
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_cheese_view, container, false);
        setupRecyclerView(recyclerView);
        return recyclerView;
    }

    /**
     * Setting up Recycler View with  Staggered Grid Layout Manager
     */
    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        CustomCheeseViewAdapter customCheeseViewAdapter = new CustomCheeseViewAdapter(mContext);
        recyclerView.setAdapter(customCheeseViewAdapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    /**
     * Adapter for cheese list recycler view
     */
    private class CustomCheeseViewAdapter extends RecyclerView.Adapter<CustomCheeseViewAdapter.ViewHolder> {
        Context context;

        CustomCheeseViewAdapter(Context mContext) {
            this.context = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
            final View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cheese_view_row_layout, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CustomCheeseViewAdapter.ViewHolder holder, int position) {
            holder.imageView.setImageResource(Cheeses.getRandomCheeseDrawable());
            final Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.imageView.startAnimation(anim);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_cheese);
            }
        }
    }

    /**
     * Class to provide spaces between grid views
     */
    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int mSpace;

        SpacesItemDecoration(int space) {
            this.mSpace = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;

            if (parent.getChildAdapterPosition(view) == 0)
                outRect.top = mSpace;
        }
    }
}
