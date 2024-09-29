/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

/**
 *
 * @author hp
 */
public class ConversorSQL {
     public String convertir(String tabla, String valores){
          String primeraParte="" , segundaParte="" ;
        
          boolean cargandoNombre=true, comillas=false;
          String separador="" , dato="", nombre="" ;



          for (int i=0;i<valores.length();i++){  //Recorre la cadena caracter a caracter
               char caracter=valores.charAt(i); //Obtiene el caracter
               if (cargandoNombre){ //Si esta rellenando el nombre
                    if (caracter=='='){ //Si encuentra el =
                         cargandoNombre=false;  //Pasa a rellenar los valores
                         comillas=false;  //Indicador de comillas para leer cadenas
                    }else{
                         nombre+=caracter; //Concatena el caracter
                    }
               }else{  //Si esta rellenando el dato
                    if (caracter=='\''){   //Si encuentra una comilla
                         comillas=!comillas;  //Alterna la detección de cadenas
                    }
                    if ((!comillas && caracter==',')){ //Si no esta en una cadena y encuentra una coma
                         cargandoNombre=true;  //Pasa a cargar el nombre
                         primeraParte+=separador+nombre;   //Concantena el nombre
                         segundaParte+=separador+dato;      //Concantena el valor
                         separador="," ;   //El separador será la coma
                         nombre="" ;     //Inicializa el nombre
                         dato="" ;      //Inicializa el valor
                    }else if(i==valores.length()-1){   //Si ha llegado al final de la cadena
                         dato+=caracter;   //Concatena el caracter
                         primeraParte+=separador+nombre;  //Concatena el nombre
                         segundaParte+=separador+dato;     //Concatena el valor
                    }else{  //Si sigue leyendo caracteres del valor
                         dato+=caracter;   //Concatena el caracter
                    }
                }
          }
          //Devuelve la cadena de inserción
          return "insert into " + tabla + "(" + primeraParte + ") " +
                     "values (" + segundaParte + ")" ;
     }
    
     //Ejemplo para probar el algoritmo.
     public static void main(String args[]){
          String ejemplo="idreferencia=2,dato2='hola',dato3='prueba'" ;
          ConversorSQL con=new ConversorSQL();
          String sql=con.convertir("mitabla", ejemplo);
          System.out.println(sql);
     }
} 