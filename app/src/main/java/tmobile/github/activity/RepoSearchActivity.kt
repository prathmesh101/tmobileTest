package tmobile.github.activity;

import android.annotation.SuppressLint;
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import tmobile.github.RetrofitInterface.ApiInterface
import tmobile.github.adapter.RepoSearchAdapter
import tmobile.github.model.Details
import tmobile.github.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tamobile.github.R
import java.text.SimpleDateFormat

class RepoSearchActivity : AppCompatActivity() {

    private var apiCall: ApiInterface? = null
    private var resultList: List<Details> = listOf()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)


        val profileName = intent.getStringExtra("user_name")
        apiCall = ApiInterface.create()

        getProfileDetails(profileName)

        recycler_view_repo_name.layoutManager = LinearLayoutManager(applicationContext, LinearLayout.VERTICAL, false)
        recycler_view_repo_name.adapter = RepoSearchAdapter(applicationContext, resultList);

        if (Constants.isOnline(applicationContext)) {
            getRepoResult(profileName)
        } else {
            Constants.toast(resources.getString(R.string.check_internet), applicationContext)
        }

        search_repo_name!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val filtermodelist = filter(resultList, s.toString())
                (recycler_view_repo_name.adapter as RepoSearchAdapter).notifiedDatasetChange(filtermodelist)
            }
        })
    }


    private fun getProfileDetails(userName: String?) {
        apiCall?.getUserDetails(Constants.BASE_URL + "/users/" + userName)?.enqueue(object : Callback<Details> {
            override fun onFailure(call: Call<Details>?, t: Throwable?) {
                showError(t?.message)
            }

            @SuppressLint("WrongConstant", "SimpleDateFormat")
            override fun onResponse(call: Call<Details>?, response: Response<Details>?) {
                val movieResponse = response?.body()!!
                if (null != movieResponse) {
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    val date = sdf.parse(movieResponse.joindate)
                    val format = SimpleDateFormat("MM/dd/yyyy").format(date)
                    val location = if (movieResponse.location != null) movieResponse.location else "Not Updated"
                    val usermail = if (movieResponse.mail != null) movieResponse.mail else "Private"

                    val userNames = "<b> UserName  : </b>" + movieResponse.userName
                    val userEmail = "<b>  Email     : </b>" + usermail
                    val luserLocation = "<b>  Location  : </b>" + location
                    val userJoinDate = "<b>  Join Date : </b>" + format
                    val userFollowers = "<b>  Followers : </b>" + movieResponse.followers
                    val userFollowind = "<b>  Following : </b>" + movieResponse.following
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        user_name.setText(Html.fromHtml(userNames, Html.FROM_HTML_MODE_COMPACT));
                        email.setText(Html.fromHtml(userEmail, Html.FROM_HTML_MODE_COMPACT));
                        locations.setText(Html.fromHtml(luserLocation, Html.FROM_HTML_MODE_COMPACT));
                        joindate.setText(Html.fromHtml(userJoinDate, Html.FROM_HTML_MODE_COMPACT));
                        follers.setText(Html.fromHtml(userFollowers, Html.FROM_HTML_MODE_COMPACT));
                        following.setText(Html.fromHtml(userFollowind, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        user_name.setText(Html.fromHtml(userNames));
                        email.setText(Html.fromHtml(userEmail));
                        locations.setText(Html.fromHtml(luserLocation));
                        joindate.setText(Html.fromHtml(userJoinDate));
                        follers.setText(Html.fromHtml(userFollowers));
                        following.setText(Html.fromHtml(userFollowind));
                    }
                    avatar_progress_bar.visibility = View.VISIBLE
                    Picasso.with(applicationContext).load(movieResponse.avatarUrl)
                            .into(avatar_image, object : com.squareup.picasso.Callback {
                                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                                override fun onError() {
                                    avatar_image.setImageDrawable(getDrawable(R.drawable.ic_launcher_background))
                                    //Show Error here
                                }
                                override fun onSuccess() {
                                    avatar_progress_bar.visibility = View.GONE
                                }

                            });

                } else {

                }
            }

        })
    }

    private fun getRepoResult(userName: String?) {
        apiCall?.getUserRepos(Constants.BASE_URL + "/users/" + userName + "/repos")?.enqueue(object : Callback<List<Details>> {
            override fun onFailure(call: Call<List<Details>>?, t: Throwable?) {
                showError(t?.message)
            }
            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<List<Details>>?, response: Response<List<Details>>?) {
                val movieResponse = response?.body()!!
                if (null != movieResponse) {
                    resultList = movieResponse
                    (recycler_view_repo_name.adapter as RepoSearchAdapter).notifiedDatasetChange(resultList)
                } else {
                    resultList = listOf()
                    (recycler_view_repo_name.adapter as RepoSearchAdapter).notifiedDatasetChange(resultList);
                }
            }

        })
    }

    private fun showError(message: String?) {
        Constants.toast(message.toString(),applicationContext)
    }

    fun filter(pl: List<Details>, querie: String): MutableList<Details> {
        var query = querie
        query = query.toLowerCase()
        val filteredModeList = ArrayList<Details>()
        for (model in pl) {
            val text = model.repoName!!.toLowerCase()
            if (text.startsWith(query)) {
                filteredModeList.add(model)
            }
        }
        return filteredModeList
    }
}
