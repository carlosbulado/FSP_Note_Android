package com.carlosbulado.fsp_note.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.adapter.AllCategoriesAdapter;
import com.carlosbulado.fsp_note.adapter.AllNotesAdapter;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.domain.Category;
import com.carlosbulado.fsp_note.domain.Note;

import java.util.ArrayList;

public class CategoryListPageActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private AllCategoriesAdapter adapter;
    private ArrayList<Category> allCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_page);
        setTitle("All Categories");
        this.recyclerView = findViewById(R.id.allCategoriesGrid);

        // Fetch all Subjects
        this.allCategories = APP.Services.categoryService.getRepository().getAll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayout.VERTICAL));
        this.adapter = new AllCategoriesAdapter(this, this.allCategories);
        this.recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume()
    {
        this.allCategories = APP.Services.categoryService.getRepository().getAll();
        this.recyclerView.refreshDrawableState();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_note_list_activity, menu);
        menu.getItem(0).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_category_list_notes:
                APP.goTo(this, MainActivity.class);
                break;
            case R.id.menu_note_list_new:
                APP.goTo(this, CategoryPageActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
