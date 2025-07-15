package com.crisdevApps.Nebra.dto.outputDto;

import com.crisdevApps.Nebra.model.Coordinate;
import com.crisdevApps.Nebra.model.Schedule;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;

import java.util.ArrayList;
import java.util.List;

public record BasicNegocioDTO(
        String codigoNegocio,
        String nombre,
        String descripcion,
        List<String> imagenes,
        ArrayList<String> telefonos,
        BusinessCategory tipoNegocio,
        Coordinate ubicacion,
        ArrayList<Schedule> schedules,
        String idUsuario

) {

}
