package seedu.address.model.patient;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.*;

public class Patient extends Person {
    // Variables
    private String telegramId;
    private MedicalHistory medicalHistory;
    private Queue<Appointment> upcomingAppointments;
    private Stack<Appointment> pastAppointments;

    // Constructor
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, String telegramId) {
        super(name, phone, email, address, tags);
        setTelegramId(telegramId);
        upcomingAppointments = new LinkedList<>();
        pastAppointments = new Stack<>();
    }

    // Get Methods
    public String getTelegramId() {
        return telegramId;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    // Set Methods
    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    // Helper Methods
    // This method adds an allergy to the patient's medical history
    public void addAllergy(String allergy) {
        medicalHistory.addAllergy(allergy);
    }

    // This method adds a condition to patient's medical history
    public void addCondition(String condition) {
        medicalHistory.addCondition(condition);
    }

    // This method adds an appointment to the Patient
    public void addUpcomingAppointment(Appointment appointment) {
        upcomingAppointments.add(appointment);
    }

    // This method completes the most recently appointment and adds it into the pastAppointment stack
    public void completeUpcomingAppointment() {
        Appointment completedAppointment = upcomingAppointments.remove();
        completedAppointment.completeAppointment();
        pastAppointments.push(completedAppointment);
    }
}
