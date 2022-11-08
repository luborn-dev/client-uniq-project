package br.com.uniq.uniq;

import br.com.uniq.uniq.database.daos.PatientDAO;
import br.com.uniq.uniq.database.dbos.Patient;

public class CallBd{
    public static void addPatient(){
        try{
            PatientDAO.signUp(new Patient("Lucas", "12142678955", 31));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // other static methods to communicate w/ database
}
