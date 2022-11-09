package br.com.uniq.database.daos.utils;

import br.com.uniq.database.daos.daos.PatientDAO;
import br.com.uniq.database.daos.dbos.Patient;
import br.com.uniq.utils.DatabaseUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDatabaseUtils {

    @Test
    @DisplayName("Deleta um paciente com sucesso")
    void deletePatient_testDeleteExistingPatient() throws Exception {
        Patient patient = new Patient("Roberto", "12345678901", 22, "123");
        PatientDAO.getInstance().signUp(patient);
        Patient rsPatient = DatabaseUtils.getPatientByCPF(patient.getCpfPatient());

        assertEquals(rsPatient.toString(), patient.toString());

        DatabaseUtils.deletePatient(patient.getCpfPatient());

        rsPatient = DatabaseUtils.getPatientByCPF(patient.getCpfPatient());
        assertNull(rsPatient);
    }
}
