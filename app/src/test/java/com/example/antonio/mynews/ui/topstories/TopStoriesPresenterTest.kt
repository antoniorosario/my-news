package com.example.antonio.mynews.ui.topstories

import com.example.antonio.mynews.capture
import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.data.Multimedium
import com.example.antonio.mynews.data.source.ArticlesDataSource.LoadArticlesCallback
import com.example.antonio.mynews.data.source.ArticlesRepository
import com.example.antonio.mynews.eq
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class TopStoriesPresenterTest {

    @Mock private lateinit var mockArticlesRepository: ArticlesRepository
    @Mock private lateinit var mockTopStoriesView: TopStoriesContract.View
    @Captor private lateinit var loadArticlesCallbackCaptor: ArgumentCaptor<LoadArticlesCallback>

    private lateinit var topStoriesPresenter: TopStoriesPresenter
    private lateinit var articles: MutableList<Article>

    @Before fun setUpTopStoriesPresenter() {
        MockitoAnnotations.initMocks(this)

        topStoriesPresenter = TopStoriesPresenter(mockArticlesRepository, mockTopStoriesView)
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
        topStoriesPresenter = TopStoriesPresenter(mockArticlesRepository, mockTopStoriesView)

        // Then the presenter is set to the view
        verify(mockTopStoriesView).presenter = topStoriesPresenter
    }

    @Test fun loadArticles_shouldShowArticlesIntoView_whenArticlesAreAvailable() {
        val section = "Health"
        topStoriesPresenter.loadArticles(section)

        // Callback is captured and invoked with stubbed tasks
        verify(mockArticlesRepository).getArticles(eq(section), capture(loadArticlesCallbackCaptor))
        loadArticlesCallbackCaptor.value.onArticlesLoaded(articles)

        verify(mockTopStoriesView).clearArticles()
        verify(mockTopStoriesView).showArticles(articles)
    }

    @Test fun loadArticles_shouldShowFailedRequestErrorMessage_whenArticlesAreUnavailable() {
        val section = "Health"

        topStoriesPresenter.loadArticles(section)

        verify(mockArticlesRepository).getArticles(
                eq(section),
                capture(loadArticlesCallbackCaptor)
        )
        loadArticlesCallbackCaptor.value.onDataNotAvailable()


        verify(mockTopStoriesView).showFailedRequestMessage()
    }


    @Test
    fun fetchArticles_shouldShowArticlesFromRemoteDataSourceIntoView_whenArticlesAreAvailable() {
        val section = "Health"
        topStoriesPresenter.fetchArticles(section)

        verify(mockArticlesRepository).getArticlesFromRemoteDataSource(
                eq(section),
                capture(loadArticlesCallbackCaptor)
        )
        loadArticlesCallbackCaptor.value.onArticlesLoaded(articles)

        verify(mockTopStoriesView).hideRefreshingIndicator()
        verify(mockTopStoriesView).clearArticles()
        verify(mockTopStoriesView).showArticles(articles)
        verify(mockTopStoriesView).showArticlesUpToDateMessage()
    }

    @Test fun fetchArticles_shouldShowFailedRequestErrorMessage_whenArticlesAreUnavailable() {
        val section = "Health"
        topStoriesPresenter.fetchArticles(section)

        // Callback is captured and invoked with stubbed tasks
        verify(mockArticlesRepository).getArticlesFromRemoteDataSource(
                eq(section),
                capture(loadArticlesCallbackCaptor)
        )
        loadArticlesCallbackCaptor.value.onDataNotAvailable()

        verify(mockTopStoriesView).hideRefreshingIndicator()
        verify(mockTopStoriesView).showFailedRequestMessage()

    }

    @Test fun onArticleClicked_shouldNavigateToArticleUrl() {
        val articleUrl = "https://www.article.com/article"
        topStoriesPresenter.onArticleClicked(articleUrl)


        verify(mockTopStoriesView).navigateToArticleUrl(articleUrl)
    }

    @Test fun onShareArticledClicked_shouldNavigateToArticleChooser() {
        val articleTitle = "title"
        val articleUrl = "https://www.article.com/article"
        topStoriesPresenter.onShareArticleButtonClicked(articleTitle, articleUrl)

        verify(mockTopStoriesView).navigateToShareArticleChooser(articleTitle, articleUrl)
    }

    @Test fun onArchiveArticleButtonClicked_shouldArchiveArticle_whenIsArchivedIsFalse() {
        val article = articles[0]
        article.isArchived = false
        topStoriesPresenter.onArchiveArticleButtonClicked(article)

        verify(mockArticlesRepository).updateArticle(article.id, true)
        verify(mockTopStoriesView).showArticleArchiveConfirmation(true)
    }

    @Test fun onArchiveArticleButtonClicked_shouldUnArchiveArticle_whenIsArchivedIsTrue() {
        val article = articles[0]
        article.isArchived = true
        topStoriesPresenter.onArchiveArticleButtonClicked(article)

        verify(mockArticlesRepository).updateArticle(article.id, false)
        verify(mockTopStoriesView).showArticleArchiveConfirmation(false)
    }
}