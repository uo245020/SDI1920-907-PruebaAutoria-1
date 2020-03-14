package com.uniovi.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PostView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaneitorTests {
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\Users\\nutba\\Desktop\\Universidad\\3º\\SECOND SEMESTER\\SDI\\LAB\\lab05\\OneDrive_2020-03-02\\PL-SDI-Sesio╠ün5-material\\geckodriver024win64.exe";
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

//	// PR01. Registro de Usuario con datos válidos.
//	@Test
//	public void PR01() {
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		PO_RegisterView.fillForm(driver, "josefo@gmail.com", "Josefo", "Perez", "77777", "77777");
//
//		PO_View.checkElement(driver, "text", "Bienvenido/a a la página principal");
//	}
//
//	// PRO2. Registro de Usuario con datos inválidos (email vacío, nombre vacío,
//	// apellidos vacíos).
//	@Test
//	public void PR02() {
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, " ", "Josefo", "Perez", "77777", "77777");
//		PO_View.getP(); // Email vacío
//		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "josefo@yahoo.com", " ", "Perez", "77777", "77777");
//		// Nombre vacío
//		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "josefo@hotmail.com", "Josefo", " ", "77777", "77777");
//		// Apellidos vacíos
//		PO_RegisterView.checkKey(driver, "Error.signup.lastName.length", PO_Properties.getSPANISH());
//	}
//
//	// PR03. Registro de Usuario con datos inválidos (repetición de contraseña
//	// inválida).
//	@Test
//	public void PR03() {
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "jose@gmail.com", "Josefo", "Perez", "77777", "7777");
//		PO_View.getP(); // Email vacío
//		PO_RegisterView.checkKey(driver, "Error.signup.passwordConfirm.coincidence", PO_Properties.getSPANISH());
//	}
//
//	// PRO4. Registro de Usuario con datos inválidos (email existente).
//	@Test
//	public void PR04() {
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//		// Rellenamos el formulario.
//		PO_RegisterView.fillForm(driver, "josefo@gmail.com", "Josefo", "Perez", "77777", "77777");
//		PO_View.getP(); // Email vacío
//		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
//	}
//
//	// PRO5. Inicio de sesión con datos válidos (administrador).
//	@Test
//	public void PR05() {
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		PO_LoginView.fillForm(driver, "admin@email.com", "admin"); 
//		
//		PO_View.checkElement(driver, "text", "Usuarios");
//	}
//
//	// PRO6. Inicio de sesión con datos válidos (usuario estándar).
//	@Test
//	public void PR06() {
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); 
//		PO_LoginView.fillForm(driver, "99999990A", "123456"); 
//		
//		PO_View.checkElement(driver, "text", "Usuarios");
//	}

//	// PRO7. Inicio de sesión con datos inválidos (usuario estándar, campo email y contraseña vacíos).
//	@Test
//	public void PR07() { 
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); 
//		PO_LoginView.fillForm(driver, " ", "123456"); 
//		PO_View.getP(); 
//		// Email vacío
//		PO_RegisterView.checkKey(driver, "Error.login.message", PO_Properties.getSPANISH());
//		PO_LoginView.fillForm(driver, "99999990A", " "); 
//		// Contraseña vacía
//		PO_RegisterView.checkKey(driver, "Error.login.message", PO_Properties.getSPANISH());
//	}
//
//	// PRO8. Inicio de sesión con datos válidos (usuario estándar, email existente, pero contraseña incorrecta).
//	@Test 
//	public void PR08() { 
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); 
//		PO_LoginView.fillForm(driver, "99999990A", "12345"); 
//		PO_View.getP(); 
//		// Contraseña incorrecta
//		PO_RegisterView.checkKey(driver, "Error.login.message", PO_Properties.getSPANISH());
//	}
//	
//	// PRO9. Hacer click en la opción de salir de sesión y comprobar que se redirige a la página de inicio de sesión (Login).
//	@Test
//	public void PR09() {
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); 
//		PO_LoginView.fillForm(driver, "admin@email.com", "admin"); 
//		
//		PO_PrivateView.clickOption(driver, "logout", "text", "Inicia Sesión");
//		//PO_View.checkElement(driver, "text", "Usuarios");
//	}
//
//	// PR10. Comprobar que el botón cerrar sesión no está visible si el usuario no está autenticado.
//	@Test
//	public void PR10() {
//		SeleniumUtils.textoNoPresentePagina(driver, "Cerrar Sesión");
//	}

	// PR11. Mostrar el listado de usuarios y comprobar que se muestran todos los
	// que existen en el sistema.
//	@Test
//	public void PR11() {
//		//Accedemos automáticamente después de iniciar sesión
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
//		
//		//PO_PrivateView.checkUserList(driver, "99999990A", "99999991B", "99999992C", "99999993D", "99999977E"); corregir
//		SeleniumUtils.textoPresentePagina(driver, "99999990A");
//		SeleniumUtils.textoPresentePagina(driver, "99999991B");
//		SeleniumUtils.textoPresentePagina(driver, "99999992C");
//		SeleniumUtils.textoPresentePagina(driver, "99999993D");
//		SeleniumUtils.textoPresentePagina(driver, "99999977E");
//		
//		// Segunda Página
//		PO_PrivateView.clickLink(driver, "?page=1");
//		SeleniumUtils.textoPresentePagina(driver, "99999994E");
//	}
//
//	// PR12. Hacer una búsqueda con el campo vacío y comprobar que se muestra la
//	// página que corresponde
//	// con el listado usuarios existentes en el sistema.
//	@Test
//	public void PR12() {
//		//Iniciamos sesión
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
//		// Introducimos el texto de búsqueda
//		PO_PrivateView.fillSearch(driver, "");
//		// Comprobamos que aparecen todos los usuarios
//		SeleniumUtils.textoPresentePagina(driver, "99999990A");
//		SeleniumUtils.textoPresentePagina(driver, "99999991B");
//		SeleniumUtils.textoPresentePagina(driver, "99999992C");
//		SeleniumUtils.textoPresentePagina(driver, "99999993D");
//		SeleniumUtils.textoPresentePagina(driver, "99999977E");
//		
//		PO_PrivateView.clickLink(driver, "?page=1");
//		SeleniumUtils.textoPresentePagina(driver, "99999994E");
//	}
//
//	// PR13. Hacer una búsqueda escribiendo en el campo un texto que no exista y
//	// comprobar que se muestra la página que corresponde, con la lista de usuarios
//	// vacía.
//	@Test
//	public void PR13() { // mejorar
//		//Iniciamos sesión
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		PO_LoginView.fillForm(driver, "admin@email.com", "admin");
//		// Introducimos el texto de búsqueda
//		PO_PrivateView.fillSearch(driver, "me");
//		// Comprobamos que no aparece ningún usuario
//		SeleniumUtils.textoNoPresentePagina(driver, "99999990A");
//		SeleniumUtils.textoNoPresentePagina(driver, "99999991B");
//		SeleniumUtils.textoNoPresentePagina(driver, "99999992C");
//		SeleniumUtils.textoNoPresentePagina(driver, "99999993D");
//		SeleniumUtils.textoNoPresentePagina(driver, "99999977E");
//		SeleniumUtils.textoNoPresentePagina(driver, "99999994E");
//	}
//
//	// PR14. Hacer una búsqueda con un texto específico y comprobar que se muestra
//	// la página que corresponde, con la lista de usuarios en los que el texto
//	// especificados sea parte de su nombre, apellidos o de su email.
//	@Test
//	public void PR14() {
//		//Iniciamos sesión
//				PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//				PO_LoginView.fillForm(driver, "admin@email.com", "admin");
//				// Introducimos el texto de búsqueda
//				PO_PrivateView.fillSearch(driver, "lu");
//				
//				SeleniumUtils.textoPresentePagina(driver, "99999991B");
//				SeleniumUtils.textoPresentePagina(driver, "99999994E");
//	}
//	
	// PR24. Ir al formulario crear publicaciones, rellenarla con datos válidos y
	// pulsar el botón Submit. Comprobar que la publicación sale en el listado de
	// publicaciones de dicho usuario.
	@Test
	public void PR24() { // mejorar
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "99999990A", "123456");

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
		PO_LoginView.fillForm(driver, "99999990A", "123456");

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
		PO_LoginView.fillForm(driver, "99999990A", "123456");

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

	@Test
	// P231. Mostrar el listado de usuarios y comprobar que se muestran todos los
	// que existen en el sistema.
	public void PR31() {
		// Accedemos automáticamente después de iniciar sesión
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "admin@email.com", "admin");

		SeleniumUtils.textoPresentePagina(driver, "99999990A");
		SeleniumUtils.textoPresentePagina(driver, "99999991B");
		SeleniumUtils.textoPresentePagina(driver, "99999992C");
		SeleniumUtils.textoPresentePagina(driver, "99999993D");
		SeleniumUtils.textoPresentePagina(driver, "99999977E");

		PO_PrivateView.clickLink(driver, "?page=1");
		SeleniumUtils.textoPresentePagina(driver, "99999994E");

	}

}
