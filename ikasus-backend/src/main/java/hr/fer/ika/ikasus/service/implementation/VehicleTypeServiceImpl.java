package hr.fer.ika.ikasus.service.implementation;

import hr.fer.ika.ikasus.DAO.TipVozila;
import hr.fer.ika.ikasus.DTO.inout.VehicleType;
import hr.fer.ika.ikasus.repository.TipVozilaRepository;
import hr.fer.ika.ikasus.service.VehicleTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Luka Ćurić
 */
@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {
    private final Pattern vehIdPattern;
    private final TipVozilaRepository tipVozilaRepository;

    public VehicleTypeServiceImpl(TipVozilaRepository tipVozilaRepository) {
        this.tipVozilaRepository = tipVozilaRepository;
        this.vehIdPattern = Pattern.compile("[a-zA-Z0-9\\-]+");
    }

    @Override
    public List<VehicleType> getAllVehicleTypes() {
        return this.tipVozilaRepository.findAll().stream()
                .map(vt -> {
                    VehicleType vehType = new VehicleType();
                    vehType.setId(vt.getId());
                    vehType.setTypeName(vt.getNazivtip());
                    vehType.setCategory(vt.getKategorija());

                    return vehType;
                })
                .collect(Collectors.toList());
    }

    @Override
    public VehicleType getVehicleType(String id) {
        if (id == null) {
            return null;
        }

        Optional<TipVozila> vtOpt = this.tipVozilaRepository.findById(id);

        if (vtOpt.isEmpty()) {
            return null;
        }

        TipVozila vt = vtOpt.get();

        VehicleType vehicleType = new VehicleType();
        vehicleType.setId(vt.getId());
        vehicleType.setTypeName(vt.getNazivtip());
        vehicleType.setCategory(vt.getKategorija());

        return vehicleType;
    }

    @Override
    public String createVehicleType(VehicleType vehicleType) {
        if (vehicleType.getId() == null || !this.vehIdPattern.matcher(vehicleType.getId()).matches() ||
                vehicleType.getTypeName() == null || vehicleType.getCategory() == null
        ) {
            return null;
        }

        TipVozila tipVozila = new TipVozila();
        tipVozila.setId(vehicleType.getId());
        tipVozila.setNazivtip(vehicleType.getTypeName());
        tipVozila.setKategorija(vehicleType.getCategory());

        tipVozila = this.tipVozilaRepository.save(tipVozila);

        return tipVozila.getId();
    }

    @Override
    public boolean updateVehicleType(String id, VehicleType vehicleType) {
        if (id == null) {
            return false;
        }

        Optional<TipVozila> vtOpt = this.tipVozilaRepository.findById(id);

        if (vtOpt.isEmpty()) {
            return false;
        }

        TipVozila vt = vtOpt.get();

        if (vehicleType.getTypeName() != null) {
            vt.setNazivtip(vehicleType.getTypeName());
        }

        if (vehicleType.getCategory() != null) {
            vt.setKategorija(vehicleType.getCategory());
        }

        this.tipVozilaRepository.save(vt);

        return true;
    }

    @Override
    public boolean deleteVehicleType(String id) {
        TipVozila vt = new TipVozila();
        vt.setId(id);

        this.tipVozilaRepository.delete(vt);

        return true;
    }
}
