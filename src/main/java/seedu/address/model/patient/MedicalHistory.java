package seedu.address.model.patient;

import java.util.ArrayList;

public class MedicalHistory {
    private ArrayList<String> allergies;
    private ArrayList<String> conditions;

    public MedicalHistory() {
        allergies = new ArrayList<String>();
        conditions = new ArrayList<String>();
    }

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    public void addCondition(String condition) {
        conditions.add(condition);
    }
}
