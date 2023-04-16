package com.example.recyclerview.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.UserActionListener
import com.example.recyclerview.UsersAdapter
import com.example.recyclerview.databinding.FragmentUserListBinding
import com.example.recyclerview.model.User

class UsersListFragment : Fragment() {
    private lateinit var binding: FragmentUserListBinding
    private lateinit var adapter: UsersAdapter

    private val viewModel: UserListVieModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                viewModel.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                navigator().showDetails(user)
            }

        })
//В фрагментах Android в качестве жизненного цикла для обсервера рекомендуется использовать viewLifecycleOwner вместо this.
// Это связано с тем, что фрагменты могут быть уничтожены и созданы заново, и при этом их жизненный цикл остается незавершенным.
// Если в этом случае использовать this, то обсервер будет продолжать работать с устаревшим жизненным циклом, что может привести к утечкам памяти и другим проблемам.
        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.users = it
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        return binding.root
    }
}