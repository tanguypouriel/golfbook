package com.mindeurfou.golfbook.ui.connection


import android.content.Context
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
import com.google.android.material.snackbar.Snackbar
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.databinding.FragmentConnectionBinding
import com.mindeurfou.golfbook.interactors.connection.ConnectionEvent
import com.mindeurfou.golfbook.interactors.connection.ConnectionInteractors.Companion.REMEMBER_ME_KEY
import com.mindeurfou.golfbook.ui.StartActivity
import com.mindeurfou.golfbook.utils.DataState
import com.mindeurfou.golfbook.utils.hide
import com.mindeurfou.golfbook.utils.makeSnackbar
import com.mindeurfou.golfbook.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConnectionFragment : Fragment(R.layout.fragment_splash) {

    private val startActivity: StartActivity by lazy { requireActivity() as StartActivity }

    private var _binding: FragmentConnectionBinding? = null

    private val binding
        get() = _binding!!

    private val viewModel: ConnectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConnectionBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(ConnectionEvent.RetrieveCredentialsEvent)

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

        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)
        val rememberMe = sharedPreferences?.getBoolean(REMEMBER_ME_KEY, true) ?: true
        binding.rememberMeCheckBox.isChecked = rememberMe

        binding.rememberMeCheckBox.setOnCheckedChangeListener { _, checked ->
            sharedPreferences?.let {
                with(it.edit()) {
                    putBoolean(REMEMBER_ME_KEY, checked)
                    apply()
                }
            }
        }

        binding.okBtn.setOnClickListener { okBtnOnClick() }

    }

    private fun subscribeObservers() {
        viewModel.connected.observe(viewLifecycleOwner) { observeConnected(it) }
        viewModel.credentials.observe(viewLifecycleOwner) { observeCredentials(it) }
    }

    private fun observeConnected(dataState: DataState<Unit>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Success -> {
                binding.progressBar.hide()
                startActivity.navigateToMainActivity()
            }
            is DataState.Failure -> {
                binding.progressBar.hide()
                dataState.errors?.let { makeSnackbar(binding.root, it) }
            }
        }
    }

    private fun observeCredentials(dataState: DataState<Pair<String, String>?>) {
        when(dataState) {
            is DataState.Loading -> {}
            is DataState.Failure -> {}
            is DataState.Success -> {
                val credentials = dataState.data
                credentials?.let {
                    binding.nameInput.editText?.setText(credentials.first)
                    binding.passwordInput.editText?.setText(credentials.second)
                }
            }
        }
    }

    private fun okBtnOnClick() {
        val username = binding.nameInput.editText!!.text.toString().trim()
        val password = binding.passwordInput.editText!!.text.toString().trim()
        val rememberMe = binding.rememberMeCheckBox.isChecked
        viewModel.setStateEvent(ConnectionEvent.ConnectEvent(username, password, rememberMe))
    }
}