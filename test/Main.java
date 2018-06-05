/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import comarduinojava2.serial.SerialCom;
import comarduinojava2.serial.SerialComLeitura;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mateus.michels
 */
public class Main extends SerialCom{

    static SerialCom listaPortas = new SerialCom();
    public static SerialComLeitura portaCom;
    
    
    public static void main(String[] args) {
        // TODO code application logic here[
        String Recebido = "";
        String Enviar = "";
        String leituras[] = new String[8];
        String nomePorta;
        int caracteres = 0;
        int baudRate;
        Scanner entrada = new Scanner(System.in);
        String[] portas = listaPortas.ObterPortas();
        
        for (int i = 0; i < portas.length; i++){
            System.out.println(portas[i]);
        }
        //System.out.println("Digite o nome da porta: ");
        nomePorta = "COM3";
        //System.out.println("Digite o baudrate: ");
        baudRate = 57600;
        portaCom = new SerialComLeitura(nomePorta, baudRate, 0);
        portaCom.ObterIdDaPorta();
        portaCom.AbrirPorta();
        portaCom.LerDados();
        while(true){
            Recebido = funcLeituraSerial();
            int tamanho = Recebido.length();
            if(tamanho >= 112){
                for(int c = 0; c < 8; c++){
                    try{
                        leituras[c] = Recebido.substring(caracteres, caracteres + 16);
                        leituras[c] = leituras[c].substring(leituras[c].indexOf(":") + 1, leituras[c].length()).trim();
                        caracteres += 16;
                        System.out.printf("%d = %s \n",c ,leituras[c]);
                    }catch(StringIndexOutOfBoundsException e){
                        System.out.printf("Limite excedido, caractere = %d", caracteres);
                        caracteres -= 16;
                    }
                }
            }
            caracteres = 0;
            Recebido = "";
        }
    }
    public static String funcLeituraSerial(){
        String lido = "";
        //String Ler2 = "";
        portaCom.HabilitarLeitura();
        //portaCom.ObterIdDaPorta();
        //portaCom.AbrirPorta();
        //Controle de tempo da portaCom aberta na serial
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.out.println("Erro na Thread: " + ex);
        }
        lido = portaCom.getDadoslidos();
        //portaCom.FecharCom();
        //System.out.println("Lido = " +lido);
        return lido;
    }
    public static void funcEscritaSerial(String enviar){
        portaCom.ObterIdDaPorta();
        //portaCom.AbrirPorta();
        //esperar(500);
        portaCom.HabilitarEscrita();
        portaCom.EnviarUmaString(enviar);
        //esperar(500);
        //portaCom.FecharCom();    
    }
    public static void esperar(int tempo){
        try {
            //System.out.println("Esperando");
            TimeUnit.MILLISECONDS.sleep(tempo);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}   
    
