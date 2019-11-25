package tmobile.github.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tmobile.github.model.Details;
import kotlinx.android.synthetic.main.list_row_details_repo.view.*
import tamobile.github.R


/**
 * Created by Amanjeet Singh on 13/11/17.
 */
class RepoSearchAdapter(final val context: Context, private var myAndroidOsList: List<Details>?) : RecyclerView.Adapter<RepoSearchAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return myAndroidOsList?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(myAndroidOsList?.get(position))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_row_details_repo,
                parent, false)
        return MyViewHolder(v)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(result: Details?) {

            itemView.reponame.text = result?.repoName.toString();
            var userForks = "<b> Forks    : </b>" + result?.forks.toString();
            var userWatchers =  "<b>  Watchers   : </b>" + result?.watchers.toString();
            var luserStrangers = "<b>  Strangers  : </b>" + result?.strangers.toString();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.forks.setText(Html.fromHtml(userForks, Html.FROM_HTML_MODE_COMPACT));
                itemView.watchers.setText(Html.fromHtml(userWatchers, Html.FROM_HTML_MODE_COMPACT));
                itemView.strangers.setText(Html.fromHtml(luserStrangers, Html.FROM_HTML_MODE_COMPACT));

            } else {
                itemView.forks.setText(Html.fromHtml(userForks));
                itemView.watchers.setText(Html.fromHtml(userWatchers));
                itemView.strangers.setText(Html.fromHtml(luserStrangers));
            }

            itemView.setOnClickListener{
                openNewTabWindow(result?.webUrl,itemView.context);
            }
        }

        private fun openNewTabWindow(urls: String?, context: Context?) {
            val uris = Uri.parse(urls)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context?.startActivity(intents)
        }
    }

    fun notifiedDatasetChange(newList: List<Details>?){
        myAndroidOsList = listOf()
        myAndroidOsList = newList
        notifyDataSetChanged()
    }



}