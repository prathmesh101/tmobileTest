package tmobile.github.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import tmobile.github.activity.RepoSearchActivity
import com.squareup.picasso.Picasso
import tmobile.github.model.Details;
import com.squareup.picasso.Callback
import kotlinx.android.synthetic.main.list_row_item.view.*
import tamobile.github.R


/**
 * Created by Amanjeet Singh on 13/11/17.
 */
class UserSearchAdapter(final val context: Context, private var myAndroidOsList: List<Details>?) : RecyclerView.Adapter<UserSearchAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return myAndroidOsList?.size!!
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(myAndroidOsList?.get(position))
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_row_item,
                parent, false)
        return MyViewHolder(v)
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(result: Details?) {
            itemView.movie_title.text = result?.userName
            itemView.android_name_text_view.text = result?.totalPages.toString()
            itemView.progress_bar.visibility = View.VISIBLE
            Picasso.with(itemView.context).load(result?.avatarUrl.toString())
                    .into(itemView.image_view_movie, object : Callback {

                        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                        override fun onError() {
                            //Show Error here
                            itemView.image_view_movie.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_launcher_background))
                        }

                        override fun onSuccess() {
                            itemView.progress_bar.visibility = View.GONE
                        }

                    });
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, RepoSearchActivity::class.java);
                intent.putExtra("user_name", result?.userName)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                itemView.context.startActivity(intent);
            }
        }
    }

    fun notifiedDatasetChange(newList: List<Details>?) {
        myAndroidOsList = listOf()
        myAndroidOsList = newList
        notifyDataSetChanged()
    }
}