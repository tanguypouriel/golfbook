package com.mindeurfou.golfbook.ui


import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentConnectionBinding
import com.mindeurfou.golfbook.databinding.FragmentSplashBinding
import com.mindeurfou.golfbook.ui.common.BaseFragment

class ConnectionFragment : BaseFragment(R.layout.fragment_splash) {

    private var _binding: FragmentConnectionBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConnectionBinding.inflate(inflater, container, false)

        // Make the "create account" label
        val spannableString = SpannableString(getString(R.string.createAccount))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                navigateToCreatePlayerFragment()
            }
        }
        spannableString.setSpan(clickableSpan, 23, 34, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.createPlayerTextView.text = spannableString
        binding.createPlayerTextView.movementMethod = LinkMovementMethod.getInstance()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToCreatePlayerFragment() {
        findNavController().navigate(R.id.action_connectionFragment_to_createPlayerFragment)
    }
}