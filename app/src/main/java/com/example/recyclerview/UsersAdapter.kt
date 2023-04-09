package com.example.recyclerview

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview.databinding.ItemUserBinding
import com.example.recyclerview.model.User


class UsersAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {
    var users: List<User> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class UsersViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)

    //Сколько элементов в списке
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            userNameTextView.text = user.name
            userCompanyTextView.text = user.company
            holder.itemView.tag = user
            moreImageViewButton.tag = user
            if (user.photo.isNotBlank()) {
                Glide.with(photoImageView.context).load(user.photo).circleCrop()
                    .placeholder(R.drawable.ic_user_avatar).error(R.drawable.ic_user_avatar)
                    .into(photoImageView)
            } else {
                photoImageView.setImageResource(R.drawable.ic_user_avatar)
            }
        }
    }

    //Для обновления элемента списка
    override fun getItemCount(): Int = users.size

    // Для создания нового элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)
        return UsersViewHolder(binding)

    }

    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val user = view.tag as User
        val position=users.indexOfFirst { it.id==user.id }
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, "Move Up").apply {
            isEnabled=position>0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, "Move Down").apply {
            isEnabled=position<users.size-1
        }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Move Remove")
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    actionListener.onUserMove(user,-1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onUserMove(user,1)
                }
                ID_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    //Cодержит константы, используемые в коде для идентификации каждого варианта выбора меню.
// Каждый вариант выбора имеет свой уникальный идентификатор, определенный в этом объекте.
    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
    }
}