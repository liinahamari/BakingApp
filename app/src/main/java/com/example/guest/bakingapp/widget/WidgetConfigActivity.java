package com.example.guest.bakingapp.widget;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.widget.RadioGroup;

import com.example.guest.bakingapp.App;
import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.data.local.pojo.IngredientLocal;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.data.remote.pojo.IngredientRemote;
import com.example.guest.bakingapp.utils.RxThreadManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

import static com.example.guest.bakingapp.data.local.Provider.URI_INGREDIENTS;

/**
 * Created by l1maginaire on 5/19/18.
 */

public class WidgetConfigActivity extends AppCompatActivity {
    private CompositeDisposable disposableList = new CompositeDisposable();
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private List<Integer> idList = new ArrayList<>();

    @BindView(R.id.radioGroup)
    RadioGroup namesRadioGroup;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_widget_config);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }
        Single.fromCallable(() -> ((App) getApplication()).dbInstance.reciepe().getRecipes())
                .compose(RxThreadManager.manageSingle())
                .doOnError(throwable -> Log.e("TAG", "Something is wrong with App-class"))
                .subscribe(recipeList -> {
                    int currentIndex = 0;
                    for (RecipeLocal recipe : recipeList) {
                        idList.add(recipe.recipeId);
                        AppCompatRadioButton button = new AppCompatRadioButton(this);
                        button.setText(recipe.name);
                        button.setId(currentIndex++);
                        namesRadioGroup.addView(button);
                    }
                    if (namesRadioGroup.getChildCount() > 0) {
                        ((AppCompatRadioButton) namesRadioGroup.getChildAt(0)).setChecked(true);
                    }
                });
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.button)
    public void onOkButtonClick() {
        int checkedItemId = namesRadioGroup.getCheckedRadioButtonId();
        String s = String.valueOf(idList.get(checkedItemId));
        String recipeName = ((AppCompatRadioButton) namesRadioGroup
                .getChildAt(checkedItemId)).getText().toString();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        List<IngredientRemote> ingredients = new ArrayList<>();
        Single.fromCallable(() -> getApplicationContext().getContentResolver().query(URI_INGREDIENTS, null, null,
                new String[]{s}, null))
                .compose(RxThreadManager.manageSingle())
                .doOnError(throwable -> Log.e("TAG", "Something is wrong with App-class"))
                .subscribe(cursor -> {
                    if (cursor.getCount() > 0) {
                        cursor.moveToPosition(-1);
                        while (cursor.moveToNext()) {
                            IngredientRemote ingredientRemote = new IngredientRemote();
                            ingredientRemote.setIngredient(cursor.getString(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_INGREDIENT)));
                            ingredientRemote.setMeasure(cursor.getString(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_MEASURE)));
                            ingredientRemote.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(IngredientLocal.COLUMN_QUANTITITY)));
                            ingredients.add(ingredientRemote);
                        }
                    }
                    WidgetProvider.updateAppWidgetContent(getApplicationContext(), appWidgetManager, appWidgetId, recipeName, ingredients);
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                    setResult(RESULT_OK, resultValue);
                    finish();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposableList.clear();
    }
}
