package model;

import java.time.LocalDate;
import java.util.Date;

public class reservation {
    private int reservavtionId;
    private int customerId;
    private int roomNumber;
    private LocalDate reserationDate;
    private LocalDate leaveDate;

    public reservation(int reservavtionId, int customerId, int roomNumber, LocalDate reserationDate, LocalDate leaveDate) {
        this.reservavtionId = reservavtionId;
        this.customerId = customerId;
        this.roomNumber = roomNumber;
        this.reserationDate = reserationDate;
        this.leaveDate = leaveDate;
    }
    public reservation()
    {

    }

    public int getReservavtionId() {
        return reservavtionId;
    }

    public void setReservavtionId(int reservavtionId) {
        this.reservavtionId = reservavtionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getReserationDate() {
        return reserationDate;
    }

    public void setReserationDate(LocalDate reserationDate) {
        this.reserationDate = reserationDate;
    }

    public LocalDate getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(LocalDate leaveDate) {
        this.leaveDate = leaveDate;
    }
}
