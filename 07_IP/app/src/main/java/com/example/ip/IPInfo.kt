package com.example.ip

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IPInfo(
    @Json(name = "ip") val ip: String,
    @Json(name = "hostname") val hostname: String,
    @Json(name = "city") val city: String,
    @Json(name = "region") val region: String,
    @Json(name = "country") val country: String,
    @Json(name = "loc") val loc: String,
    @Json(name = "org") val org: String,
    @Json(name = "postal") val postal: String,
    @Json(name = "timezone") val timezone: String,
    @Json(name = "readme") val readme: String
) {
    override fun toString(): String {
        return "IP: $ip\n" +
                "City: $city\n" +
                "Region: $region\n" +
                "Country: $country\n" +
                "Location: $loc\n" +
                "Postal: $postal\n" +
                "Timezone: $timezone\n\n" +
                "Organization: \n$org\n\n" +
                "Hostname: \n$hostname\n\n" +
                "Readme: \n$readme"
    }
}
