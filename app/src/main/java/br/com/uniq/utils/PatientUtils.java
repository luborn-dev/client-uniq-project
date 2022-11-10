package br.com.uniq.utils;

public class PatientUtils {

    public static void validateCPF(String cpfPatient) throws Exception {
        if (cpfPatient == null) {
            throw new Exception("CPF não foi inserido!");
        }
        if (cpfPatient.length() != 11) {
            throw new Exception("CPF inválido!");
        }
    }
}
