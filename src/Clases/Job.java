/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author hp
 */
/**
 */
public class Job implements Runnable{
    
    public static boolean band=false;    
    private int tiempo = 5;
    
    /**
     * Constructor de clase
     */
    public Job( int tiempo ){
        this.tiempo = tiempo;
    }
    
    @Override
    public void run() {
        band=false;
        for (int i=1; i <= this.tiempo ; i++){
            System.out.println("Haciendo algo divertido... -> " + i + " segundo transcurrido.");
            try{Thread.sleep( 1000 );}
            catch (InterruptedException e){
                System.err.println( e.getMessage() );
            }
        }
        band=true;
    }



    
}




