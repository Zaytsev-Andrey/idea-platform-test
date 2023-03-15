package ru.ideaplatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JsonFileMapper implements FileMapper {

    private final ObjectMapper objectMapper;

    public JsonFileMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<Ticket> readToTicketList(Path path) throws IOException {
        Flight flight = objectMapper.readValue(path.toFile(), Flight.class);
        return flight.getTickets();
    }
}
