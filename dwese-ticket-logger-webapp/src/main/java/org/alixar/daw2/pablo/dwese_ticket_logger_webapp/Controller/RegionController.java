package org.alixar.daw2.pablo.dwese_ticket_logger_webapp.Controller;
import jakarta.validation.Valid;
import org.alixar.daw2.pablo.dwese_ticket_logger_webapp.dao.RegionDAO;
import org.alixar.daw2.pablo.dwese_ticket_logger_webapp.entity.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

/**
 * Controlador que maneja las operaciones CRUD para la entidad `Region`.
 * Utiliza `RegionDAO` para interactuar con la base de datos.
 */
@Controller
@RequestMapping("/regions")
public class RegionController {

    private static final Logger logger = LoggerFactory.getLogger(RegionController.class);

    // DAO para gestionar las operaciones de las regiones en la base de datos
    @Autowired
    private RegionDAO regionDAO;

    // Gestiona los mensajes de messages_xx.properties desde el backend
    @Autowired
    private MessageSource messageSource;

    /**
     * Lista todas las regiones y las pasa como atributo al modelo para que sean
     * accesibles en la vista `region.html`.
     *
     * @param model Objeto del modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para renderizar la lista de regiones.
     */
    @GetMapping
    public String listRegions(Model model) {
        logger.info("Solicitando la lista de todas las regiones...");
        List<Region> listRegions = null;
        try {
            listRegions = regionDAO.listAllRegions();
            logger.info("Se han cargado {} regiones.", listRegions.size());
        } catch (DataAccessException e) {
            logger.error("Error al listar las regiones: {}", e.getMessage());
            model.addAttribute("errorMessage", "Error al listar las regiones.");
        }
        model.addAttribute("listRegions", listRegions); // Pasar la lista de regiones al modelo
        return "regions/region"; // Nombre de la plantilla Thymeleaf a renderizar
    }

    /**
     * Muestra el formulario para crear una nueva región.
     *
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva región.");
        model.addAttribute("region", new Region()); // Crear un nuevo objeto Region
        return "regions/region-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    /**
     * Muestra el formulario para editar una región existente.
     *
     * @param id    ID de la región a editar.
     * @param model Modelo para pasar datos a la vista.
     * @return El nombre de la plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model,
                               RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Mostrando formulario de edición para la región con ID {}", id);
        try {
            Region region = regionDAO.getRegionById(id);
            if (region == null) {
                // Si no existe, se vuelve al listado con un aviso (la vista no puede trabajar con una región null)
                logger.warn("No se encontró la región con ID {}", id);
                String errorMessage = messageSource.getMessage("msg.region-controller.edit.notFound", null, locale);
                redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
                return "redirect:/regions";
            }
            model.addAttribute("region", region);
        } catch (DataAccessException e) {
            logger.error("Error al obtener la región con ID {}: {}", id, e.getMessage());
            String errorMessage = messageSource.getMessage("msg.region-controller.edit.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/regions";
        }
        return "regions/region-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    /**
     * Inserta una nueva región en la base de datos.
     *
     * @param region             Objeto que contiene los datos del formulario (validado con @Valid).
     * @param result             Resultado de la validación: errores de cada campo.
     * @param redirectAttributes Atributos para mensajes flash de redirección.
     * @param locale             Idioma de la petición, para traducir los mensajes.
     * @return Redirección a la lista de regiones.
     */
    @PostMapping("/insert")
    public String insertRegion(@Valid @ModelAttribute("region") Region region,
                               BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Insertando nueva región con código {}", region.getCode());
        try {
            if (result.hasErrors()) {
                return "regions/region-form"; // Devuelve el formulario para mostrar los errores de validación
            }
            if (regionDAO.existsRegionByCode(region.getCode())) {
                logger.warn("El código de la región {} ya existe.", region.getCode());
                // Se añade el error al campo "code": se muestra junto al campo y se conservan los datos escritos
                result.rejectValue("code", "msg.region-controller.insert.codeExist");
                return "regions/region-form";
            }
            regionDAO.insertRegion(region);
            logger.info("Región {} insertada con éxito.", region.getCode());
        } catch (DataAccessException e) {
            logger.error("Error al insertar la región {}: {}", region.getCode(), e.getMessage());
            String errorMessage = messageSource.getMessage("msg.region-controller.insert.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/regions"; // Redirigir a la lista de regiones
    }

    /**
     * Actualiza una región existente en la base de datos.
     *
     * @param region             Objeto que contiene los datos del formulario (validado con @Valid).
     * @param result             Resultado de la validación: errores de cada campo.
     * @param redirectAttributes Atributos para mensajes flash de redirección.
     * @param locale             Idioma de la petición, para traducir los mensajes.
     * @return Redirección a la lista de regiones.
     */
    @PostMapping("/update")
    public String updateRegion(@Valid @ModelAttribute("region") Region region, BindingResult result, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Actualizando región con ID {}", region.getId());
        try {
            if (result.hasErrors()) {
                return "regions/region-form"; // Devuelve el formulario para mostrar los errores de validación
            }
            if (regionDAO.existsRegionByCodeAndNotId(region.getCode(), region.getId())) {
                logger.warn("El código de la región {} ya existe para otra región.", region.getCode());
                // Se añade el error al campo "code": se muestra junto al campo y se conservan los datos escritos
                result.rejectValue("code", "msg.region-controller.update.codeExist");
                return "regions/region-form";
            }
            regionDAO.updateRegion(region);
            logger.info("Región con ID {} actualizada con éxito.", region.getId());
        } catch (DataAccessException e) {
            logger.error("Error al actualizar la región con ID {}: {}", region.getId(), e.getMessage());
            String errorMessage = messageSource.getMessage("msg.region-controller.update.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/regions"; // Redirigir a la lista de regiones
    }

    /**
     * Elimina una región de la base de datos.
     *
     * @param id                 ID de la región a eliminar.
     * @param redirectAttributes Atributos para mensajes flash de redirección.
     * @param locale             Idioma de la petición, para traducir los mensajes.
     * @return Redirección a la lista de regiones.
     */
    @PostMapping("/delete")
    public String deleteRegion(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Eliminando región con ID {}", id);
        try {
            regionDAO.deleteRegion(id);
            logger.info("Región con ID {} eliminada con éxito.", id);
        } catch (DataIntegrityViolationException e) {
            // La clave ajena de provinces impide borrar una región que tiene provincias
            logger.warn("No se puede eliminar la región con ID {}: tiene provincias asociadas.", id);
            String errorMessage = messageSource.getMessage("msg.region-controller.delete.hasProvinces", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        } catch (DataAccessException e) {
            logger.error("Error al eliminar la región con ID {}: {}", id, e.getMessage());
            String errorMessage = messageSource.getMessage("msg.region-controller.delete.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
        }
        return "redirect:/regions"; // Redirigir a la lista de regiones
    }
}
