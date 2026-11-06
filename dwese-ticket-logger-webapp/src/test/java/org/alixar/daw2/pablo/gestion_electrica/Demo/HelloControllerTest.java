package org.alixar.daw2.pablo.gestion_electrica.Demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(HelloController.class) // Esta anotación indica que estamosprobando solo el controlador HelloController
public class HelloControllerTest {
    // Inyecta MockMvc para realizar peticiones simuladas
    @Autowired

    private MockMvc mockMvc;
    /**
     * Test para el endpoint /hello sin pasar el parámetro 'name'.
     * Debe devolver "Hello World!".
     */
    @Test
    public void testHelloDefault() throws Exception {
        // Realiza una petición GET a /hello sin pasar el parámetro 'name'
        mockMvc.perform(get("/hello")).andExpect(status().isOk()) // Espera que el estado de la respuesta sea 200 (OK)
                .andExpect(content().string("Hello World!")); // Espera que elcontenido de la respuesta sea "Hello World!"
    }
    /**
     * Test para el endpoint /hello pasando un parámetro 'name'.
     * Debe devolver "Hello <name>!".
     */
    @Test
    public void testHelloWithName() throws Exception {
        // Realiza una petición GET a /hello con el parámetro 'name'
        mockMvc.perform(get("/hello").param("name", "prof.albertocanca"))
                .andExpect(status().isOk()) // Espera que el estado de la respuesta sea 200 (OK)
                .andExpect(content().string("Hello prof.albertocanca!")); //Espera que el contenido de la respuesta sea "Hello Alberto!"
    }
}

