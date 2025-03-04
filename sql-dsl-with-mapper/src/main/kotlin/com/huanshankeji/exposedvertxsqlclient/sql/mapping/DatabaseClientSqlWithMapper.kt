package com.huanshankeji.exposedvertxsqlclient.sql.mapping

import com.huanshankeji.exposed.BuildWhere
import com.huanshankeji.exposed.datamapping.DataQueryMapper
import com.huanshankeji.exposed.datamapping.DataUpdateMapper
import com.huanshankeji.exposed.datamapping.updateBuilderSetter
import com.huanshankeji.exposedvertxsqlclient.DatabaseClient
import com.huanshankeji.exposedvertxsqlclient.ExperimentalEvscApi
import com.huanshankeji.exposedvertxsqlclient.sql.*
import com.huanshankeji.vertx.sqlclient.datamapping.RowDataQueryMapper
import io.vertx.sqlclient.RowSet
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder

// TODO move to a separate module

@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.executeQueryWithMapper(
    query: Query,
    dataQueryMapper: DataQueryMapper<Data>
): RowSet<Data> =
    executeWithMapping(query) { row -> dataQueryMapper.resultRowToData(row.toExposedResultRowWithTransaction(query)) }

@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.executeVertxSqlClientRowQueryWithMapper(
    query: Query, rowDataQueryMapper: RowDataQueryMapper<Data>
): RowSet<Data> =
    executeWithMapping(query, rowDataQueryMapper::rowToData)

@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.selectWithMapper(
    columnSet: ColumnSet, dataQueryMapper: DataQueryMapper<Data>, buildQuery: Query.() -> Query
) =
    executeQueryWithMapper(columnSet.select(dataQueryMapper.neededColumns).buildQuery(), dataQueryMapper)

@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.selectWithMapper(
    columnSet: ColumnSet, dataQueryMapper: DataQueryMapper<Data>
) =
    selectWithMapper(columnSet, dataQueryMapper) { this }

@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.insertWithMapper(
    table: Table, data: Data, dataUpdateMapper: DataUpdateMapper<Data>
) =
    insert(table, dataUpdateMapper.updateBuilderSetter(data))

@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.insertIgnoreWithMapper(
    table: Table, data: Data, dataUpdateMapper: DataUpdateMapper<Data>
) =
    insertIgnore(table, dataUpdateMapper.updateBuilderSetter(data))

fun <Data : Any, ColumnSetT : ColumnSet> DataUpdateMapper<Data>.batchUpdateBuilderSetter():
        ColumnSetT.(UpdateBuilder<*>, Data) -> Unit = { insertStatement, element ->
    setUpdateBuilder(element, insertStatement)
}

// TODO: consider removing the table param by adding it to `DataUpdateMapper`

@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.batchInsertWithMapper(
    table: Table, data: Iterable<Data>, dataUpdateMapper: DataUpdateMapper<Data>
) =
    batchInsert(table, data, dataUpdateMapper.batchUpdateBuilderSetter())

@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.batchInsertIgnoreWithMapper(
    table: Table, data: Iterable<Data>, dataUpdateMapper: DataUpdateMapper<Data>
) =
    batchInsertIgnore(table, data, dataUpdateMapper.batchUpdateBuilderSetter())


/**
 * In most cases you should specify the fields to update in a more detailed way instead of using this function.
 */
@ExperimentalEvscApi
suspend fun <Data : Any> DatabaseClient<*>.updateWithMapper(
    table: Table, where: BuildWhere? = null, limit: Int? = null, data: Data, dataUpdateMapper: DataUpdateMapper<Data>
) =
    update(table, where, limit, dataUpdateMapper.updateBuilderSetter(data))
