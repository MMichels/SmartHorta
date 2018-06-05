/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comarduinojava2.serial;

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;


/**
 *
 * @author mateus.michels
 * Classe responsavel por escrever dados na Serial.
 */
public class SerialCom {
    
    protected String[] portas;
    protected Enumeration listaDePortas;
    
    public SerialCom(){
        this.listaDePortas = CommPortIdentifier.getPortIdentifiers();
    }
    
    public String[] ObterPortas(){
        ListarPortas();
        return portas;
    }
    
    //função de armazenar uma lista das portas seriais do seu sistema 
    //disponíveis para a comunicação
    protected void ListarPortas(){
        int i = 0;
        portas = new String[10];
        while (listaDePortas.hasMoreElements()){
            CommPortIdentifier ips = (CommPortIdentifier)listaDePortas.nextElement();
            portas[i] = ips.getName();
            i++;
        }
    }
    
    /*
    identificar se a porta seleciona existe e está tudo em funcionamento com ela
    */
    
    public boolean PortaExiste(String COMp){
        int i = 0;
        String[] temp = {};
        boolean existe = false;
        while(listaDePortas.hasMoreElements()){
            CommPortIdentifier ips = (CommPortIdentifier)listaDePortas.nextElement();
            temp[i] = ips.getName();
            if (temp[i].equals(COMp)==true){
                existe = true;
            }
            i++;
        }
        return existe;
    }   
}
