package com.example.antonio.mynews.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.antonio.mynews.R
import com.example.antonio.mynews.data.Article
import com.example.antonio.mynews.utils.getElapsedPublicationTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_list_item.view.*
import org.joda.time.DateTime

/*
   storing the data within the adapter itself and manipulating it (by removing, adding, or
   updating elements) breaks the passive view’s principle and general MVP pattern.

   Follow this guide(https://android.jlelse.eu/recyclerview-in-mvp-passive-views-approach-8dd74633158)
   and make ArticleAdapter more passive
 */
class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {

    private var articles = mutableListOf<Article>()
    lateinit var articleClickListener: OnArticleClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.article_list_item, parent, false)

        return ArticleHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val article = getItem(position)
        holder.bindArticle(article, articleClickListener)
    }

    override fun getItemCount(): Int = articles.size

    fun addArticles(articlesToAdd: List<Article>) {
        articles.addAll(articlesToAdd)
        notifyDataSetChanged()
    }

    fun removeArticles() {
        articles = mutableListOf()
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Article = articles[position]

    class ArticleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val context = itemView.context
        private val titleTextView = itemView.article_title
        private val articleImageImageView = itemView.article_image
        private val abstractTextView = itemView.article_abstract
        private val publicationDateTextView = itemView.article_publication_date
        private val shareArticleButton = itemView.share_article_button
        private val archiveArticleButton = itemView.archive_article_button


        fun bindArticle(article: Article, articleClickListener: OnArticleClickListener) {

            itemView.setOnClickListener({
                articleClickListener.onArticleClicked(article.url)
            })

            shareArticleButton.setOnClickListener({
                articleClickListener.onShareArticleButtonClicked(article.title, article.url)
            })

            archiveArticleButton.setOnClickListener({
                articleClickListener.onArchiveArticleButtonClicked(article)
            })

            with(article) {
                titleTextView.text = article.title
                abstractTextView.text = article._abstract

                val publishedDateTime = DateTime(DateTime.parse(article.publishedDate))
                publicationDateTextView.text = publishedDateTime.getElapsedPublicationTime(context)
                if (multimedia.isNotEmpty()) {
                    Picasso
                            .with(context)
                            .load(multimedia[0].url)
                            .fit()
                            .into(articleImageImageView)
                } else {

                articleImageImageView.visibility = View.GONE
                }
            }
        }
    }
}