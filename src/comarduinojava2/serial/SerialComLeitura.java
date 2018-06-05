/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comarduinojava2.serial;

//import comarduinojava2.Main;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author mateus.michels
 */
public class SerialComLeitura implements Runnable, SerialPortEventListener{
    
    public String Dadoslidos;
    public int nodeBytes;
    private int baudrate;
    private int timeout;
    private CommPortIdentifier cp;
    private SerialPort porta;
    private OutputStream saida;
    private InputStream entrada;
    private Thread threadLeitura;
    private boolean IDPortaOK;
    private boolean PortaOK;
    private boolean Leitura;
    private boolean Escrita;
    private String Porta;
    protected String pesoLido;
    public String Exception;
    
    public void setDadoslidos(String Dadoslidos){
        this.Dadoslidos = Dadoslidos;
    }
    public String getDadoslidos(){
        return Dadoslidos;
    }
    public void setPesoLido(String pesoLido){
        this.pesoLido = pesoLido;
    }
    public String getPesoLido(){
        return pesoLido;
    }
    //Construtor para definir os parametros da Serial.
    public SerialComLeitura(String p, int b, int t){
        this.Porta = p;
        this.baudrate = b;
        this.timeout = t;
    }
    //Habilitar a escrita e desabilitar a leitura serial:
    public void HabilitarEscrita(){
        Escrita = true;
        Leitura = false;
    }
    //Desabilitar a escrita e habilitar a leitura:
    public void HabilitarLeitura(){
        Escrita = false;
        Leitura = true;
    }
    //O método seguinte irá obter o ID da porta   
    public void ObterIdDaPorta(){
        try{
            cp = CommPortIdentifier.getPortIdentifier(Porta);
            if(cp == null){
                System.out.println("Erro na porta");
                IDPortaOK = false;
                System.exit(1);
            }
            IDPortaOK = true;
            System.out.println(cp.getName());
        }catch(Exception e){
            System.out.println("Erro obtendo ID da porta: " + e);
            IDPortaOK = false;
            //System.exit(1);
            setException(e.getMessage());
        }
    }
    //O método a seguir irá abrir a comunicação com a porta serial, 
    //após essa porta aberta podemos ou enviar ou ler a serial
    public void AbrirPorta(){
        try{
            porta = (SerialPort)cp.open("SerialComLeitura", timeout);
            PortaOK = true;
            porta.setSerialPortParams(baudrate,
                                      porta.DATABITS_8, 
                                      porta.STOPBITS_1, 
                                      porta.PARITY_NONE);
            porta.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            setException("Conectado");
        }catch(Exception e){
            PortaOK = false;
            System.out.println("Erro abrindo comunicação: " +e);
            //System.exit(1);
            setException(e.getMessage());
        }
    }
    public void LerDados(){
        if(Escrita == false){
            try{
                entrada = porta.getInputStream();
            }catch(Exception e){
                System.out.println("Erro de Stream: " +e);
                //System.exit(1);
                setException(e.getMessage());
            }
        }
        try {
            porta.addEventListener((SerialPortEventListener)this);
        }catch(Exception e){
            System.out.println("Erro de listener: " +e);
            //System.exit(1);
            setException(e.getMessage());
        }
        porta.notifyOnDataAvailable(true);
        try{
            threadLeitura = new Thread((Runnable) this);
            threadLeitura.start();
            run();
        }catch(Exception e){
            System.out.println("Erro de Thread: " +e);
            setException(e.getMessage());
        }
    }
    public void run(){
        try {
            Thread.sleep(5);
        } catch (Exception e) {
            System.out.println("Erro de Thred:  "+ e);
            setException(e.getMessage());
        }

    }
    public void serialEvent(SerialPortEvent ev){
        StringBuffer bufferLeitura = new StringBuffer();
        int novoDado = 0;
        
        switch (ev.getEventType()) {

            case SerialPortEvent.BI:

            case SerialPortEvent.OE:

            case SerialPortEvent.FE:

            case SerialPortEvent.PE:

            case SerialPortEvent.CD:

            case SerialPortEvent.CTS:

            case SerialPortEvent.DSR:

            case SerialPortEvent.RI:

            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:

            break;

            case SerialPortEvent.DATA_AVAILABLE:
                //Novo algoritmo de leitura.
                boolean continuar = true;
                while(continuar){
                    while(novoDado != -1){
                        try{
                            novoDado = entrada.read();
                            if(novoDado == -1){
                                break;
                            }
                            if((char)novoDado == '\r'){
                                bufferLeitura.append('\n');
                                continuar = false;
                                //setDadoslidos(new String(bufferLeitura));
                                //setPesoLido(new String(bufferLeitura));
                                //break;
                            }else if((char)novoDado == '\n'){
                                continuar = false;
                            }else{
                                bufferLeitura.append((char)novoDado);
                                //Dadoslidos = (new String(bufferLeitura));
                                //setPesoLido(new String(bufferLeitura));
                            }
                            }catch(IOException ioe){
                                System.out.println("Erro de leitura serial: " + ioe);
                                setException(ioe.getMessage());
                            }
                        setDadoslidos(new String(bufferLeitura));
                        //setPesoLido(new String(bufferLeitura));
                        }
                    //System.out.println("pesolido: " +getPesoLido());
                    //System.out.println("Dadoslidos: " +getDadoslidos());
                }
            break;
        }
    }
    //O método abaixo irá enviar uma string para a porta serial quando invocado:
    public void EnviarUmaString(String msg){
        if (Escrita == true){
            try{
                saida = porta.getOutputStream();
                System.out.println("FLUXO OK! ");
            }catch(Exception e){
                System.out.println("Erro.STATUS" +e);
            }
            try{
                //System.out.println("Enviando um Byte para " +Porta);
                System.out.println("Enviando: "+msg);
                saida.write(msg.getBytes());
                Thread.sleep(1300);
                saida.flush();            
            }catch(Exception e){
                    System.out.println("Houve um erro durante o envio.");
                    System.out.println("STATUS: "+e);
                    //System.exit(1);
                    setException(e.getMessage());
                }
        } else {
            System.exit(1);
        }
    }   
    public void FecharCom(){
        try {
            porta.close();
            setException("Desconectado");
        } catch (Exception e) {
            System.out.println("Erro fechando porta: " + e);
            //System.exit(0);
            setException(e.getMessage());

        }
    }
    
    public void funcEscritaSerial(String enviar){
        ObterIdDaPorta();
        AbrirPorta();
        esperar(500);
        HabilitarEscrita();
        EnviarUmaString(enviar);
        esperar(500);
        FecharCom();    
    }
    public void esperar(int tempo){
        try {
            System.out.println("Esperando");
            TimeUnit.MILLISECONDS.sleep(tempo);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }  
    }
    
    
    private void setException(String exception){
        this.Exception = exception;
    }
    public String getException(){
        return Exception;
    }
    
    public String obterPorta(){
        return Porta;
    }
    public int obterBaudrate(){
        return baudrate;
    }
}
    
 
