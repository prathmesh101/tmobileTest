package tmobile.github.activity

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.Toast
import tmobile.github.RetrofitInterface.ApiInterface
import tmobile.github.adapter.UserSearchAdapter
import tmobile.github.model.UserDetails
import tmobile.github.model.Details
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tamobile.github.R
import tmobile.github.utils.Constants
import kotlin.Result.Companion as Result1

class UserSearchActivity : AppCompatActivity() {


    private var apiCall: ApiInterface? = null
    private var resultList: List<Details> = listOf()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        showHide()

        internet_text.setOnClickListener(View.OnClickListener {
            showHide()
        })

        search_user_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length > 0) {
                    getSearchResult(p0.toString())
                } else {
                    resultList = listOf()
                    (recycler_view_name.adapter as UserSearchAdapter).notifiedDatasetChange(resultList);
                }
            }
        });
        apiCall = ApiInterface.create()
        recycler_view_name.layoutManager = LinearLayoutManager(applicationContext,
                LinearLayout.VERTICAL, false)
        recycler_view_name.adapter = UserSearchAdapter(applicationContext, resultList);
    }

    private fun showError(message: String?) {
        toast(message.toString())
    }

    private fun showHide() {
        if (Constants.isOnline(applicationContext)) {
            internet_text.visibility=View.GONE
            recycler_view_name.visibility=View.VISIBLE
        } else {
            internet_text.visibility=View.VISIBLE
            recycler_view_name.visibility=View.GONE
        }
    }

    fun Context.toast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    private fun getSearchResult(message: String?) {
        apiCall?.getSearchUsers(message.toString())?.enqueue(object : Callback<UserDetails> {
            override fun onFailure(call: Call<UserDetails>?, t: Throwable?) {
                showError(t?.message)
            }

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<UserDetails>?, response: Response<UserDetails>?) {
                val movieResponse = response?.body()
                if (null != movieResponse) {
                    resultList = movieResponse.list!!
                    (recycler_view_name.adapter as UserSearchAdapter).notifiedDatasetChange(resultList)
                } else {
                    resultList = listOf()
                    (recycler_view_name.adapter as UserSearchAdapter).notifiedDatasetChange(resultList);
                }
            }

        })
    }


}
