package application.device;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepo;
    @Autowired
    DeviceDataRepository deviceDataRepo;

    @GetMapping
    public Iterable<Device> getAllDevices() {
        return deviceRepo.findAll();
    }

    @GetMapping("/{id}")
    public Device getOneDevice(@PathVariable String id) {
        Optional<Device> result = deviceRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Device Not Found"
            );
        }
        return result.get();
    }
    @PostMapping
    public Device insertOneDevice(@RequestBody Device device) {
        if(deviceRepo.existsById(device.getId())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Device Already Registered"
            );
        }
        return deviceRepo.save(device);
    }

    @GetMapping("/{id}/data")
    public Iterable<DeviceData> getAllDeviceData(@PathVariable String id) {
        Optional<Device> result = deviceRepo.findById(id);
        if(result.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Device Not Found"
            );
        }
        return deviceDataRepo.findByDevice(result.get());
    }
    
    @PostMapping("/{id}/data")
    public DeviceData insertOneDeviceData(@PathVariable String id, @RequestBody DeviceData deviceData) {
        if(!deviceRepo.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Device Not Found"
            );
        }
        return deviceDataRepo.save(deviceData);
    }
}
