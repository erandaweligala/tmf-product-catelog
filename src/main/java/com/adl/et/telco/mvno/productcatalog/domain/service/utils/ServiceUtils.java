package com.adl.et.telco.mvno.productcatalog.domain.service.utils;

import com.adl.et.telco.mvno.productcatalog.domain.entities.TimePeriod;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceUtils {

    public static boolean checkVersionDecrement(String prev, String next) {
        if (next == null || next.isEmpty() || prev == null || prev.isEmpty()) {
            return false;
        }

        String[] prevTokenized = prev.split("\\.");
        String[] nextTokenized = next.split("\\.");

        for (int i = 0; i < prevTokenized.length || i < nextTokenized.length; i++) {
            int prevCheck = i < prevTokenized.length ? Integer.parseInt(prevTokenized[i]) : 0;
            int nextCheck = i < nextTokenized.length ? Integer.parseInt(nextTokenized[i]) : 0;
            if (prevCheck < nextCheck) {
                return false;
            }
            if (prevCheck > nextCheck) {
                return true;
            }
        }

        return false;
    }

    public static boolean isInvalidTimePeriod(TimePeriod period) {

        return period != null &&
                period.getEndDateTime() != null &&
                period.getStartDateTime() != null &&
                period.getStartDateTime().isAfter(period.getEndDateTime());
    }
}
