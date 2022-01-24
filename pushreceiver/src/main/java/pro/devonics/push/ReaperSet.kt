package pro.devonics.push

//const val MY_APP_ID = "31cfe899-246a-471d-8a97-342a4435bcc3"
const val BASE_URL = "aHR0cHM6Ly9wdXNoLmRldm9uaWNzLnByby8="

const val VIEW = "api/apps/{uuid}/show"
const val SUBSCRIBE_USER = "api/push-users"
const val SESSION = "api/push-users/{registrationId}/session"
const val UPDATE_USER = "api/push-users/{registrationId}"
const val SAVE_TAG = "api/push-users/{registrationId}/tag"
const val DURATION = "api/push-users/{registrationId}/time"
const val TRANSITION = "api/push-users/{registrationId}/transition"