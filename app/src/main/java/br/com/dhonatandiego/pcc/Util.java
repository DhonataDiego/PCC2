package br.com.dhonatandiego.pcc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.dhonatandiego.pcc.exception.ValidationException;

public class Util {
    public static SimpleDateFormat dateFormat;
    public static final String dataFormater_dd_MM_yyyy = "dd-MM-yyyy";
    public static final String dataTimeFormater_dd_MM_yyy_HH_mm_ss = "dd-MM-yyyy HH:mm:ss";
    public static final String dataTimeFormater_SQLITE = "dd-MM-yyyy HH:mm:ss";
    static boolean CamposPreenchidos = false;


    public static boolean verificarConexaoInternet() {
        return true;
    }

    public static boolean validaSenha(String senha) throws ValidationException{
        if (senha.length() < 8){
            throw new ValidationException("A senha deve ter no mínimo 8 caracteres");
        }
        else
            return true;
    }

    public static boolean validarEmail(String email) throws ValidationException {
        email = email.trim();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (email == null || email.isEmpty()) {
            throw new ValidationException("Campo Email Vazio");
        }
        else if (!matcher.matches()) {
            throw new ValidationException("Formato de email inválido");
        }
        else
            return true;
    }


    public static void validarLoginCadastroLogin(String nome, String login, String senha) throws ValidationException {

        if (nome != null) {
            if (nome.trim().isEmpty() || nome.split(" ").length < 2) {
                throw new ValidationException("Digite nome e sobre no campo Nome");
            }
        }

        Util.validarEmail(login);
        if (senha.isEmpty() || senha.length() < 8) {
            throw new ValidationException("Campo Senha deve ter no mínimo 6 caracteres");
        }
    }



    public static String getStringToFormaterDate(Date date, String formater) {

        dateFormat = new SimpleDateFormat(formater);
        return dateFormat.format(date);

    }

    public static Date getDateToString(String valor, String formater) {

        try {
            dateFormat = new SimpleDateFormat(formater);
            return dateFormat.parse(valor);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean CamposPreenchidos(String s,String e) throws ValidationException {
            if (validarEmail(e) == true && validaSenha(s) == true )
                return true;
        return false;
    }
}
