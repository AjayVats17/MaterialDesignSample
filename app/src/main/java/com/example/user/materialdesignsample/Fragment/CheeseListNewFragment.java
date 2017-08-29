package com.example.user.materialdesignsample.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.materialdesignsample.Activity.NewCheeseDetailActivity;
import com.example.user.materialdesignsample.Models.Cheeses;
import com.example.user.materialdesignsample.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Divya Khanduri
 */

public class CheeseListNewFragment extends Fragment {
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_cheese_list_new, container, false);
        setupRecyclerView(rv);
        return rv;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;

    }

    /**
     * SetUp Recycler View
     */

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CheeseListNewFragment.SimpleStringRecyclerViewAdapter(mContext, getRandomSublist(Cheeses.sCheeseStrings, 30)));
    }

    /**
     *Get Random Cheese names
     */

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    /**
     * Adapter for cheese list recycler view
     */

    class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<CheeseListNewFragment.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        //  private int mBackground;
        private List<String> mValues;

        // Allows to remember the last item shown on screen
        private int lastPosition = -1;

        class ViewHolder extends RecyclerView.ViewHolder {
            String mBoundString;

            final View mView;
            final CardView mCardView;
            final ImageView mImageView;
            final TextView mTextView;

            ViewHolder(View view) {
                super(view);
                mView = view;
                mCardView=view.findViewById(R.id.card_view);
                mImageView = view.findViewById(R.id.avatar);
                mTextView = view.findViewById(android.R.id.text1);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        SimpleStringRecyclerViewAdapter(Context context, List<String> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
//            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public CheeseListNewFragment.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cheese_list_row_new, parent, false);
//            view.setBackgroundResource(mBackground);
            return new CheeseListNewFragment.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CheeseListNewFragment.SimpleStringRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mBoundString = mValues.get(position);
            holder.mTextView.setText(mValues.get(position));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, NewCheeseDetailActivity.class);
                    intent.putExtra(NewCheeseDetailActivity.EXTRA_NAME, holder.mBoundString);
                    // For exit Transition
                   /* Bundle bundle= ActivityOptions.makeSceneTransitionAnimation(getActivity(),view,view.getTransitionName()).toBundle();
                    mContext.startActivity(intent,bundle);*/

                    transitionTo(intent);

                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(Cheeses.getRandomCheeseDrawable())
                    .fitCenter()
                    .into(holder.mImageView);

            // Here you apply the animation when the view is bound
            setAnimation(holder.mView, position);
        }


        /**
         * Here is the key method to apply the animation
         */

        private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        @Override
        public void onViewDetachedFromWindow(CheeseListNewFragment.SimpleStringRecyclerViewAdapter.ViewHolder holder) {
            holder.mView.clearAnimation();
            super.onViewDetachedFromWindow(holder);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }

    /**
     * Transition to enter new Activity
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unchecked") void transitionTo(Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(getActivity(), true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }
}
