package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Appointment in the health book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment implements Comparable<Appointment> {

    private AppointmentId appointmentId;
    private String doctor;
    private String patient;
    private LocalDateTime dateTime;
    private Status status;
    private String comments;
    private List<Prescription> prescriptions;

    public Appointment(int appointmentCounter, String doctor, String patient, LocalDateTime dateTime) {
        appointmentId = new AppointmentId(appointmentCounter);
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = Status.UPCOMING;
        prescriptions = new ArrayList<>();
    }

    public Appointment(AppointmentId appointmentId, String doctor, String patient,
                       LocalDateTime dateTime, Status status,
                       String comments, List<Prescription> prescriptions) {
        this.appointmentId = appointmentId;
        this.doctor = doctor;
        this.patient = patient;
        this.dateTime = dateTime;
        this.status = status;
        if (comments != null) {
            this.comments = comments;
        }
        if (prescriptions != null) {
            this.prescriptions = prescriptions;
        } else {
            this.prescriptions = new ArrayList<>();
        }
    }

    public int getAppointmentId() {
        return appointmentId.getAppointmentId();
    }

    public String getDoctor() {
        return doctor;
    }

    public String getPatient() {
        return patient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    /**
     * already verified that prescription exists
     */
    public void deletePrescription(String medicineName) {
        Prescription toRemove = null;
        for (Prescription p : prescriptions) {
            if (p.getMedicineName().toString().equals(medicineName)) {
                toRemove = p;
            }
        }
        prescriptions.remove(toRemove);
    }

    public void completeAppointment() {
        status = Status.COMPLETED;
    }

    /**
     * Checks if target appointment is later than appointment argument by LocalDateTime.
     * Return true if target appointment is later than other appointment. Else, return false.
     */
    public boolean isLaterThan(Appointment appointmentToCheck) {
        return dateTime.isAfter(appointmentToCheck.dateTime);
    }

    /**
     * Returns true if {@code appointments} has the same appointmentId.
     */
    public boolean isSameAppointment(Appointment toCheck) {
        return this.appointmentId.getAppointmentId() == toCheck.getAppointmentId();
    }

    /**
     * Returns true if {@code appointments} has the clash with appointmentId.
     */
    public boolean hasClashAppointment(Appointment toCheck) {
        LocalDateTime lowerBound = this.dateTime;
        LocalDateTime upperBound = this.dateTime.plusMinutes(30);
        LocalDateTime timeToCheckStart = toCheck.getDateTime();
        LocalDateTime timeToCheckEnd = toCheck.getDateTime().plusMinutes(30);

        // Check if start of appointment toCheck is between current appointment.
        if (timeToCheckStart.equals(lowerBound)
                || (timeToCheckStart.isAfter(lowerBound) && timeToCheckStart.isBefore(upperBound))) {
            return true;
        }

        // Check if end of appointment toCheck is between current appointment.
        if (timeToCheckEnd.equals(upperBound)
                || (timeToCheckEnd.isAfter(lowerBound) && timeToCheckEnd.isBefore(upperBound))) {
            return true;
        }
        return false;
    }

    /**
     *  Returns 1 if {@code appointment} is earlier than other appointment
     */
    @Override
    public int compareTo(Appointment o) {
        if (this.isLaterThan(o)) {
            return -1;
        } else if (!this.isLaterThan(o)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two appointments.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Appointment)) {
            return false;
        }
        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getAppointmentId() == getAppointmentId()
                && otherAppointment.getDoctor().equals(getDoctor())
                && otherAppointment.getPatient().equals(getPatient())
                && otherAppointment.getDateTime().equals(getDateTime());
    }
}
