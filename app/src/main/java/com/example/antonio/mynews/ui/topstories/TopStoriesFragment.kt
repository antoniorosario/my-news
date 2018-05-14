package com.example.antonio.mynews.ui.topstories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.antonio.mynews.R
import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.di.Injector
import com.example.antonio.mynews.ui.adapter.ArticleAdapter
import com.example.antonio.mynews.ui.adapter.OnArticleClickListener
import com.example.antonio.mynews.utils.indefiniteSnackbar
import com.example.antonio.mynews.utils.snackbar
import kotlinx.android.synthetic.main.fragment_top_stories.*

class TopStoriesFragment : Fragment(), TopStoriesView {

    private lateinit var topStoriesPresenter: TopStoriesPresenter
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var section: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        section = arguments!!.getString(ARG_SECTION)
        topStoriesPresenter = Injector.provideTopStoriesPresenter(activity!!)
        topStoriesPresenter.topStoriesView = this
        articleAdapter = Injector.provideArticleAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_top_stories, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        topStoriesPresenter.loadArticles(section)

        swipe_refresh_layout.setOnRefreshListener {
            topStoriesPresenter.fetchArticles(section)
        }
    }

    private fun initRecyclerView() {
        article_recycler_view.layoutManager = Injector.provideLayoutManager(activity)
        articleAdapter.articleClickListener = object : OnArticleClickListener {
            override fun onArticleClicked(articleUrl: String) {
                topStoriesPresenter.onArticleClicked(articleUrl)
            }

            override fun onShareArticleButtonClicked(articleTitle: String, articleUrl: String) {
                topStoriesPresenter.onShareArticleButtonClicked(articleTitle, articleUrl)
            }

            override fun onArchiveArticleButtonClicked(article: Article) {
                topStoriesPresenter.onArchiveArticleButtonClicked(article)
            }
        }
        article_recycler_view.adapter = articleAdapter
    }

    override fun showArticles(articles: List<Article>) {
        articleAdapter.addArticles(articles)
    }

    override fun showArticlesUpToDateMessage() =
            snackbar(article_recycler_view, R.string.articles_up_to_date)

    override fun showFailedRequestMessage() =
            indefiniteSnackbar(
                    article_recycler_view,
                    R.string.no_internet_connection,
                    R.string.retry,
                    { topStoriesPresenter.loadArticles(section) }
            )

    override fun showArticleArchiveConfirmation(isArchived: Boolean) {

        snackbar(article_recycler_view,
                getString(R.string.archive_confirmation_text, isArchived.toString())
        )

    }

    override fun navigateToArticleUrl(articleUrl: String) {
        activity?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl)))
    }

    override fun navigateToShareArticleChooser(articleTitle: String, articleUrl: String) {

        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, activity?.getString(R.string.share_article_subject))
        intent.putExtra(Intent.EXTRA_TEXT, activity?.getString(R.string.share_article_text, articleTitle, articleUrl))
        intent = Intent.createChooser(intent, activity?.getString(R.string.send_article))

        if (intent.resolveActivity(activity?.packageManager) != null) {
            activity?.startActivity(intent)
        }
    }

    override fun clearArticles() {
        articleAdapter.removeArticles()
    }

    override fun hideRefreshingIndicator() {
        swipe_refresh_layout.isRefreshing = false
    }

    companion object {
        private const val ARG_SECTION = "arg_section"
        fun newInstance(section: String): TopStoriesFragment {
            val args = Bundle()
            args.putString(ARG_SECTION, section)

            val fragment = TopStoriesFragment()
            fragment.arguments = args

            return fragment
        }
    }

}