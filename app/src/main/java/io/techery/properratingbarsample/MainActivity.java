package io.techery.properratingbarsample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.techery.properratingbar.ProperRatingBar;
import io.techery.properratingbar.RatingListener;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.rootView)
    ViewGroup rootView;
    @Bind(R.id.upperRatingBar)
    ProperRatingBar upperRatingBar;
    @Bind(R.id.lowerRatingBar)
    ProperRatingBar lowerRatingBar;
    @Bind(R.id.toggleClicksEnabledButton)
    Button toggleClicksEnabledButton;

    private String[] ticksArray = new String[] {"$", "★"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        lowerRatingBar.setListener(ratingListener);
    }

    @OnClick(R.id.toggleTick) void toggleTick() { // here goes shit-code. No time to do properly
        String tick;
        if (upperRatingBar.getSymbolicTick().equals(ticksArray[0])) tick = ticksArray[1];
        else tick = ticksArray[0];
        upperRatingBar.setSymbolicTick(tick);
    }

    @OnClick(R.id.getRatingButton) void getRatingClicked() {
        Snackbar.make(rootView,
                String.format(getString(R.string.get_rating_snack_caption_format), lowerRatingBar.getRating()),
                Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.setRatingButton) void setRatingClicked() {
        lowerRatingBar.setRating(2);
        Snackbar.make(rootView, R.string.set_rating_snack_caption, Snackbar.LENGTH_SHORT).show();
    }

    @OnClick(R.id.toggleClicksEnabledButton) void toggleClicksEnabled() {
        lowerRatingBar.toggleClickable();
        toggleClicksEnabledButton.setText(lowerRatingBar.isClickable() ?
                R.string.disable_clicks_button_caption : R.string.enable_clicks_button_caption);
        Snackbar.make(rootView,
                lowerRatingBar.isClickable() ? R.string.enabled_clicks_snack_caption :
                        R.string.disabled_clicks_snack_caption,
                Snackbar.LENGTH_SHORT).show();
    }

    private RatingListener ratingListener = new RatingListener() {
        @Override
        public void onRatePicked(ProperRatingBar ratingBar) {
            Snackbar.make(rootView,
                    String.format(getString(R.string.rating_listener_snack_caption), ratingBar.getRating()),
                    Snackbar.LENGTH_SHORT).show();
        }
    };
}
