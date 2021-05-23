package com.inertia.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.preference.UserPreferences
import com.inertia.databinding.FragmentProfileBinding
import com.inertia.ui.login.LoginActivity
import com.inertia.ui.main.MainActivity
import com.inertia.utils.ViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var preferences: UserPreferences

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        val factory = ViewModelFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user= viewModel.getUser()

        if (user.phoneNumber == null) {
            binding.tvUserNama.text = "Silahkan login"
            binding.tvUserNoHp.text = ""
            binding.btnLogout.text = "Login"
            binding.btnLogout.setOnClickListener {
                startActivity(Intent(context, LoginActivity::class.java))
                requireActivity().finish()
            }
        }else{
            binding.tvUserNama.text = user.name
            binding.tvUserNoHp.text = user.phoneNumber
            binding.btnLogout.text = "Logout"
            binding.btnLogout.setOnClickListener {
                preferences.setUser(UserEntity())
                startActivity(Intent(context, MainActivity::class.java))
                requireActivity().finish()
            }
        }

    }
}