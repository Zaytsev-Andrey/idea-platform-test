package ru.ideaplatform;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Flight {

    @JsonProperty("tickets")
    private List<Ticket> tickets;

    public Flight() {
    }

    public Flight(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

}
