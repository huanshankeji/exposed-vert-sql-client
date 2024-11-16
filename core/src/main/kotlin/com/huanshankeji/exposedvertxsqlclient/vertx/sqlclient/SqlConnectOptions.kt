package com.huanshankeji.exposedvertxsqlclient.vertx.sqlclient

import com.huanshankeji.exposedvertxsqlclient.ConnectionConfig
import io.vertx.sqlclient.SqlConnectOptions

fun SqlConnectOptions.setFrom(connectionConfig: ConnectionConfig.Socket) {
    host = connectionConfig.host
    connectionConfig.port?.let { port = it }
    database = connectionConfig.database
    user = connectionConfig.user
    password = connectionConfig.password
}

fun SqlConnectOptions.setFrom(connectionConfig: ConnectionConfig.UnixDomainSocketWithPeerAuthentication) {
    host = connectionConfig.path
    database = connectionConfig.database
    user = System.getProperty("user.name")
}

fun SqlConnectOptions.setFrom(connectionConfig: ConnectionConfig) =
    when (connectionConfig) {
        is ConnectionConfig.Socket -> setFrom(connectionConfig)
        is ConnectionConfig.UnixDomainSocketWithPeerAuthentication -> setFrom(connectionConfig)
    }
