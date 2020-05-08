package com.fpdualbbdd.view;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;
import com.fpdualbbdd.data.DataConnection;

public class View {
	
	private static DataConnection mc = DataConnection.getInstance();
	private static int op;

	public static void main(String[] args) throws Exception {
		
		menu();
		op=readInt();
		exmenu(op);
	}

	private static String readString() throws Exception {
		BufferedReader br = new BufferedReader(
		new InputStreamReader(System.in));
		return br.readLine();
	}
	
	private static int readInt() {
		Scanner sc = new Scanner(System.in);
		return sc.nextInt();
		}
	
	public static void menu(){
		System.out.println("Seleccioneu una opció del menú:");
		System.out.println("1. Añadir nuevo producto");
		System.out.println("2. Visualizar productos");
		System.out.println("3. Actualizar Categoria");
		System.out.println("4. Borrar Producto");
		System.out.println("0. Salir");
	}
	
	private static void exmenu(int op2) throws Exception {
		String nom;
		String cat;
		boolean ok=false;
		
		switch (op2) {
		case 1:			//Añadir producto
			System.out.println("Nombre del producto");
			nom=readString();
			System.out.println("Categoria del producto");
			cat=readString();
			addProd(nom,cat);
			System.out.println("producto añadido");
			break;
		case 2:
			//Consultar todos los productos
			mostraProds();
			break;
		case 3:			
			System.out.println("Nombre del producto a cambiar la categoria");
			nom=readString();
			System.out.println("Nueva categoria del producto");
			cat=readString();
			actuCat(nom,cat);
			System.out.println("producto Modificado");
		break;
		case 4:
			System.out.println("Nombre del producto que desee borrar");
			nom=readString();
			borrarProd(nom);
			System.out.println("producto Borrado Exitosamente");
			break;
			
		case 0:
			mc.closeConnection();
			break;
		default:
			menu();
			op=readInt();
			exmenu(op);
		break;
		}
		if(op!=-1){
			System.out.println("Presiona intro para continuar");
			String nada=readString();//no se usa, es para pausar
									 //por si ha de salir otra vez el menu para hacer otra accion
			menu();
			op=readInt();
			exmenu(op);
		}
	}
	
	static void addProd(String nom,String cat){
		try {
			mc.insertarProducto(nom,cat);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void mostraProds(){
		try {
			for (int i = 0; i < mc.devolverProducto().size(); i++) {
				System.out.println(mc.devolverProducto().get(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void actuCat(String nom,String cat){
		try {
			mc.actualizarCat(nom,cat);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void borrarProd(String nom){
		try {
			mc.borrarProd(nom);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}