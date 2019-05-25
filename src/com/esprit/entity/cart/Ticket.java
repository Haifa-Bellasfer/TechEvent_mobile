package com.esprit.entity.cart;

import com.esprit.entity.event.Event;

/**
 *
 * @author PC ASUS
 */
public class Ticket {

    private int id_ticket;
    private int user_id;
    private Event event;

    private int status;
    private String qr_code;
    private int code;

    public Ticket() {
    }

    public int getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(int id_ticket) {
        this.id_ticket = id_ticket;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Ticket(int id_ticket, Event event) {
        this.id_ticket = id_ticket;
        this.event = event;
    }

    public Ticket(int id_ticket, int user_id, Event event, int status) {
        this.id_ticket = id_ticket;
        this.user_id = user_id;
        this.event = event;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Ticket{" + "id_ticket=" + id_ticket + ", user_id=" + user_id + ", event=" + event + ", status=" + status + ", qr_code=" + qr_code + ", code=" + code + '}';
    }

}
