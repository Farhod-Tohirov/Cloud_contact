package com.star.onlinelesson12.ui.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todotask.utils.bindItem
import com.example.todotask.utils.inflate
import com.star.contactsrestapi.utils.SingleBlock
import com.star.contactsrestapi2.data.local.room.entity.ContactData
import com.star.onlinelesson12.R
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactAdapter : ListAdapter<ContactData, ContactAdapter.ViewHolder>(ITEM_CALLBACK) {

    companion object {
        var ITEM_CALLBACK = object : DiffUtil.ItemCallback<ContactData>() {
            override fun areItemsTheSame(oldItem: ContactData, newItem: ContactData) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ContactData, newItem: ContactData) =
                oldItem.firstName == newItem.firstName && oldItem.firstName == newItem.firstName &&oldItem.phoneNumber == newItem.phoneNumber

        }
    }

    private var deleteListener: SingleBlock<ContactData>? = null
    private var editListener: SingleBlock<ContactData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.contact_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() = bindItem {
            val data = getItem(adapterPosition)
            val tempName = data.firstName + " " + data.lastName
            contactName.text = tempName
            telephoneText.text = data.phoneNumber
            buttonMore.setOnClickListener {
                val menu = PopupMenu(context, it)
                menu.inflate(R.menu.menu_pop_up)
                menu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.deleteContact -> deleteListener?.invoke(data)
                        R.id.editContact -> {
                            editListener?.invoke(data)
                        }
                    }
                    true
                }
                menu.show()
            }
        }
    }

    fun setOnDeleteContactListener(f: SingleBlock<ContactData>) {
        deleteListener = f
    }

    fun setOnEditContactListener(f: SingleBlock<ContactData>) {
        editListener = f
    }
}