package com.mindeurfou.golfbook.ui.connection


import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentConnectionBinding
import com.mindeurfou.golfbook.utils.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectionFragment : Fragment(R.layout.fragment_splash) {

    private var _binding: FragmentConnectionBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: ConnectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConnectionBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToCreatePlayerFragment() {
        findNavController().navigate(R.id.action_connectionFragment_to_createPlayerFragment)
    }

    private fun setupUI() {

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

        binding.okBtn.setOnClickListener {
            viewModel.setStateEvent(ConnectionEvent.ConnectEvent("test", "paaaasword"))
        }
    }

    private fun subscribeObservers() {
        viewModel.connected.observe(viewLifecycleOwner) { observeConnected(it) }
    }

    private fun observeConnected(dataState: DataState<Boolean>) {
        when(dataState) {
            is DataState.Loading -> showLoading()
            is DataState.Success -> {
                hideLoading()
                if (dataState.data)
                    Toast.makeText(requireContext(), "Connected !", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(requireContext(), "Not connected !", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
}