package io.l8.brochr.api.edeka

import java.util.Calendar

interface QueryBuilder {
    companion object {
        /**
         * Builds a shop search query for the Edeka-API, which seems to use Apache Solr
         * @param query
         * Query string to use when searching. Currently, query is only performed by postal code
         */
        fun build(query: String): String {
            // Use start of day as date query parameter
            val startOfDay = Calendar.getInstance()
            startOfDay.set(Calendar.HOUR, 0)
            startOfDay.set(Calendar.MINUTE, 0)
            startOfDay.set(Calendar.SECOND, 0)
            startOfDay.set(Calendar.MILLISECOND, 0)
            val startOfDayMillis = startOfDay.timeInMillis
            val endOfYesterDayMillis = startOfDayMillis - 1

            // TODO: Figure out how to query by city or shop name
            val stringBuilder = StringBuilder()
            stringBuilder.append("(indexName:b2cMarktDBIndex AND plz_tlc:")
            stringBuilder.append(query)
            stringBuilder.append("* AND kanalKuerzel_tlcm:edeka AND ((freigabeVonDatum_longField_l:[0 TO ")
            stringBuilder.append(endOfYesterDayMillis)
            stringBuilder.append("] AND freigabeBisDatum_longField_l:[")
            stringBuilder.append(startOfDayMillis)
            stringBuilder.append(" TO *])AND NOT (datumAppHiddenVon_longField_l:[0 TO ")
            stringBuilder.append(endOfYesterDayMillis)
            stringBuilder.append("] AND datumAppHiddenBis_longField_l:[")
            stringBuilder.append(startOfDayMillis)
            stringBuilder.append(" TO *]))) OR (indexName:b2cMarktDBIndex AND plz_tlc:")
            stringBuilder.append(query)
            stringBuilder.append("* AND kanalKuerzel_tlcm:edeka AND ((freigabeVonDatum_longField_l:[0 TO ")
            stringBuilder.append(endOfYesterDayMillis)
            stringBuilder.append("] AND freigabeBisDatum_longField_l:[")
            stringBuilder.append(startOfDayMillis)
            stringBuilder.append(" TO *])AND (datumAppHiddenVon_longField_l:[* TO *] AND datumAppHiddenBis_longField_l:[* TO *])))")
            return stringBuilder.toString()
        }
    }
}