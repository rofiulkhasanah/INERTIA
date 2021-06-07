package com.inertia.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inertia.R
import com.inertia.databinding.FragmentProfileBinding
import com.inertia.ui.home.HomeFragment
import com.inertia.ui.laporanmu.LaporanmuActivity
import com.inertia.ui.login.LoginActivity
import com.inertia.ui.main.MainActivity
import com.inertia.ui.terdampak.TerdampakActivity
import com.inertia.utils.ViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(requireActivity(), factory)[ProfileViewModel::class.java]
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user= viewModel.getUser()
        if (user.nomorWa == null) {
            binding.tvUserNama.text = getString(R.string.silakan_login)
            binding.tvUserNoHp.text = ""
            binding.btnLogout.text = getString(R.string.login)
            binding.btnLogout.setOnClickListener {
                startActivity(Intent(context, LoginActivity::class.java))
                requireActivity().finish()
            }
        }else{
            binding.tvUserNama.text = user.nama
            binding.tvUserNoHp.text = user.nomorWa
            binding.btnLogout.text = getString(R.string.logout)
            binding.btnLogout.setOnClickListener {
                showConfirmDialog()
            }
            binding.tvLaporanmu.setOnClickListener {
                startActivity(Intent(context, LaporanmuActivity::class.java))
            }
            binding.txtPenilaianmu.setOnClickListener {
                val intent = Intent(context, TerdampakActivity::class.java)
                intent.putExtra(TerdampakActivity.USER, user)
                startActivity(intent)
            }
        }
    }

    private fun showConfirmDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("Logout")
            .setMessage("Apakah anda yakin ingin logout?")
            .setPositiveButton("Ya") { _, _ ->
                viewModel.logout()
                startActivity(Intent(context, MainActivity::class.java))
                requireActivity().finish()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.cancel()
            }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    companion object {
        @Volatile
        private var instance: ProfileFragment? = null

        @JvmStatic
        fun getInstance(): ProfileFragment = instance ?: synchronized(this) {
            instance ?: ProfileFragment().apply {
                instance = this
            }
        }
    }
}