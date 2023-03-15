package ru.ideaplatform;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileMapper {

    List<Ticket> readToTicketList(Path path) throws IOException;
}
