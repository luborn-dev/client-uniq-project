package test.java.patientdao;

import database.daos.PatientDAO;
import database.dbos.Patient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.DatabaseUtils;

import static org.junit.Assert.*;

public class TestPatientDAO {

    @Test
    @DisplayName("Realiza o cadastro de um paciente e consulta se foi cadastrado no banco")
    void signUp_testReturnSignUpSuccess() throws Exception {
        Patient patient = new Patient("Ruan", "69246924912", 69);
        PatientDAO.signUp(patient);
        Patient rsPatient = DatabaseUtils.getPatientByCPF(patient.getCpfPatient());

        Assertions.assertEquals(rsPatient.toString(), patient.toString());

        DatabaseUtils.deletePatient(patient.getCpfPatient());
    }

    @Test
    @DisplayName("Insere um paciente e tenta inserir ele novamente retornando um erro")
    void signUp_testInsertSomePatient() throws Exception {
        Patient patient = new Patient("Roberto", "12345678901", 15);
        PatientDAO.signUp(patient);
        Patient rsPatient = DatabaseUtils.getPatientByCPF(patient.getCpfPatient());

        Assertions.assertEquals(rsPatient.toString(), patient.toString());
        Exception exception = assertThrows(Exception.class, () -> PatientDAO.signUp(patient));
        Assertions.assertEquals("Paciente já registrado, insira outro CPF", exception.getMessage());

        DatabaseUtils.deletePatient(patient.getCpfPatient());
    }

    @Test
    @DisplayName("Inserido diversos valores inválidos")
    void signUp_testInvalidValues() {
        Exception exception = assertThrows(Exception.class, () -> PatientDAO.signUp(null));
        Assertions.assertEquals("Paciente não fornecido", exception.getMessage());

        Patient patient1 = new Patient("João", "123", 15);
        Exception exception1 = assertThrows(Exception.class, () -> PatientDAO.signUp(patient1));
        Assertions.assertEquals("CPF inválido!", exception1.getMessage());

        Patient patient2 = new Patient("Cláudio", "12345678901", 121);
        Exception exception2 = assertThrows(Exception.class, () -> PatientDAO.signUp(patient2));
        Assertions.assertEquals("Idade inválida!", exception2.getMessage());
    }
}
