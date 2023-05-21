package hr.fer.ika.ikasus.tests.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author Luka Ćurić
 */
public class InstantUtil {
    public static record InstantRange(Instant from, Instant to, String name) {}

    public static List<InstantRange> overlappingInstants(Instant relativeTo) {
        InstantRange overlapping = new InstantRange(
                relativeTo.minus(90, ChronoUnit.DAYS),
                relativeTo.minus(70, ChronoUnit.DAYS),
                "overlapping"
        );

        InstantRange inside = new InstantRange(
                relativeTo.minus(70, ChronoUnit.DAYS),
                relativeTo.minus(65, ChronoUnit.DAYS),
                "inside"
        );

        InstantRange contains = new InstantRange(
                relativeTo.minus(90, ChronoUnit.DAYS),
                relativeTo.minus(50, ChronoUnit.DAYS),
                "contains"
        );

        return List.of(overlapping, inside, contains);
    }

    public static List<InstantRange> nonOverlappingInstants(Instant relativeTo) {
        InstantRange beforeAll = new InstantRange(
                relativeTo.minus(120, ChronoUnit.DAYS),
                relativeTo.minus(90, ChronoUnit.DAYS),
                "beforeAll"
        );

        InstantRange between = new InstantRange(
                relativeTo.minus(39, ChronoUnit.DAYS),
                relativeTo.minus(31, ChronoUnit.DAYS),
                "between"
        );

        InstantRange afterAll = new InstantRange(
                relativeTo.plus(50, ChronoUnit.DAYS),
                relativeTo.plus(90, ChronoUnit.DAYS),
                "afterAll"
        );

        InstantRange borderCase = new InstantRange(
                relativeTo.minus(40, ChronoUnit.DAYS),
                relativeTo.minus(30, ChronoUnit.DAYS),
                "borderCase"
        );

        return List.of(beforeAll, between, afterAll, borderCase);
    }

    public static InstantRange pickInstant(Instant relativeTo, int i) {
        return switch (i % 4) {
            case 0 -> new InstantRange(
                    relativeTo.minus(80, ChronoUnit.DAYS),
                    relativeTo.minus(60, ChronoUnit.DAYS),
                    "default"
            );

            case 1 -> new InstantRange(
                    relativeTo.minus(50, ChronoUnit.DAYS),
                    relativeTo.minus(40, ChronoUnit.DAYS),
                    "default"
            );

            case 2 -> new InstantRange(
                    relativeTo.minus(30, ChronoUnit.DAYS),
                    relativeTo.minus(20, ChronoUnit.DAYS),
                    "default"
            );

            default -> new InstantRange(
                    relativeTo.plus(20, ChronoUnit.DAYS),
                    relativeTo.plus(40, ChronoUnit.DAYS),
                    "default"
            );
        };
    }
}
