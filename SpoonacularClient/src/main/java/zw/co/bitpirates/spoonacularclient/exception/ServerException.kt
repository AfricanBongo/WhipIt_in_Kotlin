package zw.co.bitpirates.spoonacularclient.exception

import java.lang.Exception

/**
 * This exception is thrown when there is an error with communicating with the server.
 */
class ServerException @JvmOverloads constructor(
    message: String = "Error communicating with the server.",
    cause: Throwable) : Exception(message, cause)