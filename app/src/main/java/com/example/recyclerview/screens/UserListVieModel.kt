package com.example.recyclerview.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.model.User
import com.example.recyclerview.model.UserService
import com.example.recyclerview.model.UsersListener

//Желательно в конструктор модели передать все зависимости
class UserListVieModel(
    //Определяет операции
    private val userService: UserService
) : ViewModel() {
    //ViewModel не должна знать ни о модели ни о вью
    //Позволяет не только слушать , но и изменять данные внутри себя
    private val _users = MutableLiveData<List<User>>()

    //view будет получать доступ и слушать данные тут но не изменять их в поле выше
    val users: LiveData<List<User>> = _users

    //Слушатель  для получения уведомлений об изменениях в списке пользователей и обновления LiveData (users) с новым значением.
    private val listener: UsersListener = {
        _users.value = it
    }

    init {
        loadUsers()
    }

    //Отписываемся чтобы не было утечек памяти
    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }

    fun loadUsers() {
        userService.addListener(listener)
    }

    fun moveUser(user: User, moveBy: Int) {
        userService.moveUser(user, moveBy)

    }

    fun deleteUser(user: User) {
        userService.deleteUser(user)
    }


}