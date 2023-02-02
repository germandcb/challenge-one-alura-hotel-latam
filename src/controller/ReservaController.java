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
		this.reservaDAO.guardar(reserva);
	}

	public List<Reserva> listar() {
		return this.reservaDAO.listar();
	}

	public int modificar(Date fechaEntrada, Date fechaSalida, Float valor, String formaPago, Integer id) {
		return this.reservaDAO.modificar(fechaEntrada, fechaSalida, valor, formaPago, id);
	}
 
}
