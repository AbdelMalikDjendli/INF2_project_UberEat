package top.nextnet.service;

import java.util.Collection;

public interface TicketingService {
    Collection<ETicket> fillTicketsWithCustomerInformations(Collection<ETicket> tickets);

    void notifyCreatedTicket(String ticketKey);
}
