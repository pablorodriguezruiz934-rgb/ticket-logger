package org.alixar.daw2.pablo.dwese_ticket_logger_webapp.Controller;

import jakarta.validation.Valid;
import org.alixar.daw2.pablo.dwese_ticket_logger_webapp.dao.ProvinceDAO;
import org.alixar.daw2.pablo.dwese_ticket_logger_webapp.dao.RegionDAO;
import org.alixar.daw2.pablo.dwese_ticket_logger_webapp.entity.Province;
import org.alixar.daw2.pablo.dwese_ticket_logger_webapp.entity.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;

/**
 * Controlador que maneja las operaciones CRUD para la entidad `Province`.
 * Usa `ProvinceDAO` para las provincias y `RegionDAO` para rellenar el desplegable de regiones.
 */
@Controller
@RequestMapping("/provinces")
public class ProvinceController {

    private static final Logger logger = LoggerFactory.getLogger(ProvinceController.class);

    private final ProvinceDAO provinceDAO;
    private final RegionDAO regionDAO;
    private final MessageSource messageSource;

    // Inyección por constructor
    public ProvinceController(ProvinceDAO provinceDAO, RegionDAO regionDAO, MessageSource messageSource) {
        this.provinceDAO = provinceDAO;
        this.regionDAO = regionDAO;
        this.messageSource = messageSource;
    }

    /**
     * Lista todas las provincias.
     */
    @GetMapping
    public String listProvinces(Model model, Locale locale) {
        logger.info("Solicitando la lista de todas las provincias...");
        List<Province> listProvinces = null;
        try {
            listProvinces = provinceDAO.listAllProvinces();
            logger.info("Se han cargado {} provincias.", listProvinces.size());
        } catch (DataAccessException e) {
            logger.error("Error al listar las provincias: {}", e.getMessage());
            model.addAttribute("errorMessage", messageSource.getMessage("msg.province-controller.list.error", null, locale));
        }
        model.addAttribute("listProvinces", listProvinces);
        return "provinces/province";
    }

    /**
     * Muestra el formulario para crear una nueva provincia.
     */
    @GetMapping("/new")
    public String showNewForm(Model model, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Mostrando formulario para nueva provincia.");
        Province province = new Province();
        province.setRegion(new Region()); // Región vacía para que el formulario pueda enlazar region.id
        model.addAttribute("province", province);
        return loadRegionsAndShowForm(model, redirectAttributes, locale);
    }

    /**
     * Muestra el formulario para editar una provincia existente.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") int id, Model model,
                               RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Mostrando formulario de edición para la provincia con ID {}", id);
        try {
            Province province = provinceDAO.getProvinceById(id);
            if (province == null) {
                logger.warn("No se encontró la provincia con ID {}", id);
                redirectAttributes.addFlashAttribute("errorMessage",
                        messageSource.getMessage("msg.province-controller.edit.notFound", null, locale));
                return "redirect:/provinces";
            }
            model.addAttribute("province", province);
        } catch (DataAccessException e) {
            logger.error("Error al obtener la provincia con ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.province-controller.edit.error", null, locale));
            return "redirect:/provinces";
        }
        return loadRegionsAndShowForm(model, redirectAttributes, locale);
    }

    /**
     * Inserta una nueva provincia en la base de datos.
     */
    @PostMapping("/insert")
    public String insertProvince(@Valid @ModelAttribute("province") Province province, BindingResult result,
                                 Model model, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Insertando nueva provincia con código {}", province.getCode());
        try {
            checkRegionSelected(province, result);
            if (result.hasErrors()) {
                return loadRegionsAndShowForm(model, redirectAttributes, locale);
            }
            if (provinceDAO.existsProvinceByCode(province.getCode())) {
                logger.warn("El código de la provincia {} ya existe.", province.getCode());
                result.rejectValue("code", "msg.province-controller.insert.codeExist");
                return loadRegionsAndShowForm(model, redirectAttributes, locale);
            }
            provinceDAO.insertProvince(province);
            logger.info("Provincia {} insertada con éxito.", province.getCode());
        } catch (DataAccessException e) {
            logger.error("Error al insertar la provincia {}: {}", province.getCode(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.province-controller.insert.error", null, locale));
        }
        return "redirect:/provinces";
    }

    /**
     * Actualiza una provincia existente en la base de datos.
     */
    @PostMapping("/update")
    public String updateProvince(@Valid @ModelAttribute("province") Province province, BindingResult result,
                                 Model model, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Actualizando provincia con ID {}", province.getId());
        try {
            checkRegionSelected(province, result);
            if (result.hasErrors()) {
                return loadRegionsAndShowForm(model, redirectAttributes, locale);
            }
            if (provinceDAO.existsProvinceByCodeAndNotId(province.getCode(), province.getId())) {
                logger.warn("El código de la provincia {} ya existe para otra provincia.", province.getCode());
                result.rejectValue("code", "msg.province-controller.update.codeExist");
                return loadRegionsAndShowForm(model, redirectAttributes, locale);
            }
            provinceDAO.updateProvince(province);
            logger.info("Provincia con ID {} actualizada con éxito.", province.getId());
        } catch (DataAccessException e) {
            logger.error("Error al actualizar la provincia con ID {}: {}", province.getId(), e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.province-controller.update.error", null, locale));
        }
        return "redirect:/provinces";
    }

    /**
     * Elimina una provincia de la base de datos.
     */
    @PostMapping("/delete")
    public String deleteProvince(@RequestParam("id") int id, RedirectAttributes redirectAttributes, Locale locale) {
        logger.info("Eliminando provincia con ID {}", id);
        try {
            provinceDAO.deleteProvince(id);
            logger.info("Provincia con ID {} eliminada con éxito.", id);
        } catch (DataAccessException e) {
            logger.error("Error al eliminar la provincia con ID {}: {}", id, e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.province-controller.delete.error", null, locale));
        }
        return "redirect:/provinces";
    }

    /**
     * Comprueba que en el formulario se ha elegido una región.
     * Si se deja la opción "-- Selecciona una región --", llega una Region con id null.
     */
    private void checkRegionSelected(Province province, BindingResult result) {
        if (province.getRegion() == null || province.getRegion().getId() == null) {
            result.rejectValue("region.id", "msg.province.region.notNull");
        }
    }

    /**
     * Carga las regiones para el desplegable y devuelve la vista del formulario.
     * Si no se pueden cargar, vuelve al listado con un mensaje de error.
     */
    private String loadRegionsAndShowForm(Model model, RedirectAttributes redirectAttributes, Locale locale) {
        try {
            model.addAttribute("listRegions", regionDAO.listAllRegions());
            return "provinces/province-form";
        } catch (DataAccessException e) {
            logger.error("Error al cargar las regiones para el formulario: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.province-controller.regions.error", null, locale));
            return "redirect:/provinces";
        }
    }
}
