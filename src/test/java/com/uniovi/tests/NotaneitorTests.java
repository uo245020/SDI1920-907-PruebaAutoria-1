package com.uniovi.tests;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_InvitationView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PostView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaneitorTests {
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\Usuario\\Desktop\\2019-20\\Segundo Semestre\\SDI\\geckodriver022win64.exe";
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8080";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	} // Antes de la primera prueba

	@BeforeClass
	static public void begin() {
	} // Al finalizar la última prueba

	@AfterClass
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	// PR01. Registro de Usuario con datos válidos.
	@Test
	public void PR01() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "pablo@gmail.com", "Pablo", "Perez", "123456", "123456");

		PO_View.checkElement(driver, "text", "Bienvenido/a a la página principal");
	}

	// PRO2. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
	// apellidos vacíos).
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, " ", "Pablo", "Perez", "123456", "123456");
		PO_View.getP(); // Email vacío
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pablo@yahoo.com", " ", "Perez", "123456", "123456");
		// Nombre vacío
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pablo@hotmail.com", "Pablo", " ", "123456", "123456");
		// Apellidos vacíos
		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
	}

	// PR03. Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pablo@mail.com", "Pablo", "Perez", "123456", "12345");
		PO_View.getP(); // Email vacío
		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
	}

	// PRO4. Registro de Usuario con datos inválidos (email existente).
	@Test
	public void PR04() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "pablo@gmail.com", "Pablo", "Perez", "123456", "123456");
		PO_View.getP(); // Email vacío
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
	}

	// PRO5. Inicio de sesión con datos válidos (administrador).
	@Test
	public void PR05() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		PO_View.checkElement(driver, "text", "Usuarios");
	}

	// PRO6. Inicio de sesión con datos válidos (usuario estándar).
	@Test
	public void PR06() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "pedro@email.com", "123456");

		PO_View.checkElement(driver, "text", "Usuarios");
	}

	// PRO7. Inicio de sesión con datos inválidos (usuario estándar, campo email y
	// contraseña vacíos).
	@Test
	public void PR07() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, " ", "123456");
		PO_View.getP();
		// Email vacío
		PO_RegisterView.checkKey(driver, "Error.login.message", PO_Properties.getSPANISH());
		PO_LoginView.fillForm(driver, "pedro@email.com", " ");
		// Contraseña vacía
		PO_RegisterView.checkKey(driver, "Error.login.message", PO_Properties.getSPANISH());
	}

	// PRO8. Inicio de sesión con datos válidos (usuario estándar, email existente,
	// pero contraseña incorrecta).
	@Test
	public void PR08() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "pedro@email.com", "12345");
		PO_View.getP();
		// Contraseña incorrecta
		PO_RegisterView.checkKey(driver, "Error.login.message", PO_Properties.getSPANISH());
	}

	// PRO9. Hacer click en la opción de salir de sesión y comprobar que se redirige
	// a la página de inicio de sesión (Login).
	@Test
	public void PR09() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		PO_PrivateView.clickOption(driver, "logout", "text", "Inicia Sesión");
	}

	// PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no
	// está autenticado.
	@Test
	public void PR10() {
		SeleniumUtils.textoNoPresentePagina(driver, "Cerrar Sesión");
	}

	// PR11. Mostrar el listado de usuarios y comprobar que se muestran todos los
	// que existen en el sistema.
	@Test
	public void PR11() {
		// Accedemos automáticamente después de iniciar sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		SeleniumUtils.textoPresentePagina(driver, "pedro@email.com");
		SeleniumUtils.textoPresentePagina(driver, "lucas@email.com");
		SeleniumUtils.textoPresentePagina(driver, "maria@email.com");
		SeleniumUtils.textoPresentePagina(driver, "marta@email.com");
		SeleniumUtils.textoPresentePagina(driver, "pelayo@email.com");

		// Segunda Página
		PO_PrivateView.clickLink(driver, "?page=1");
		SeleniumUtils.textoPresentePagina(driver, "lucia@email.com");
	}

	// PR12. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
	// página que corresponde
	// con el listado usuarios existentes en el sistema.
	@Test
	public void PR12() {
		// Iniciamos sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Introducimos el texto de búsqueda
		PO_PrivateView.fillSearch(driver, "");
		// Comprobamos que aparecen todos los usuarios
		SeleniumUtils.textoPresentePagina(driver, "pedro@email.com");
		SeleniumUtils.textoPresentePagina(driver, "lucas@email.com");
		SeleniumUtils.textoPresentePagina(driver, "maria@email.com");
		SeleniumUtils.textoPresentePagina(driver, "marta@email.com");
		SeleniumUtils.textoPresentePagina(driver, "pelayo@email.com");

		PO_PrivateView.clickLink(driver, "?page=1");
		SeleniumUtils.textoPresentePagina(driver, "lucia@email.com");
	}

	// PR13. Hacer una búsqueda escribiendo en el campo un texto que no exista y
	// comprobar que se muestra la página que corresponde, con la lista de usuarios
	// vacía.
	@Test
	public void PR13() { // mejorar
		// Iniciamos sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Introducimos el texto de búsqueda
		PO_PrivateView.fillSearch(driver, "me");
		// Comprobamos que no aparece ningún usuario
		SeleniumUtils.textoNoPresentePagina(driver, "pedro@email.com");
		SeleniumUtils.textoNoPresentePagina(driver, "lucas@email.com");
		SeleniumUtils.textoNoPresentePagina(driver, "maria@email.com");
		SeleniumUtils.textoNoPresentePagina(driver, "marta@email.com");
		SeleniumUtils.textoNoPresentePagina(driver, "pelayo@email.com");
		SeleniumUtils.textoNoPresentePagina(driver, "lucia@email.com");
	}

	// PR14. Hacer una búsqueda con un texto específico y comprobar que se muestra
	// la página que corresponde, con la lista de usuarios en los que el texto
	// especificados sea parte de su nombre, apellidos o de su email.
	@Test
	public void PR14() {
		// Iniciamos sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
		// Introducimos el texto de búsqueda
		PO_PrivateView.fillSearch(driver, "lu");

		SeleniumUtils.textoPresentePagina(driver, "lucas@email.com");
		SeleniumUtils.textoPresentePagina(driver, "lucia@email.com");
	}

	// PR15. Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario.
	// Comprobar que la solicitud de amistad aparece en el listado de invitaciones
	// (punto siguiente).
	@Test
	public void PR15() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@email.com", "123456");
		// Comprobamos que entramos en la pagina privada de Maria
		PO_View.checkElement(driver, "text", "Usuarios");

		PO_InvitationView.clickEnlace(driver, "Marta");
	}

	// PR16. Desde el listado de usuarios de la aplicación, enviar una invitación de
	// amistad a un usuario al
	// que ya le habíamos enviado la invitación previamente. No debería dejarnos
	// enviar la invitación, se podría
	// ocultar el botón de enviar invitación o notificar que ya había sido enviada
	// previamente.
	@Test
	public void PR16() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "maria@email.com", "123456");
		// Comprobamos que entramos en la pagina privada de Maria
		PO_View.checkElement(driver, "text", "Usuarios");
		// Desaparece el boton por lo que si descomentamos la siguiente linea da error
		// PO_InvitationView.clickEnlace(driver, "Marta");

	}

	// PR17. Mostrar el listado de invitaciones de amistad recibidas. Comprobar con
	// un listado que
	// contenga varias invitaciones recibidas
	@Test
	public void PR17() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");

		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@email.com", "123456");
		// Comprobamos que entramos en la pagina privada de Lucas
		PO_View.checkElement(driver, "text", "Usuarios");

		PO_InvitationView.clickEnlace(driver, "Pelayo");

		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pedro@email.com", "123456");
		// Comprobamos que entramos en la pagina privada de Pedro
		PO_View.checkElement(driver, "text", "Usuarios");

		PO_InvitationView.clickEnlace(driver, "Pelayo");

		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "marta@email.com", "123456");
		// Comprobamos que entramos en la pagina privada de Marta
		PO_View.checkElement(driver, "text", "Usuarios");

		PO_InvitationView.clickEnlace(driver, "Pelayo");

		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");

		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@email.com", "123456");
		// Comprobamos que entramos en la pagina privada de Pelayo
		PO_View.checkElement(driver, "text", "Usuarios");

		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'invitation-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'/user/invitationsList')]");
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		PO_View.checkElement(driver, "text", "Marta");
		PO_View.checkElement(driver, "text", "Pedro");
		PO_View.checkElement(driver, "text", "Lucas");

	}

	// PR18. Sobre el listado de invitaciones recibidas. Hacer click en el
	// botón/enlace de una de ellas y
	// comprobar que dicha solicitud desaparece del listado de invitaciones
	@Test
	public void PR18() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@email.com", "123456");
		// Comprobamos que entramos en la pagina privada de Pelayo
		PO_View.checkElement(driver, "text", "Usuarios");
		// Aprovechamos las invitaciones creadas anteriormente
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'invitation-menu')]/a");
		elementos.get(0).click();
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'/user/invitationsList')]");
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		// Aceptamos una invitacion
		PO_InvitationView.clickEnlace(driver, "Marta");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		// Comprobamos que la invitación desaparecio
		assertTrue(elementos.size() == 2);
		// Aceptamos otra
		PO_InvitationView.clickEnlace(driver, "Lucas");
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(elementos.size() == 1);

	}

	// PR19. Mostrar el listado de amigos de un usuario. Comprobar que el listado
	// contiene los amigos
	// que deben ser.
	@Test
	public void PR19() {
		// Vamos al formulario de logueo
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "pelayo@email.com", "123456");
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "text", "Usuarios");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/friendsList')]");
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		// Aprovechamos los dos amigos aceptados anteriormente
		assertTrue(elementos.size() == 2);
	}

	// PR20. Visualizar al menos cuatro páginas en Español/Inglés/Español
	// (comprobando que algunas de las etiquetas cambian al idioma correspondiente).
	// Ejemplo, Página principal/Opciones Principales de Usuario/Listado de
	// Usuarios.
	@Test
	public void PR20() {
		// Página principal
		PO_View.checkElement(driver, "text", "Bienvenido/a a la página principal");
		// Cambiamos a inglés
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		PO_View.checkElement(driver, "text", "Welcome to homepage");
		// Cambiamos otra vez a español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		PO_View.checkElement(driver, "text", "Bienvenido/a a la página principal");

		// Página Login
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "Inicia Sesión");
		PO_View.checkElement(driver, "text", "Contraseña:");
		// Cambiamos a inglés
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		PO_View.checkElement(driver, "text", "Log in");
		PO_View.checkElement(driver, "text", "Password:");
		// Cambiamos otra vez a español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		PO_View.checkElement(driver, "text", "Inicia Sesión");
		PO_View.checkElement(driver, "text", "Contraseña:");

		// Iniciamos sesión
		PO_LoginView.fillForm(driver, "pedro@email.com", "123456");

		// Página Ver Usuarios
		PO_View.checkElement(driver, "text", "Usuarios");
		PO_View.checkElement(driver, "text", "Nombre");
		PO_View.checkElement(driver, "text", "Apellidos");
		// Cambiamos a inglés
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		PO_View.checkElement(driver, "text", "Users");
		PO_View.checkElement(driver, "text", "Name");
		PO_View.checkElement(driver, "text", "Surname");
		// Cambiamos otra vez a español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		PO_View.checkElement(driver, "text", "Usuarios");
		PO_View.checkElement(driver, "text", "Nombre");
		PO_View.checkElement(driver, "text", "Apellidos");

		// Página Crear Publicación
		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_View.checkElement(driver, "text", "Título:");
		PO_View.checkElement(driver, "text", "Texto:");
		// Cambiamos a inglés
		PO_HomeView.changeIdiom(driver, "btnEnglish");
		PO_View.checkElement(driver, "text", "Title:");
		PO_View.checkElement(driver, "text", "Text:");
		// Cambiamos otra vez a español
		PO_HomeView.changeIdiom(driver, "btnSpanish");
		PO_View.checkElement(driver, "text", "Usuarios");
		PO_View.checkElement(driver, "text", "Título:");
		PO_View.checkElement(driver, "text", "Texto:");

	}

	// PR21. Intentar acceder sin estar autenticado a la opción de listado de
	// usuarios. Se deberá volver al formulario de login.
	@Test
	public void PR21() {
		// Intentamos ir a Ver Usuarios
		PO_HomeView.clickOption(driver, "user/list", "class", "btn btn-primary");
		// Redirige a Login
		PO_View.checkElement(driver, "text", "Inicia Sesión");
	}

	// PR22. Intentar acceder sin estar autenticado a la opción de listado de
	// publicaciones de un usuario estándar. Se deberá volver al formulario de
	// login.
	@Test
	public void PR22() {
		// reset? logout?
		// Intentamos ir a Ver Usuarios
		PO_HomeView.clickOption(driver, "post/list", "class", "btn btn-primary");
		// Redirige a Login
		PO_View.checkElement(driver, "text", "Inicia Sesión");
	}

	// PR23. Estando autenticado como usuario estándar intentar acceder a una opción
	// disponible solo para usuarios administradores (Se puede añadir una opción
	// cualquiera en el menú). Se deberá indicar un mensaje de acción prohibida.
	@Test
	public void PR23() {
		// Iniciamos sesión como usuario estándar
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "pedro@email.com", "123456");
		// Intentamos acceder a la opción Administrar
		// PO_HomeView.clickOption(driver, "admin", "class", "btn btn-primary");
		PO_PrivateView.clickAdminOption(driver, "admin", "class", "btn btn-primary");
		// Obtenemos un error
		SeleniumUtils.textoPresentePagina(driver, "HTTP Status 403 – Forbidden");
	}

	// PR24. Ir al formulario crear publicaciones, rellenarla con datos válidos y
	// pulsar el botón Submit. Comprobar que la publicación sale en el listado de
	// publicaciones de dicho usuario.
	@Test
	public void PR24() { // mejorar
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "pedro@email.com", "123456");

		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_PostView.fillForm(driver, "Mi primer post", "Hola Mundo!");

		SeleniumUtils.textoPresentePagina(driver, "Mis publicaciones");
		SeleniumUtils.textoPresentePagina(driver, "Mi primer post");
	}

	// PR25. Ir al formulario de crear publicaciones, rellenarla con datos inválidos
	// (campo título vacío) y pulsar el botón Submit. Comprobar que se muestra el
	// mensaje de campo obligatorio.
	@Test
	public void PR25() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "pedro@email.com", "123456");

		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_PostView.fillForm(driver, " ", "Hola Mundo!");
		PO_View.getP();
		PO_PostView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());

		PO_PostView.fillForm(driver, "Mi primer post", " ");
		PO_PostView.checkKey(driver, "Error.post.text.length", PO_Properties.getSPANISH());

	}

	// PR26. Mostrar el listado de publicaciones de un usuario y comprobar que se
	// muestran todas las que existen para dicho usuario.
	@Test
	public void PR26() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "pedro@email.com", "123456");

		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_PostView.fillForm(driver, "Mi primer post: hola mundo", "Hola, Mundo!");
		// Redirige a la lista de publicaciones
		SeleniumUtils.textoPresentePagina(driver, "Mi primer post: hola mundo");

		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_PostView.fillForm(driver, "Mi segundo post: hola mundo", "Hola, Mundo!");
		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_PostView.fillForm(driver, "Mi tercer post: hola mundo", "Hola, Mundo!");

		SeleniumUtils.textoPresentePagina(driver, "Mi primer post: hola mundo");
		SeleniumUtils.textoPresentePagina(driver, "Mi segundo post: hola mundo");
		SeleniumUtils.textoPresentePagina(driver, "Mi tercer post: hola mundo");

	}
	
	// PR27. Mostrar el listado de publicaciones de un usuario amigo y comprobar que se muestran todas
	//las que existen para dicho usuario
	@Test
	public void PR27() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "pelayo@email.com", "123456");
		//Creamos varios post
		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_PostView.fillForm(driver, "Mi primer post: hola mundo", "Hola, Mundo!");
		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_PostView.fillForm(driver, "Mi segundo post: hola mundo", "Hola, Mundo!");
		PO_HomeView.clickOption(driver, "post/add", "class", "btn btn-primary");
		PO_PostView.fillForm(driver, "Mi tercer post: hola mundo", "Hola, Mundo!");
		//Nos desconectamos
		PO_NavView.clickOption(driver, "logout", "class", "btn btn-primary");
		//Vamos al formulario de logueo con un amigo creado anteriormente
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "lucas@email.com", "123456");
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/friendsList')]");
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		PO_PrivateView.clickLink(driver, "/post/friendList/pelayo@email.com");
		//elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		SeleniumUtils.textoPresentePagina(driver, "Mi primer post: hola mundo");
		SeleniumUtils.textoPresentePagina(driver, "Mi segundo post: hola mundo");
		SeleniumUtils.textoPresentePagina(driver, "Mi tercer post: hola mundo");
		
	}
	
	
	//PR28. Utilizando un acceso vía URL u otra alternativa, tratar de listar las publicaciones de un
	//usuario que no sea amigo del usuario identificado en sesión. Comprobar que el sistema da un error de
	//autorización
	@Test
	public void PR28() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "marta@email.com", "123456");
		String URLFallo = "http://localhost:8080/post/friendList/maria@email.com";
		driver.navigate().to(URLFallo);
		
		SeleniumUtils.textoPresentePagina(driver, "No eres amig@ de este usuario");
	}
	

	@Test
	// P231. Mostrar el listado de usuarios y comprobar que se muestran todos los
	// que existen en el sistema.
	public void PR31() {
		// Accedemos automáticamente después de iniciar sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		SeleniumUtils.textoPresentePagina(driver, "pedro@email.com");
		SeleniumUtils.textoPresentePagina(driver, "lucas@email.com");
		SeleniumUtils.textoPresentePagina(driver, "maria@email.com");
		SeleniumUtils.textoPresentePagina(driver, "marta@email.com");
		SeleniumUtils.textoPresentePagina(driver, "pelayo@email.com");

		PO_PrivateView.clickLink(driver, "?page=1");
		SeleniumUtils.textoPresentePagina(driver, "lucia@email.com");

	}
	
	//PR32. Ir a la lista de usuarios, borrar el primer usuario de la lista, comprobar que la lista se actualiza
		//y dicho usuario desaparece
		@Test
		public void PR32() {
			
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "admin");
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/list')]");
			elementos.get(0).click();
			elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
			assertTrue(elementos.get(0).getText().contains("Pedro"));
			PO_PrivateView.clickLink(driver, "/user/delete/1");
			List<WebElement> elementos2 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
			//Comprobamos que ha desaparecido el usuario
			assertFalse(elementos2.get(0).getText().contains("Pedro"));
		}
		
		//PR33. Ir a la lista de usuarios, borrar el último usuario de la lista, comprobar que la lista se actualiza
		//y dicho usuario desaparece.
		@Test
		public void PR33() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "admin");
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/list')]");
			elementos.get(0).click();
			elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
			assertTrue(elementos.size()==5);
			//Comprobamos que la usuaria de la ultima posicion se llama Lucia
			assertTrue(elementos.get(elementos.size()-1).getText().contains("lucia@email.com"));
			//Ponemos +1 ya que hacemos el borrado según el id y en el apartado anterior hemos eliminado un usuario, además de que los ids empiezan desde 1.
			PO_PrivateView.clickLink(driver, "/user/delete/" +  Integer.toString(elementos.size() +1) );
			List<WebElement> elementos2 = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
			assertTrue(elementos2.size() ==4);
			//Comprobamos que ya no está la usuaria
			SeleniumUtils.textoNoPresentePagina(driver, "lucia@email.com");
		}
		
		//PR34. Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la lista se actualiza y dichos
		//usuarios desaparecen
		@Test
		public void PR34() {
			PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
			PO_LoginView.fillForm(driver, "admin@email.com", "admin");
			List<WebElement> elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, '/user/list')]");
			elementos.get(0).click();
			//Seleccionamos los tres usuarios a borrar
			//Comprobamos que estan los usuarios:
			SeleniumUtils.textoPresentePagina(driver, "lucas@email.com");
			SeleniumUtils.textoPresentePagina(driver, "maria@email.com");
			SeleniumUtils.textoPresentePagina(driver, "marta@email.com");
			WebElement usuario1 = driver.findElement(By.id("2"));
			usuario1.click();
			WebElement usuario2 = driver.findElement(By.id("3"));
			usuario2.click();
			WebElement usuario3 = driver.findElement(By.id("4"));
			usuario3.click();
			WebElement boton = driver.findElement(By.id("botonEliminar"));
			boton.click();
			//Comprobamos que ya no están l@s usuari@s
			SeleniumUtils.textoNoPresentePagina(driver, "lucas@email.com");
			SeleniumUtils.textoNoPresentePagina(driver, "maria@email.com");
			SeleniumUtils.textoNoPresentePagina(driver, "marta@email.com");
			
			
			
			
			//elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
			
		}
		

}
