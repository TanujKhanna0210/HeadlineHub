package com.example.headlinehub.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.headlinehub.R
import com.example.headlinehub.domain.model.Article
import com.example.headlinehub.domain.model.Source
import com.example.headlinehub.presentation.Dimens.ArticleImageHeight
import com.example.headlinehub.presentation.Dimens.MediumPadding1
import com.example.headlinehub.presentation.details.components.DetailsTopBar
import com.example.headlinehub.ui.theme.HeadlineHubTheme

@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse(article.url)
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookmarkClick = {
                event(DetailsEvent.UpsertDeleteArticle(article = article))
            },
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(
                start = MediumPadding1,
                top = MediumPadding1,
                end = MediumPadding1
            )
        ) {
            item {

                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(article.urlToImage)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(MediumPadding1))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )

                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.body)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    HeadlineHubTheme(dynamicColor = false) {
        DetailsScreen(
            article = Article(
                author = "",
                title = "Elon Musk Won’t Have To Testify In Tesla Wrongful Death Suit",
                description = "Two teenagers in Florida were killed after they accelerated around a curve at 116 miles per hour.",
                content = "An appeals court has ruled that Tesla CEO Elon Musk will not have to testify in a wrongful death lawsuit after two teenagers were killed in a Tesla Model S going 116 miles per hour in 2018after a Tes… [+2556 chars]",
                publishedAt = "2024-01-03T20:20:38Z",
                source = Source(
                    id = "",
                    name = "Forbes"
                ),
                url = "https://www.forbes.com/sites/zacharyfolk/2024/01/03/elon-musk-wont-have-to-testify-in-tesla-wrongful-death-suit/",
                urlToImage = "https://imageio.forbes.com/specials-images/imageserve/6595c16e5a8c4e95357332cd/0x0.jpg?format=jpg&crop=4256,2396,x0,y212,safe&height=900&width=1600&fit=bounds"
            ),
            event = {}) {

        }
    }
}