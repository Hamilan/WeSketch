package networking;

import java.util.ArrayList;

import shared.Requirement;
import shared.Session;
import shared.User;
import sketching.Sketch;

/**
 * This class holds the information to be managed by the server.
 * Since it is only created at the beginning of the server this could mean
 * reading information from the database or files.
 * There must be only one object of this class and it won't be transmitted through the net.  
 * @author Hamilan
 */
public class ServerSharedData{
	/**
	 * Current design session of the server
	 */
	private Session session;
	/**
	 * Registered users
	 */
	private ArrayList<User> users;
	/**
	 * Registered requirements
	 */
	private ArrayList<Requirement> requirements = new ArrayList<Requirement>();

	public ServerSharedData() {
		//loadUsers();
		//loadRequirements();
		loadSession();
	}
	/**
	 * Retrieves previously saved sessions from database/file
	 */
	private void loadSession() {
		session = new Session("Sesión de prototipado por defecto");
	}
	/**
	 * Retrieves previously created requirements from database/file
	 */
	private void loadRequirements() {
		requirements = new ArrayList<Requirement>();
		//for this first version of the software, 4 default requirements are created
		requirements.add( new Requirement(1, "Editar el Perfil de Usuario en un sitio web",
				"Considere que la interfaz gráfica debe:\n" +
				"1. Mostrar la foto de perfil actual y permitir lanzar la carga de una foto ubicada en el disco duro para cambiar la actual.\n" +
				"2. Permitir cambiar:\n" +
				"a. nombres\n" +
				"b. apellidos\n" +
				"c. género\n" +
				"d. fecha de nacimiento\n" +
				"e. ciudad de residencia\n" +
				"f. email\n" +
				"g. web personal")
		);
		requirements.add( new Requirement(2,"Programar y publicar un evento",
				"Para cada evento se debe capturar y/o mostrar la siguiente información:\n" +
				"• Nombre del evento\n" +
				"• Fecha del evento\n" +
				"• Tipo de evento (Fiesta, Comida, Reunión, General)\n" +
				"• Descripción\n" +
				"• Lugar\n" +
				"• Visibilidad (Todos, Solo invitados, Solo inscritos)") 
		);
		requirements.add( new Requirement(3,"Ver listado de eventos programados.",
				"Debe visualizarse una lista de información resumida de todos los eventos:\n"+
				"•	Nombre del evento, Fecha, Tipo de evento, Visibilidad\n"+
				"•	Opción de inscribirse al evento (solicitar inscripción con un solo clic)\n")
		);
		requirements.add( new Requirement(4,"Ver información de un evento",
				"En esta ventana debe visualizarse toda la información del evento")
		);
	}
	/**
	 * Retrieves previously registered users from database/file
	 */
	private void loadUsers() {
		users = new ArrayList<User>();
		//for this first version of the software, 6 default users are created
		users.add( new User(1,"hamilan","123", "Hamilton", "Hernandez", "hamilan@cveguid.com", "7460262", 'M') );
		users.add( new User(2,"david","123", "David", "Cadena", "david@cveguid.com", "7460262", 'M') );
		users.add( new User(3,"jesse","123", "Jesse", "Lopez", "jesse@cveguid.com", "7460262", 'M') );
		users.add( new User(4,"stephanie","123", "Stephanie", "Londoño", "stephanie@cveguid.com", "7460262", 'F') );
		users.add( new User(5,"helmuth","123", "Helmuth", "Trefftz", "helmuth@cveguid.com", "7460262", 'F') );
		users.add( new User(6,"marilyn","123", "Marilyn", "Tremaine", "marilyn@cveguid.com", "7460262", 'F') );
	}
	
	public Session getSession() {
		return session;
	}
	public ArrayList<Requirement> getRequirements() {
		return requirements;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public void registerUser(User u){
		users.add(u);
		//TODO: save new user in the database or file
	}
	public void registerRequirement(Requirement req){
		requirements.add(req);
		//TODO: save new requirement in the database or file
	}
	/**
	 * removes from the session the requirement indicated by id
	 * @param id
	 */
	public void removeRequirement(long id) {
		for (Requirement req : requirements) {
			if(req.id==id){
				requirements.remove(req);
				return;
			}
		}
	}
	/**
	 * removes from the server the user indicated by id.
	 * This method should be never called, we don't want to lose information about users
	 * @param id
	 */
	public void removeUser(long id) {
		for (User u : users) {
			if (u.id==id) {
				users.remove(u);
				return;
			}
		}
	}
	public String getUserLogin(long userId) {
		for (User u : users) {
			if (u.id==userId) {
				return u.login;
			}
		}
		return null;
	}
	public boolean existsUser(String login) {
		for (User u : users) {
			if (u.login.equals(login)) {
				return true;
			}
		}
		return false;
	}
	public void registerSketch(Sketch sk) {
		this.session.sketches.add(sk);
	}
	
	
}
