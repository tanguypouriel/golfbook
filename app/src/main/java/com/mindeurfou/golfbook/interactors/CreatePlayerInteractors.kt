package com.mindeurfou.golfbook.interactors

import com.mindeurfou.golfbook.datasource.network.player.PlayerNetworkDataSourceImpl
import javax.inject.Inject

class CreatePlayerInteractors
@Inject constructor(
   private val playerNetworkDataSourceImpl: PlayerNetworkDataSourceImpl
) {


}