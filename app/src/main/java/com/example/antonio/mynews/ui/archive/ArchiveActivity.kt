package com.example.antonio.mynews.ui.archive

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.antonio.mynews.R
import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.di.Injector
import com.example.antonio.mynews.ui.adapter.ArticleAdapter
import com.example.antonio.mynews.ui.adapter.OnArticleClickListener
import com.example.antonio.mynews.utils.snackbar
import kotlinx.android.synthetic.main.activity_archive.*
import kotlinx.android.synthetic.main.toolbar.*

class ArchiveActivity : AppCompatActivity(), ArchiveContract.View {
    override lateinit var presenter: ArchiveContract.Presenter
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        articleAdapter = Injector.provideArticleAdapter()
        presenter = Injector.provideArchivePresenter(this, this)
        initToolbar()
        initRecyclerView()
        presenter.start()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowTitleEnabled(true)
        }
    }

    private fun initRecyclerView() {
        article_recycler_view.layoutManager = Injector.provideLayoutManager(this)
        articleAdapter.articleClickListener = object : OnArticleClickListener {
            override fun onArticleClicked(articleUrl: String) {
                presenter.onArticleClicked(articleUrl)
            }

            override fun onShareArticleButtonClicked(articleTitle: String, articleUrl: String) {
                presenter.onShareArticleButtonClicked(articleTitle, articleUrl)
            }

            override fun onArchiveArticleButtonClicked(article: Article) {
                presenter.onArchiveArticleButtonClicked(article)
            }
        }

        article_recycler_view.adapter = articleAdapter
    }


    override fun showArticles(articles: List<Article>) {
        empty_text.visibility = View.GONE
        articleAdapter.addArticles(articles)
    }

    override fun showArticleArchiveConfirmation(isArchived: Boolean) {
        snackbar(
                article_recycler_view,
                getString(R.string.archive_confirmation_text, isArchived.toString())
        )
    }

    override fun showEmptyText() {
        empty_text.visibility = View.VISIBLE
    }

    override fun navigateToArticleUrl(articleUrl: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl)))
    }

    override fun navigateToShareArticleChooser(articleTitle: String, articleUrl: String) {

        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_article_subject))
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_article_text, articleTitle, articleUrl))
        intent = Intent.createChooser(intent, getString(R.string.send_article))

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    companion object {
        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, ArchiveActivity::class.java)
        }
    }
}
