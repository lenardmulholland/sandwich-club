package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnownAsLabel;
    private TextView mAlsoKnownAsText;
    private TextView mIngredientsLabel;
    private TextView mIngredientsText;
    private TextView mPlaceOfOriginLabel;
    private TextView mPlaceOfOriginText;
    private TextView mDescriptionLabel;
    private TextView mDescriptionText;

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

        findViews();
        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void findViews() {
        mAlsoKnownAsLabel = findViewById(R.id.also_known_label);
        mAlsoKnownAsText = findViewById(R.id.also_known_tv);
        mIngredientsLabel = findViewById(R.id.ingredients_label);
        mIngredientsText = findViewById(R.id.ingredients_tv);
        mPlaceOfOriginLabel = findViewById(R.id.origin_label);
        mPlaceOfOriginText = findViewById(R.id.origin_tv);
        mDescriptionLabel = findViewById(R.id.description_label);
        mDescriptionText = findViewById(R.id.description_tv);
    }

    public StringBuilder listToText(List<String> currentList) {
        StringBuilder listText= new StringBuilder();
        for (int i=0;i<currentList.size();i++){
            listText.append(currentList.get(i)).append(", ");
        }
        return listText;
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getAlsoKnownAs().isEmpty()){
            mAlsoKnownAsLabel.setVisibility(View.GONE);
            mAlsoKnownAsText.setVisibility(View.GONE);
        }else {
            mAlsoKnownAsText.setText(listToText(sandwich.getAlsoKnownAs()));
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()){
            mPlaceOfOriginLabel.setVisibility(View.GONE);
            mPlaceOfOriginText.setVisibility(View.GONE);
        }else {
            mPlaceOfOriginText.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getIngredients().isEmpty()){
            mIngredientsLabel.setVisibility(View.GONE);
            mIngredientsText.setVisibility(View.GONE);
        }else {
            mIngredientsText.setText(listToText(sandwich.getIngredients()));
        }

        if (sandwich.getDescription().isEmpty()){
            mDescriptionLabel.setVisibility(View.GONE);
            mDescriptionText.setVisibility(View.GONE);
        }else {
            mDescriptionText.setText(sandwich.getDescription());
        }
    }
}
