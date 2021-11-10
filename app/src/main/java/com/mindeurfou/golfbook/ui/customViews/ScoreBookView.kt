package com.mindeurfou.golfbook.ui.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.data.game.local.ScoreDetails
import com.mindeurfou.golfbook.data.game.local.ScoreType
import com.mindeurfou.golfbook.utils.DataState

class ScoreBookView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var scoreBook: LiveData<DataState<ScoreBook>>? = null
) : AbstractComposeView(context, attrs, defStyleAttr){

    @Composable
    override fun Content() {
        ScoreBookProcessing(scoreBook)
    }
}

@Composable
private fun ScoreBookProcessing(
    scoreBook: LiveData<DataState<ScoreBook>>?
) {

    val observedScoreBook = scoreBook ?: MutableLiveData()

    val data by observedScoreBook.observeAsState(null)

    data?.let{ dataState ->
        when (dataState) {
            is DataState.Loading -> ProgressBarCircular()
            is DataState.Success ->  ScoreBook(scoreBook = dataState.data)
        }

    }
}

@Composable
private fun ScoreBook(
    scoreBook: ScoreBook
) {
    val lightGrey = colorResource(id = R.color.lightGrey)
    val lighterGrey = colorResource(id = R.color.lighterGrey)

    val parOut = scoreBook.par.dropLast(9)
    val parOutSum = parOut.reduce { acc, i ->  acc + i }
    val parIn = scoreBook.par.drop(9)
    val parInSum = parIn.reduce { acc, i ->  acc + i } + parOutSum

    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, colorResource(id = R.color.colorSecondary)),
        color = colorResource(id = R.color.grey) // Divider color
    ) {
        Column(
            Modifier.fillMaxWidth()
        ) {
            HeaderRow(true)
            ParRow(parOut, parOutSum, lighterGrey)
            scoreBook.playerScores.forEachIndexed { index, playerScore ->
                val scoreOut = playerScore.scores.dropLast(9)
                val withBottomDivider = index != scoreBook.playerScores.size - 1
                PlayerData(playerScore.name, scoreOut, playerScore.scoreSum, playerScore.netSum, withBottomDivider)
            }
            HeaderRow(false)
            ParRow(parIn, parInSum, lighterGrey)
            scoreBook.playerScores.forEachIndexed { index, playerScore ->
                val scoreIn = playerScore.scores.drop(9)
                val withBottomDivider = index != scoreBook.playerScores.size - 1
                PlayerData(playerScore.name, scoreIn, playerScore.scoreSum, playerScore.netSum, withBottomDivider)
            }
        }
    }
}

@Composable
private fun ProgressBarCircular() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.colorSecondary),
            modifier = Modifier.wrapContentWidth(CenterHorizontally))
    }
}

@Composable
private fun PlayerData(name: String, scores: List<ScoreDetails?>, scoreSum: Int?, netSum: String?, withBottomDivider: Boolean) {
    val grossScore = scores.map { it?.score }
    val scoreTypes = scores.map { it?.scoreType }

    val netScore = scores.map { it?.net }
    ScoreRow(name, score = grossScore, scoreTypes = scoreTypes,  scoreSum = scoreSum)
    NetRow(values = netScore, sum = netSum, withBottomDivider = withBottomDivider)
}

@Composable
private fun HeaderRow(out: Boolean) {

    val holeList = if (out) List(9) {(it+1).toString()} else List(9) {(it+10).toString()}
    val endText = if (out) stringResource(id = R.string.out) else stringResource(id = R.string.inString)

    BaseRow(
        startText = stringResource(id = R.string.hole),
        values = holeList,
        scoreTypes = null,
        endText = endText,
        textColor = colorResource(id = R.color.white),
        backgroundColor = colorResource(id = R.color.colorSecondaryVariant),
        withDivider = false,
        withBottomDivider = false,
        headerTypo = CellTypo.HEADER_TYPO,
        valuesTypo = CellTypo.HEADER_TYPO
    )
}

@Composable
private fun ParRow(par: List<Int?>, parSum: Int, backgroundColor: Color) {
    BaseRow(
        startText = stringResource(id = R.string.par),
        values = par.map { it.toString() },
        scoreTypes = null,
        endText = parSum.toString(),
        textColor = colorResource(id = R.color.black),
        backgroundColor = backgroundColor,
        withDivider = true,
        withBottomDivider = true,
        headerTypo = CellTypo.HEADER_TYPO,
        valuesTypo = CellTypo.VALUE_TYPO
    )
}

@Composable
private fun NetRow(values: List<String?>, sum: String?, withBottomDivider: Boolean) {
    BaseRow(
        startText = stringResource(id = R.string.net),
        values = values,
        scoreTypes = null,
        endText = sum,
        textColor = colorResource(id = R.color.black),
        backgroundColor = colorResource(id = R.color.lighterGrey),
        withDivider = true,
        withBottomDivider = withBottomDivider,
        headerTypo = CellTypo.VALUE_TYPO,
        valuesTypo = CellTypo.LIGHT_VALUE_TYPO
    )
}

@Composable
private fun ScoreRow(name: String, score: List<Int?>, scoreTypes: List<ScoreType?>, scoreSum: Int?) {
    BaseRow(
        startText = name,
        values = score.map { it?.toString() },
        scoreTypes = scoreTypes,
        endText = scoreSum?.toString(),
        textColor = colorResource(id = R.color.black),
        backgroundColor = colorResource(R.color.lightGrey),
        withDivider = true,
        withBottomDivider = true,
        headerTypo = CellTypo.HEADER_TYPO,
        valuesTypo = CellTypo.VALUE_TYPO
    )
}

@Composable
private fun BaseRow(
    startText: String,
    values: List<String?>,
    scoreTypes: List<ScoreType?>?,
    endText: String?,
    textColor: Color,
    backgroundColor: Color,
    withDivider: Boolean,
    withBottomDivider: Boolean,
    headerTypo: CellTypo,
    valuesTypo: CellTypo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val dividers = if (withDivider)
            if (withBottomDivider)
                listOf(0.dp, 0.dp, 1.dp, 1.dp)
            else
                listOf(0.dp, 0.dp, 1.dp, 0.dp)
        else
            listOf(0.dp, 0.dp, 0.dp, 0.dp)

        CellView(
            text = startText,
            textColor = textColor,
            backgroundColor = backgroundColor,
            cellTypo = headerTypo,
            dividers = dividers,
            scoreType = null,
            modifier = Modifier
                .aspectRatio(2f)
                .weight(2f)
        )

        for (i in 0..8) {
            CellView(
                values[i],
                textColor,
                backgroundColor = backgroundColor,
                cellTypo = valuesTypo,
                dividers = dividers,
                scoreType = scoreTypes?.get(i),
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
            )
        }

        CellView(
            text = endText,
            textColor = textColor,
            backgroundColor = backgroundColor,
            cellTypo = headerTypo,
            dividers = dividers,
            scoreType = null,
            modifier = Modifier
                .aspectRatio(2f)
                .weight(2f)
        )
    }
}

enum class CellTypo { HEADER_TYPO, VALUE_TYPO, LIGHT_VALUE_TYPO }

@Composable
private fun CellView (
    text: String?,
    textColor: Color,
    backgroundColor: Color,
    cellTypo: CellTypo,
    scoreType: ScoreType?,
    dividers: List<Dp>,
    modifier: Modifier
) {

    val fontSize = when(cellTypo) {
        CellTypo.HEADER_TYPO -> 14.sp
        CellTypo.VALUE_TYPO -> 12.sp
        CellTypo.LIGHT_VALUE_TYPO -> 11.sp

    }
    val fontWeight = when (cellTypo) {
        CellTypo.HEADER_TYPO -> FontWeight.Bold
        CellTypo.VALUE_TYPO -> FontWeight.Normal
        CellTypo.LIGHT_VALUE_TYPO -> FontWeight.Light
    }

    Box(
        modifier = modifier
    ) {
        Surface(
            color = backgroundColor,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dividers[0],
                    top = dividers[1],
                    end = dividers[2],
                    bottom = dividers[3]
                )
        ) {
            if (text != null) {
                Text(
                    text = text,
                    fontSize = fontSize,
                    fontWeight = fontWeight,
                    fontFamily = FontFamily(Font(R.font.glacial_indif)), // always Glacial indif
                    textAlign = TextAlign.Center,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }

        scoreType?.let {
            when (it) {
                ScoreType.EAGLE -> {
                    Circle(padding = 2.dp)
                    Circle(padding = 4.dp)
                }
                ScoreType.BIRDIE -> {
                    Circle(padding = 2.dp)
                }
                ScoreType.PAR -> {}
                ScoreType.BOGEY -> {
                    Rectangle(padding = 3.dp)
                }
                ScoreType.DOUBLE_BOGEY, ScoreType.HIGHER_THAN_DB -> {
                    Rectangle(padding = 3.dp)
                    Rectangle(padding = 5.dp)
                }
            }
        }
    }
}

@Composable
private fun Circle(padding: Dp) {
    Surface(
        shape = RoundedCornerShape(50),
        border = BorderStroke(0.5.dp, Color.Gray),
        color = Color.Transparent,
        modifier = Modifier.padding(padding)
    ) {
        Spacer(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun Rectangle(padding: Dp) {
    Surface(
        border = BorderStroke(0.5.dp, Color.Gray),
        color = Color.Transparent,
        modifier = Modifier.padding(padding)
    ) {
        Spacer(modifier = Modifier.fillMaxSize())
    }
}

//@Preview()
@Composable
private fun PreviewScoreCard() {
    val par = List(9) {
        if (it % 2 == 0)
            3
        else
            4
    }

    val score = List(18) {
        if (it <= 12) {
            if (it % 2 == 0)
                4
            else
                3
        } else
            0
    }

    val scoreTypes = List(18) {
        when (it) {
            2 -> ScoreType.BIRDIE
            6 -> ScoreType.BOGEY
            7 -> ScoreType.HIGHER_THAN_DB
            10 -> ScoreType.EAGLE
            9 -> ScoreType.DOUBLE_BOGEY
            else -> {
                if (it <= 12)
                    ScoreType.PAR
                else
                    null
            }
        }
    }

//    ScoreCard(par, 36, score, scoreTypes, 34)
}
