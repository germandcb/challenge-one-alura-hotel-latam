package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Huesped;

public class HuespedDAO {
	
	private Connection con;

	public HuespedDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Huesped huesped) {
		
		try {
			
			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva) "
					+ "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			try (statement) {
				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setDate(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setInt(6, huesped.getIdReserva());
				
				statement.execute();
				
				ResultSet resultSet = statement.getGeneratedKeys();
				
				try (resultSet) {
					while (resultSet.next()) {
						huesped.setId(resultSet.getInt(1));
					}
				}
			} 
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huesped> listar() {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		
		try {
			PreparedStatement statement = con.prepareStatement(
					"SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes");
			
			try (statement) {
				statement.execute();
				
				buscarHuespedes(huespedes, statement);
			}
			
			return huespedes;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huesped> listar(String nombre) {
		List<Huesped> huespedes = new ArrayList<Huesped>();
		
		try {
			PreparedStatement statement = con.prepareStatement(
					"SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE nombre = ?");
			
			try (statement) {
				statement.setString(1, nombre);
				statement.execute();
				
				buscarHuespedes(huespedes, statement);
			}
			
			return huespedes;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void buscarHuespedes(List<Huesped> huespedes, PreparedStatement statement) throws SQLException {
		ResultSet resultSet = statement.getResultSet();
		try (resultSet){
			while (resultSet.next()) {
				Huesped huesped = new Huesped(
						resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getDate(4),
						resultSet.getString(5),
						resultSet.getString(6),
						resultSet.getInt(7));
				
				huespedes.add(huesped);
			}
		}
	}

	public int modificar(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		
		try {
			PreparedStatement statement = con.prepareStatement(
					"UPDATE huespedes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, nacionalidad = ?, telefono = ?, id_reserva = ? " + 
					" WHERE ID = ? ");
			
			try (statement) {
				statement.setString(1, nombre);
				statement.setString(2, apellido);
				statement.setDate(3, fechaNacimiento);
				statement.setString(4, nacionalidad);
				statement.setString(5, telefono);
				statement.setInt(6, idReserva);
				statement.setInt(7, id);
				
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminar(Integer id) {
		
		try {
			PreparedStatement statement = con.prepareStatement(
					"DELETE FROM huespedes WHERE id = ?");
			
			try(statement) {
				statement.setInt(1, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
				
				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
