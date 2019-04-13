package com.carlosbulado.fsp_note.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.activity.NotePageActivity;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.domain.Category;
import com.carlosbulado.fsp_note.domain.Note;

import java.util.ArrayList;

public class AllNotesAdapter extends RecyclerView.Adapter<AllNotesAdapter.AllNotesViewHolder>
{
    public final class AllNotesViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView categoryName;

        public AllNotesViewHolder (@NonNull View holder)
        {
            super(holder);

            this.title = itemView.findViewById(R.id.item_grid_all_notes_title);
            this.categoryName = itemView.findViewById(R.id.item_grid_all_notes_category);
        }
    }

    private ArrayList<Note> allNotesArray;
    private Context context;

    public AllNotesAdapter (Context context, ArrayList<Note> notes)
    {
        this.allNotesArray = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public AllNotesViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(this.context)
                .inflate(R.layout.item_grid_all_notes, viewGroup, false);

        AllNotesViewHolder mVH = new AllNotesViewHolder(itemView);
        return mVH;
    }

    @Override
    public void onBindViewHolder (@NonNull final AllNotesViewHolder notesViewHolder, int position)
    {
        Note note = this.allNotesArray.get(position);
        notesViewHolder.itemView.setTag(note);
        notesViewHolder.title.setText(note.getTitle());
        ArrayList<Category> allCatList = APP.Services.categoryService.getRepository().getAll();

        String secondLine = "";
        if(!note.getCategory().isEmpty())
        {
            for (int i = 0; i < allCatList.size(); i++)
                if (allCatList.get(i).getId().equals(note.getCategory()))
                    secondLine = allCatList.get(i).getText();
        }
        else { secondLine = "No Category"; }

        secondLine += " /  Last Updated := " + (note.getUpdated() == null || note.getUpdated().isEmpty() ? note.getCreated() : note.getUpdated());

        notesViewHolder.categoryName.setText(secondLine);
        notesViewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Note note = (Note) view.getTag();
                Context context = view.getContext();
                Intent nextPage = new Intent(context, NotePageActivity.class);
                nextPage.putExtra("noteId", note.getId());
                context.startActivity(nextPage);
            }
        });
    }

    @Override
    public int getItemCount ()
    {
        return this.allNotesArray.size();
    }

}