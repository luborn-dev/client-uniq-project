package test.java.utils;

import database.daos.PatientDAO;
import database.dbos.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class TestDatabaseUtils {

    @Test
    @DisplayName("Deleta um paciente com sucesso")
    void deletePatient_testDeleteExistingPatient() throws Exception {
        Patient patient = new Patient("Roberto", "12345678901", 22);
        PatientDAO.signUp(patient);
        Patient rsPatient = DatabaseUtils.getPatientByCPF(patient.getCpfPatient());

        Assertions.assertEquals(rsPatient.toString(), patient.toString());

        DatabaseUtils.deletePatient(patient.getCpfPatient());

        rsPatient = DatabaseUtils.getPatientByCPF(patient.getCpfPatient());
        Assertions.assertNull(rsPatient);
    }
}
