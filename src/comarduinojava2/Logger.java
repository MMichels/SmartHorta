
package comarduinojava2;

import java.io.IOException;
import java.util.Calendar;
import txtlogfiles.Registrador;
public class Logger {
    private String nome_log;
    private Registrador log;
    private String[] content;
    
    public Logger(){
        this.content = new String[8];
        Calendar.getInstance();
        this.nome_log= Integer.toString(Calendar.YEAR) + Integer.toString(Calendar.MONTH) + Integer.toString(Calendar.DATE);
        this.log = new Registrador(this.nome_log + ".txt", "logs/");
    }
    public void analiseTemp(String temp){
        if(this.content[0].equalsIgnoreCase(temp)){
            this.content[0] = temp;
            try {
                this.log.setContent("Temperatura: " + temp);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } 
    }
    public void analiseUmidAr(String umid){
        if(this.content[1].equalsIgnoreCase(umid)){
            this.content[1] = umid;
            try {
                this.log.setContent("Humidade do Ar: " + umid);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } 
    }
    public void analiseUmidTerra(String umid){
        if(this.content[1].equalsIgnoreCase(umid)){
            this.content[1] = umid;
            try {
                this.log.setContent("Humidade da Terra: " + umid);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } 
    }
    public void analiseAgua(String vol){
        if(this.content[0].equalsIgnoreCase(vol)){
            this.content[0] = vol;
            try {
                this.log.setContent("Volume de Agua: " + vol);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } 
    }
    public void analiseTorn(String state){
        if(this.content[0].equalsIgnoreCase(state)){
            this.content[0] = state;
            try {
                this.log.setContent("Torneira: " + state);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } 
    }
    public void analiseLumin(String nivel){
        if(this.content[0].equalsIgnoreCase(nivel)){
            this.content[0] = nivel;
            try {
                this.log.setContent("Luminosidade: " + nivel);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } 
    }
    public void analiseFtLed(String state){
        if(this.content[0].equalsIgnoreCase(state)){
            this.content[0] = state;
            try {
                this.log.setContent("Fita de Leds: " + state);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } 
    }
    public void analiseTimeLuz(String tempo){
        if(this.content[0].equalsIgnoreCase(tempo)){
            this.content[0] = tempo;
            try {
                this.log.setContent("Tempo sem luz ambiente: " + tempo);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        } 
    }
}
