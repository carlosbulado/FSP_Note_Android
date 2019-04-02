package com.carlosbulado.fsp_note.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.activity.CategoryPageActivity;
import com.carlosbulado.fsp_note.domain.Category;

import java.util.ArrayList;

public class AllCategoriesAdapter extends RecyclerView.Adapter<AllCategoriesAdapter.AllCategoriesViewHolder>
{
    public final class AllCategoriesViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;

        public AllCategoriesViewHolder (@NonNull View holder)
        {
            super(holder);

            this.title = itemView.findViewById(R.id.item_grid_all_categories_title);
        }
    }

    private ArrayList<Category> allCategoriesArray;
    private Context context;

    public AllCategoriesAdapter (Context context, ArrayList<Category> categories)
    {
        this.allCategoriesArray = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public AllCategoriesAdapter.AllCategoriesViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(this.context)
                .inflate(R.layout.item_grid_all_categories, viewGroup, false);

        AllCategoriesAdapter.AllCategoriesViewHolder mVH = new AllCategoriesAdapter.AllCategoriesViewHolder(itemView);
        return mVH;
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoriesViewHolder allCategoriesViewHolder, int position)
    {
        Category category = this.allCategoriesArray.get(position);
        allCategoriesViewHolder.itemView.setTag(category);
        allCategoriesViewHolder.title.setText(category.getText());
        allCategoriesViewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Category category = (Category) view.getTag();
                Context context = view.getContext();
                Intent nextPage = new Intent(context, CategoryPageActivity.class);
                nextPage.putExtra("categoryId", category.getId());
                context.startActivity(nextPage);
            }
        });
    }

    @Override
    public int getItemCount ()
    {
        return this.allCategoriesArray.size();
    }

}