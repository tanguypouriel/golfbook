package com.mindeurfou.golfbook.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.player.remote.GetPlayersResponse
import com.mindeurfou.golfbook.databinding.FragmentPlayersBinding
import com.mindeurfou.golfbook.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayersFragment : Fragment(R.layout.fragment_players) {

    private var _binding: FragmentPlayersBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: PlayersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)

        setupUI()
        subscribeObservers()

        viewModel.setStateEvent(PlayersEvent.GetPlayersEvent())

        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.getPlayersResponse.observe(viewLifecycleOwner) { observePlayers(it) }
    }

    private fun observePlayers(dataState: DataState<GetPlayersResponse>) {
        when(dataState) {
            is DataState.Loading -> binding.progressBar.show()
            is DataState.Success -> {
                binding.progressBar.hide()

                val selfPlayer = dataState.data.selfPlayer
                binding.imageAvatar.setAvatarResource(selfPlayer.drawableResourceId)
                binding.titlePlayer.text = selfPlayer.username
                binding.playerName.text = selfPlayer.name
                binding.playerLastName.text = selfPlayer.lastName
                binding.cardView.cropTopEdge()
                binding.cardView.visibility = View.VISIBLE
                binding.imageAvatar.visibility = View.VISIBLE
                binding.cardView.setOnClickListener { navigateToPlayerDetails(selfPlayer.id) }

                binding.recyclerPlayers.adapter = PlayerAdapter(dataState.data.players) { player ->
                    navigateToPlayerDetails(player.id)
                }
            }
            is DataState.Failure -> binding.progressBar.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI() {}

    private fun navigateToPlayerDetails(playerId: Int) {
        val action = PlayersFragmentDirections.actionPlayersFragmentToPlayerDetailsFragment(playerId)
        findNavController().navigate(action)
    }
}