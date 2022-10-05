package montanez.alexander.saturnwallpapers.model

import java.util.*

class UnknownException: Exception()

class PhoneNetworkException: Exception()

class NotDataForDayFoundException: Exception()

class MissingPhotoException(val astronomicPhoto: AstronomicPhoto) : Exception()