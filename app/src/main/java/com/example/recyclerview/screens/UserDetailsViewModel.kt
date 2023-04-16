package com.example.recyclerview.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.UserNotFoundException
import com.example.recyclerview.model.UserDetails
import com.example.recyclerview.model.UserService

class UserDetailsViewModel(
    private val userService: UserService
) : ViewModel() {

    private val _userDetails = MutableLiveData<UserDetails>()
    val userDetails: LiveData<UserDetails> = _userDetails

    fun loadUser(userId: Long) {
        if (_userDetails.value != null) return
        try {
            _userDetails.value = userService.getById(userId)
        } catch (e: UserNotFoundException) {
            e.printStackTrace()
        }
    }

    fun deleteUser() {
        val userDetails = this.userDetails.value ?: return
        userService.deleteUser(userDetails.user)
    }
}