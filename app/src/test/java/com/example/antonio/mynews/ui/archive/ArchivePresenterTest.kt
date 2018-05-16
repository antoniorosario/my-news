package com.example.antonio.mynews.ui.archive

import com.example.antonio.mynews.capture
import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.data.Multimedium
import com.example.antonio.mynews.data.source.ArticlesDataSource
import com.example.antonio.mynews.data.source.ArticlesRepository
import org.junit.Before
import org.junit.Test
import org.mockito.*
import java.util.*

//TODO Tests coming soon, I promise :).
class ArchivePresenterTest {
    @Mock private lateinit var mockArticlesRepository: ArticlesRepository
    @Mock private lateinit var mockArchiveView: ArchiveContract.View
    @Captor private lateinit var loadArticlesCallbackCaptor: ArgumentCaptor<ArticlesDataSource.LoadArticlesCallback>

    private lateinit var archivePresenter: ArchivePresenter
    private lateinit var articles: MutableList<Article>

    @Before fun setUpArchivePresenter() {
        MockitoAnnotations.initMocks(this)

        archivePresenter = ArchivePresenter(mockArticlesRepository, mockArchiveView)
        articles = Collections.singletonList(
                Article(
                        "id",
                        "section",
                        "title",
                        "abstract",
                        "published date",
                        Collections.singletonList(Multimedium("url")),
                        "url",
                        false
                )
        )
    }


    @Test fun createPresenter_setsThePresenterToView() {
        // Get a reference to the class under test
        archivePresenter = ArchivePresenter(mockArticlesRepository, mockArchiveView)

        // Then the presenter is set to the view
        Mockito.verify(mockArchiveView).presenter = archivePresenter
    }

    @Test fun loadArchivedArticles_shouldShowArticlesIntoView_whenArticlesAreAvailable() {
        archivePresenter.loadArchivedArticles()

        // Callback is captured and invoked with stubbed tasks
        Mockito.verify(mockArticlesRepository).getArchivedArticles(capture(loadArticlesCallbackCaptor))
        loadArticlesCallbackCaptor.value.onArticlesLoaded(articles)

        Mockito.verify(mockArchiveView).showArticles(articles)
    }

    @Test fun loadArchivedArticles_shouldShowEmptyText_whenArticlesAreUnavailable() {
        archivePresenter.loadArchivedArticles()

        // Callback is captured and invoked with stubbed tasks
        Mockito.verify(mockArticlesRepository).getArchivedArticles(capture(loadArticlesCallbackCaptor))
        loadArticlesCallbackCaptor.value.onDataNotAvailable()

        Mockito.verify(mockArchiveView).showEmptyText()
    }

    @Test fun onArticleClicked_shouldNavigateToArticleUrl() {
        val articleUrl = "https://www.article.com/article"
        archivePresenter.onArticleClicked(articleUrl)


        Mockito.verify(mockArchiveView).navigateToArticleUrl(articleUrl)
    }

    @Test fun onShareArticledClicked_shouldNavigateToArticleChooser() {
        val articleTitle = "title"
        val articleUrl = "https://www.article.com/article"
        archivePresenter.onShareArticleButtonClicked(articleTitle, articleUrl)

        Mockito.verify(mockArchiveView).navigateToShareArticleChooser(articleTitle, articleUrl)
    }

    @Test fun onArchiveArticleButtonClicked_shouldArchiveArticle() {
        val article = articles[0]
        archivePresenter.onArchiveArticleButtonClicked(article)

        Mockito.verify(mockArticlesRepository).updateArticle(article.id, article.isArchived)
        Mockito.verify(mockArchiveView).showArticleArchiveConfirmation(article.isArchived)
    }
}