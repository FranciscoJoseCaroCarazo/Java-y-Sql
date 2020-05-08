package com.fpdualbbdd.data;

import java.util.ArrayList;
import java.sql.*;

public class DataConnection {

	// Atributos
	private static Connection con;
	private static DataConnection INSTANCE = null;

	private DataConnection() {

	}

	public static void performConnection() {
		String host = "fcaro.salesianas.es";
		String user = "Admin2";
		String pass = "@currocaro2001*";
		String dtbs = "fcaro_bd_fpdual";

		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dtbs + "?autoReconnect=true&useSSL=false",
					user, pass);
			System.out.println("Conexión establecida.");

		} catch (Exception e) {
			System.out.println("Error en l'obertura de la connexió.");
		}
	}

	/**
	 * Crear una instancia de conexion
	 */

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DataConnection();
			performConnection();
		}
	}

	public static DataConnection getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	/**
	 * Método cerrar conexion
	 */

	public void closeConnection() {
		try {
			con.close();
		} catch (Exception e) {
			System.out.println("Error de la conexion con la base de datos.");
		}
	}

	public ArrayList<String> devolverProducto() throws SQLException {
		ArrayList<String> ls = new ArrayList<String>();

		PreparedStatement ps = con.prepareStatement("SELECT * FROM productos");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ls.add(rs.getString("nombre"));
			ls.add(rs.getString("categoria"));
		}
		rs.close();
		return ls;
	}

	public void insertarProducto(String nom, String cat) throws SQLException {

		String insert = "INSERT INTO `productos` (`nombre`,`categoria`)VALUES (?,?)";

		PreparedStatement ps = con.prepareStatement(insert);

		ps.setString(1, nom);

		ps.setString(2, cat);

		ps.executeUpdate();

	}

	public void actualizarCat(String nom, String cat) throws SQLException {

		String update = "UPDATE `productos` SET categoria = ? WHERE nombre = ?";

		try {
			PreparedStatement ps = con.prepareStatement(update);
			ps.setString(2, nom);
			ps.setString(1, cat);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Los datos son incorrectos");
		}

	}
	
	public void borrarProd(String nom) throws SQLException {

		String delete = "DELETE FROM `productos` WHERE nombre = ?";

		try {
			PreparedStatement ps = con.prepareStatement(delete);
			ps.setString(1, nom);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Los datos son incorrectos");
		}

	}
	
	

}
