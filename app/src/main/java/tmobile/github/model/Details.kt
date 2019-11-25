package tmobile.github.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by Amanjeet Singh on 29/11/17.
 */
data class Details(
        @SerializedName("login")
        @Expose
        var userName: String? = null,
        @SerializedName("avatar_url")
        @Expose
        var avatarUrl: String? = null,
        @SerializedName("score")
        @Expose
        var totalPages: Double? = null,
        @SerializedName("email")
        @Expose
        var mail: String? = null,
        @SerializedName("location")
        @Expose
        var location: String? = null,
        @SerializedName("followers")
        @Expose
        var followers: Int? = null,
        @SerializedName("following")
        @Expose
        var following: Int? = null,
        @SerializedName("created_at")
        @Expose
        var joindate: String? = null,
        @SerializedName("forks")
        @Expose
        var forks: Int? = null,
        @SerializedName("watchers")
        @Expose
        var watchers: Int? = null,
        @SerializedName("stargazers_count")
        @Expose
        var strangers: Int? = null,
        @SerializedName("name")
        @Expose
        var repoName: String? = null,
        @SerializedName("html_url")
        @Expose
        var webUrl: String? = null)