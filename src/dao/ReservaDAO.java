package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Reserva;

public class ReservaDAO {
	
	private Connection con;
	
	public ReservaDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Reserva reserva) {
		try {

			final PreparedStatement statement = con.prepareStatement(
					"INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_pago) " + "VALUES(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			try (statement) {

				statement.setDate(1, reserva.getFechaEntrada());
				statement.setDate(2, reserva.getFechaSalida());
				statement.setFloat(3, reserva.getValor());
				statement.setString(4, reserva.getFormaPago());

				statement.execute();

				final ResultSet resultSet = statement.getGeneratedKeys();

				try (resultSet) {
					while (resultSet.next()) {

						reserva.setId(resultSet.getInt(1));

					}
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Reserva> listar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		try {
			PreparedStatement statement = con.prepareStatement(
					"SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas");
			
			try(statement) {
				statement.execute();
				
				buscarReservas(reservas, statement);
			}
			
			return reservas;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> listar(Integer id) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		try {
			PreparedStatement statement = con.prepareStatement(
					"SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas WHERE id = ?");
			
			try(statement) {
				statement.setInt(1, id);
				statement.execute();
				
				buscarReservas(reservas, statement);
			}
			
			return reservas;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	private void buscarReservas(List<Reserva> reservas, PreparedStatement statement) throws SQLException {
		ResultSet resultSet = statement.getResultSet();
		
		try (resultSet) {
			while (resultSet.next()) {
				Reserva reserva = new Reserva(
						resultSet.getInt(1),
						resultSet.getDate(2),
						resultSet.getDate(3),
						resultSet.getFloat(4),
						resultSet.getString(5));
				
				reservas.add(reserva);
			}
		}
	}

	public int modificar(Date fechaEntrada, Date fechaSalida, Float valor, String formaPago, Integer id) {
		
		try {
			PreparedStatement statement = con.prepareStatement(
					"UPDATE reservas SET fecha_entrada = ?, fecha_salida = ?, valor = ?, forma_pago = ? WHERE ID = ?");
			
			try(statement) {
				statement.setDate(1, fechaEntrada);
				statement.setDate(2, fechaSalida);
				statement.setFloat(3, valor);
				statement.setString(4, formaPago);
				statement.setInt(5, id);
				
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
					"DELETE FROM reservas WHERE id = ?");
			
			try (statement) {
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
