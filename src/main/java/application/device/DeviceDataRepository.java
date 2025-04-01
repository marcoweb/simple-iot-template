package application.device;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {
    public Iterable<DeviceData> findByDevice(Device device);
}
