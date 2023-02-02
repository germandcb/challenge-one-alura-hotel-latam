package controller;

import java.sql.Date;
import java.util.List;

import dao.HuespedDAO;
import factory.ConnectionFactory;
import modelo.Huesped;

public class HuespedController {

	private HuespedDAO huespedDAO;

	public HuespedController() {
		huespedDAO = new HuespedDAO(new ConnectionFactory().recuperaConexion());
	}

	public void guardar(Huesped huesped) {
		huespedDAO.guardar(huesped);
	}

	public List<Huesped> listar() {
		return huespedDAO.listar();
	}

	public int modificar(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono,
			Integer idReserva, Integer id) {
		return huespedDAO.modificar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}

	public int eliminar(Integer id) {
		return huespedDAO.eliminar(id);
	}

	public List<Huesped> listar(String nombre) {
		return huespedDAO.listar(nombre);
	}

}
