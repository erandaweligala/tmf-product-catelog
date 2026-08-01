package com.adl.et.telco.mvno.productcatalog.external.repositories.utils;

import com.adl.et.telco.dte.mvno.plugin.tmf.domain.entities.Filter;
import com.adl.et.telco.dte.mvno.plugin.tmf.external.exception.RepositoryException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositoryUtils {

    public static void convertFilterValueToDate(Filter filter) {
        if ("lastUpdate".equals(filter.getField()) ||
                filter.getField().endsWith("validFor.startDateTime") ||
                filter.getField().endsWith("validFor.endDateTime")) {

            try {
                filter.setValue(Arrays.stream(filter.getValue())
                        .map(o -> OffsetDateTime.parse((String) o).toInstant().toEpochMilli())
                        .toArray());
            } catch (DateTimeParseException ex) {
                throw new RepositoryException(filter.getField() +
                        " must be in valid date time format", "COMMON_0001");
            }
        }
    }
}
