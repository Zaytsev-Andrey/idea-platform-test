package ru.ideaplatform;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class FlightApp {

    private static final String ORIGIN_CITY = "Владивосток";

    private static final String DESTINATION_CITY = "Тель-Авив";

    private static final double PERCENTILE = 90;

    public static void main(String[] args) {
        FileMapper fileMapper = new JsonFileMapper();
        Path path = null;
        try {
            path = PathParser.parse(args);
            List<Ticket> tickets = fileMapper.readToTicketList(path);
            List<Long> flightSecond = getStreamFlightSecondBetween(tickets, ORIGIN_CITY, DESTINATION_CITY);

            System.out.printf("Среднее время полета между %s и %s составляет %s%n",
                    ORIGIN_CITY, DESTINATION_CITY, getAverageFlightTime(flightSecond));
            System.out.printf("%2.0f-й процентиль времени полета между %s и %s составляет %s%n",
                    PERCENTILE, ORIGIN_CITY, DESTINATION_CITY, getPercentile(flightSecond, PERCENTILE));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.printf("Файл '%s' не найден или имеет не корректный формат данных%n", path);
        }

    }

    private static List<Long> getStreamFlightSecondBetween(List<Ticket> tickets, String origin, String destination) {
        return tickets.stream()
                .filter(ticket -> origin.equalsIgnoreCase(ticket.getOriginName()) &&
                        destination.equalsIgnoreCase(ticket.getDestinationName()) ||
                        origin.equalsIgnoreCase(ticket.getDestinationName()) &&
                                destination.equalsIgnoreCase(ticket.getOriginName())
                )
                .map(ticket ->
                        ChronoUnit.SECONDS.between(
                                LocalDateTime.of(ticket.getArrivalDate(), ticket.getDepartureTime()),
                                LocalDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime())
                        )
                )
                .collect(Collectors.toList());
    }

    private static LocalTime getAverageFlightTime(List<Long> flightSecond) {
        OptionalDouble avgOpt = flightSecond.stream()
                .mapToLong(Long::longValue)
                .average();
        long avgSecond = (long) avgOpt.orElse(0);
        return LocalTime.ofSecondOfDay(avgSecond);
    }

    private static LocalTime getPercentile(List<Long> flightSecond, double percentile) {
        Collections.sort(flightSecond);
        int index = (int) Math.ceil(percentile / 100 * flightSecond.size());
        return LocalTime.ofSecondOfDay(flightSecond.get(index - 1));
    }

}
