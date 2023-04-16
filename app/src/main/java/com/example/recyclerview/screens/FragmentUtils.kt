package com.example.recyclerview.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclerview.App
import com.example.recyclerview.Navigator

//Если viewmodel без конструктора , то фабрика не нужна
//Фабрика viewmodel для создания viewmodel с нужными параметрами
//которые передаются в конструктор
class ViewFragmentFactory(
    private val app: App
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            UserListVieModel::class.java -> {
                UserListVieModel(app.usersService)
            }
            UserDetailsViewModel::class.java -> {
                UserDetailsViewModel(app.usersService)
            }else -> {
                throw IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }

}

// Функция для лаконичности и удобства вызова из любого фрагмента и не создавать каждый раз viewfragmentfactory
fun Fragment.factory() = ViewFragmentFactory(requireContext().applicationContext as App)
//Метод для получения навигатора
fun Fragment.navigator()=requireActivity() as Navigator