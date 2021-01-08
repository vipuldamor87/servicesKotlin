package com.vipuldamor87.foregroundservice.ApiCalls

import com.google.gson.annotations.SerializedName

class Contact {
    @SerializedName("id")
    var id = ""

    @SerializedName("email")
    var email = ""

    @SerializedName("first_name")
    var first_name = ""

    @SerializedName("last_name")
    var last_name = ""

    @SerializedName("avatar")
    var avatar = ""
}