package com.example.recyclerview

import com.example.recyclerview.model.User

interface UserActionListener {
    fun onUserMove(user: User, moveBy: Int)
    fun onUserDelete(user: User)
    fun onUserDetails(user: User)
}