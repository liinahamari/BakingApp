package com.example.guest.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.guest.bakingapp.R;
import com.example.guest.bakingapp.data.local.pojo.IngredientLocal;
import com.example.guest.bakingapp.data.local.pojo.RecipeLocal;
import com.example.guest.bakingapp.data.remote.pojo.IngredientRemote;
import com.example.guest.bakingapp.data.remote.pojo.RecipeRemote;
import com.example.guest.bakingapp.utils.RxThreadManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.guest.bakingapp.data.local.Provider.URI_INGREDIENTS;

/**
 * Created by l1maginaire on 5/19/18.
 */

public class WidgetConfigActivity extends AppCompatActivity {

//    @Inject
//    WidgetDataHelper widgetDataHelper;

    private CompositeDisposable disposableList = new CompositeDisposable();
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @BindView(R.id.radioGroup)
    RadioGroup namesRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_widget_config);
        ButterKnife.bind(this);
//        DaggerWidgetDataHelperComponent.builder()
//                .recipeRepositoryComponent(
//                        ((BakingApp) getApplication()).getRecipeRepositoryComponent())
//                .build()
//                .inject(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }
        }
        List<RecipeRemote> recipes = new ArrayList<>();
        Single.fromCallable(() -> getApplicationContext().getContentResolver().query(URI_INGREDIENTS, null, null,
                new String[]{String.valueOf(1)}, null))
                .compose(RxThreadManager.manageSingle())
                .doOnError(throwable -> Log.e("TAG", "Something is wrong with App-class"))
                .subscribe(cursor -> {
                    if (cursor.getCount() > 0) {
                        cursor.moveToPosition(-1);
                        while (cursor.moveToNext()) {
                            RecipeRemote ingredient = new RecipeRemote();
                            ingredient.setId(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeLocal.COLUMN_RECIPE_ID)));
                            ingredient.setImage(cursor.getString(cursor.getColumnIndexOrThrow(RecipeLocal.COLUMN_IMAGE)));
                            ingredient.setName(cursor.getString(cursor.getColumnIndexOrThrow(RecipeLocal.COLUMN_NAME)));
                            ingredient.setServings(cursor.getInt(cursor.getColumnIndexOrThrow(RecipeLocal.COLUMN_SERVINGS)));
                            recipes.add(ingredient);
                        }
                    }

                    int currentIndex = 0;

                    for (RecipeRemote recipe : recipes) {
                        AppCompatRadioButton button = new AppCompatRadioButton(this);
                        button.setText(recipe.getName());
                        button.setId(currentIndex++);
                        namesRadioGroup.addView(button);
                    }

                    // Check the first item when loaded
                    if (namesRadioGroup.getChildCount() > 0) {
                        ((AppCompatRadioButton) namesRadioGroup.getChildAt(0)).setChecked(true);
                    }
                });
//        Set<String> names = widgetDataHelper.getRecipeNamesFromPrefs();

//        if (names.size() == 0) {
//            Toast.makeText(this, noDataErrorMessage, Toast.LENGTH_SHORT).show();
//            finish();
//        }

        // Fill the radioGroup

    }

    @OnClick(R.id.button)
    public void onOkButtonClick() {

        int checkedItemId = namesRadioGroup.getCheckedRadioButtonId();
        String recipeName = ((AppCompatRadioButton) namesRadioGroup
                .getChildAt(checkedItemId)).getText().toString();

//        widgetDataHelper.saveRecipeNameToPrefs(appWidgetId, recipeName);

        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

       /* Disposable subscription = widgetDataHelper
                .getIngredientsList(recipeName)
                .subscribe(
                        // OnNext
                        ingredients ->
                                WidgetProvider
                                        .updateAppWidgetContent(context, appWidgetManager, appWidgetId, recipeName,
                                                ingredients),
                        // OnError
                        throwable ->
                                Log.d("TAG","Error: unable to populate widget data."));
*//*
        disposableList.add(subscription);
*/
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposableList.clear();
    }
}
