package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich currentSandwich) {

        if (currentSandwich.getAlsoKnownAs() != null && !currentSandwich.getAlsoKnownAs().isEmpty()) {
            // set views
            // Find the tv with the id also_known_tv
            TextView alsoKnownAsTv = (TextView) findViewById(R.id.also_known_tv);
            alsoKnownAsTv.setText(toString().valueOf(currentSandwich.getAlsoKnownAs()));
            for (int i = 0; i < currentSandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownAsTv.append(currentSandwich.getAlsoKnownAs().get(i) + "\n");
            }
        } else {
            // hide views
            TextView alsoKnownAsLabel = (TextView) findViewById(R.id.aka_label);
            alsoKnownAsLabel.setVisibility(View.GONE);
        }

        // Find the tv with the id ingredients_tv
        TextView ingredientsTv = (TextView) findViewById(R.id.ingredients_tv);
        ingredientsTv.setText(toString().valueOf(currentSandwich.getIngredients()));
        //for (int i = 0; i < currentSandwich.getIngredients().size(); i++) {
        //    ingredientsTv.append(currentSandwich.getIngredients().get(i) + "\n");
        //}

        if (!TextUtils.isEmpty(currentSandwich.getPlaceOfOrigin())) {
            // Put string into textview
            TextView originTv = (TextView) findViewById(R.id.origin_tv);
            originTv.setText(toString().valueOf(currentSandwich.getPlaceOfOrigin()));
        } else {
            // hide views
            TextView placeOfOriginLabel = (TextView) findViewById(R.id.origin_label);
            placeOfOriginLabel.setVisibility(View.GONE);
        }

        // Find the tv with the id description_tv
        TextView descriptionTv = (TextView) findViewById(R.id.description_tv);
        descriptionTv.setText(toString().valueOf(currentSandwich.getDescription()));
    }

}
