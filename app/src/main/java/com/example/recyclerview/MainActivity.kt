package com.example.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.model.User
import com.example.recyclerview.model.UserService
import com.example.recyclerview.model.UsersListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UsersAdapter
    private val usersService: UserService
        get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                usersService.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                Toast.makeText(this@MainActivity, "User:${user.name}", Toast.LENGTH_SHORT).show()
            }
        })
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        usersService.addListener(usersListener)
    }

    //Объявляет переменную usersListener и присваивает ей анонимную функцию типа UsersListener.
    // UsersListener - это тип псевдонима для функции обратного вызова (user: List<User>) -> Unit,
    // который используется для оповещения слушателей об изменениях в списке пользователей.
    private val usersListener: UsersListener = { adapter.users = it }

    //От утечек памяти
    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }
}