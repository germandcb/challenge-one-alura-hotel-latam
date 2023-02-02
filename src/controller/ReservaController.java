package controller;

import java.sql.Date;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import modelo.Reserva;

public class ReservaController {

	private ReservaDAO reservaDAO;
	
	public ReservaController () {
		this.reservaDAO = new ReservaDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Reserva reserva) {
		reservaDAO.guardar(reserva);
	}

	public List<Reserva> listar() {
		return reservaDAO.listar();
	}

	public int modificar(Date fechaEntrada, Date fechaSalida, Float valor, String formaPago, Integer id) {
		return reservaDAO.modificar(fechaEntrada, fechaSalida, valor, formaPago, id);
	}

	public int eliminar(Integer id) {
		return reservaDAO.eliminar(id);
	}

	public List<Reserva> listar(Integer id) {
		return reservaDAO.listar(id);
	}
 
}
