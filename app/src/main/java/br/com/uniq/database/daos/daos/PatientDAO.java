package br.com.uniq.database.daos.daos;

import br.com.uniq.database.daos.ConnectionFactory;
import br.com.uniq.database.daos.dbos.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PatientDAO {

    public static void signUp(Patient patient) throws Exception {
        if (patient == null) {
            throw new Exception("Paciente não fornecido");
        }
        if (patient.getCpfPatient().length() != 11) {
            throw new Exception("CPF inválido!");
        }
        if (patient.getAge() > 120) {
            throw new Exception("Idade inválida!");
        }
        if (isSignUp(patient.getCpfPatient())) {
            throw new Exception("Paciente já registrado, insira outro CPF");
        }

        Connection connection = ConnectionFactory.getConnection();
        try {
            String sql;

            sql = "INSERT INTO [dbo].[Pacientes] " +
                  "(nome, cpf_paciente, idade, senha) " +
                  "VALUES (?, ?, ?, ?)";
            if (connection != null) {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, patient.getName());
                ps.setString(2, patient.getCpfPatient());
                ps.setInt(3, patient.getAge());
                ps.setString(4, patient.getPassword());

                ps.executeUpdate();
                System.out.println("Paciente registrado com sucesso!");
                connection.close();
            }
        } catch (Exception e) {
            throw new Exception("Erro ao registrar paciente: " + patient.getCpfPatient());
        }
    }

    public static boolean isSignUp(String cpfPatient) throws Exception {
        if (cpfPatient == null) {
            throw new Exception("CPF não inserido.");
        }
        if (cpfPatient.length() != 11) {
            throw new Exception("CPF inválido!");
        }
        boolean isSignUp = false;
        Connection connection = ConnectionFactory.getConnection();

        String sql;
        sql = "SELECT cpf_paciente from [dbo].[Pacientes] WHERE cpf_paciente = ?";

        if (connection != null) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, cpfPatient);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isSignUp = true;
            }
        }
        connection.close();
        return isSignUp;
    }
}
