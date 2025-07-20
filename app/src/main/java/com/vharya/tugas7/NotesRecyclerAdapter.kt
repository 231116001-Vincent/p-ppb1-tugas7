package com.vharya.tugas7

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.vharya.tugas7.database.NotesHelper
import com.vharya.tugas7.model.NotesModel

class NotesRecyclerAdapter(
    private val context: Context,
    private val items: ArrayList<NotesModel>
) : Adapter<NotesRecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView = view.findViewById(R.id.text_title)
        val buttonEdit: Button = view.findViewById(R.id.button_edit)
        val buttonDelete: Button = view.findViewById(R.id.button_delete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = items[position].title

        holder.buttonEdit.setOnClickListener {
            val intent = Intent(context, EditNotesActivity::class.java)

            intent.putExtra("id", items[position].id)
            intent.putExtra("title", items[position].title)
            intent.putExtra("description", items[position].description)

            context.startActivity(intent)
        }

        holder.buttonDelete.setOnClickListener {
            NotesHelper(context).delete(items[position].id)
            items.removeAt(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}