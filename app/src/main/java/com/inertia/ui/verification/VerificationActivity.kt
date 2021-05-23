package com.inertia.ui.verification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inertia.data.datasource.local.entity.UserEntity
import com.inertia.data.preference.UserPreferences
import com.inertia.databinding.ActivityVerificationBinding
import com.inertia.ui.main.MainActivity

class VerificationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_CODE = "extra_code"
    }

    private var user: UserEntity? = null
    private var code: String? = null

    private lateinit var preferences: UserPreferences

    private lateinit var binding: ActivityVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getParcelableExtra(EXTRA_USER)
        code = intent.getStringExtra(EXTRA_CODE)

        binding = ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = UserPreferences(this)

        with(binding) {
            btnVerify.setOnClickListener {
                verify(otpView.text.toString())
            }
            otpView.setOtpCompletionListener {
                verify(it)
            }
        }
    }

    private fun verify(otp: String) {
        if (otp == code) {
            user?.name = "Budi Jayanto"
            user?.api_key = "Misalkan ini api key"

            preferences.setUser(user)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}