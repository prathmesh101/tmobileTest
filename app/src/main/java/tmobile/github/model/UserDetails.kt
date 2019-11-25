package tmobile.github.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



/**
 * Created by Amanjeet Singh on 29/11/17.
 */
data class UserDetails(
       /* @SerializedName("page")
                  @Expose
                  var page: Int? = null,
                  @SerializedName("total_results")
                  @Expose
                  var totalResults: Int? = null,
                  @SerializedName("total_pages")
                  @Expose
                  var totalPages: Int? = null,
                  @SerializedName("results")
                  @Expose
                  var results: List<Result>? = null)*/
        @SerializedName("total_count")
        @Expose
        var count: Int? = null,
        @SerializedName("incomplete_results")
        @Expose
        var results: Boolean? = null,
        @SerializedName("score")
        @Expose
        var totalPages: Double? = null,
        @SerializedName("items")
        @Expose
        var list: List<Details>? = null)
