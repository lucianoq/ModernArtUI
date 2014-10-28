package it.lusio.android.modernartui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


/**
 * Main Activity of the App ModernArtUi
 * Week 4 assignment of Coursera course "Programming Mobile Applications for Android Handheld Systems"
 * by Dr. Adam Porter
 *
 * @author Luciano Quercia
 * @version 1.0 2014/10/28
 */
public class MainActivity extends Activity {

    public static final String TAG = "it.lusio.android.modernartui";
    public static final int ROWS = 13;
    public static final int COLUMNS = 5;

    private final List<LinearLayout> mRows = new ArrayList<LinearLayout>(ROWS);
    private final List<Rectangle> mRects = new ArrayList<Rectangle>(ROWS * COLUMNS + ROWS);
    private final List<Integer> mLeWittColors = new ArrayList<Integer>() {{
        add(Color.rgb(180, 49, 5)); // Red
        add(Color.rgb(0, 104, 165)); // Blue
        add(Color.rgb(237, 197, 1)); // Yellow
        add(Color.rgb(0, 132, 13)); // Green
        add(Color.rgb(94, 64, 134)); // Violet
        add(Color.rgb(230, 98, 0)); // Orange
        add(Color.rgb(155, 155, 155)); // Gray (mandatory)
    }};
    private final Random mRandom = new Random(System.nanoTime());
    private SeekBar mSeekBar;
    private LinearLayout mContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContent = (LinearLayout) findViewById(R.id.content);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);

        leWitt();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeColor(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }


    /**
     * Constructs the Activity in a non-deterministic way.
     * The aspect is similar to "Wall Drawing #1144, Broken Bands of Color in Four Directions"
     * by Sol LeWitt. ( ref. http://www.moma.org/collection/object.php?object_id=94278 )
     */
    private void leWitt() {
        Log.i(TAG, "Constructing UI");
        for (int i = 0; i < ROWS; i++) {
            LinearLayout horizontal = new LinearLayout(this);
            horizontal.setOrientation(LinearLayout.HORIZONTAL);
            horizontal.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));


            int previousColor = randomColor();
            for (int j = 0; j < COLUMNS + plusOrMinusOne(); j++) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 7 + plusOrMinusOne()));
                imageView.setImageResource(R.drawable.rectangle);


                Set<Integer> setOfColors = new HashSet<Integer>();

                if (i > 0) {
                    for (int k = j - 1; k <= j + 1; k++) {
                        View view = mRows.get(i - 1).getChildAt(k);
                        if (view != null) {
                            setOfColors.add(((ColorDrawable) view.getBackground()).getColor());
                        }
                    }
                }

                setOfColors.add(previousColor);

                int color = randomColor(setOfColors);
                imageView.setBackgroundColor(color);
                previousColor = color;

                mRects.add(new Rectangle(imageView, color));
                horizontal.addView(imageView);
            }
            mRows.add(horizontal);
            mContent.addView(horizontal);
        }
    }


    /**
     * Returns a random integer in {-1; 0; +1}
     *
     * @return integer color
     */
    private int plusOrMinusOne() {
        return new Random().nextInt(3) - 1;
    }


    /**
     * Returns one of seven LeWitt colors
     *
     * @return integer color
     */
    private int randomColor() {
        return mLeWittColors.get(mRandom.nextInt(mLeWittColors.size()));
    }

    /**
     * Returns one of seven LeWitt colors different from colors in input
     *
     * @param colorsToBeDifferent arbitrary numbers of integer colors
     * @return integer color
     */
    private int randomColor(int... colorsToBeDifferent) {
        Set<Integer> set = new HashSet<Integer>(colorsToBeDifferent.length);
        for (int diff : colorsToBeDifferent)
            set.add(diff);

        int color;
        while (set.contains(color = randomColor())) {
        }
        return color;
    }

    /**
     * Returns one of seven LeWitt colors different from colors in the Set in input
     *
     * @param colorsToBeDifferent Set of integer colors
     * @return integer color
     */
    private int randomColor(Set<Integer> colorsToBeDifferent) {
        int color;
        while (colorsToBeDifferent.contains(color = randomColor())) {
        }
        return color;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.more_info) {
            Log.i(TAG, "Calling dialog 'More info'");
            new VisitMomaDialog().show(getFragmentManager(), "more_info");
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Changes colors of rectangles of Activity
     * while the seekBar changes.
     *
     * @param progress integer level of seekBar
     */
    private void changeColor(int progress) {
        for (Rectangle rect : mRects) {
            if (rect.getOriginalColor() != Color.rgb(155, 155, 155)) { //only not gray rectangles
                int originalColor = rect.getOriginalColor();
                int red = Color.red(originalColor);
                int green = Color.green(originalColor);
                int blue = Color.blue(originalColor);

                if (red < 128) red += progress;
                else red -= progress;

                if (green < 128) green += progress;
                else green -= progress;

                if (blue < 128) blue = progress;
                else blue -= progress;

                rect.getImageView().setBackgroundColor(Color.rgb(red, green, blue));
            }
        }
    }
}