package io.techery.properratingbarsample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.techery.properratingbar.ProperRatingBar;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rootView)
    ViewGroup rootView;
    @Bind(R.id.ratingBar)
    ProperRatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.getRatingButton) void getRatingClicked() {
        Snackbar.make(rootView, "RatingBar rating: " + ratingBar.getRating(), Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.setRatingButton) void setRatingClicked() {
        ratingBar.setRating(2);
    }
}
