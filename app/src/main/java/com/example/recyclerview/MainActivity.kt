package com.example.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.model.User
import com.example.recyclerview.screens.UserDetailsFragment
import com.example.recyclerview.screens.UsersListFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, UsersListFragment())
                .commit()
        }
    }

    override fun showDetails(user: User) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, UserDetailsFragment.newInstance(user.id))
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }
}