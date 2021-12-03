package com.mindeurfou.golfbook.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindeurfou.golfbook.R
import com.mindeurfou.golfbook.data.game.local.ScoreBook
import com.mindeurfou.golfbook.data.game.local.ScoreSummary
import com.mindeurfou.golfbook.utils.FakeData

class ScoreInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    var scoreBook: ScoreBook
        get() = scoreBookState.value
        set(value) {
            scoreBookState.value = value
        }

    var par: List<Int>
        get() = parState.value
        set(value) {
            parState.value = value
        }

    var scoreSummaries: List<ScoreSummary>
        get() = scoreSummariesState.value
        set(value) {
            scoreSummariesState.value = value
        }

    var scoreInputListener: ScoreInputListener = object : ScoreInputListener {
        override fun onScoreEntered(scoreCellData: ScoreCellData) {}
    }

    private val parState = mutableStateOf(listOf<Int>())
    private val scoreBookState = mutableStateOf(ScoreBook(playerScores = listOf()))
    private val scoreSummariesState = mutableStateOf(listOf<ScoreSummary>())

    @Composable
    override fun Content() {
        ScoreInput(
                scoreBook = scoreBookState.value,
                par = parState.value,
                scoreSummaries = scoreSummariesState.value,
                scoreInputListener = scoreInputListener
        )
    }
}

data class ScoreColumnItemData(
        val holeNumber: Int,
        val par: Int,
        val playersScore: List<Int?>
)

data class ScoreCellData(
        val playerName: String,
        val holeNumber: Int,
        val par: Int,
        val score: Int?
)

private val rowHeight = 64.dp
private val headerRowSize = 48.dp
private val fontFamily =  FontFamily(Font(R.font.glacial_indif))

@Composable
private fun ScoreInput(
    scoreBook: ScoreBook,
    par: List<Int>,
    scoreSummaries: List<ScoreSummary>,
    scoreInputListener: ScoreInputListener
) {

    var showedScoreCellData : ScoreCellData? by remember { mutableStateOf(null)}

    Surface(
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, color = colorResource(id = R.color.colorSecondary)),
            color = showedScoreCellData?.let { colorResource(id = R.color.scoreInputPlayerBottom) } ?: colorResource(id = R.color.grey)
    ) {
        Box {
            if (showedScoreCellData == null) {
                Row(
                        Modifier.fillMaxSize()
                ) {
                    PlayerColumn(scoreSummaries = scoreSummaries, modifier = Modifier.weight(1.5f))
                    ScoreColumn(scoreBook = scoreBook, par = par, modifier = Modifier.weight(1f)) { scoreCellData ->
                        showedScoreCellData = scoreCellData
                    }
                }
            } else {
                ScoreContextView(scoreCellData = showedScoreCellData!!, modifier = Modifier.align(Alignment.TopCenter))
                ScoreKeyboard(par = showedScoreCellData!!.par, modifier = Modifier.align(Alignment.BottomCenter)) { score ->
                    scoreInputListener.onScoreEntered(
                            scoreCellData = showedScoreCellData!!.copy(score = score)
                    )
                    showedScoreCellData = null
                }
            }
        }
    }

}

@Composable
private fun PlayerColumn(scoreSummaries: List<ScoreSummary>, modifier: Modifier = Modifier) {
    Column(
            modifier
                    .fillMaxHeight()
    ) {
        Surface(
                color = colorResource(id = R.color.colorSecondaryVariant),
                modifier = Modifier
                        .height(headerRowSize)
                        .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.fillMaxSize())
        }
        scoreSummaries.forEach { scoreSummary ->
            PlayerCell(scoreSummary = scoreSummary)
        }
        Spacer(modifier = Modifier
                .background(colorResource(id = R.color.scoreInputPlayerBottom))
                .fillMaxSize()
        )
    }
}

@Composable
private fun PlayerCell(scoreSummary: ScoreSummary, modifier: Modifier = Modifier) {
    Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                    .padding(bottom = 1.dp)
                    .background(color = Color.White)
                    .height(rowHeight)
                    .fillMaxWidth()
    ) {
        Text(
                text = scoreSummary.name,
                fontSize = 20.sp,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                        .padding(start = 16.dp)
                        .wrapContentSize()
        )
        Text(
                text = scoreSummary.score,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                        .padding(end = 16.dp)
                        .wrapContentSize()
        )
    }
}

@Composable
private fun ScoreColumn(scoreBook: ScoreBook, par: List<Int>, modifier: Modifier = Modifier, onCellClick: (scoreCellData: ScoreCellData) -> Unit) {
    val scoresData = processScoreColumnData(scoreBook = scoreBook, par = par)
    val names: MutableList<String> = mutableListOf()
    scoreBook.playerScores.forEach { names.add(it.name) }

    LazyRow(
            modifier = modifier
    ) {
        items(scoresData) { scoreData ->
            ScoreColumnItem(scoreData = scoreData, names = names, onCellClick = onCellClick)
        }
    }
}

@Composable
private fun ScoreColumnItem(scoreData: ScoreColumnItemData, names: List<String>, onCellClick: (scoreCellData: ScoreCellData) -> Unit) {
    Column(
            modifier = Modifier.fillMaxHeight()
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                        .size(rowHeight, headerRowSize)
                        .background(color = colorResource(id = R.color.colorSecondaryVariant))

        ) {
            Text(
                    text = scoreData.holeNumber.toString(),
                    color = colorResource(id = R.color.white),
                    fontSize = 22.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                            .padding(top = 2.dp)
                            .wrapContentSize()
            )
            Text(
                    text = "Par ${scoreData.par}",
                    color = colorResource(id = R.color.white),
                    fontSize = 12.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 4.dp)
            )

        }
        Column {

            scoreData.playersScore.forEachIndexed { index, score ->
                val scoreCellData = ScoreCellData(
                        playerName = names[index],
                        holeNumber = scoreData.holeNumber,
                        par = scoreData.par,
                        score = score
                )
                ScoreCell(scoreCellData = scoreCellData, onCellClick = onCellClick)
            }

            Spacer(
                    modifier = Modifier
                            .background(color = colorResource(id = R.color.scoreInputScoreBottom))
                            .fillMaxHeight()
                            .width(rowHeight)
            )
        }
    }
}

@Composable
private fun ScoreCell(scoreCellData: ScoreCellData, onCellClick: (scoreCellData: ScoreCellData) -> Unit) {
    Surface(
            color = colorResource(id = R.color.lighterGrey),
            modifier = Modifier
                    .padding(bottom = 1.dp)
                    .size(rowHeight, rowHeight)
                    .clickable { onCellClick(scoreCellData) }
    ) {

        Text(
                text = scoreCellData.score?.toString() ?: "-",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                        .wrapContentSize()
        )
    }
}

private fun processScoreColumnData(
        scoreBook: ScoreBook,
        par: List<Int>
): List<ScoreColumnItemData> {

    if (par.isEmpty() || scoreBook.playerScores.isEmpty())
        return emptyList()

    val scoresData: MutableList<ScoreColumnItemData> = mutableListOf()

    for (i in 0..17) {
        val outPlayersScore: MutableList<Int?> = mutableListOf()
        scoreBook.playerScores.forEach { playerScore ->
            outPlayersScore.add(playerScore.scores[i]?.score)
        }
        scoresData.add(ScoreColumnItemData(
                holeNumber = i+1,
                par = par[i],
                playersScore = outPlayersScore
            )
        )
    }

    return scoresData
}

@Composable
private fun ScoreKeyboard(par: Int, modifier: Modifier = Modifier, onKeyClick: (score: Int) -> Unit) {
    Surface(
            color = colorResource(id = R.color.scoreKeyboardbackgroundColor),
            modifier = modifier
    ) {
        Column(
                Modifier.padding(2.dp)
        ) {
            for (i in 1..7 step 3) {
                Row(
                        modifier = Modifier.height(70.dp)
                ) {
                    for (j in i..(i + 2)) {
                        val parContext = when(j - par) {
                            -2 -> "Eagle"
                            -1 -> "Birdie"
                            0 -> "Par"
                            1 -> "Bogey"
                            2 -> "Double Bogey"
                            else -> null
                        }
                        ValueInput(value = j, parContext = parContext, modifier = Modifier.weight(1f), onKeyClick = onKeyClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun ValueInput(value: Int, parContext: String?, modifier: Modifier, onKeyClick: (score: Int) -> Unit) {
    Surface(
            color = colorResource(id = R.color.colorSecondaryVariant),
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                    .fillMaxHeight()
                    .padding(2.dp)
                    .clickable { onKeyClick(value) }
    ) {
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            Text(
                    text = value.toString(),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.wrapContentSize()
            )
            parContext?.let {
                Text(
                        text = it,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                                .wrapContentSize()
                                .padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ScoreContextView(scoreCellData: ScoreCellData, modifier: Modifier) {

    Column(
            modifier = modifier
                    .background(color = colorResource(id = R.color.scoreInputScoreBottom))
                    .fillMaxWidth()
    ) {
        Row (
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(headerRowSize)
        ){
            Text(
                    text = "Trou ${scoreCellData.holeNumber}",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
            )
            Divider(
                    color = Color.Black,
                    modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .padding(vertical = 8.dp)
            )
            Text(
                    text = "Par ${scoreCellData.par}",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
            )
        }

        Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
        ) {
            Text(
                    text = scoreCellData.playerName,
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 32.dp, end = 16.dp)
            )

            Surface(
                    color = colorResource(id = R.color.colorSecondaryVariant),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .size(64.dp, 64.dp)
            ) {
                Text(
                        text = scoreCellData.score?.toString() ?: "-",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = fontFamily,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentSize()
                )
            }

        }

    }

}


@Preview
@Composable
private fun ScoreKeyboardPreview() {
    ScoreKeyboard(par = 4) { _ ->

    }
}

@Preview
@Composable
private fun ScoreContextViewPreview() {
    ScoreContextView(
            scoreCellData = ScoreCellData(
                    "Tanguy",
                    4,
                    3,
                    null
            ),
            modifier = Modifier)
}

@Preview
@Composable
private fun ScoreInputPreview() {
    val par = List(18) { if (it % 2 == 0) 3 else 4}
    val scoreSummaries = listOf(
        ScoreSummary("1.", "Tanguy Pouriel", "-1"),
        ScoreSummary("T2.", "Romane Philbert", "0"),
        ScoreSummary("T2.", "Romain Prasil", "0"),
    )
    val scoreInputListener = object : ScoreInputListener {
        override fun onScoreEntered(scoreCellData: ScoreCellData) {}
    }

    ScoreInput(scoreBook = FakeData.scoreBook(), par = par, scoreSummaries = scoreSummaries, scoreInputListener = scoreInputListener)
}