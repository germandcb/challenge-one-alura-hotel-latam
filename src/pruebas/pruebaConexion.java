package pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class pruebaConexion {

	public static void main(String[] args) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
        System.out.println("Abriendo conexion");
        con.close();
        System.out.println("Cerrando la conexión");

	}
}
