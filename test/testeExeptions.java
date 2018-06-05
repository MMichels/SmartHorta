

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mateus.michels
 */
public class testeExeptions {
    public static void main(String[] args) {
        String umidadeTerra = "Umid.DaTerra:78%";
        try{
            int umidade = Integer.parseInt(umidadeTerra.substring(13, 15));
            System.out.println(umidade);
        }catch(NumberFormatException e){
            System.out.println(e);
        }
    }
}
