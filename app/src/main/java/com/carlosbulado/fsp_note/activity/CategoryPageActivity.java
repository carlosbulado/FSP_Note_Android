package com.carlosbulado.fsp_note.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.domain.Category;
import com.carlosbulado.fsp_note.domain.Note;

public class CategoryPageActivity extends AppCompatActivity
{
    EditText title;
    String categoryId;
    Category category;
    boolean isNew;
    static CategoryPageActivity categoryPageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        setTitle("New Category");

        this.isNew = true;
        this.title = findViewById(R.id.category_page_title);
        this.category = new Category();
        CategoryPageActivity.categoryPageActivity = this;

        Intent previousPage = getIntent();
        Bundle mBundle = previousPage.getExtras();
        this.categoryId = mBundle != null ? mBundle.getString("categoryId") : "";
        if(this.categoryId != null && !this.categoryId.isEmpty())
        {
            this.category = APP.Services.categoryService.getRepository().getById(this.categoryId);
            setTitle("Edit Category");
            this.isNew = false;
        }

        this.loadCategoryInfo();
    }

    private void loadCategoryInfo()
    {
        this.title.setText(this.category.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_note_page_activity, menu);
        menu.getItem(2).setVisible(false);
        if(this.isNew) menu.getItem(3).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_note_page_save:
                boolean valid = true;

                if(this.title.getText().toString().isEmpty())
                {
                    Toast.makeText(this, "Put a title in your category!", Toast.LENGTH_SHORT).show();
                    valid = false;
                }

                if(valid)
                {
                    this.category.setText(this.title.getText().toString());

                    APP.Services.categoryService.save(this.category);

                    APP.goTo(this, CategoryListPageActivity.class);
                }
                break;
            case R.id.menu_note_page_cancel:
                APP.goTo(this, MainActivity.class);
                break;
            case R.id.menu_note_page_delete:
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Do you want to remove the category?");
                alertDialog.setMessage("This category will not be available once it's removed!");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                APP.Services.categoryService.delete(categoryPageActivity.category);
                                APP.goTo(categoryPageActivity, CategoryListPageActivity.class);
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
