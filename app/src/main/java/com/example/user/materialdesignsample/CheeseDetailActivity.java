package com.example.user.materialdesignsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class CheeseDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = CheeseDetailActivity.class.getSimpleName();

    public static final String EXTRA_NAME = "cheese_name";

    private ImageView mImageViewSmall;
    private ImageView mImageViewLarge;

    private FloatingActionButton mFab;

    private int mRandomCheese;
    private Button mBtnOk;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheese_detail);
        // set an exit transition

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);
        mImageViewSmall = (ImageView) findViewById(R.id.backdrop);
        mImageViewLarge = (ImageView) findViewById(R.id.imageView_large);

        mBtnOk=(Button)findViewById(R.id.button_ok);
        mBtnOk.setOnClickListener(this);

        mFab=(FloatingActionButton)findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        mImageViewSmall.setOnClickListener(this);

        mImageViewLarge.setOnClickListener(this);

        loadBackdrop();
    }

    private void loadBackdrop() {
        mRandomCheese= Cheeses.getRandomCheeseDrawable();
        Glide.with(this).load(mRandomCheese).centerCrop().into(mImageViewSmall);

//        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(mImageViewLarge);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_cheese_detail_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isChecked()){
            item.setChecked(false);
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.settings:
                item.setChecked(true);
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                break;

            case R.id.about:
                item.setChecked(true);
                Toast.makeText(CheeseDetailActivity.this, android.os.Build.MODEL,Toast.LENGTH_LONG).show();
                break;

            default:
                Log.e(TAG, getString(R.string.wrong_case_selection));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void showReveal() {
        View myView = findViewById(R.id.imageView_large);

     // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

     // get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(cx, cy);

     // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

     // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void hideReveal() {
        final View myView = findViewById(R.id.imageView_large);

     // get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

     // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

     // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

     // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                myView.setVisibility(View.GONE);
            }
        });

     // start the animation
        anim.start();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                Intent intent = new Intent("android.intent.action.VIEW");
                Uri data = Uri.parse("sms:");
                intent.setData(data);
                startActivity(intent);
                break;
            case R.id.imageView_large:
                mFab.setVisibility(View.VISIBLE);
                hideReveal();
                break;
            case R.id.backdrop:
                Glide.with(CheeseDetailActivity.this).load(mRandomCheese).centerCrop().into(mImageViewLarge);
                mFab.setVisibility(View.GONE);
                showReveal();
                break;
            case R.id.button_ok:
                break;
            default:
                Log.e(TAG, getString(R.string.wrong_case_selection));
                break;
        }
    }

}
