package au.edu.adelaide.connected_health_app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

public class PatientSingleton {
    private static PatientSingleton mInstance = null;

    private String mString;

    private JSONArray jJournalEntries;
    private JSONArray jMedicalNotes;
    private JSONArray jMeasurementTypes;
    private JSONArray jQuestionnaires;

    private JSONArray jCurrentArray;
    private JSONObject jCurrentObject;

    private PatientSingleton(){
        mString = "Private constructor string";
        jQuestionnaires = new JSONArray();
    }

    public static PatientSingleton getInstance(){
        if(mInstance == null)
        {
            mInstance = new PatientSingleton();
        }
        return mInstance;
    }

    public String getString(){
        return this.mString;
    }

    public void setString(String value){
        mString = value;
    }

    public void addQuestionnaire(String questionnaire) throws JSONException {
        jQuestionnaires.put(new JSONObject(questionnaire));
    }

    public JSONArray getQuestionnaires() {
        return jQuestionnaires;
    }

    public ArrayList<JSONObject> getQuestionnaires(int first, int last) throws JSONException {
        ArrayList<JSONObject> questionnaires = new ArrayList<JSONObject>();
        for (int i = first; i <= last; i++) {
            if (i >= jQuestionnaires.length()) {
                break;
            }
            questionnaires.add(jQuestionnaires.getJSONObject(i));
        }
        return questionnaires;
    }

    public void setJournalEntries(String journalEntries) throws JSONException {
        jJournalEntries = new JSONArray(journalEntries);
    }

    public void setMedicalNotes(String medicalNotes) throws JSONException {
        jMedicalNotes = new JSONArray(medicalNotes);
    }

    public JSONArray getJournalEntries() {
        jCurrentArray = jJournalEntries;
        return jJournalEntries;
    }

    public ArrayList<JSONObject> getJournalEntries(int first, int last) throws JSONException {
        jCurrentArray = jJournalEntries;
        ArrayList<JSONObject> journalEntries = new ArrayList<JSONObject>();
        for (int i = first; i <= last; i++) {
            if (i >= jJournalEntries.length()) {
                break;
            }
            journalEntries.add(jJournalEntries.getJSONObject(i));
        }
        return journalEntries;
    }

    public void setMeasurementTypes(String measurementTypes) throws JSONException {
        jMeasurementTypes = new JSONArray(measurementTypes);
    }

    public JSONArray getMeasurementTypes() {
        return jMeasurementTypes;
    }

    public ArrayList<JSONObject> getMeasurementTypes(int first, int last) throws JSONException {
        jCurrentArray = jMeasurementTypes;
        ArrayList<JSONObject> measurementTypes = new ArrayList<JSONObject>();
        for (int i = first; i <= last; i++) {
            if (i >= jMeasurementTypes.length()) {
                break;
            }
            measurementTypes.add(jMeasurementTypes.getJSONObject(i));
        }
        return measurementTypes;
    }

    public JSONArray getMedicalNotes() {
        jCurrentArray = jMedicalNotes;
        return jMedicalNotes;
    }

    public ArrayList<JSONObject> getMedicalNotes(int first, int last) throws JSONException {
        jCurrentArray = jMedicalNotes;
        ArrayList<JSONObject> medicalNotes = new ArrayList<JSONObject>();
        for (int i = first; i <= last; i++) {
            if (i >= jMedicalNotes.length()) {
                break;
            }
            medicalNotes.add(jMedicalNotes.getJSONObject(i));
        }
        return medicalNotes;
    }

    public void setCurrentObject(JSONObject object) {
        jCurrentObject = object;
    }

    public JSONObject getCurrentObject() {
        return jCurrentObject;
    }

    public JSONArray getCurrentArray() {
        return jCurrentArray;
    }
}
